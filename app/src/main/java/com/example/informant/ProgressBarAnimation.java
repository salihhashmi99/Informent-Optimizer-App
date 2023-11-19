package com.example.informant;

import android.content.Context;
import android.content.Intent;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ProgressBarAnimation extends Animation {

    private Context context;
    private ProgressBar progress;
    private float loadingtxt;
    private float from;
    private float to;

//    public ProgressBarAnimation(Context context1, ProgressBar progress, int loadingtxt, float from, float to) {
//
//    }

    public ProgressBarAnimation(MainActivity context1, ProgressBar progressBar, TextView loading, float from, float to, float v) {
        this.context = context1;
        this.progress = progress;
        this.loadingtxt = loadingtxt;
        this.from = from;
        this.to = to;
    }

    protected void ApplyTransformation(float interpolateTime, Transformation t){
        super.applyTransformation(interpolateTime, t);
        float value =  from + (to - from) + interpolateTime;
        progress.setProgress((int) value);

        if (value==to){
            context.startActivity(new Intent(context, HomeScreen.class));
        }

    }
}
