# miya
毕设：Miya电商平台
> 该项目是本人的优秀毕设项目，毕设资料截图已在【毕设资料集合】文件夹下,可供参考； 

# About
此项目是 Spring Cloud + Vue/Jsp/Freemark + Kafka + Solr + FastDFS 等构建的B2C电商系统，所有的数据都是从服务器实时获取的真实数据，具有真实的注册、登陆、管理数据等功能。

# 说明

>  如果对您对此项目有兴趣，可以点 "Star" 支持一下 谢谢！ ^_^

>  或者您可以 "follow" 一下，我会不断开源更多的有趣的项目

>  开发环境 jdk1.8  Vue2.0

>  如有问题请直接在 Issues 中提，或者您发现问题并有非常好的解决方案，欢迎 PR 👍
 
 
 
## 项目运行


```
git clone  

访问: http://localhost:8002

```
# 技术栈
#### 微服务架构
系统采用微服务架构进行设计的,微服务架构也指一种松耦合的、有一定的有界上下文的面向服务架构。它是当下最新的热门话题之一。简而言之,微服务架构(Microservice Architecture)是一种架构概念,旨在通过将功能分解到各个离散的服务中以实现对解决方案的解耦。每个服务为独立的业务开发,一个微服务一般完成某个特定的功能,通过一些轻量级的通信机制进行通信,一系列独立运行的微服务共同构建起了整个系统。这使系统结构变化和维护更加容易,增加代码的可复用性。
#### 所用技术
-  后台技术：SpringCloud(Eureka、Zuul、Feign、Hystrix、Spring Boot) MyBatis   前台技术：Vue、Jsp、Freemark
-  Solr(搜索)
-  Kafka(消息传递)
-  Redis(缓存服务器)
-  FastDFS(文件存储)
-  Mysql(数据存储)
-  Tomcat(web服务器)
-  Git(代码管理)
-  Nexus(jar包管理)

八大功能模块分别是：门户模块、单点登录模块、搜索模块、用户模块、商品模块、购物车模块、订单模块、后台管理模块
16个项目  15个jar包 

<img src="https://github.com/zhao520a1a/miya/blob/master/miya-public-module/miya-parent/src/main/screenshot/%E7%94%B5%E5%95%86%E7%B3%BB%E7%BB%9F%E6%9E%B6%E6%9E%84.png"/>

 

# 配置清单
 http://www.miya.com:8888/eureka

content-service : 8081

http://www.miya.com:8082/

user-service: 8083

http://passport.miya.com:8084/user/showLogin
http://passport.miya.com:8084/user/showRegister

sso-service: 8085

http://item.miya.com:8086/products/560.html
http://item.miya.com:8086/item/605616.html

item-service: 8087

http://cart.miya.com:8088/cart/cart
zull-service:  8089

http://order.miya.com:8090/order-cart
order-service: 8091

http://search.miya.com:8810/search?q=" 

search-service :  8811

manage.miya.com



# 功能列表

- [x] 登陆/注册 -- 完成
- [x] 搜索商品 -- 完成
- [x] 个人中心 -- 完成 
- [x] 添加购物车 -- 完成
- [x] 生成订单 -- 完成

- [x] 添加商品 -- 完成
- [x] 添加商品分类 -- 待完成
- [x] 添加内容 -- 完成
- [x] 添加内容分类 -- 未完成
- [x] 添加商品规格参数 -- 完成
- [x] 数据展示 -- 完成
- [x] 管理用户 -- 完成
- [x] 管理订单 -- 完成
- [x] 管理商品 -- 完成
- [x] 管理内容 -- 完成
- [x] 管理员设置 -- 完成
- [x] 图表📈 -- 待完成
- [x] 富文本编辑器 -- 完成


## 部分截图


#### 首页
<img src="https://github.com/zhao520a1a/miya/blob/master/miya-public-module/miya-parent/src/main/screenshot/%E9%A6%96%E9%A1%B5/%E9%A6%96%E9%A1%B5.png"/>

#### 商品搜索
<img src="https://github.com/zhao520a1a/miya/blob/master/miya-public-module/miya-parent/src/main/screenshot/%E6%90%9C%E7%B4%A2/%E6%90%9C%E7%B4%A2.png?raw=true"/>

#### 商品列表
<img src="https://github.com/zhao520a1a/miya/blob/master/miya-public-module/miya-parent/src/main/screenshot/%E5%95%86%E5%93%81/%E5%95%86%E5%93%81%E5%88%97%E8%A1%A8.png?raw=true"/>

#### 商品详情
<img src="https://github.com/zhao520a1a/miya/blob/master/miya-public-module/miya-parent/src/main/screenshot/%E5%95%86%E5%93%81/%E5%95%86%E5%93%81%E8%AF%A6%E6%83%85.png?raw=true"/>

#### 购物车
<img src="https://github.com/zhao520a1a/miya/blob/master/miya-public-module/miya-parent/src/main/screenshot/%E8%B4%AD%E7%89%A9%E8%BD%A6/%E8%B4%AD%E7%89%A9%E8%BD%A6.png?raw=true"/>

#### 生成订单
<img src="https://github.com/zhao520a1a/miya/blob/master/miya-public-module/miya-parent/src/main/screenshot/%E8%AE%A2%E5%8D%95/%E6%B7%BB%E5%8A%A0%E8%AE%A2%E5%8D%95.png?raw=true"/>


#### 个人中心
<img src="https://github.com/zhao520a1a/miya/blob/master/miya-public-module/miya-parent/src/main/screenshot/%E4%B8%AA%E4%BA%BA%E7%94%A8%E6%88%B7/%E4%B8%AA%E4%BA%BA%E4%BF%A1%E6%81%AF.png?raw=true"/>


#### 后台首页
<img src="https://github.com/zhao520a1a/miya/blob/master/miya-public-module/miya-parent/src/main/screenshot/%E5%90%8E%E5%8F%B0%E7%AE%A1%E7%90%86/%E5%90%8E%E5%8F%B0%E9%A6%96%E9%A1%B5.png?raw=true"/>
