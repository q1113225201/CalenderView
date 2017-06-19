package com.sjl.calenderview;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.sjl.calenderlibrary.bean.CalenderBean;
import com.sjl.calenderlibrary.util.CalenderUtil;
import com.sjl.calenderlibrary.view.CalenderItemView;

import java.util.ArrayList;
import java.util.List;

/**
 * CalenderViewAdapter
 *
 * @author SJL
 * @date 2017/6/19
 */

public class CalenderViewAdapter extends PagerAdapter {
    private static final String TAG = "CalenderViewAdapter";
    private Context context;
    private List<CalenderBean> list;
    private List<CalenderItemView> viewList;

    public CalenderViewAdapter(Context context, List<CalenderBean> list) {
        this.context = context;
        this.list = list;
        initCalenderView();
    }

    private void initCalenderView() {
        if (viewList == null) {
            viewList = new ArrayList<>();
            viewList.add(new CalenderItemView(context));
            viewList.add(new CalenderItemView(context));
            viewList.add(new CalenderItemView(context));
        }
        for (int i = 0; i < list.size(); i++) {
            CalenderBean calenderBean = viewList.get(i).getCalenderBean();
            viewList.get(i).setDate(calenderBean.getYear(), calenderBean.getMonth());
        }
    }

    @Override
    public int getCount() {
        return (list == null || list.size() == 0) ? 0 : Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        position = position % 3;
        CalenderItemView calenderItemView = viewList.get(position);
        calenderItemView.setDate(list.get(position).getYear(), list.get(position).getMonth());
        try {
            if (calenderItemView.getParent() != null) {
                container.removeView(calenderItemView);
            }
            container.addView(calenderItemView);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return calenderItemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);
//        container.removeView((View) object);
    }

    public void setList(List<CalenderBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void setCurrentPosition(int position) {
        position = position % 3;
        CalenderBean calenderBean = list.get(position);
        int tmpPosition = (position + 1) % 3;
        list.remove(tmpPosition);
        list.add(tmpPosition, CalenderUtil.getNextCalender(calenderBean.getYear(), calenderBean.getMonth()));
        tmpPosition = (position - 1 + 3) % 3;
        list.remove(tmpPosition);
        list.add(tmpPosition, CalenderUtil.getPreCalender(calenderBean.getYear(), calenderBean.getMonth()));
        initCalenderView();
        notifyDataSetChanged();
    }
}
