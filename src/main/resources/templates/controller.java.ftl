package ${package.Controller};

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import ${package.Entity}.${entity};
import ${package.Service}.${table.serviceName};
import org.springframework.web.bind.annotation.RequestMapping;
import org.ylc.frame.springboot.common.annotation.Permission;
import org.ylc.frame.springboot.common.base.HttpResult;

<#if restControllerStyle>
import org.springframework.web.bind.annotation.RestController;
<#else>
import org.springframework.stereotype.Controller;
</#if>
<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>

/**
 * <p>
 * ${table.comment!} 前端控制器
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
@Api(value = "${table.controllerName}")
<#if restControllerStyle>
@RestController
<#else>
@Controller
</#if>
@RequestMapping("/${table.entityPath}s")
<#if kotlin>
class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
<#if superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass} {
<#else>
public class ${table.controllerName} {
</#if>

    private final ${table.serviceName} ${table.entityPath}Service;

    @Autowired
    public ${table.controllerName}(${table.serviceName} ${table.entityPath}Service) {
        this.${table.entityPath}Service = ${table.entityPath}Service;
    }

    @ApiOperation(value = "新增")
    @PostMapping
    @Permission("${table.entityPath}:add")
    public HttpResult addInfo() {

        return HttpResult.success();
    }

    @ApiOperation(value = "删除")
    @DeleteMapping("/{id}}")
    @Permission("${table.entityPath}:del")
    public HttpResult delInfo(@ApiParam(name = "id", value = "id")
                              @PathVariable("id") Long id) {

        return HttpResult.success();
    }

    @ApiOperation(value = "更新")
    @PutMapping
    @Permission("${table.entityPath}:update")
    public HttpResult updateInfo() {

        return HttpResult.success();
    }

    @ApiOperation(value = "分页查询")
    @GetMapping("/page/{size}/{current}")
    @Permission("pc")
    public HttpResult pageInfo(@ApiParam(name = "size", value = "每页显示", defaultValue = "10") @PathVariable Long size,
                               @ApiParam(name = "current", value = "当前页", defaultValue = "1") @PathVariable Long current) {

        return HttpResult.success();
    }

    @ApiOperation(value = "根据ID查询信息")
    @GetMapping("/{id}}")
    @Permission("pc")
    public HttpResult getInfoById(@ApiParam(name = "id", value = "id")
                                  @PathVariable(name = "id") Long id) {

        return HttpResult.success();
    }

 }
</#if>
