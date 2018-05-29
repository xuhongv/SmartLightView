# SmartLightView
高仿米家的筒灯控制UI

-----------------

 - 效果如下：
 
![](https://i.imgur.com/5brg7Xd.gif)


###  一. 前言；

 - 兜兜转转，不知不觉做Android开发已经快2年了，上半年一直在搞`wifi`模块开发，导致不务正业，写个自定义UI还要折腾半天，真是对不起自己的良心了！最近要对接小米开放平台，不小心看到了一个控制双色灯的界面，大概就仿了下来，特此写下此文，共勉技术！

---------------------

###  二. 效果图；


![这里写图片描述](https://img-blog.csdn.net/20180528175244508?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3hoODcwMTg5MjQ4/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)


---------------------

### 三. 功能实现；

- 亮度调节拖动条：实现灯泡和周围的涟漪的亮度变化；


- 色温调节拖动条：实现灯泡和周围的涟漪的色温变化，从冷色调到暖色调；

---------------------

### 四. 原理图；

-  下图中，基本可以看到各个绘图中需要的点的坐标了。这个有助于我们快速绘图呢！

![这里写图片描述](https://img-blog.csdn.net/20180529112021644?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3hoODcwMTg5MjQ4/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)

---------
### 五. 绘图步骤；

 - 第一步先绘制灯泡，灯泡的绘制分为绘制上面的圆弧部分+下面的缺角的矩形！注意此要有自定义view的基本常识！

```

        Path mPathCircle = new Path();
        //画圆
        mPathCircle.addCircle(getWidth() / 2, getHeight() / 2, mCircleRadial, Path.Direction.CW);

        //画一个矩形
        Path mPathReact = new Path();
        mPathReact.moveTo(mHalfWidth - mOffsetDistance + 10, mHalfHeight + mCircleRadial - 12); //设定起始点:
        mPathReact.lineTo(mHalfWidth - mOffsetDistance + 10, mHalfHeight + (int) (4 * mCircleRadial / 3));//第一条直线的终点，也是第二条直线的起点
        mPathReact.lineTo(mHalfWidth + mOffsetDistance - 10, mHalfHeight + (int) (4 * mCircleRadial / 3));//画第二条直线
        mPathReact.lineTo(mHalfWidth + mOffsetDistance - 10, mHalfHeight + mCircleRadial - 12);//第三条直线
        mPathReact.close();

        //把矩形添加进去
        mPathCircle.op(mPathReact, Path.Op.UNION);
        //画出来
        canvas.drawPath(mPathCircle, mPaintLight);

```
--------

 - 第二步绘制涟漪圆圈，步骤是画4个实心圆圈，注意是逐渐把透明度降低以及不断增加半径，这样就达到了涟漪的效果啦！

```
        for (int i = 0; i < 5; i++) {
                //涟漪圆圈的透明度
                mPaintCircle.setAlpha(partValue * i * 2);
                canvas.drawCircle(mHalfWidth, mHalfHeight + 10, mCircleRadial + 100 * i, mPaintCircle);
                mPaintLight.setAlpha((int) (2.5 * progressShow));
            }
```
--------

 - 第三步绘制灯泡下面的直线。

      - 这里其实没什么好说的，注意其的起点和终点在上面灯泡矩形的转折点的`y`坐标加数值即可！

---------
### 六. 透明的和色彩的变化；

-----

 - 上面做好了还只是一个静态的页面，最主要的是如何提供接口，可以实现动态地更新暗亮变化和颜色的变化相结合！
 
   - 技术点①：第一个图看到的亮度调节只是调节亮度，不参与色温的调节！ 同样地，色温调节只是调节色彩，不参与亮度的调节！这个理解就是<font color=red>亮度调节只是调节画笔的**透明度**，而色温调节只是改变了画笔的**颜色**！

  - 技术点②：透明度的调节其实是简单的，其只需要调节一个数值即可！但是色温的调节是个技术活，需要自己计算下，下面是我总结出来的公式！注意我们的最暖色其实就是橘黄色！
  - 技术点③：下图的分析总结下，最暖色（255，183，0）和最冷色（255，255，255）变化的只是后面的2个参数`green`和`blue`，抓住这2个参数的变化，我发现每当`blue`加4，`green`才加一。所以下面要除于4！

- ![这里写图片描述](https://img-blog.csdn.net/201805291524318?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3hoODcwMTg5MjQ4/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)

----------------

```
 public synchronized void setProgressTemper(int tempProgressTemper) {

        //得到色温调节的数值，范围是0到100；需要转换
        int progressTemper = (int) (tempProgressTemper * 2.5);
        this.progressTemper = progressTemper;
        //灯泡的色彩
        mPaintLight.setARGB(255, 255, progressTemper / 4 + 183, progressTemper);
        //涟漪圈的色彩
        mPaintCircle.setARGB(150, 255, progressTemper / 4 + 183, progressTemper);
        postInvalidate();
    }
```

---------
### 七. 其他；

-----

![这里写图片描述](https://img-blog.csdn.net/20180529153547385?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3hoODcwMTg5MjQ4/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)

------

下载：https://download.csdn.net/download/xh870189248/10445098
 gitHub：https://github.com/xuhongv/SmartLightView
