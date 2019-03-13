package com.example.bin.productmanagement.Demo.Util;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.bin.productmanagement.R;


public class AnimationUtil {

    public static void shakeView(View view, Context context) {
        Animation shake = AnimationUtils.loadAnimation(context, R.anim.shake_view);
        view.startAnimation(shake);
    }
}