package ${package.Controller};

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
<#if restControllerStyle>
@RestController
<#else>
@Controller
</#if>
@RequestMapping("<#if package.ModuleName??>/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>")
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
    @PostMapping("/addInfo")
    @EmployeePermission("agency:${table.entityPath}:addInfo")
    public HttpResult addInfo() {
        return HttpResult.success();
    }

    @ApiOperation(value = "根据ID批量删除")
    @GetMapping("/delInfo")
    @EmployeePermission("agency:${table.entityPath}:delInfoById")
    public HttpResult delInfo() {
        return HttpResult.success();
    }

    @ApiOperation(value = "更新")
    @PostMapping("/updateInfo")
    @EmployeePermission("agency:${table.entityPath}:updateInfo")
    public HttpResult updateInfo() {
        return HttpResult.success();
    }

    @ApiOperation(value = "分页查询")
    @PostMapping("/pageInfo")
    public HttpResult pageInfo() {
        return HttpResult.success();
    }

    @ApiOperation(value = "查询所有")
    @GetMapping("/getAllInfo")
    public HttpResult pageInfo(@ApiParam(name = "agencyId", value = "agencyId")
                               @RequestParam(name = "agencyId") String agencyId) {
        return HttpResult.success();
    }

    @ApiOperation(value = "根据ID查询信息")
    @GetMapping("/getInfoById")
    public HttpResult getInfoById(@ApiParam(name = "id", value = "id")
                                  @RequestParam(name = "id") String id) {
        return Result.success();
    }

 }
</#if>
