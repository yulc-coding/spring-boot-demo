package org.ylc.frame.springboot;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.SqlServerTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.IColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.ArrayList;

/**
 * 代码千万行，注释第一行，
 * 注释不规范，同事泪两行。
 * <p>
 * 代码生成
 *
 * @author yulc
 * @version 1.0.0
 * @date 2019/9/25
 */
public class CodeGenerator {

    /**
     * 需要生成或排除的表
     */
    private static final String[] TABLES = {"sys_user"};

    /**
     * 对应表的前缀：t_name   前缀为 t_ ，实体会去掉前缀
     */
    private static final String TABLE_PREFIX = "sys_";

    /**
     * 自定义模板路径
     * resources/templates
     */
    private static final String templatesPath = "/templates/";


    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();

        // String projectPath = System.getProperty("user.dir");
        String projectPath = "D:/CodeGenerator";
        // 生成文件的输出目录
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setAuthor("CodeGenerator");
        // XML 中的baseMapper
        gc.setBaseResultMap(true);
        // 实体属性 Swagger2 注解
        // gc.setSwagger2(true);
        // 自定义文件命名，注意 %s 会自动填充表实体属性！
        gc.setServiceName("%sService");
        // 主键的ID类型
        gc.setIdType(IdType.AUTO);
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL);
        dsc.setUrl("jdbc:mysql://localhost:3306/demo?useUnicode=true&useSSL=false&characterEncoding=utf8&serverTimezone=UTC");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("root2019!");
        // 类型精度数字转成BigDecimal
        dsc.setTypeConvert(new SqlServerTypeConvert() {
            @Override
            public IColumnType processTypeConvert(GlobalConfig globalConfig, String fieldType) {
                if ((fieldType.contains("decimal") || fieldType.contains("numeric"))) {
                    return DbColumnType.BIG_DECIMAL;
                }
                return super.processTypeConvert(globalConfig, fieldType);
            }
        });
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent("org.ylc.frame.springboot.biz");
        mpg.setPackageInfo(pc);

        // 自定义配置
        // InjectionConfig cfg = new InjectionConfig() {
        //     @Override
        //     public void initMap() {
        //         // to do nothing
        //     }
        // };

        // 如果模板引擎是 freemarker
        // String templatePath = "/templates/mapper.xml.ftl";
        // 如果模板引擎是 velocity
        // String templatePath = "/templates/mapper.xml.vm";

        // 自定义输出配置
        // List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        // focList.add(new FileOutConfig(templatePath) {
        //     @Override
        //     public String outputFile(TableInfo tableInfo) {
        //         // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
        //         return projectPath + "/mapper/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
        //     }
        // });
        //cfg.setFileOutConfigList(focList);
        //mpg.setCfg(cfg);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();
        templateConfig.setController(templatesPath + "/controller.java");
        mpg.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        // lombok模型 无需get set
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        // 是否生成实体时，生成字段注解
        strategy.setEntityTableFieldAnnotationEnable(true);
        // 逻辑删除字段
        strategy.setLogicDeleteFieldName("del_flag");
        //设置填充字段
        strategy.setTableFillList(new ArrayList<TableFill>() {{
            add(new TableFill("create_user", FieldFill.INSERT));
            add(new TableFill("create_time", FieldFill.INSERT));
            add(new TableFill("del_flag", FieldFill.INSERT));

            add(new TableFill("update_user", FieldFill.UPDATE));
            add(new TableFill("update_time", FieldFill.UPDATE));
        }});
        // 需要生成的表
        strategy.setInclude(TABLES);
        // 需要排除的表
        //strategy.setExclude(TABLES);
        // 对应表的前缀：t_name   前缀为 t_ ，实体会去掉前缀
        strategy.setTablePrefix(TABLE_PREFIX);
        mpg.setStrategy(strategy);

        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }
}
