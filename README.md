# HansConvertToWubi

顾名思义，一个把汉语语汇转为五笔编码的工具。

目前，市面上对五笔的支持不太好，通过这个工具可以把网络常用词语转换为五笔编码

后继再加以调整便可直接应用在一些软件上

~~ 至于为什么用Java写？因为我还不会C（ ~~

自己用的，各位笑笑就好。

---

### 如何食用

1. 首先，你要先安装[JDK]()并配置好`环境变量`。
网上许多教程，此处不多赘述

在后面的更新中，应该会再进行优化的，目前先这样。

2. 使用以下命令编译这个项目
``` bash
    cd HansConvertToWubi/src/main/java
    javac Main.java
```

3. 使用以下命令实现转换
``` bash
    java Main
```
在使用中，会提示用户输入要转换的文件路径，支持路径，会把路径下所有文件进行转换

其次，会询问是否添加一级二级简码，请回答y，不然可能会死循环。请使用`Ctrl+C`结束程序。

### 致谢
- CNMan的项目[UnicodeCJK-WuBi](https://github.com/CNMan/UnicodeCJK-WuBi/tree/master)
- fxsjy大佬的[jieba词库](https://github.com/fxsjy/jieba/tree/master)
- [极点五笔官方词库](https://github.com/KyleBing/rime-wubi86-jidian/blob/master/wubi86_jidian.dict.yaml)