package com.example.rcovi.wake_me_app.model;

/**
 * Created by rcovi on 26/08/2017.
 */

import android.graphics.Color;

import com.example.rcovi.wake_me_app.R;

import java.util.Random;

public class ThemeWheel {
    // Fields (Member Variables) - Properties about the object
    private int[] mColors = {
            R.style.LightBlue, // light blue
            R.style.DarkBlue, // dark blue
            R.style.Mauve, // mauve
            R.style.Red, // red
            R.style.Orange, // orange
            R.style.Lavender, // lavender
            R.style.Purple, // purple
            R.style.Aqua, // aqua
            R.style.Green, // green
            R.style.Mustard, // mustard
            R.style.DarkGrey, // dark gray
            R.style.Pink, // pink
            R.style.LightGrey  // light gray
    };

    // Methods - Actions the object can take

    public int getThemeId() {
        int theme;
        // Randomly select a color
        Random randomGenerator = new Random();
        int randomNumber = randomGenerator.nextInt(mColors.length);
        theme = mColors[randomNumber];;

        return theme;
    }
}