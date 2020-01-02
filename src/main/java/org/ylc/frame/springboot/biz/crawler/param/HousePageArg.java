package org.ylc.frame.springboot.biz.crawler.param;

import cn.hutool.core.util.StrUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import javax.validation.constraints.NotNull;

/**
 * 代码千万行，注释第一行，
 * 注释不规范，同事泪两行。
 * <p>
 * 分页查询条件
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2019-12-23
 */
@Getter
@Setter
public class HousePageArg {

    @NotNull(message = "当前页不能为空")
    private Integer curPage;

    @NotNull(message = "每页显示不能为空")
    private Integer pageSize;

    /**
     * 城市
     */
    private String city;

    /**
     * 名称
     */
    private String name;

    /**
     * 统计日期
     */
    @NotNull(message = "统计日期不能为空")
    private Integer reportDate;

    /**
     * 生成查询条件
     *
     * @return query
     */
    public Query generatePageQuery() {
        Query query = new Query();
        Criteria criteria = new Criteria();
        if (this.reportDate != null) {
            criteria.and("reportDate").is(this.reportDate);
        }
        if (StrUtil.isNotBlank(this.city)) {
            criteria.and("city").is(this.city);
        }
        if (StrUtil.isNotBlank(this.name)) {
            // 名称，模糊查询
            criteria.and("name").regex("^.*" + this.name + ".*$");
        }
        query.addCriteria(criteria);
        return query;
    }

}
