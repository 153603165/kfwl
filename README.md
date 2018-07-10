# 说明文档

### 1、项目使用资源
1. 项目框架使用 springboot
2. 安全控制使用 security-oauth2
3. 数据库使用 mariadb，数据库操作使用 jpa
4. 页面模板使用 thymeleaf
5. 接口文档使用 swagger

### 2、项目说明
因公司规模问题，想做一个使用于一个以后可以对应该公司规模项目的一个开箱即用的项目，该项目是一个小型的系统架构，把资源认证和权限认证放在了一起，适用于单需求项目，该项目开包即用，自带角色管理，用户管理，权限管理，菜单管理和api接口文档模块，并添加了一个业务数据的例子模型，如有需要可把实体类、权限认证、service层、对外api层拆分当做一个微服务系统使用。
添加微信公众号配置，可直接对接公众号进行开发

### 3、项目结构介绍

apiconfig ：swagger的api的url配置
 
common ：包含了一些通用的实体类包括，菜单、资源树、一些精彩资源枚举等
 
config ：oauth2的权限认证配置和资源认证配置

controller ：控制器

dao ：数据库操作

entity ：数据库实体

exception ：自定义异常

security ：数据库操作监听，对新增保存数据添加操作时间，操作人

service ：业务逻辑

utils ：公用功能包

org.sword.wechat4j ：微信公众号功能包

### 4、项目使用说明

对除api接口需要使用access_token进行访问其余项目内资源需要登入后可以访问,因为该项目仅自身使用，建议使用password的认证模式
对微信页面以/wx开头的链接跳过oauth验证

获取access_token方法：
post访问：
> /oauth/token  

参数如下

参数| 值 | 是否必填
---|---|---
grant_type| password | 是
client_id | acme | 是
client_secret | acmesecret | 是
username | {登录名} | 是
password | {登录密码} | 是  

### Api接口
##### 一、根据用户Id获取到用户信息数据

Api链接
> /api/user/{id}


参数 | 参数值
---|---
id | 用户的id主键
accessToken | {access_token值}

或使用键Authorization，值为bearer {access_token}加入headers作为参数替换上参数表中accessToken参数

> http://127.0.0.1:9090/api/user/2?access_token=7f4c7706-c80f-4a22-a81a-e6333dad09cb

### 5、结束语
该项目开源免费使用，如有问题请邮箱联系153603165@qq.com
