package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by 傻明也有春天 on 2016/4/14.
 */
public class HistogramView extends View {
    private Canvas canvas = null;
    public HistogramView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public HistogramView(Context context, AttributeSet attr) {
        super(context, attr);
    }

    private Paint myPaint;
    private static final String myString1 = "历史骑行记录";

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);
        myPaint = new Paint();
        this.canvas = canvas;
        //绘制标题
        myPaint.setColor(Color.BLACK); //设置画笔颜色
        myPaint.setTextSize(40);//设置文字大小
       // canvas.drawText(myString1, 450, 140, myPaint);
        //绘制坐标轴
        canvas.drawLine(200, 250, 200, 750, myPaint);//纵坐标轴
        canvas.drawLine(200, 750, 900, 750, myPaint);//横坐标轴
        int[] array1 = new int[]{0, 60, 120, 180, 240, 300, 360, 420};

        //绘制纵坐标刻度
        myPaint.setTextSize(25);//设置文字大小
        canvas.drawText("单位：米", 90, 230, myPaint);
        for (int i = 0; i < array1.length; i++) {
            canvas.drawLine(196, 750 - array1[i], 200, 750 - array1[i], myPaint);
            canvas.drawText(array1[i] + "", 145, 750 - array1[i], myPaint);
        }
        //绘制横坐标文字
        int[] array3 = new int[]{0,110,210,310,410,510,610,710};
        String[] array2 = new String[]{"周一", "周二", "周三", "周四", "周五", "周六", "周日"};

        for (int i = 0; i < array2.length; i++) {
            canvas.drawText(array2[i], array3[i] + 205, 780, myPaint);
        }
        //绘制条形图
        myPaint.setColor(getResources().getColor(R.color.blue5)); //设置画笔颜色
        myPaint.setStyle(Paint.Style.FILL); //设置填充
        canvas.drawRect(new Rect(225, 750 - 56, 245, 750), myPaint);//画一个矩形,前两个参数是矩形左上角坐标，后两个参数是右下角坐标
        canvas.drawRect(new Rect(325, 750 - 98, 345, 750), myPaint);//第二个矩形
        canvas.drawRect(new Rect(425, 750 - 207, 445, 750), myPaint);//第三个矩形
        canvas.drawRect(new Rect(525, 750 - 318, 545, 750), myPaint);//第四个矩形
        myPaint.setColor(Color.BLACK); //设置画笔颜色
        canvas.drawText("56.32", 215, 750 - 58, myPaint);//第一个矩形的数字说明
        canvas.drawText("98.00", 315, 750 - 100, myPaint);
        canvas.drawText("207.65", 415,750 - 209, myPaint);
        canvas.drawText("318.30", 515, 750 - 320, myPaint);
        //绘制出处
        myPaint.setColor(Color.BLACK); //设置画笔颜色
        myPaint.setTextSize(25);//设置文字大小
        //canvas.drawText(myString2, 20, 560, myPaint);
    }
    public void onDrawRect(int startX,int startY,int stopX,int stopY,Paint paint){
        this.canvas.drawRect(new Rect(startX,startY,stopX,stopY),paint);

    }


}
