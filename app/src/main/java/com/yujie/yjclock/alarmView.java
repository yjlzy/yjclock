package com.yujie.yjclock;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Administrator on 2017/5/24 0024.
 */

public class alarmView extends LinearLayout {
    private ListView alarmList;
    private Button addAlarm;
    private ArrayAdapter<timeNum> adapter;
    public alarmView(Context context) {
        super(context);
    }

    public alarmView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public alarmView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        alarmList = (ListView) findViewById(R.id.alarm_list);
        addAlarm = (Button) findViewById(R.id.add_alarm);
        //设置列表资源
        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1);
        alarmList.setAdapter(adapter);
        //按钮事件
        addAlarm.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                addTime();
            }
        });
    }

    private void addTime() {
        Calendar nowTime = Calendar.getInstance();
        new MyTimePickerDialog(getContext(), new MyTimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Calendar setTime = Calendar.getInstance();
                setTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                setTime.set(Calendar.MINUTE, minute);
                Calendar rightTime = Calendar.getInstance();
                if (setTime.getTimeInMillis() <= rightTime.getTimeInMillis()) {
                    setTime.setTimeInMillis(setTime.getTimeInMillis()+24*60*60*1000);
                }
                timeNum tn = new timeNum(setTime.getTimeInMillis());
                adapter.add(tn);
            }

        },nowTime.get(Calendar.HOUR_OF_DAY),nowTime.get(Calendar.MINUTE),true).show();
    }

    private class timeNum {
        private timeNum(long time) {
            this.time = time;
            date = Calendar.getInstance();
            date.setTimeInMillis(time);
            timeLab = String.format(Locale.CHINA,"%d月%d日 %d:%d",
                    date.get(Calendar.MONTH),
                    date.get(Calendar.DATE),
                    date.get(Calendar.HOUR_OF_DAY),
                    date.get(Calendar.MINUTE));
        }

        public long getTime() {
            return time;
        }

        public String getTimeLab() {
            return timeLab;
        }

        @Override
        public String toString() {
            return timeLab;
        }

        private long time=0;
        private String timeLab="";
        private Calendar date;
    }

}