# FFXIV Translation Patch Tool

FFXIV的中文汉化器。更多信息可参考[Wiki页面](https://github.com/GpointChen/FFXIVChnTextPatch-GP/wiki)。

English description can be found in [Wiki pages](https://github.com/GpointChen/FFXIVChnTextPatch-GP/wiki).

相较于原版：

1. 针对5.5X以后版本修正中文字库补丁。
2. 新增使用CSV进行汉化的功能。CSV是使用修改过的SaintCoinach输出。
3. 删除原版exe中与teemo连线的部分。

## 使用

目前可以使用CSV、中国服文件或他人制作的汉化覆盖档进行汉化。

为了避免更新时出现问题，建议每次更新前先还原文件，下载完更新后再次汉化。

还原时不需设置文件夹，直接点击还原即可。

请注意，如果没有额外备份原游戏文件，请不要重复汉化，因为会覆盖`backup`文件夹里面的备份档。

请从[release](https://github.com/GpointChen/FFXIVChnTextPatch-GP/releases)下载。

<img src="https://github.com/GpointChen/FFXIVChnTextPatch-GP/blob/master/docs/fig1.png?raw=true" width="480px" />

<img src="https://github.com/GpointChen/FFXIVChnTextPatch-GP/blob/master/docs/fig2.png?raw=true" width="480px" />

### 如何使用CSV进行汉化？（推荐）

0. 下载右边的release版本或自行编译
1. 开启EXE程序
![](https://i.imgur.com/RPim0G0.png)
2. 点选「设置」
![](https://i.imgur.com/OypMCof.png)
3. 选择FFXIV游戏根目录（例如：`D:\FFXIV\SquareEnix\FINAL FANTASY XIV - A Realm Reborn`）
4. 「文件语言」：CSV代表使用`resource/rawexd`里面的CSV文件进行汉化（推荐）
5. 「原始语言」：想要覆盖游戏中的哪种语言（我自己是覆盖日文，不保证覆盖其他语言会不会有问题）
6. 「目标语言」：（不需选择）
7. 点击「确定」
![](https://i.imgur.com/RPim0G0.png)
8. 点击「汉化」

其他汉化方法不保证100%可用，请参考[Wiki页面](https://github.com/GpointChen/FFXIVChnTextPatch-GP/wiki)。

## 编译笔记

[制作过程的笔记可以参考这里。](https://hackmd.io/@GpointChen/SJi_gv-ad)

如果你是使用 MacOS，可能需要参考[这篇](https://github.com/GpointChen/FFXIVChnTextPatch-GP/blob/master/docs/MACOS_BUILD.md)。  
如果你是使用 SteamOS（在 SteamDeck 上使用），可能需要参考[这篇](https://github.com/GpointChen/FFXIVChnTextPatch-GP/blob/master/docs/LINUX_BUILD.md)。

## 更新注记

详参本项目的[Wiki](https://github.com/GpointChen/FFXIVChnTextPatch-GP/wiki/1.-%E9%A6%96%E9%A0%81)。

## 原項目說明

项目说明：

 此工具作用于：
 对国际服客户端(SE版)进行中文资源注入

 此程序
 默认只对国际服客户端打中文字库补丁
 不包含任何中文内容

 此项目于 2019-09-01 完全开源

使用方法：

 1.下载编译项目，或者直接下载release发布包
 2.解压运行项目
 3.选择FFXIV游戏根目录
 4.点击汉化按钮 等待
 5.Enjoy

 如果需要中文内容替换
 请自行将中文客户端的
 >最终幻想XIV/game/sqpack/ffxiv
 文件夹下的三个文件
 0a0000.win32.dat0
 0a0000.win32.index
 0a0000.win32.index2
 复制到汉化工具的
 >resource/text
 文件夹下重新运行程序即会自动读取

 PS:这次的补丁包含了字库内容
 所以不需要重新再打字库补丁
 PS2:每次汉化流程都会备份当前文件
 所以避免在已经汉化的文件上进行二度汉化
 这样会备份已汉化文件导致还原回滚失效
 PS3:因为不确定更新是否会覆盖文件
 所以在每次更新前尽量还原文件以免游戏导致不测

 注意:
 繁体中文/正体中文 版本
 可能因为翻译原因有部分的BUG存在
 请慎用使用

 特别注意：
 本程序采取修改客户端的形式进行中文资源的加载
 使用本程序表示你已经知晓这是违反官方规则的操作
 确认自行承担使用程序带来的任何后果

免责声明：

 1.此项目仅供学习技术以及技术交流使用
 2.严禁使用于任何商业用途
 3.请下载后24小时内删除
