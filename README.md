# lostad-android framework（<a href="http://weibo.com/lostbottle">有图有真相</a>）
## <a href="https://github.com/justjavac/free-programming-books-zh_CN">免费的编程中文书籍索引</a> ##
<img src="http://ww3.sinaimg.cn/bmiddle/45ad345ajw1ey6k1hgtyzj20k00zk77w.jpg"/>

## 一、框架简介：##

本框架是作者在长期的项目开发中积累的劳动成果，是最优秀android开发框架的整合.
项目的目标:做为二次发的框架，减少重复劳动，提高开发效率！


## 二、为什么选择使用该框架？##

1、提供了一个基础的开发框架，并为您做好了多渠道分发的相关配置，简化开发步骤，让初学者也可以轻松开发出一个专业的App。
     
      1.1 
        app打开一个activity时，框架自动记录此Activity，销毁Activity时取消记录，App退出时清空所有未处理的Activity。
      1.2  
        所有的View都View自动注入，不需要手写声明代码和findViewById代码块。
      1.3
        本框架在不断的完善，尽量集成最少的必要第三方组件，以降低维护成本
      
2、集成了大部分app开发的必要组件：

    2.1
      主界面上的Tab 做了两种演示风格，一种是tab左右滑动切换（类微信），另一种是tab不可滑动切换（类网易新闻），
      Demo中默认展示的第一种形式，点击主界面右上角的菜单按钮，可调出第二种展现风格。
    2.2
      如果您希望手机状态栏背景色与标题背景色融合，需要做二个工作：
      1) Activity继承Demo中的BaseActivity （或setSystemBarStyle(R.color.bg_title)）,
      2) Activity对应的布局文件加入属性：android:fitsSystemWindows="true" ;

    2.3
      下拉刷新上拉加载更多、圆形多边形头像、登录、注册、找回密码、自定义提示窗口;

    2.4 zxing 扫码 CaptureActivity

3、现成的工具类，提示窗口工具类（DialogUtil、UI特效工具类EffectUtil等），主界面、登陆、注册界面和基本交互流程。
   
   用户基本信息编辑的模块，仿微信的交互方式（一次只可编辑一项，完事直接保存，录入界面友好可重用性高）。
   把头像选择、裁剪封装到一个工具类里，简化您的操作过程，操作完成后，直接返回图片的bitmap !

4、修复了一些第三方组件的bug,并进行了扩展 ListviewPull、WaterDropListView 等。

   比如 pullToRefresh-ListView 类似微博的效果，进入列表自动展现刷新提示， 滚动到地步自动出现loadding.

5、良好的架构
   您直接继承listad 这个lib工程中的BaseApplication和BaseAppActivity就可以。
   当然您也要实现这两个抽象类的抽象方法。


## 三、本框架具体整合了哪些东西 ##

  Xutils （最著名的android开发框架之一,Http请求、View注入、db操作、图像加载）、
  PullToRefresh-ListView （最流行的listView）、
  WaterDropListView（下拉刷新水滴效果）、
  gson(Google的Json解析库)、
  多种形状的 ImagageView 控件等。

## 四、如何使用？##

直接下载本项目，在app项目下的gradle配置文件中的 applicationId 改成您自己的applicationId
就可以在上面进行二次开发了。


## 五、最后，期待您在使用过本项目后前来发表有任何吐槽或建议##
   后面，本项目也会放出android端的代码生成器的相关源码。


  （<a href="http://weibo.com/lostbottle">欢迎关注本人微博进行各种吐槽</a>）

 

<img src="http://ww1.sinaimg.cn/large/45ad345ajw1ey6k1kla26j20y50ke0y7.jpg"/>