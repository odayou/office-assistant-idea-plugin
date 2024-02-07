# 说明
idea插件，自己定制一些效率功能
功能介绍:
1. 快速生成JSON字符串
2. 转换文本格式
3. 
## 开发参考资料
 - https://zhuanlan.zhihu.com/p/400059601
 - https://www.jianshu.com/p/3b8b65ca73c3
 - https://www.ideaplugin.com/
 - https://github.com/organics2016/pojo2json/blob/master/src/main/java/ink/organics/pojo2json/POJO2JSONAction.java

## 插件开发
### 引入三方包步骤
> build.gradle中dependencies节点添加依赖, 如
```
dependencies {
    implementation    'com.alibaba:fastjson:1.2.62'
}
```
### 打包
1. gradle菜单中执行 buildPlugin > intellij > buildPlugin
2. 打包后插件文件在./build/distributions/目录下的zip文件
3. 插件安装: idea -> Preferences -> Plugins -> Install plugin from disk -> 选择zip文件