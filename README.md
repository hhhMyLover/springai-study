# Spring AI Study Repository

这是一个Spring AI学习仓库，展示了Spring AI框架的各种功能和特性。

## 项目概述

本项目是一个完整的Spring AI学习示例，包含了以下主要功能：

- **基础聊天功能**：使用阿里云通义千问模型进行对话
- **记忆功能**：基于文件的聊天记忆实现
- **顾问系统**：自定义的对话顾问，包括恋爱助手和违禁词过滤
- **RAG功能**：检索增强生成，结合向量数据库进行知识问答
- **工具调用**：演示Spring AI的函数调用功能
- **配置管理**：完整的Spring Boot配置示例

## 技术栈

- **Spring Boot 3.5.5**
- **Spring AI Alibaba** (通义千问模型)
- **Redis** (缓存和会话存储)
- **Lombok** (代码简化)
- **Knife4j** (API文档)
- **Hutool** (工具库)

## 项目结构

```
src/main/java/com/wzh/springai/
├── advisor/              # 对话顾问
│   ├── LoverAdvisor.java       # 恋爱助手顾问
│   └── ProhibitedWordsAdvisor.java # 违禁词过滤顾问
├── chatmemory/          # 聊天记忆实现
│   └── FileBaseChatMemory.java # 基于文件的记忆存储
├── config/              # 配置类
│   ├── RedisConfig.java        # Redis配置
│   └── LoverVectorStoreConfig.java # 向量存储配置
├── constant/            # 常量类
│   └── FileSaveConstant.java   # 文件保存路径常量
├── controller/          # REST控制器
│   ├── MainController.java     # 健康检查接口
│   └── LoverController.java    # 聊天功能接口
├── demo/                # 演示类
│   └── SpringaiInvoke.java     # Spring AI调用演示
├── model/               # 数据模型
│   └── vo/
│       └── LoverReportVO.java  # 聊天报告数据模型
├── rag/                 # RAG功能
│   └── LoverRagCustomerAdvisorFactory.java # RAG顾问工厂
├── service/             # 服务层
│   ├── LoverService.java       # 服务接口
│   └── impl/
│       └── LoverServiceImpl.java # 服务实现
├── tool/                # 工具类
│   └── WebSearchTool.java      # 网络搜索工具
└── SpringAiApplication.java    # 主启动类
```

## 快速开始

### 1. 环境要求

- Java 17+
- Maven 3.6+
- Redis (可选，用于缓存功能)

### 2. 配置API密钥

在 `application.yml` 中配置阿里云通义千问的API密钥：

```yaml
spring:
  ai:
    dashscope:
      api-key: ${DASHSCOPE_API_KEY:your-api-key-here}
```

或者设置环境变量：

```bash
export DASHSCOPE_API_KEY=your-api-key-here
```

### 3. 运行项目

```bash
# 编译项目
mvn clean compile

# 运行项目
mvn spring-boot:run
```

### 4. 访问接口

项目启动后，可以访问以下接口：

- 健康检查：`GET http://localhost:8123/api/heath`
- 简单聊天：`GET http://localhost:8123/api/lover/chatLover?message=你好`
- 带记忆聊天：`GET http://localhost:8123/api/lover/chatMemory?message=你好&chatId=session1`
- API文档：`http://localhost:8123/api/swagger-ui.html`

## 主要功能说明

### 1. 聊天功能

基于阿里云通义千问模型，提供智能对话功能。支持：
- 基础对话
- 带记忆的对话
- 自定义系统提示词

### 2. 顾问系统

- **LoverAdvisor**：恋爱助手顾问，为恋爱相关问题提供专业建议
- **ProhibitedWordsAdvisor**：违禁词过滤器，确保对话内容健康

### 3. RAG功能

结合向量数据库，实现知识库问答功能。可以：
- 存储和检索文档
- 基于上下文回答问题
- 自定义检索策略

### 4. 工具调用

演示Spring AI的函数调用功能：
- 网络搜索工具
- 时间获取工具
- 自定义业务工具

### 5. 记忆管理

- 基于文件的聊天记忆存储
- 支持多会话管理
- 可配置记忆容量

## 开发说明

### 添加新的顾问

```java
@Component
public class CustomAdvisor implements CallAroundAdvisor {
    @Override
    public String getName() {
        return "custom-advisor";
    }
    
    @Override
    public AdvisedResponse aroundCall(AdvisedRequest request, CallAroundAdvisorChain chain) {
        // 自定义逻辑
        return chain.nextAroundCall(request);
    }
}
```

### 添加新的工具

```java
@Bean
@Description("工具描述")
public Function<RequestType, ResponseType> customTool() {
    return request -> {
        // 工具逻辑
        return response;
    };
}
```

## 注意事项

1. 需要有效的阿里云通义千问API密钥才能正常使用聊天功能
2. Redis配置是可选的，如果没有Redis，某些功能会使用模拟实现
3. 文件存储路径默认为项目根目录下的 `/tmp` 文件夹
4. 向量存储使用了模拟实现，生产环境建议使用真实的向量数据库

## 学习资源

- [Spring AI官方文档](https://docs.spring.io/spring-ai/reference/)
- [阿里云通义千问文档](https://help.aliyun.com/zh/dashscope/)
- [Spring Boot官方文档](https://spring.io/projects/spring-boot)

## 贡献

欢迎提交Issue和Pull Request来改进这个学习项目！
