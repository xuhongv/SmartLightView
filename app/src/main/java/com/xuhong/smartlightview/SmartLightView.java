package com.xuhong.smartlightview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class SmartLightView extends View {

    //灯泡画笔
    private Paint mPaintLight;

    //圆环画笔
    private Paint mPaintCircle;

    //灯泡的半径
    private int mCircleRadial;

    //灯泡上部小圈起始角度:也就是圆弧角为 360 - 120 = 240°
    private float mAngleStar = 120f;

    //总开关
    private boolean isLightOn = true;

    //接口
    private SmartLightViewOnClickListener mSmartLightViewOnClickListener;

    public SmartLightView(Context context) {
        super(context);
        initPaint();
    }

    public SmartLightView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public SmartLightView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }


    private void initPaint() {

        mPaintLight = new Paint();
        mPaintLight.setColor(getResources().getColor(R.color.colorCircle));
        mPaintLight.setAntiAlias(true);
        mPaintLight.setStrokeWidth(15f);
        mPaintLight.setStrokeCap(Paint.Cap.SQUARE);
        mPaintLight.setStyle(Paint.Style.STROKE);

        mPaintCircle = new Paint();
        mPaintCircle.setColor(getResources().getColor(R.color.colorCircle));
        mPaintCircle.setAntiAlias(true);
        mPaintCircle.setStyle(Paint.Style.FILL_AND_STROKE);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //确定灯泡的半径
        mCircleRadial = (Math.min(getWidth(), getHeight())) / 2 / 6;

        //控件宽度一半数值
        int mHalfWidth = getWidth() / 2;
        //控件高度一半数值
        int mHalfHeight = getHeight() / 2;


        //下方缺角
        float mOffsetAngle = mAngleStar - 90;
        //下发缺角的偏移长度
        int mOffsetDistance = (int) (Math.PI * mOffsetAngle / 180 * mCircleRadial);


        if (isLightOn) {
            mPaintCircle.setARGB(60, 233, 231, 239);
            canvas.drawCircle(mHalfWidth, mHalfHeight + 10, mCircleRadial + 100, mPaintCircle);

            mPaintCircle.setARGB(40, 233, 231, 239);
            canvas.drawCircle(mHalfWidth, mHalfHeight + 10, mCircleRadial + 200, mPaintCircle);

            mPaintCircle.setARGB(20, 233, 231, 239);
            canvas.drawCircle(mHalfWidth, mHalfHeight + 10, mCircleRadial + 300, mPaintCircle);

            mPaintCircle.setARGB(10, 233, 231, 239);
            canvas.drawCircle(mHalfWidth, mHalfHeight + 10, mCircleRadial + 400, mPaintCircle);

            mPaintLight.setARGB(255, 233, 231, 239);

        } else {

            mPaintLight.setARGB(80, 233, 231, 239);

        }


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


        //画灯泡下面的最下面的直线
        Path mPathLine = new Path();
        mPathLine.moveTo(mHalfWidth - mOffsetDistance + 10, mHalfHeight + (int) (4 * mCircleRadial / 3) + 50);//第一条直线的终点，也是第二条直线的起点
        mPathLine.lineTo(mHalfWidth + mOffsetDistance - 10, mHalfHeight + (int) (4 * mCircleRadial / 3) + 50);//画第二条直线
        mPathLine.close();
        canvas.drawPath(mPathLine, mPaintLight);


    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (getLightStatus())
                setLightOn(false);
            else setLightOn(true);

            if (mSmartLightViewOnClickListener != null)
                mSmartLightViewOnClickListener.lightStatusCallBack(getLightStatus());

        }
        invalidate();
        return true;
    }


    public boolean getLightStatus() {
        return isLightOn;
    }

    public void setLightOn(boolean lightOn) {
        isLightOn = lightOn;
    }

    public interface SmartLightViewOnClickListener {
        //点击事件回调
        void lightStatusCallBack(boolean isOpen);
    }

    public void setSmartLightViewOnClickListener(SmartLightViewOnClickListener mSmartLightViewOnClickListener) {
        this.mSmartLightViewOnClickListener = mSmartLightViewOnClickListener;
    }

}
