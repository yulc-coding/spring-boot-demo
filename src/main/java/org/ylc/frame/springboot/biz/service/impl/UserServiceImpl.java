package org.ylc.frame.springboot.biz.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.ylc.frame.springboot.biz.dto.UserDTO;
import org.ylc.frame.springboot.biz.entity.Menu;
import org.ylc.frame.springboot.biz.entity.User;
import org.ylc.frame.springboot.biz.entity.UserRole;
import org.ylc.frame.springboot.biz.mapper.MenuMapper;
import org.ylc.frame.springboot.biz.mapper.UserMapper;
import org.ylc.frame.springboot.biz.params.LoginParam;
import org.ylc.frame.springboot.biz.params.UserPageParams;
import org.ylc.frame.springboot.biz.service.CacheService;
import org.ylc.frame.springboot.biz.service.UserRoleService;
import org.ylc.frame.springboot.biz.service.UserService;
import org.ylc.frame.springboot.biz.vo.LoginResponseVO;
import org.ylc.frame.springboot.biz.vo.UserVO;
import org.ylc.frame.springboot.common.constant.CacheConst;
import org.ylc.frame.springboot.common.constant.ConfigConst;
import org.ylc.frame.springboot.common.constant.EnumConst;
import org.ylc.frame.springboot.common.tree.MenuTree;
import org.ylc.frame.springboot.common.util.*;
import org.ylc.frame.springboot.setting.component.redis.RedisUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author CodeGenerator
 * @since 2019-09-26
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final MenuMapper menuMapper;

    private final UserRoleService userRoleService;

    private final RedisUtils redisUtils;

    private final CacheService cacheService;

    @Autowired
    public UserServiceImpl(MenuMapper menuMapper, UserRoleService userRoleService, RedisUtils redisUtils, CacheService cacheService) {
        this.menuMapper = menuMapper;
        this.userRoleService = userRoleService;
        this.redisUtils = redisUtils;
        this.cacheService = cacheService;
    }

    /**
     * 新增用户
     * 账号重复校验
     * 密码加密
     * 姓名缓存
     * 头像缓存
     */
    @Override
    public void addInfo(UserDTO dto) {
        usernameRepeat(dto.getUsername(), null);

        User entity = dto.convertToEntity();
        String salt = PBKDF2.generateSalt();
        entity.setSalt(salt);
        entity.setPassword(PBKDF2.getPBKDF2(ConfigConst.DEFAULT_PWD, salt));
        entity.setState(EnumConst.UserStateEnum.ENABLED.getCode());
        baseMapper.insert(entity);

        redisUtils.set(CacheConst.USER_NAME_PREFIX + entity.getId(), entity.getName());
        if (ParamUtils.notEmpty(entity.getAvatar())) {
            redisUtils.set(CacheConst.USER_AVATAR_PREFIX + entity.getId(), entity.getAvatar());
        }
    }

    @Override
    public void delInfo(long id) {
        OperationCheck.isExecute(baseMapper.deleteById(id), "删除失败");
    }

    /**
     * 更新
     * 账号重复校验
     * 姓名不一致，修改缓存
     * 头像不一致，修改缓存
     */
    @Override
    public void updateInfo(UserDTO dto) {
        usernameRepeat(dto.getUsername(), dto.getId());

        User user = baseMapper.selectById(dto.getId());
        ParamCheck.notNull(user, "无效数据");

        User updateUser = dto.convertToEntity();
        OperationCheck.isExecute(baseMapper.updateById(updateUser), "删除失败");

        if (!user.getName().equals(dto.getName())) {
            redisUtils.set(CacheConst.USER_NAME_PREFIX + updateUser.getId(), updateUser.getName());
        }
        if (ParamUtils.notEmpty(dto.getAvatar()) && dto.getAvatar().equals(user.getAvatar())) {
            redisUtils.set(CacheConst.USER_AVATAR_PREFIX + updateUser.getId(), updateUser.getAvatar());
        }
    }

    @Override
    public IPage<UserVO> pageInfo(UserPageParams page) {
        IPage<User> entityPage = super.page(
                new Page<>(page.getPage(), page.getSize()),
                new QueryWrapper<User>()
                        .like(ParamUtils.notEmpty(page.getName()), "name", page.getName())
                        .eq(ParamUtils.notEmpty(page.getUsername()), "username", page.getUsername())
                        .eq(ParamUtils.notEmpty(page.getDepId()), "dep_id", page.getDepId())
                        .eq(ParamUtils.notEmpty(page.getState()), "state", page.getState())
        );
        List<User> entityList = entityPage.getRecords();
        if (ParamUtils.isEmpty(entityList)) {
            return new Page<>(page.getPage(), page.getSize());
        }
        IPage<UserVO> voPage = new Page<>();
        List<UserVO> voList = new ArrayList<>();
        for (User entity : entityList) {
            voList.add(entityConvertToVo(entity));
        }
        BeanUtils.copyProperties(entityPage, voPage);
        return voPage.setRecords(voList);
    }

    @Override
    public UserDTO getInfoById(long id) {
        User entity = baseMapper.selectById(id);
        UserDTO dto = new UserDTO();
        if (entity != null) {
            BeanUtils.copyProperties(entity, dto);
            dto.setGenderDesc(EnumConst.UserGenderEnum.getValueByCode(entity.getGender()));
            dto.setDepName(cacheService.getDepNameByCode(entity.getDepCode()));
        }
        return dto;
    }

    /**
     * 先删除已有的，再批量新增
     *
     * @param userId 用户ID
     * @param roles  角色列表
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void bindRole(long userId, List<Long> roles) {
        List<UserRole> userRoles = new ArrayList<>();
        for (Long roleId : roles) {
            userRoles.add(new UserRole(userId, roleId));
        }
        userRoleService.remove(new QueryWrapper<UserRole>().eq("user_id", userId));
        userRoleService.saveBatch(userRoles);
    }

    @Override
    public void changePwd(String oldPwd, String newPwd, String repeatPwd) {
        ParamCheck.assertEquals(newPwd, repeatPwd, "新密码输入不一致");

        User user = baseMapper.selectById(ThreadLocalUtils.getUserId());
        ParamCheck.notNull(user, "无效用户");

        ParamCheck.assertTrue(PBKDF2.verify(newPwd, user.getPassword(), user.getSalt()), "原密码错误");

        String newSalt = PBKDF2.generateSalt();
        User updateUser = new User();
        updateUser.setId(user.getId());
        updateUser.setSalt(newSalt);
        updateUser.setPassword(PBKDF2.getPBKDF2(newPwd, newSalt));
        baseMapper.updateById(updateUser);
    }

    /**
     * 登录
     * 1、校验账号密码
     * 2、生成token
     * 3、获取用户权限
     * 4、生成权限树
     * 5、缓存token和权限列表
     */
    @Override
    public LoginResponseVO login(LoginParam args) {
        User user = baseMapper.selectOne(
                new QueryWrapper<User>()
                        .select("id,name,password,salt")
                        .eq("username", args.getUsername())
        );
        ParamCheck.notNull(user, "账号或密码错误");
        ParamCheck.assertTrue(PBKDF2.verify(args.getPassword(), user.getPassword(), user.getSalt()), "账号或密码错误");

        // 生成token
        JSONObject tokenJson = new JSONObject();
        tokenJson.put("userId", user.getId());
        tokenJson.put("loginFrom", args.getLoginFrom());
        String token = JWTUtils.createJWT(tokenJson);

        // 获取权限列表
        List<Menu> menuList = menuMapper.getUserMenuList(user.getId(), args.getLoginFrom());
        ParamCheck.notEmptyCollection(menuList, "当前账号未授权");
        // 树结构
        List<MenuTree> menuTrees = new ArrayList<>();
        // 权限值列表
        List<String> permissions = new ArrayList<>();
        MenuTree menuTree;
        for (Menu menu : menuList) {
            permissions.add(menu.getPermission());
            menuTree = new MenuTree();
            BeanUtils.copyProperties(menu, menuTree);
            menuTrees.add(menuTree);
        }

        // 生成树
        MenuTree menuRootTree = new MenuTree();
        menuRootTree.setChildren(new ArrayList<>());
        menuRootTree.setName("根目录");
        menuRootTree.setId(0L);
        TreeBuildUtil.build(menuRootTree, menuTrees);

        // 将token 和 权限列表 存入redis 缓存
        Long expireTime;
        if (ConfigConst.LOGIN_PC.equals(args.getLoginFrom())) {
            expireTime = ConfigConst.DEFAULT_PC_TOKEN_INVALID_TIME;
        } else {
            expireTime = ConfigConst.DEFAULT_APP_TOKEN_INVALID_TIME;
        }
        redisUtils.set(CacheConst.USER_TOKEN_PREFIX + user.getId() + ":" + args.getLoginFrom(), token, expireTime);
        redisUtils.delete(CacheConst.USER_PERMISSION_PREFIX + user.getId() + ":" + args.getLoginFrom());
        redisUtils.listPushAll(CacheConst.USER_PERMISSION_PREFIX + user.getId() + ":" + args.getLoginFrom(), permissions, expireTime);

        LoginResponseVO vo = new LoginResponseVO();
        vo.setName(user.getName());
        vo.setToken(token);
        vo.setMenuTree(menuRootTree);
        return vo;
    }

    /**
     * 登出
     * 1、解析token
     * 2、清除token
     * 3、清除用户的权限缓存
     *
     * @param token token信息
     */
    @Async
    @Override
    public void logout(String token) {
        if (StringUtils.isEmpty(token)) {
            return;
        }
        JSONObject tokenJson = JWTUtils.parseJWT(token);
        if (tokenJson == null) {
            return;
        }
        String userId = tokenJson.getString("userId");
        String loginFrom = tokenJson.getString("loginFrom");
        if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(loginFrom)) {
            return;
        }
        redisUtils.delete(CacheConst.USER_TOKEN_PREFIX + userId + ":" + loginFrom);
        redisUtils.delete(CacheConst.USER_PERMISSION_PREFIX + userId + ":" + loginFrom);
    }

    @Override
    public void resetPwd(long userId) {
        String salt = PBKDF2.generateSalt();
        String newPwd = PBKDF2.getPBKDF2(ConfigConst.DEFAULT_PWD, salt);
        User user = new User();
        user.setId(userId);
        user.setSalt(salt);
        user.setPassword(newPwd);
        baseMapper.updateById(user);
    }

    /**
     * 校验账号是否重复
     *
     * @param username 账号
     * @param id       如果修改需要传当前ID
     */
    private void usernameRepeat(String username, Long id) {
        int count = baseMapper.selectCount(
                new QueryWrapper<User>()
                        .ne(ParamUtils.notEmpty(id), "id", id)
                        .eq("username", username)
        );
        ParamCheck.assertTrue(count <= 0, "已存在相同的账号");
    }

    /**
     * 实体转换为vo
     */
    private UserVO entityConvertToVo(User entity) {
        UserVO vo = new UserVO();
        if (entity != null) {
            BeanUtils.copyProperties(entity, vo);
            vo.setGender(EnumConst.UserGenderEnum.getValueByCode(entity.getGender()));
            vo.setState(EnumConst.UserStateEnum.getValueByCode(entity.getState()));
            vo.setDepName(cacheService.getDepNameByCode(entity.getDepCode()));
        }
        return vo;
    }


}
