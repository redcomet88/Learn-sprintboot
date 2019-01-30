一罐好运H5
---
### 0功能点
+ 微信的网页授权验证
+ 抽奖的并发实现
+ token缓存的解决

### 1接口
 
 ### 2测试
 帮赞时可能存在BUG（？）
 
 ### 3数据库初始化数据
 > probablity和amount的解释：概率是一个相对的值，不需要加起来等于1；数量如果为0，则抽奖就自动会规避，相对概率也不会计入

 insert into t_award(awardName,probability,amount) values ('未中奖',0.2,1000000);
 insert into t_award(awardName,probability,amount) values ('卡券',0.5,700);
 insert into t_award(awardName,probability,amount) values ('一般罐',0.25,250);
 insert into t_award(awardName,probability,amount) values ('定制罐',0.05,50);

### 4ehcache缓存创建方法
+ 在resources目录下创建ehcache.xml
+ springboot 配置spring缓存方案（配置ehcache.xml路径）
+ spring启动类增加注解@EnableCaching
+ 


