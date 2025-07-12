# HansConvertToWubi

顾名思义，一个把汉语语汇转为五笔编码的工具。

目前，市面上对五笔的支持不太好，通过这个工具可以把网络常用词语转换为五笔编码
后继再加以调整便可直接应用在一些软件上

~~ 至于为什么用Java写？因为我还不会C（ ~~

自己用的，各位笑笑就好。

特别感谢大佬CNMan的项目[UnicodeCJK-WuBi](https://github.com/CNMan/UnicodeCJK-WuBi/tree/master)

---

### 如何食用

1. 首先，你要先安装[JDK]  
网上许多教程，此处不多赘述

2. 在类Main中，包含了INPUT\_FILE和OUTPUT\_PATH，请自行更改输入/输出目录，默认为用户桌面

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

