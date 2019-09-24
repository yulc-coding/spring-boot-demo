package org.ylc.frame.springboot.api.setting.component.mongodb.base;

import lombok.Data;

import java.util.Collections;
import java.util.List;

/**
 * 代码千万行，注释第一行，
 * 注释不规范，同事泪两行。
 * <p>
 * 分页
 *
 * @author yulc
 * @version 1.0.0
 * @date 2019/9/24
 */
@Data
public class Pagination<T> {
    /**
     * 一页数据默认20条
     */
    private int pageSize = 20;
    /**
     * 当前页码
     */
    private int curPage = 1;
    /**
     * 上一页
     */
    private int upPage;
    /**
     * 下一页
     */
    private int nextPage;
    /**
     * 一共有多少条数据
     */
    private long totalCount;
    /**
     * 一共有多少页
     */
    private int totalPage;
    /**
     * 数据集合
     */
    private List<T> records = Collections.emptyList();

    /**
     * 获取第一条记录位置
     *
     * @return index
     */
    public int getFirstResult() {
        return (this.curPage - 1) * this.pageSize + 1;
    }

    /**
     * 获取最后记录位置
     *
     * @return index
     */
    public int getLastResult() {
        return this.curPage * this.pageSize;
    }

    /**
     * 计算一共多少页
     */
    private void setTotalPage() {
        this.totalPage = (int) ((this.totalCount % this.pageSize > 0) ? (this.totalCount / this.pageSize + 1) : this.totalCount / this.pageSize);
    }

    /**
     * 设置 上一页
     */
    private void setUpPage() {
        this.upPage = (this.curPage > 1) ? this.curPage - 1 : this.curPage;
    }

    /**
     * 设置下一页
     */
    private void setNextPage() {
        this.nextPage = (this.curPage == this.totalPage) ? this.curPage : this.curPage + 1;
    }

    public Pagination(int curPage, int pageSize, long totalCount) {
        this.curPage = curPage;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
        init();
    }

    /**
     * 初始化计算分页
     */
    private void init() {
        this.setTotalPage();// 设置一共页数
        this.setUpPage();// 设置上一页
        this.setNextPage();// 设置下一页
    }

}
