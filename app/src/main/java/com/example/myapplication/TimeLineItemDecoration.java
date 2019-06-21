package com.example.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.View;

public class TimeLineItemDecoration extends RecyclerView.ItemDecoration {
    public final static int STATUS_REPORT =0;//上报
    public final static int STATUS_MEDIATION=1;//调处
    public final static int STATUS_COMPLETE =2;//完成

    private Context context;
    // 时间轴线画笔
    private Paint mPaint;
    private Paint circlePaint;
    //左边年份
    private TextPaint paintYear;
    // 左边月份
    private TextPaint paintMonth;

    // 轴点半径
    public final int circle_radius = 10;
    // 图标
    private Bitmap timeIcon;

    public static final int LEFT_OFFSETS = 300;
    public static final int TOP_OFFSETS = 100;
    public static final int BOTTOM_OFFSETS = 0;

    public static float[] TOUCH_RECT = {0, 0, 0, 0};//rect xy ,xy


    public TimeLineItemDecoration(Context context) {
        this.context = context;
        init();
    }

    public TimeLineItemDecoration(Context context, String timeYear) {
        this.context = context;
        this.timeYear = timeYear;
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(context.getResources().getColor(R.color.gray_999999));
        mPaint.setAntiAlias(true);

        circlePaint =new Paint();
        circlePaint.setColor(context.getResources().getColor(R.color.gray_999999));
        circlePaint.setAntiAlias(true);

        paintYear = new TextPaint();
        paintYear.setColor(context.getResources().getColor(R.color.year_blue));
        paintYear.setTextSize(30);
        paintYear.setAntiAlias(true);

        paintMonth = new TextPaint();
        paintMonth.setColor(context.getResources().getColor(R.color.month_gray));
        paintMonth.setTextSize(28);
        paintMonth.setAntiAlias(true);

        timeIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.ico_time);
        // 得到新的图片
        timeIcon = zoomImg(timeIcon, 60, 60);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(LEFT_OFFSETS, TOP_OFFSETS, 0, BOTTOM_OFFSETS);
    }

    private boolean isHeader = true;
    private String timeYear = "2019-6-18";

    @Override
    public void onDraw(Canvas c, final RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            // 获取每个Item对象
            final View child = parent.getChildAt(i);

            // 轴点圆心(x,y)
            float centerx = child.getLeft() - LEFT_OFFSETS / 3;
            float centery = child.getTop() + child.getPaddingTop() + circle_radius * 1.5f;
            if (isHeader) {
                isHeader = false;
                child.setTag("head");
            }
            if (child.getTag().equals("head")) {
                c.drawBitmap(timeIcon, centerx - timeIcon.getWidth() / 2, centery / 2, mPaint);
                c.drawLine(centerx, centery + timeIcon.getHeight() / 4 - circle_radius, centerx, centery * 2, mPaint);

                // 画年份Title Text
                final float timeTitleX = child.getLeft() - LEFT_OFFSETS * 0.9f;
                final float timeTitleY = centery + timeIcon.getHeight() / 2;
                c.drawText(timeYear, timeTitleX, timeTitleY, paintYear);


                /*画三角形*/
                int sideLength = 30;
                float[] startPoint = {timeTitleX + paintYear.measureText(timeYear), timeTitleY + (paintYear.descent() + paintYear.ascent())};//三角形起始位置
                Path path = new Path();
                path.moveTo(startPoint[0], startPoint[1]);
                path.lineTo(startPoint[0] + sideLength, startPoint[1]);
                path.lineTo(startPoint[0] + sideLength / 2, startPoint[1] + sideLength / 2);
                path.close();
                c.drawPath(path, paintYear);//借用年份的画笔
                saveClickRect(timeTitleX,timeTitleY,sideLength);
                continue;
            }

            OperationHistoryEntity entity = (OperationHistoryEntity) child.getTag();
            if (i == 1) {//第一个ITEM .
                c.drawLine(centerx, child.getTop(), centerx, child.getBottom() + TOP_OFFSETS, mPaint);
            } else {
                c.drawLine(centerx, child.getTop() - TOP_OFFSETS, centerx, child.getBottom() + TOP_OFFSETS, mPaint);
            }

            //绘轴点
            if (entity.getStatus() == STATUS_COMPLETE) {
                circlePaint.setColor(context.getResources().getColor(R.color.green_8FC31F));
                c.drawCircle(centerx, centery, circle_radius + 5, circlePaint);
            } else if (entity.getStatus() == STATUS_REPORT){
                circlePaint.setColor(context.getResources().getColor(R.color.gray_999999));
                c.drawCircle(centerx, centery, circle_radius, circlePaint);
            }else if (entity.getStatus() == STATUS_MEDIATION){
                circlePaint.setColor(context.getResources().getColor(R.color.gray_999999));
                c.drawCircle(centerx, centery, circle_radius, circlePaint);
            }

            //左侧月份
            float Text_x = child.getLeft() - LEFT_OFFSETS * 0.8f;
            float Text_y = centery - circle_radius;

            if (null != entity.getTime() && TextUtils.isEmpty(entity.getTime())) return;
            c.drawText(entity.getTime(), Text_x + 5, Text_y + 20, paintMonth);
        }
    }

    private View.OnClickListener clickListener;

    public void setOnClickeListener(View.OnClickListener clickeListener) {
        this.clickListener = clickeListener;
    }

    public void setTimeText(String time) {
        timeYear = time;
    }

    public Bitmap zoomImg(Bitmap bm, int newWidth, int newHeight) {
        // 获得图片的宽高
        int width = bm.getWidth();
        int height = bm.getHeight();
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片
        Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
        return newbm;
    }

    /**
     * 点击区域
     * @param startX
     * @param startY
     * @param padding 预留边
     */
    private void  saveClickRect(float startX,float startY,int padding){
        float textHight = (paintYear.descent() + paintYear.ascent());
        TOUCH_RECT[0] = startX - padding;
        TOUCH_RECT[1] = startY - padding;
        TOUCH_RECT[2] = startX + paintYear.measureText(timeYear) + padding;
        TOUCH_RECT[3] = startY + Math.abs(textHight) + padding;
    }
}
