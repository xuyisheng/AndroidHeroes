package com.imooc.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

public class RoundRectShaderView extends View {

    private BitmapShader mBitmapShader;
    private Bitmap mBitmap;
    private Paint mPaint;

    public RoundRectShaderView(Context context) {
        super(context);
    }

    public RoundRectShaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RoundRectShaderView(Context context, AttributeSet attrs,
                               int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mBitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.ic_launcher);
        mBitmapShader = new BitmapShader(mBitmap,
                Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
//        mBitmap = BitmapFactory.decodeResource(getResources(),
//                R.drawable.test);
//        mBitmapShader = new BitmapShader(mBitmap,
//                Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        mPaint = new Paint();
        mPaint.setShader(mBitmapShader);
        canvas.drawCircle(500, 250, 200, mPaint);
    }
}
