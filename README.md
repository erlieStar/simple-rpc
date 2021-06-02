# RPC简易实现

master分支：一个超级简单的demo，方便理解rpc实现的本质

v2.0分支：利用 java spi 实现的具有可扩展性的rpc框架


依赖了lz写的工具类库，但是暂时还没有发布到maven仓库，可以手动install到本地仓库


```shell
git clone git@github.com:erlieStar/common-tool.git
cd common-tool
git checkout v2.0
mvn clean install -DskipTests=true
```

v3.0分支：和spring进行整合的rpc框架