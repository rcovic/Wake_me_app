package com.example.rcovi.wake_me_app;

/**
 * Created by rcovi on 03/09/2017.
 */

import android.graphics.Color;
import android.graphics.Paint;


public class BlueBlock extends Block {


    public BlueBlock(GameView gameView) {
        super(gameView);
    }

    @Override
    public Paint getPaint() {
        Paint p = new Paint();
        p.setAntiAlias(true);
        p.setColor(Color.parseColor("#03A9F4"));
        p.setStyle(Paint.Style.FILL);
        p.setStrokeWidth(10f);
        return p;
    }
}