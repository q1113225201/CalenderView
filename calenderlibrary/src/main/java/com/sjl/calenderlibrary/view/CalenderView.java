package com.sjl.calenderlibrary.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.sjl.calenderlibrary.bean.CalenderBean;
import com.sjl.calenderlibrary.util.CalenderUtil;

import java.util.Date;

/**
 * CalenderView
 *
 * @author SJL
 * @date 2017/6/16
 */

public class CalenderView extends View {
    public CalenderView(Context context) {
        this(context, null);
    }

    public CalenderView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CalenderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private int itemWidth;
    private int itemHeight;
    private int rows = 7;
    private int cols = 6;
    private int preSelectDate = -1;
    private int selectDate = -1;
    private int[][] dates = new int[cols][rows];

    //头部字体颜色
    private int headerTextColor;
    //头部字体大小
    private int headerTextSize;
    //头部画笔
    private Paint headerPaint;

    //日期字体颜色
    private int dateTextColor;
    //日期字体大小
    private int dateTextSize;
    //日期画笔
    private Paint datePaint;

    //背景色
    private int backColor;
    //选中色
    private int selectColor;
    //选中项画笔
    private Paint selectItemPaint;

    private void init() {
        initAttrs();
        initTools();
    }

    private void initAttrs() {
        headerTextColor = Color.BLACK;
        headerTextSize = 56;

        dateTextColor = Color.BLACK;
        dateTextSize = 56;

        backColor = getDrawingCacheBackgroundColor();
        selectColor = Color.GRAY;

        CalenderBean calenderBean = CalenderUtil.getCalender(new Date());
        initDates(calenderBean.getYear(), calenderBean.getMonth());
    }

    private void initDates(int year, int month) {
        CalenderBean calenderBean = CalenderUtil.getCalender(year, month);
        int week = calenderBean.getWeek();
        int wholeMonth = calenderBean.getWholeMonth();
        int num = 0;
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                if ((i == 0 && j == week) || num > 0) {
                    num++;
                }
                dates[i][j] = num;
                if (num > wholeMonth) {
                    dates[i][j] = 0;
                }
            }
        }
    }

    private void initTools() {
        //初始化头部画笔
        headerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        headerPaint.setColor(headerTextColor);
        headerPaint.setTextSize(headerTextSize);

        //初始化具体日期画笔
        datePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        datePaint.setColor(dateTextColor);
        datePaint.setTextSize(dateTextSize);

        //初始化选中画笔
        selectItemPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        selectItemPaint.setColor(selectColor);
        selectItemPaint.setStrokeWidth(itemWidth);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(MeasureSpec.makeMeasureSpec(widthMeasureSpec, MeasureSpec.EXACTLY));
        itemWidth = width / rows;
        itemHeight = itemWidth;
        setMeasuredDimension(itemWidth * rows, itemHeight * cols);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawHeader(canvas);
        drawDayItem(canvas);
    }

    /**
     * 头部绘制
     *
     * @param canvas
     */
    private void drawHeader(Canvas canvas) {
        drawOneText(canvas, "日", itemWidth / 2 + itemWidth * 0, itemWidth / 2, headerPaint);
        drawOneText(canvas, "一", itemWidth / 2 + itemWidth * 1, itemWidth / 2, headerPaint);
        drawOneText(canvas, "二", itemWidth / 2 + itemWidth * 2, itemWidth / 2, headerPaint);
        drawOneText(canvas, "三", itemWidth / 2 + itemWidth * 3, itemWidth / 2, headerPaint);
        drawOneText(canvas, "四", itemWidth / 2 + itemWidth * 4, itemWidth / 2, headerPaint);
        drawOneText(canvas, "五", itemWidth / 2 + itemWidth * 5, itemWidth / 2, headerPaint);
        drawOneText(canvas, "六", itemWidth / 2 + itemWidth * 6, itemWidth / 2, headerPaint);
    }

    /**
     * 日期绘制
     *
     * @param canvas
     */
    private void drawDayItem(Canvas canvas) {
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                if (dates[i][j] == preSelectDate) {
                    drawItem(canvas, itemWidth * j + itemWidth / 2, itemHeight * (i + 1) + itemHeight / 2, false);
                }
                if (dates[i][j] == selectDate) {
                    drawItem(canvas, itemWidth * j + itemWidth / 2, itemHeight * (i + 1) + itemHeight / 2, true);
                }
                if (dates[i][j] > 0) {
                    drawOneText(canvas, String.valueOf(dates[i][j]), itemWidth * j + itemWidth / 2, itemHeight * (i + 1) + itemHeight / 2, datePaint);
                }
            }
        }
    }

    private void drawItem(Canvas canvas, int centerX, int centerY, boolean isSelect) {
        Rect rect = new Rect(centerX - itemWidth / 2, centerY - itemHeight / 2, centerX + itemWidth / 2, centerY + itemHeight / 2);
        selectItemPaint.setColor(isSelect ? selectColor : backColor);
//        canvas.drawRect(rect,selectItemPaint);

        canvas.drawCircle(centerX, centerY, Math.min(itemWidth, itemHeight) / 2, selectItemPaint);
    }

    /**
     * 绘制一个文字
     *
     * @param canvas
     * @param text
     * @param centerX
     * @param centerY
     * @param paint
     */
    private void drawOneText(Canvas canvas, String text, int centerX, int centerY, Paint paint) {
        float textWidth = paint.measureText(text);
        canvas.drawText(text, centerX - textWidth / 2, centerY - (paint.descent() + paint.ascent()) / 2, paint);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            float x = event.getX();
            float y = event.getY();
            for (int i = 0; i < cols; i++) {
                for (int j = 0; j < rows; j++) {
                    if (x > itemWidth * j && x < itemWidth * (j + 1) && y > itemHeight * (i + 1) && y < itemHeight * (i + 2)) {
                        preSelectDate = selectDate;
                        selectDate = dates[i][j];
                        invalidate();
                    }
                }
            }
            return true;
        }
        return super.onTouchEvent(event);
    }
}
