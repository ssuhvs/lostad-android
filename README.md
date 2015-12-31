# lostad-android （<a href="http://weibo.com/lostbottle">有图有真相</a>）

一、框架简介：

本框架是作者在长期的项目开发中积累的劳动成果，是最优秀android开发框架的整合.
项目的目标:做为二次发的框架，减少重复劳动，提高开发效率！


二、为什么选择使用该框架？

1、提供了一个基础的开发框架，并为您做好了多渠道分发的相关配置，简化开发步骤，让初学者也可以轻松开发出一个专业的App。


2、集成了大部分app开发的必要组件：
   (下拉刷新上拉加载更多、圆形多边形头像、登录、注册、找回密码、自定义提示窗口);

3、现成的工具类，提示窗口工具类（DialogUtil、UI特效工具类EffectUtil等），主界面、登陆、注册界面和基本交互流程。

4、修复了一些第三方组件的bug,并进行了扩展 ListviewPull、WaterDropListView 等。
（比如 pullToRefresh-ListView 类似微博的效果，进入列表自动展现刷新提示， 滚动到地步自动出现loadding）.

5、良好的架构，您直接继承listad 这个lib工程中的BaseApplication和BaseAppActivity就可以。
   当然您也要实现这两个抽象类的抽象方法。


三、本框架具体整合了哪些东西

xutils （最著名的android开发框架之一）、
PullToRefresh-ListView （最流行的listView）、
gson(最好用的Json解析工具)等、
多种形状的 ImagageView 控件。

四、如何使用？

直接下载本项目，在app项目下的gradle配置文件中的 applicationId 改成您自己的applicationId
就可以在上面进行二次开发了。


五、最后，期待您在使用过本项目后前来发表有任何吐槽或建议
   后面，本项目也会放出android端的代码生成器的相关源码。


   （<a href="http://weibo.com/lostbottle">欢迎关注本人微博进行各种吐槽</a>）
