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

### 工时词汇表
|  领域名词 |  英文   |  模型  |   表名  |  备注  |
|  :----:  | :----: | :----: | :----: | :----: |
|    工时  | effort |  Effort  | effort | / | 
|    工作日  | working day |  workingDay  | / | / | 
|    工作小时数  | working hours |  workingHours  | / | / | 
|    是否收取费用  | billable |  billable  | / | / | 
|     备注  | note |  note  | / | / | 
|    工时  | effort |  Effort  | effort | / | 
|    工时状态  | effort status |  effortStatus  | / | / | 
|    已保存  | saved |  saved  | / | / | 
|    已提交  | submitted |  submitted  | / | / | 

### 工时卡词汇表
| 领域名词 |  英文   |  模型  |   表名  |  备注  |
|  :----:  | :----: | :----: | :----: | :----: |
| 工时卡  | timecard | Timecard | / | / |  
| 时间段  | period | Period | / | / | 
| 总小时数  | total hours | totalHours | / | / | 
| 登记项  | entry |  Entry | / | / | 

### 登记项词汇表
| 领域名词 |  英文   |  模型  |   表名  |  备注  |
|  :----:  | :----: | :----: | :----: | :----: |
| 登记项  | entry | entry | / | / |  
| 小时数  | hours | hours | / | / |  
| 子登记项  | sub entry | subEntry | / | / |  

### 子登记项词汇表
| 领域名词 |  英文   |  模型  |   表名  |  备注  |
|  :----:  | :----: | :----: | :----: | :----: |
| 子登记项  |  sub entry | sub entry | / | / |  
| 是否收取费用  |  billable | billable | / | / |  
| 备注  | note | note | / | / |  

### 地点词汇表
| 领域名词 |  英文   |  模型  |   表名  |  备注  |
|  :----:  | :----: | :----: | :----: | :----: |
| 地点  |  location | location | / | / |  
| 地区缩写  |  code | code | / | / |  
| 地区名称  | name | name | / | / |  
