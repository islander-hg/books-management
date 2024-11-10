## 平台简介

基于Springboot 实现的图书管理系统，具有用户、角色、菜单、图书、图书借阅等管理功能，目前只有实现了后端服务，无前端服务

## 内置功能

1.  用户管理：用户是系统操作者，该功能主要完成系统用户配置，基于 spring security 实现用户认证和接口权限拦截
2.  角色管理：角色菜单权限分配、设置角色按机构进行数据范围权限划分
4.  菜单管理：配置系统菜单，操作权限，按钮权限标识等
6.  图书管理：对图书数据进行维护
7.  图书借阅：本系统用户可对图书进行借阅

## 在线体验

- 无前端页面

API地址（需本地启动）：http://localhost:8080/swagger-ui/index.html

## 架构说明

[![image-20241110153709185.png](https://i.postimg.cc/4NsLgXYP/image-20241110153709185.png)

本系统有以下几个模块

1.  books-management-api：存放接口入参出参定义、Dubbo 接口（如有）定义等
2.  books-management-common：存放通用工具
3.  books-management-server：服务启动入口，HTTP接口定义的地方，进行全局配置
4.  books-management-service：具体业务实现

依赖的中间件：

1.  nacos：作为配置中心，存放服务配置
2.  mysql：业务数据存储
3.  redis：缓存数据，如用户Token，验证码等
4.  邮箱：目前仅用于发送验证码信息（忘记密码时使用）

## 启动方法

1. 创建 nacos 配置：启动 nacos 并创建配置，配置模版参考上图中的 nacos/nacos_config.template

2. 初始化 mysql ：启动 mysql 并在 mysql 中执行上图中的 sql目录下的 V1.0.0.sql 文件

3. redis：启动 redis 

4. 使用JVM参数传入 nacos 配置连接信息启动服务，JVM参数参考：

   ```
   -DNACOS_ADDR=127.0.0.1:8848
   -DNACOS_NAMESPACE=books-management-local
   -DNACOS_GROUP=DEFAULT_GROUP
   -DNACOS_DATA_ID=books-management
   ```

   