# Arathoth-Zero
What is Arathoth?
- 克苏鲁神话: 旧日的支配者

And What is Arathoth-Zero?
- 一款简约风格的高自定义度属性插件

Why you named it so strange?
- 模仿的坏黑大佬的起名风格XD

# 更新日志
2020.11.28 Start

2020.11.29 0.0.2 Update
- PlaceHolderHook Update
- 大改,面向对象的属性处理方法
- Listattr命令改动
- reload命令现在可以重载全部Attribute文件，Rules暂不支持
- 注册了第一个Attribute: AdditionalHealth,Rules: LevelRequired
- 重写了FileConfiguration有关逻辑
- API基本完成，剩下的就是写一些简化属性注册的方法
- 理论上是第一个可用版本(未经测试)

2020.11.29 0.0.3 Update
- bug fix
- 注册了PhysicalDamage属性，写了一个属性集中处理的监听类AttributeListener，目前只支持EntityDamagedByEntityEvent
- 属性计算优先级模块完善
- AttrUtils类，提供来方便属性操作的方法，目前只有一个方法: getrandom(Double value1,Double value2) 处理浮动属性值

2020.12.06 0.0.3.02 Update
- bug fix
- Rules基本注册完毕
- PAPIRequest: 画了张饼，通过PAPI变量判断让使用者自行创建规则
- PAPIRequest 计划实现的三种功能: 数值类型papi判断，string类型papi判断 contains,equals.....
- 明天起来注册属性

2020.12.06 0.0.4 Update
- bug fix
- 目前注册了11个属性，下次回来完善其他的属性
- 事件: ArathothStatusUpdateEvent

2020.12.12 0.1.0 Update
- 为什么直接横跨6个版本?因为更新得比前面四个版本全部内容都要多
- Attributes包全部重写，累死我了
- 作废了上个版本的SubAttribute抽象类，现在注册属性有两个抽象类可以继承:
- NumberAttribute，SpecialAttribute 他们都是ArathothAttribute的子抽象类
- NumberAttribute用作一般的数字属性的注册，SpecialAttribute则是用于特殊属性的注册
- 所有基础属性已经全部注册完毕，AttackSpeedRank是SpecialAttribute实例
- PlaceHolder现在也支持查询Special属性，但因为是从Object直接转String，所以肯定有些属性很鬼畜
- 牛逼的PAPIRequest落实了
- statusinfo 指令更新，现在支持隐藏配置中注册过并且值为0的papi变量
- AttributeMessage鸽了，因为傻逼Bukkit不提供ActionBar方法，spigot那个又玄乎得我看不懂
- 事件全部重写:现在有ArathothAttackCDEvent ArathothCritEvent ArathothDodgeEvent ArathothStatusUpdateEvent四个事件
- 新的类:ArathothAPI，提供了简化属性操作的方法
- 现在有: 21个属性 4个条件
- 插件未经测试，不知道有无bug
- 准备发mcbbs了

2020.12.20 0.1.1 Update
- StatusInfo报错修复
- StatusInfo现在用来查询指定玩家属性，如果要查询自身属性请使用mystatus
- tabcomplete 更新
- 新的一条特殊属性: DurabilityFix  Asgard传统艺能
- 修复了AdditionalHealth导致的报错
- 优化API中获取属性值方法逻辑，规避了一些特殊情况下的NullPointerException
- SpecialAttribute抽象类新增一条抽象方法 : String getPlaceHolder(Player p) - papi返回这个方法返回的值

2021.1.1 0.1.3 Update
- 基本重写，现在属性不用hashmap储存
- 删除了statusupdateevent，现在靠监听新的事件操作属性 - ArathothStatusExecuteEvent
- 新增了一个specialAttribute attackrange 攻击范围
- 弓箭属性现在使用元数据储存
- ActionbarRemind功能有了
- 修复AttackSpeedRank卡线程的问题
- 修复了绝大多数NPE
- 但是你妈的NumberAttribute的正则式死活匹配不出来
- 我裂开了，求求哪位大佬帮我看看吧，这bug我实在de不动了

2021.1.10 0.1.4 Update
- 匹配不到lore的问题终究还是解决了，就是additionhealth的实现方式还是有问题
- 修了几万个报错
- 美化命令帮助信息
