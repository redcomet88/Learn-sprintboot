一罐好运H5
---
### 0功能点
+ 微信的网页授权验证
+ 抽奖的并发实现
+ token缓存的解决

### 1 接口
[接口文档](https://coding.net/u/yisako/p/netTools/git/blob/master/zhufu.md)
 
 ### 2 特性
 + 帮赞不能给自己赞，赞过不能赞
 + access_token过期，需要缓存在服务端，减少和微信的交互
 + 抽奖程序需要实现同步
 
 ### 3 数据库初始化数据
 > probablity和amount的解释：概率是一个相对的值，不需要加起来等于1；数量如果为0，则抽奖就自动会规避，相对概率也不会计入

 insert into t_award(award_Name,probability,amount) values ('未中奖',0.2,1000000);
 insert into t_award(award_Name,probability,amount) values ('卡券',0.5,700);
 insert into t_award(award_Name,probability,amount) values ('一般罐',0.25,250);
 insert into t_award(award_Name,probability,amount) values ('定制罐',0.05,50);

### 4 ehcache缓存创建方法
+ 在resources目录下创建ehcache.xml
+ springboot 配置spring缓存方案（配置ehcache.xml路径）
+ spring启动类增加注解@EnableCaching
+ 在TokenService中添加需要操作缓存的方法
+ @CacheConfig制定函数全局的cacheNames，也可以用value来指定不同缓存配置（这个不配置也会报错）

> **save方法一定要有返回对象的，否则缓存里是空的，这个问题查了很久!**


