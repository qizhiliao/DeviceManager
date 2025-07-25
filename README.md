# 设备管理器 (DeviceManager)

一个全面的设备出租管理系统，包含Vue.js前端和Spring Boot后端。

## 功能特性

- 实现对于摄影设备出租的管理
- 用户认证与授权
- 设备信息的增删改查
- 设备的租凭与归还
- 设备状态的监控
- 对租赁的评价与管理


## 安装步骤

### 后端 (DeviceManagerApi)
1. 进入后端目录：
   ```
   cd DeviceManagerApi
   ```
2. 使用Maven构建：
   ```
   mvn clean install
   ```

### 前端 (DeviceManagerVue)
1. 进入前端目录：
   ```
   cd DeviceManagerVue
   ```
2. 安装依赖：
   ```
   npm install
   ```

## 使用方法

### 运行后端
```
cd DeviceManagerApi
mvn spring-boot:run
```

### 运行前端
```
cd DeviceManagerVue
npm run dev
```

## 项目结构

- `DeviceManagerApi/`: Spring Boot后端应用
- `DeviceManagerVue/`: Vue.js前端应用
- `device_manager.sql`: 数据库 schema

## 数据库设置
将`device_manager.sql`文件导入到您的MySQL数据库中。
