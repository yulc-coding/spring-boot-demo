package org.ylc.frame.springboot.common.util;

import org.springframework.util.CollectionUtils;
import org.ylc.frame.springboot.common.tree.BaseTree;

import java.util.ArrayList;
import java.util.List;

/**
 * 代码全万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * 树形结构构造
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2019/10/1 12:53
 */
public class TreeBuildUtil {

    /**
     * 构建单个根目录的树形结构
     *
     * @param parent    父节点
     * @param childList 其他目录
     */
    public static void build(BaseTree parent, List<? extends BaseTree> childList) {
        if (parent == null) {
            return;
        }
        if (CollectionUtils.isEmpty(childList)) {
            return;
        }
        Long rootId = parent.getId();
        // 子类节点
        List<BaseTree> curChild = new ArrayList<>();
        // 其他节点
        List<BaseTree> otherList = new ArrayList<>();

        for (BaseTree entity : childList) {
            if (entity.getPid().equals(rootId)) {
                entity.setPName(parent.getName());
                curChild.add(entity);
            } else {
                otherList.add(entity);
            }
        }
        if (curChild.size() > 0) {
            parent.setChildren(curChild);
            for (BaseTree nextRoot : curChild) {
                // 递归
                build(nextRoot, otherList);
            }
        }
    }

    /**
     * 构建多个根目录的树结构
     *
     * @param rootList   根目录
     * @param branchList 分支目录
     */
    public static void multiRootTreeBuild(List<? extends BaseTree> rootList, List<? extends BaseTree> branchList) {
        if (rootList == null || rootList.size() == 0 || branchList == null || branchList.size() == 0) {
            return;
        }
        for (BaseTree root : rootList) {
            build(root, branchList);
        }
    }

}
