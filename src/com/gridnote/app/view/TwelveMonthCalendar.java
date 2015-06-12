package com.gridnote.app.view;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.gridnote.app.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class TwelveMonthCalendar extends LinearLayout {

    private static Context context;

    private ListView lv;

    private GridView gv;

    private static List<String> lvList = new ArrayList<String>();//存放月份

    private static List<String> gvList = new ArrayList<String>();//存放�?

    private String choiceMonth;//选中的年�?

    private OnDaySelectListener callBack;//回调函数

    @SuppressLint("SimpleDateFormat")
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMM");//日期格式�?

    /**
     * 构�?函数
     * @param context
     */
    public TwelveMonthCalendar(Context context) {
        super(context);
        TwelveMonthCalendar.context = context;
        init();
    }

    /**
     * 构�?函数
     * @param context
     */
    public TwelveMonthCalendar(Context context, AttributeSet attrs) {
        super(context, attrs);
        TwelveMonthCalendar.context = context;
        init();
    }

    /**
     * 初始化日期以及view等控�?
     */
    private void init() {
        lvList.clear();//清空月份里面的数�?
        final Calendar cal = Calendar.getInstance();//获取日历实例
        Date nowDate = new Date();//获取当前date
        cal.setTime(nowDate);
        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) - 11);
        for (int i = 0; i < 12; i++) {//为月份添加数�?
            lvList.add(dateFormat.format(cal.getTime()));
            cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 1);
        }
        choiceMonth = lvList.get(lvList.size() - 1);//设置默认选中的年�?
        cal.setTime(nowDate);//cal设置为当天的
        cal.set(Calendar.DATE, 1);//cal设置当前day为当前月第一�?
        int tempSum = countNeedHowMuchEmpety(cal);//获取当前月第�?��为星期几
        int dayNumInMonth = getDayNumInMonth(cal);//获取当前月有多少�?
        setGvListData(tempSum, dayNumInMonth);

        View view = LayoutInflater.from(context).inflate(
                R.layout.comm_calendar, this, true);//获取布局，开始初始化
        lv = (ListView) view.findViewById(R.id.lv_calendar);
        final CalendarListViewAdapter lvAdapter = new CalendarListViewAdapter();
        lv.setAdapter(lvAdapter);

        gv = (GridView) view.findViewById(R.id.gv_calendar);
        final calendarGridViewAdapter gridViewAdapter = new calendarGridViewAdapter();
        gv.setAdapter(gridViewAdapter);

        setListViewHeight(gv, lv);
        lv.setSelection(lv.getAdapter().getCount() - 1);//设置默认进去时listview展示到列表尾端数�?
        lvAdapter.setSelectPosition(lv.getAdapter().getCount() - 1);

        lv.setOnItemClickListener(new OnItemClickListener() {//listview的监听事件，主要是�?知gridview数据变化

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view,
                    int position, long arg3) {
                if (position == lvAdapter.getSelectPosition()) {
                    return;
                } else {
                    lvAdapter.setSelectPosition(position);
                }
                String num = (String) adapterView.getAdapter()
                        .getItem(position);
                try {
                    cal.setTime(dateFormat.parse(num));
                    cal.set(Calendar.DATE, 1);
                    choiceMonth = num;
                    setGvListData(countNeedHowMuchEmpety(cal),
                            getDayNumInMonth(cal));
                    gridViewAdapter.notifyDataSetChanged();
                    setListViewHeight(gv, lv);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                lv.setSelection(position);
            }

        });

        gv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View arg1,
                    int position, long arg3) {
                String choiceDay = (String) adapterView.getAdapter().getItem(
                        position);
                if (!"".equals(choiceDay)) {
                    if (Integer.parseInt(choiceDay) < 10) {
                        choiceDay = "0" + choiceDay;
                    }
                    String date = choiceMonth + choiceDay;
                    if (callBack != null) {//调用回调函数回调数据
                        callBack.onDaySelectListener(date);
                    }
                }
            }
        });
    }

    /**
     * 根据gridview�?��有的高度设置listview的高�?
     * @param gv
     * @param lv
     */
    private void setListViewHeight(GridView gv, ListView lv) {
        int totalHeight = 0;
        ListAdapter adapter = gv.getAdapter();
        for (int i = 0, len = adapter.getCount(); i < len; i++) {
            View listItem = adapter.getView(i, null, gv);
            listItem.measure(0, 0);
            totalHeight = Math.max(totalHeight, listItem.getMeasuredHeight());
        }
        int row = adapter.getCount() % 7 == 0 ? adapter.getCount() / 7
                : adapter.getCount() / 7 + 1;

        ViewGroup.LayoutParams lvparams = lv.getLayoutParams();
        lvparams.height = row * totalHeight + gv.getPaddingTop()
                + gv.getPaddingBottom();
        lv.setLayoutParams(lvparams);
    }

    /**
     * 为gridview中添加需要展示的数据
     * @param tempSum
     * @param dayNumInMonth
     */
    private void setGvListData(int tempSum, int dayNumInMonth) {
        gvList.clear();
        for (int i = 0; i < tempSum; i++) {
            gvList.add("");
        }

        for (int j = 1; j <= dayNumInMonth; j++) {
            gvList.add(String.valueOf(j));
        }
    }

    /**
     * 获取当前月的总共天数
     * @param cal
     * @return
     */
    private int getDayNumInMonth(Calendar cal) {
        return cal.getActualMaximum(Calendar.DATE);
    }

    /**
     * 获取当前月第�?��在第�?��礼拜的第几天，得出第�?��是星期几
     * @param cal
     * @return
     */
    private int countNeedHowMuchEmpety(Calendar cal) {
        int firstDayInWeek = cal.get(Calendar.DAY_OF_WEEK) - 1;
        return firstDayInWeek;
    }

    /**
     * listview中adapter的viewholder
     * @author Administrator
     *
     */
    static class ListViewHolder {
        TextView tvYear;

        TextView tvMonth;
    }

    /**
     * ListView的adapter
     * @author Administrator
     *
     */
    static class CalendarListViewAdapter extends BaseAdapter {

        private int selectPosition;

        public int getSelectPosition() {
            return selectPosition;
        }

        public void setSelectPosition(int selectPosition) {
            this.selectPosition = selectPosition;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return lvList.size();
        }

        @Override
        public String getItem(int position) {
            return lvList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ListViewHolder holder;
            if (convertView == null) {
                holder = new ListViewHolder();
                convertView = inflate(context,
                        R.layout.comm_calendar_listview_item, null);
                holder.tvYear = (TextView) convertView
                        .findViewById(R.id.tv_calendar_year);
                holder.tvMonth = (TextView) convertView
                        .findViewById(R.id.tv_calendar_month);
                convertView.setTag(holder);
            } else {
                holder = (ListViewHolder) convertView.getTag();
            }
            if ("01".equals(getItem(position).substring(4))) {
                holder.tvYear.setVisibility(View.VISIBLE);
            } else {
                holder.tvYear.setVisibility(View.INVISIBLE);
            }
            if (selectPosition == position) {
                holder.tvYear.setTextColor(Color.rgb(
                        Integer.parseInt("1D", 16), Integer.parseInt("B6", 16),
                        Integer.parseInt("F2", 16)));
                holder.tvMonth.setTextColor(Color.rgb(
                        Integer.parseInt("1D", 16), Integer.parseInt("B6", 16),
                        Integer.parseInt("F2", 16)));
            } else {
                holder.tvYear.setTextColor(Color.rgb(
                        Integer.parseInt("79", 16), Integer.parseInt("78", 16),
                        Integer.parseInt("78", 16)));
                holder.tvMonth.setTextColor(Color.rgb(
                        Integer.parseInt("79", 16), Integer.parseInt("78", 16),
                        Integer.parseInt("78", 16)));
            }
            holder.tvYear
                    .setText("(" + getItem(position).substring(0, 4) + ")");
            int month = Integer.parseInt(getItem(position).substring(4));
            holder.tvMonth.setText(String.valueOf(month) + "��");
            return convertView;
        }

    }

    /**
     * gridview中adapter的viewholder
     * @author Administrator
     *
     */
    static class GrideViewHolder {

        TextView tvDay;
    }

    /**
     * gridview的adapter
     * @author Administrator
     *
     */
    static class calendarGridViewAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return gvList.size();
        }

        @Override
        public String getItem(int position) {
            return gvList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            GrideViewHolder holder;
            if (convertView == null) {
                holder = new GrideViewHolder();
                convertView = inflate(context,
                        R.layout.common_calendar_gridview_item, null);
                holder.tvDay = (TextView) convertView
                        .findViewById(R.id.tv_calendar_day);
                convertView.setTag(holder);
            } else {
                holder = (GrideViewHolder) convertView.getTag();
            }
            holder.tvDay.setText(getItem(position));
            return convertView;
        }
    }

    /**
     * 自定义监听接�?
     * @author Administrator
     *
     */
    public interface OnDaySelectListener {
        void onDaySelectListener(String date);
    }

    /**
     * 自定义监听接口设置对�?
     * @param o
     */
    public void setOnDaySelectListener(OnDaySelectListener o) {
        callBack = o;
    }
}
