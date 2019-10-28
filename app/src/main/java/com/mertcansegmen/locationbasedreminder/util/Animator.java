package com.mertcansegmen.locationbasedreminder.util;

import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Animator {

    public static void animateFloatingActionButton(FloatingActionButton button) {
        ScaleAnimation disappearAnim = new ScaleAnimation(0,1,0,1, 90, 90);
        disappearAnim.setFillBefore(true);
        disappearAnim.setFillAfter(true);
        disappearAnim.setFillEnabled(true);
        disappearAnim.setDuration(300);
        disappearAnim.setInterpolator(new OvershootInterpolator() {
            @Override
            public float getInterpolation(float input) {
                return Math.abs(input -1f);
            }
        });
        disappearAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                ScaleAnimation appearAnim = new ScaleAnimation(0,1,0,1, 90, 90);
                appearAnim.setFillBefore(true);
                appearAnim.setFillAfter(true);
                appearAnim.setFillEnabled(true);
                appearAnim.setDuration(300);
                appearAnim.setInterpolator(new OvershootInterpolator());
                button.startAnimation(appearAnim);
            }

            @Override
            public void onAnimationRepeat(Animation animation) { }
        });
        button.startAnimation(disappearAnim);
    }
}