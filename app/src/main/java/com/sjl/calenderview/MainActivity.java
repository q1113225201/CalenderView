package com.sjl.calenderview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sjl.calenderlibrary.bean.CalenderBean;
import com.sjl.calenderlibrary.util.CalenderUtil;
import com.sjl.calenderlibrary.view.CalenderItemView;
import com.sjl.calenderlibrary.view.CalenderView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends Activity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    private TextView tvDate;
    private TextView tvSelectDate;
    private CalenderView calenderView;
    private Button btnGetSelectDate;
    private Button btnSetCurrentDate;
    private Button btnSelectDate;
    private Button btnPre;
    private Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        tvDate = (TextView) findViewById(R.id.tvDate);
        tvSelectDate = (TextView) findViewById(R.id.tvSelectDate);
        calenderView = (CalenderView) findViewById(R.id.calenderView);
        calenderView.setOnItemSelectListener(new CalenderItemView.OnItemSelectListener() {
            @Override
            public void onSelect(CalenderBean calenderBean) {
                tvSelectDate.setText(calenderBean.toString());
            }
        });
        calenderView.setOnCalenderPageChangeListener(new CalenderView.OnCalenderPageChangeListener() {
            @Override
            public void onChange(CalenderBean calenderBean) {
                tvDate.setText(String.format("%d-%d",calenderBean.getYear(),calenderBean.getMonth()));
            }
        });
        calenderView.setCurrentCalender(CalenderUtil.getCalender(new Date()));
        btnGetSelectDate = (Button) findViewById(R.id.btnGetSelectDate);
        btnGetSelectDate.setOnClickListener(this);
        btnSetCurrentDate = (Button) findViewById(R.id.btnSetCurrentDate);
        btnSetCurrentDate.setOnClickListener(this);
        btnSelectDate = (Button) findViewById(R.id.btnSelectDate);
        btnSelectDate.setOnClickListener(this);
        btnPre = (Button) findViewById(R.id.btnPre);
        btnPre.setOnClickListener(this);
        btnNext = (Button) findViewById(R.id.btnNext);
        btnNext.setOnClickListener(this);

        //设置当前日期
        CalenderBean calenderBean = CalenderUtil.getCalender(new Date());
        calenderView.setCurrentCalender(calenderBean);
        tvDate.setText(String.format("%d-%d",calenderBean.getYear(),calenderBean.getMonth()));
    }

    @Override
    public void onClick(View v) {
        CalenderBean calenderBean;
        switch (v.getId()) {
            case R.id.btnGetSelectDate:
                calenderBean = calenderView.getSelectDate();
                tvSelectDate.setText(calenderBean != null ? calenderBean.toString() : "null");
                break;
            case R.id.btnSetCurrentDate:
                calenderView.setCurrentCalender(CalenderUtil.getCalender(new Date()));
                break;
            case R.id.btnSelectDate:
                calenderView.setSelectDate(CalenderUtil.getCalender(new Date()));
                break;
            case R.id.btnPre:
                calenderBean = calenderView.getCurrentCalender();
                calenderView.setCurrentCalender(CalenderUtil.getPreCalender(calenderBean.getYear(), calenderBean.getMonth()));
                break;
            case R.id.btnNext:
                calenderBean = calenderView.getCurrentCalender();
                calenderView.setCurrentCalender(CalenderUtil.getNextCalender(calenderBean.getYear(), calenderBean.getMonth()));
                break;
        }
    }
}
