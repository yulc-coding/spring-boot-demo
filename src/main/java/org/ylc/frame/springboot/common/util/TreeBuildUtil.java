package org.ylc.frame.springboot.common.util;

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

    public static void build(BaseTree rootTree, List<? extends BaseTree> childList) {
        Long rootId = rootTree.getId();
        List<BaseTree> curChild = new ArrayList<>();
        List<BaseTree> otherList = new ArrayList<>();

        for (BaseTree entity : childList) {
            if (entity.getPid().equals(rootId)) {
                curChild.add(entity);
            } else {
                otherList.add(entity);
            }
        }
        for (BaseTree nextTree : curChild) {
            build(nextTree, otherList);
        }
        // noinspection unchecked
        rootTree.setChildren(curChild);
    }

}
