package com.example.informant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewbinding.ViewBinding;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.informant.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    ProgressBar progressBar;
    TextView loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //set the loading on the progress bar
        progressBar=findViewById(R.id.progressBarloading);
        progressBar.setMax(100);
        progressBar.setScaleY(3f);

        CountDownTimer countDownTimer = new CountDownTimer(4000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                progressBar.incrementProgressBy(25);
//                Toast.makeText(getApplicationContext(), "ticker called", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinish() {
                startActivity(new Intent(getApplicationContext(), HomeScreen.class));
//                Toast.makeText(getApplicationContext(), "finished called", Toast.LENGTH_SHORT).show();
            }
        }.start();
        progressAnimation();

        binding.animationimage.setImageResource(R.drawable.info);
        binding.txtappName.setText("Informant");

        loading=findViewById(R.id.loadingtxt);

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent intent = new Intent(getApplicationContext(), HomeScreen.class);
//                startActivity(intent);
//                finish();
//            }
//        },4000);
    }

    private void progressAnimation() {
        ProgressBarAnimation anim = new ProgressBarAnimation(this, progressBar,loading, 0f, 100f, 100f);
        anim.setDuration(8000);
        progressBar.startAnimation(anim);
    }
}