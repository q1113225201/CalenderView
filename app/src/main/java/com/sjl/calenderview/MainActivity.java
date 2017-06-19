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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends Activity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    private TextView tvDate;
    private CalenderItemView calenderItemView;
    private Button btnPre;
    private Button btnNext;
    private Button btnJump;
    private ViewPager viewPager;

    private int year, month;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        tvDate = (TextView) findViewById(R.id.tvDate);
        calenderItemView = (CalenderItemView) findViewById(R.id.calenderItemView);
        btnPre = (Button) findViewById(R.id.btnPre);
        btnPre.setOnClickListener(this);
        btnNext = (Button) findViewById(R.id.btnNext);
        btnNext.setOnClickListener(this);
        btnJump = (Button) findViewById(R.id.btnJump);
        btnJump.setOnClickListener(this);
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        CalenderBean calenderBean = CalenderUtil.getCalender(new Date());
        year = calenderBean.getYear();
        month = calenderBean.getMonth();
        tvDate.setText(year + "-" + month);

        initViewPager();
    }

    private CalenderViewAdapter calenderViewAdapter;

    private void initViewPager() {
        initCalenderBeanList();
        calenderViewAdapter = new CalenderViewAdapter(this, list);
        viewPager.setAdapter(calenderViewAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                calenderViewAdapter.setCurrentPosition(position);
                tvDate.setText(list.get(position%3).getYear()+","+list.get(position%3).getMonth());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        int currentPosition = Integer.MAX_VALUE/2;
        if(currentPosition%3==2){
            currentPosition++;
        }else if(currentPosition%3==1){
            currentPosition--;
        }
        viewPager.setCurrentItem(currentPosition);
    }

    private List<CalenderBean> list;

    private void initCalenderBeanList() {
        list = new ArrayList<>();
        CalenderBean calenderBean = CalenderUtil.getCalender(new Date());
        list.add(calenderBean);
        list.add(CalenderUtil.getNextCalender(calenderBean.getYear(),calenderBean.getMonth()));
        list.add(CalenderUtil.getPreCalender(calenderBean.getYear(),calenderBean.getMonth()));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnPre:
                month--;
                if (month <= 0) {
                    month = 12;
                    year--;
                }
                calenderItemView.setDate(year, month);
                tvDate.setText(year + "-" + month);
                break;
            case R.id.btnNext:
                month++;
                if (month >= 12) {
                    month = 1;
                    year++;
                }
                calenderItemView.setDate(year, month);
                tvDate.setText(year + "-" + month);
                break;
            case R.id.btnJump:
                startActivity(new Intent(this,CalenderViewActivity.class));
                break;
        }
    }
}
