# spring-boot-demo
* Mongodb
* Redis
* MyBatis-Plus
* MySQL
****
* swagger api
* 全局异常
* AOP
* ApplicationEvent 和 ApplicationListener 简单事件监听
* websocket
* MyBatis-Plus 自动生成代码
* schedule定时任务

#### 功能
* 部门管理
* 用户管理
* 角色管理
* 权限管理（细分到按钮）


#### 流程
* 通过JWT生成用户token，redis 保存token进项token过期控制；
* 通过自定义注解 @Permission 去设置每个接口的权限；
* 通过AOP 解析token 获取用户信息和对应接口的权限，判断是否有访问权限；MongoDB记录访问日志；
* ThreadLocal 存放token解析后的用户信息；
