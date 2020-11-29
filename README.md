# Arathoth-Zero
丢人自制半成品属性插件，大佬们发现有写错的地方请一定指出!

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
