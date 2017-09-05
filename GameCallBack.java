package com.example.rcovi.wake_me_app;

/**
 * Created by rcovi on 03/09/2017.
 */

public interface GameCallBack {

    void onGameOver(long endTime, boolean isDeath);

    void onBonusTouched();

    void onSecondLife();
}
