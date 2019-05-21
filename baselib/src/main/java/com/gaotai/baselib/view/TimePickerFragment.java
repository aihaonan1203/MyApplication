package com.gaotai.baselib.view;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * 选择时分 控件
 */
@SuppressLint("NewApi")
public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    /**
     * 小时
     */
    private int hour = 0;
    /**
     * 分
     */
    private int minute = 0;

    /**
     * 返回的操作
     */
    private int what = 0;

    /**
     * 操作handlerOP
     */
    private Handler handlerOP;

    public TimePickerFragment() {
    }

    public TimePickerFragment(int hour, int minute, Handler handlerOP, int what) {
        this.hour = hour;
        this.minute = minute;
        this.what = what;
        this.handlerOP = handlerOP;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar calendar = Calendar.getInstance();
        if (hour == 0 && minute == 0) {
            hour = calendar.get(Calendar.HOUR_OF_DAY);
            minute = calendar.get(Calendar.MINUTE);
        }
        return new TimePickerDialog(getActivity(), this, hour, minute, DateFormat.is24HourFormat(getActivity()));
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        //处理设置的时间，这里我们作为示例，在日志中输出我们选择的时间
        Log.d("onTimeSet", "hourOfDay: " + hourOfDay + "Minute: " + minute);
        String time = String.valueOf(hourOfDay);
        if (hourOfDay < 10) {
            time = "0" + String.valueOf(hourOfDay);
        }
        if (minute < 10) {
            time += ":" + "0" + String.valueOf(minute);
        } else {
            time += ":" + String.valueOf(minute);
        }
        Message message = handlerOP.obtainMessage();
        message.what = what;
        message.obj = time;
        handlerOP.sendMessage(message);
    }
}

