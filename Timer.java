package com.example.rcovi.wake_me_app;

/**
 * Created by rcovi on 03/09/2017.
 */

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Timer {

    private long startTime = 0;
    private int x = 0;
    private int y = 0;
    private Paint p;
    private long t = 0;
    private boolean started = false;
    private TimeCallBack mTimeCallback;

    public Timer(GameView gameView) {
        p = new Paint();
        p.setAntiAlias(true);
        p.setColor(Color.BLACK);
        p.setStyle(Paint.Style.FILL);
        p.setAlpha(70);
        p.setTextSize(gameView.getHeight() / 8);
        p.setTextAlign(Paint.Align.CENTER);
        x = gameView.getWidth() / 2;
        y = (int) ((gameView.getHeight() / 2) - ((p.descent() + p.ascent()) / 2));

    }

    public void startTime() {
        started = true;
        startTime = System.currentTimeMillis();
    }

    private void update() {
        if (started) {
            checkTime();
            t = getElapsedTimeSecs();
        }
    }

    private void checkTime() {
        if (t != getElapsedTimeSecs() && getElapsedTimeSecs() % 10 == 0 && t != 0)
            mTimeCallback.onTenSeconds();
        if (t != getElapsedTimeSecs() && getElapsedTimeSecs() % 25 == 0 && t != 0)
            mTimeCallback.onThirtySeconds();
        if (t != getElapsedTimeSecs() && getElapsedTimeSecs() % 30 == 0 && t != 0)
            mTimeCallback.onMinute();

    }

    public void setTimeCallback(TimeCallBack cb) {
        this.mTimeCallback = cb;
    }

    public void onDraw(Canvas canvas) {
        update();
        canvas.drawText(t + "", x, y, p);
    }

    //elaspsed time in seconds
    public long getElapsedTimeSecs() {
        long elapsed;
        elapsed = ((System.currentTimeMillis() - startTime) / 1000);
        return elapsed;
    }

    public void restart() {
        this.started = false;
        this.t = 0;
    }

    public long getT() {
        return t;
    }
}
