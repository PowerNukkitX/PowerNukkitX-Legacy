[<img alt="PowerNukkitX" width="838" src="https://raw.githubusercontent.com/PowerNukkitX/PowerNukkitX/master/blob/images/PNX_BANNER.png" />](https://www.powernukkitx.com)

<h2>Version 1.0 of PNX has been discontinued, please check out our version 2.0</h2>
<h2>🌐Need to switch languages?&ensp;/&ensp;多语言文档</h2>

[![Discord](https://img.shields.io/discord/944227466912870410?color=7289da&logo=discord&logoColor=white&style=flat-square)](https://discord.gg/BcPhZCVJHJ)
[![English](https://img.shields.io/badge/English-Click%20me-purple?style=flat-square)](/README.md)
[![简体中文](https://img.shields.io/badge/简体中文-Click%20me-purple?style=flat-square)](/blob/zh-hans/README.md)
[![繁體中文](https://img.shields.io/badge/繁體中文-Click%20me-purple?style=flat-square)](/blob/zh-hant/README.md)
[![License: GPL v3](https://img.shields.io/badge/License-GPL%20v3-blue.svg?style=flat-square)](/LICENSE)
[![ChangeLog](https://img.shields.io/badge/ChangeLog-blue?style=flat-square)](/CHANGELOG.md)
[![FAQ](https://img.shields.io/badge/FAQ-blue?style=flat-square)](https://www.powernukkitx.com/doc/en-us/faq/common.html)
[![PNX-DOC](https://img.shields.io/badge/PNX-DOC-blue?style=flat-square)](https://doc.powernukkitx.cn)
[![Maven Central](https://img.shields.io/maven-central/v/cn.powernukkitx/powernukkitx.svg?label=Maven%20Central&style=flat-square)](https://search.maven.org/search?q=g:%22cn.powernukkitx%22%20AND%20a:%22powernukkitx%22)
[![](https://www.jitpack.io/v/PowerNukkitX/PowerNukkitX.svg)](https://www.jitpack.io/#PowerNukkitX/PowerNukkitX)

🤔What is PowerNukkitX ?
---
[PowerNukkitX](https://github.com/PowerNukkitX/PowerNukkitX) is a branch version based on [PowerNukkit](https://github.com/PowerNukkit/PowerNukkit), developed and maintained by [PowerNukkitX](https://github.com/PowerNukkitX), any problems can be submitted to [Issue](https://github.com/PowerNukkitX/PowerNukkitX/issues) feedback.

Join US:

* [Discord](https://discord.gg/BcPhZCVJHJ)
* [QQ](https://jq.qq.com/?_wv=1027&k=6rm3gbUI)

What's new in PowerNukkitX? Let's See：

- Support for 1.20.60 protocol.
- Support for 384 world height.
- Support for The Nether world, no need to install additional plugin.
- Support 3D biomes.<!--Custom dimension interface TODO-->
- Support for vanilla commands and command block.
- Built-in [Terra](https://github.com/PolyhedralDev/Terra) generator (if you have
   questions [click here to view](https://www.powernukkitx.com/doc/en-us/faq/terra.html)) .
- Support for writing plugins using the JavaScript language (development documentation can be
   found [here](https://www.powernukkitx.com/doc/en-us/dev/js/setup_dev_env.html)).
- Support custom blocks / items / entity (refinement in progress, [documentation](https://doc.powernukkitx.cn/) to be
   added).
- Original Entity AI framework, no need to install MobPlugin (under development, Not all creature completed).

---

## 🎮 How to use

### 1.Run from [PNX-CLI](https://github.com/PowerNukkitX/PNX-CLI):

1. Download the `PNX-CLI-Jar.zip` latest version from [release](https://github.com/PowerNukkitX/PNX-CLI/releases)
2. Run `java -jar PNX-CLI-0.1.6.jar`

you can [click here](https://www.powernukkitx.com/doc/en-us/Get_Started.html) to get more information.

### 2.Run from command:

PowerNukkitX requires a minimum Java version of 17, please install it yourself and configure the environment
variables.

1. Download libs.tar.gz and powernukkitx.jar from [release](https://github.com/PowerNukkitX/PowerNukkitX/releases)
2. Extract the libs folder in libs.tar.gz to the same path as powernukkitx.jar
3. Run the following command

Windows Version

```
java -Dfile.encoding=UTF-8 ^
-Djansi.passthrough=true ^
-Dterminal.ansi=true ^
-XX:+UnlockExperimentalVMOptions ^
-XX:+UseG1GC ^
-XX:+UseStringDeduplication ^
-XX:+EnableJVMCI ^
--module-path=.\libs\graal-sdk-23.0.0.jar;.\libs\truffle-api-23.0.0.jar; ^
--add-opens java.base/java.lang=ALL-UNNAMED ^
--add-opens java.base/java.io=ALL-UNNAMED ^
-cp .\powernukkitx.jar;.\libs\* ^
cn.nukkit.Nukkit
```

Linux Version

```
java -Dfile.encoding=UTF-8 \
-Djansi.passthrough=true \
-Dterminal.ansi=true \
-XX:+UnlockExperimentalVMOptions \
-XX:+UseG1GC \
-XX:+UseStringDeduplication \
-XX:+EnableJVMCI \
--module-path=./libs/truffle-api-23.0.0.jar:./libs/graal-sdk-23.0.0.jar: \
--add-opens java.base/java.lang=ALL-UNNAMED \
--add-opens java.base/java.io=ALL-UNNAMED \
-cp ./powernukkitx.jar:./libs/* \
cn.nukkit.Nukkit
```

### 3.Run from docker image:

https://hub.docker.com/r/coolloong/powernukkitx

---
📌 Statement
---
<!-- Just Do it :-D -->
<!--使用DeepL暴力翻译-->
**PowerNukkitX** is a modified and optimized version based on PowerNukkit and Nukkit, fixing its bugs, adding more
feature support, such as [LiteLoader](https://github.com/PowerNukkitX/LiteLoader-Libs) (WIP) plugin
compatibility.

* Please note that **PowerNukkitX** is not maintained by Cloudburst developers, it relies on the open source community
  to generate electricity with love. If you encounter any problems when using PowerNukkitX, you should
  first [create an issue](https://github.com/PowerNukkitX/PowerNukkitX/issues) in this repository (please also note to
  read the rules in
  the [Contribution Help Guide](/CODE_OF_CONDUCT.md)).

* We also recommend that you perform regular backups and other operations, and use the plug-ins made
  for [PowerNukkitX](https://github.com/PowerNukkitX/PowerNukkitX) and
  use [stable version](https://github.com/PowerNukkitX/PowerNukkitX/releases) for deployment.

<!--结束-->

* Most [Cloudburst Nukkit](https://github.com/cloudburstmc/nukkit) plugins are supported but they may not understand the
  new blocks and items and the new stuff that you can do with PowerNukkitX.

🧾 About Nukkit

[Nukkit](https://github.com/Nukkit/Nukkit) is nuclear-powered server software for Minecraft: Pocket Edition. It has a
few key advantages over other server software:

* Written in Java, Nukkit is faster and more stable.
* Having a friendly structure, it's easy to contribute to Nukkit's development and rewrite plugins from other platforms
  into Nukkit plugins.
* Nukkit is **under improvement** yet, we welcome contributions.

---

🛠 Creating Plugins
---

* Add PowerNukkitX to your dependencies (it is hosted by Maven Central, so you don't need to specify a custom repository)
  .

[Click here to see full Gradle Example](https://github.com/PowerNukkitX/ExamplePlugin-Gradle)

```groovy
repositories {
    mavenCentral()
}

dependencies {
    compile group: 'cn.powernukkitx', name: 'powernukkitx', version: '1.20.40-r1'
}
```

[Click here to see full Maven Example](https://github.com/PowerNukkitX/ExamplePlugin-Maven)
```xml
<dependencies>
    <dependency>
        <groupId>cn.powernukkitx</groupId>
        <artifactId>powernukkitx</artifactId>
        <version>1.20.40-r1</version>
    </dependency>
</dependencies>
```
---

🛠  Build JAR file
---
- `git clone https://github.com/PowerNukkitX/PowerNukkitX`
- `cd PowerNukkitX`
- `git submodule update --init`
- `./mvnw clean package`

* The compiled JAR can be found in the target/ directory.
* Please check how to use the compiled JAR file when you run it.
* To run the jar please see [How to use](#Steps:).

🧐 Contributing
---
Please read the [CONTRIBUTING](/CODE_OF_CONDUCT.md) guide before submitting any issue.  Issues with insufficient information or in the wrong format will be closed and will not be reviewed.

---

🌐 Links
---

- __[🌐 PowerNukkitX Website](https://powernukkitx.cn/)__
- __[👩🏽‍💻 PowernNukkitX Document library](https://doc.powernukkitx.cn/)__
- __[💬 PowerNukkitX Discord](https://discord.gg/BcPhZCVJHJ)__
- __[💾 Download PowerNukkitX Released Build](https://github.com/PowerNukkitX/PowerNukkitX/releases)__
- __[💾 Download PowerNukkitX Snapshot Build](https://github.com/PowerNukkitX/PowerNukkitX/actions)__
- __[🔌 Cloudburst Nukkit Plugins](https://cloudburstmc.org/resources/categories/nukkit-plugins.1/)__
- __[🔌 PowerNukkitX Plugins Hub](https://powernukkitx.com/hub/plugin)__

🎨  Statistics
---

[![Issues](https://img.shields.io/github/issues/PowerNukkitX/PowerNukkitX?style=flat-square)](https://github.com/PowerNukkitX/PowerNukkitX/issues)
[![Issues-Closed](https://img.shields.io/github/issues-closed/PowerNukkitX/PowerNukkitX?style=flat-square)](https://github.com/PowerNukkitX/PowerNukkitX/issues?q=is%3Aissue+is%3Aclosed)
[![Pull-pr](https://img.shields.io/github/issues-pr/PowerNukkitX/PowerNukkitX?style=flat-square)](https://github.com/PowerNukkitX/PowerNukkitX/pulls)
[![Pull-pr-closed](https://img.shields.io/github/issues-pr-closed/PowerNukkitX/PowerNukkitX?style=flat-square)](https://github.com/PowerNukkitX/PowerNukkitX/pulls?q=is%3Apr+is%3Aclosed)

## ⭐ Stargazers over time

[![Stargazers over time](https://starchart.cc/PowerNukkitX/PowerNukkitX.svg)](https://starchart.cc/PowerNukkitX/PowerNukkitX)

2019 - 2023 © [BlocklyNukkit](https://wiki.blocklynukkit.com) | [PowerNukkitX](https://www.powernukkitx.com)

<!--
2022/2/2 - 2023/12/26
这真的只是句留言，真的
如果你看到了这句话，恭喜你也发现了个小彩蛋（雾）
Message from chencu
-->