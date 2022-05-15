# 工时管理服务

此项目为工时管理服务

## 目录
* [环境准备](#环境准备)
* [常用命令](#常用命令)
* [词汇表](#词汇表)

## 环境准备
- Java8
- Docker

## 运行命令
```bash

sh ./env-build-scripts/create_database_instance.sh  //在项目根目录下执行, 创建数据库实例

// env-build-scripts/init_db.sql 为在数据库实例中创建本项目所需数据库的 sql 语句

./gradlew build      //项目根目录下执行，构建

./gradlew bootRun       //项目根目录下执行，项目启动

```

## 词汇表
|  领域名词 |  英文   |  模型  |   表名  |  备注  |
|  :----:  | :----: | :----: | :----: | :----: |
|    工时  | effort |  Effort  | effort | / | 
| 工时卡  | timecard | Timecard | / | / |  
| 登记项  | entry |  Entry | / | / | 
| 子登记项  | subentry |  SubEntry | / | / |  
| 地点  | location | Location | location | / |
| 时间段  | period | Period | / | / | 
