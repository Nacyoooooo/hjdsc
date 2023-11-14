# 题目:皇家斗兽场



## 开发环境：

| IDE        | idea 2023.2 |
| :--------- | ----------- |
| jdk        | 1.8         |
| mysql      | 5.7         |
| springboot | 2.5.15      |



## 开发进度安排：

- [x] 项目分包，
- [x] 完成基础框架搭建（如统一返回类，统一异常处理，模块的导入）
- [x] 实现用户登录（简陋版）
- [x] 实现用户注册（简陋版）
- [ ] ~~实现管理员登录注册~~
- [x] 通过aop注解自动校验字段是否为空（简陋版）
- [x] 用户登录功能（增强）
- [x] 通过aop实现角色的自动装配（简陋版）
- [x] 建立前端文件，并实现基础的前端表单校验和登录
- [x] 实现注册功能（前端）（简陋版）
- [x] 用户签到天数记录（本月内）
- [x] 用户查看个人信息
- [x] 用户修改个人信息
- [x] 用户可查看宠物配置信息，并保存到redis
- [x] 统计签到信息至数据库
- [x] 用户可以查看自己的宠物
- [x] 管理员登录
- [x] 管理员查看用户信息
- [x] 封禁用户
- [ ] 管理员维护宠物园，向上面分配可捕捉的宠物
- [ ] 增删改查宠物配置信息
- [ ] 查看用户所拥有的宠物
- [ ] 用户可修改个人头像
- [ ] 管理员封禁并管理用户





## 可能完成的需求：

### 管理员模块：

- [x] 增删改查宠物信息，设置宠物数值，名称，最大生命值，技能，品质，是否可捕捉
- [ ] 维护宠物池，供玩家捕捉
- [ ] 设置宠物升级规则
- [ ] 管理用户信息
- [ ] 查看游戏运营情况
- [ ] 设置金币功能，新用户登录给予金币
- [ ] 设置技能机制
- [ ] 设置宠物商城，可以购买宠物
- [x] 禁用某些用户
- [ ] 发送公告

### 用户模块：

- [x] 可以登录注册，密码加密
- [ ] 首次登陆，三属性宠物三选一
- [x] 可查看自己的宠物背包，查看宠物信息，设置宠物血量，要用redis缓存
- [ ] 修改个人信息
- [ ] 设置宠物出战顺序，别人pk时，以这个顺序出战
- [ ] 查看其他用户的宠物信息
- [ ] 向其他用户发起pk，被pk用户必须要接受
- [ ] 可以设置自己的宠物信息是否公开，公开就是接收pk请求，不公开就是一律拒绝
- [ ] 被禁止登陆之后，可向管理员发送请求，并接收管理员发送的通知以及反馈
- [x] 用户可查看签到情况（一个月内）
- [ ] 可以在线pk，回合制，不一定按照出战顺序，两个窗口
- [ ] 设置预定时间，到达预定时间向对方发起pk请求
- [ ] 设置相似度概念，交集概念，要用redis完成
- [ ] 宠物要有想克制的属性
- [ ] 考虑并发，多个用户同时发起pk或者捕捉某个宠物时，如何解决高并发
- [ ] 用集合时，要用stream流+lambda表达式
- [ ] 自定义异常处理