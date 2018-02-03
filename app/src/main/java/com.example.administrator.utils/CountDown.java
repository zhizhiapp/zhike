package com.example.administrator.utils;

import android.os.Handler;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CountDown {
    //倒计时时长 秒
    private int time;
    private Handler handler;
    private SimpleDateFormat format;
    //经过的时间
    private int useTime;
    private Listener listener;

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public CountDown(int time) {
        this.time = time;
        handler = new Handler();
        format = new SimpleDateFormat("mm:ss", Locale.CHINA);
    }

    public void start() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (time != 0) {
                    time--;
                    useTime++;
                    String txt = format.format(new Date(time * 1000));
                    if (listener != null) {
                        listener.onTimeChange(txt);
                    }
                    handler.postDelayed(this, 1000);
                } else {
                    handler.removeCallbacks(this);
                }
            }
        }, 0);
    }

    public int getUseTime() {
        return useTime;
    }

    public int getTime() {
        return time;
    }

    public interface Listener {
        void onTimeChange(String txt);
    }
}
