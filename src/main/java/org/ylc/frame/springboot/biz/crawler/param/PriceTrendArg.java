package org.ylc.frame.springboot.biz.crawler.param;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 代码千万行，注释第一行，
 * 注释不规范，同事泪两行。
 * <p>
 * 房价趋势
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2019-12-23
 */
@Getter
@Setter
public class PriceTrendArg {

    @NotBlank(message = "名称不能为空")
    private String name;

    /**
     * 统计日期
     */
    @NotNull(message = "开始日期不能为空")
    private Integer beginDate;

    private Integer endDate;

    /**
     * 生成查询条件
     *
     * @return query
     */
    public Query generatePageQuery() {
        Query query = new Query();
        Criteria criteria = new Criteria();

        criteria.and("name").is(this.name);
        if (this.endDate != null) {
            criteria.andOperator(
                    Criteria.where("reportDate").gte(this.beginDate),
                    Criteria.where("reportDate").lte(this.endDate)
            );
        } else {
            if (this.beginDate != null) {
                criteria.and("reportDate").gte(this.beginDate);
            }
        }
        query.addCriteria(criteria);
        query.with(Sort.by(Sort.Direction.ASC, "reportDate"));
        return query;
    }

}
