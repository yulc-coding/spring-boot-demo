package org.ylc.frame.springboot.biz.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.ylc.frame.springboot.biz.entity.User;
import org.ylc.frame.springboot.biz.mapper.MenuMapper;
import org.ylc.frame.springboot.biz.mapper.UserMapper;
import org.ylc.frame.springboot.biz.params.LoginArg;
import org.ylc.frame.springboot.biz.service.UserService;
import org.ylc.frame.springboot.biz.vo.LoginResponseVO;
import org.ylc.frame.springboot.common.constant.CacheConst;
import org.ylc.frame.springboot.common.tree.MenuTree;
import org.ylc.frame.springboot.common.util.JWTUtils;
import org.ylc.frame.springboot.common.util.PBKDF2;
import org.ylc.frame.springboot.common.util.ParamCheck;
import org.ylc.frame.springboot.common.util.TreeBuildUtil;
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


    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public LoginResponseVO login(LoginArg args) {
        User user = baseMapper.selectOne(
                new QueryWrapper<User>()
                        .select("id,name,password,salt")
                        .eq("username", args.getUsername())
        );
        ParamCheck.notNull(user, "账号或密码错误");
        ParamCheck.isTrue(PBKDF2.verify(args.getPassword(), user.getPassword(), user.getSalt()), "账号或密码错误");

        // 生成token
        JSONObject tokenJson = new JSONObject();
        tokenJson.put("userId", user.getId());
        tokenJson.put("loginFrom", args.getLoginFrom());
        String token = JWTUtils.createJWT(tokenJson);

        // 获取权限列表
        List<MenuTree> menuTrees = menuMapper.getUserMenuList(user.getId(), args.getLoginFrom());
        ParamCheck.notEmptyCollection(menuTrees, "当前账号未授权");
        MenuTree rootTree = null;
        List<MenuTree> childList = new ArrayList<>();
        for (MenuTree menus : menuTrees) {
            if (0 == menus.getId()) {
                rootTree = menus;
            } else {
                childList.add(menus);
            }
        }
        ParamCheck.notNull(rootTree, "无效菜单列表");
        TreeBuildUtil.build(rootTree, childList);

        LoginResponseVO vo = new LoginResponseVO();
        vo.setName(user.getName());
        vo.setToken(token);
        vo.setMenuTree(rootTree);
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
        redisUtils.del(CacheConst.USER_TOKEN_PREFIX + userId + ":" + loginFrom);
        redisUtils.del(CacheConst.USER_PERMISSION_PREFIX + userId + ":" + loginFrom);
    }
}
