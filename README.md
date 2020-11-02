# RPC简易实现

master分支：一个超级简单的demo，方便理解rpc实现的本质

v2.0分支：具备基本功能的一个rpc框架

需要开启一下Enable annotation processing

Settings -> Build -> Compiler -> Annotation Processors -> Enable annotation processing

依赖了lz写的工具类库，但是暂时还没有发布到maven仓库，可以手动install到本地仓库


```shell
git clone git@github.com:erlieStar/common-tool.git
cd common-tool
git checkout v2.0
mvn clean install -DskipTests=true
```

v3.0分支：将rpc框架和spring进行整合