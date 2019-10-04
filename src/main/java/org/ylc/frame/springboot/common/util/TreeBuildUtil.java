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
     * 构建树形结构
     *
     * @param root      根目录
     * @param childList 其他目录
     */
    public static void build(BaseTree root, List<? extends BaseTree> childList) {
        if (root == null) {
            return;
        }
        Long rootId = root.getId();
        // 子类节点
        List<BaseTree> curChild = new ArrayList<>();
        // 其他节点
        List<BaseTree> otherList = new ArrayList<>();

        for (BaseTree entity : childList) {
            if (entity.getPid().equals(rootId)) {
                curChild.add(entity);
            } else {
                otherList.add(entity);
            }
        }
        // 子类不为空，递归
        if (CollectionUtils.isEmpty(curChild)) {
            for (BaseTree nextRoot : curChild) {
                build(nextRoot, otherList);
            }
            // noinspection unchecked
            root.setChildren(curChild);
        }
    }

}
