package com.example.informant;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.StatFs;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.informant.databinding.ActivityHomeScreenBinding;

public class HomeScreen extends AppCompatActivity {
    private ActivityHomeScreenBinding binding;
    ProgressBar RAMprogress, memoryProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityHomeScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        RAMprogress=findViewById(R.id.progressbar);
        memoryProgress=findViewById(R.id.memoryprogressbar);

        //Add on Click Listener on the App Info Card view
        binding.btnappinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("INtent", "Inside The app info");
                Toast.makeText(HomeScreen.this, "you clicked on App info", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), AppInfo.class);
                startActivity(intent);
            }
        });

        //Add on Click Listener on the wifi status Card view
        binding.btnwifistatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HomeScreen.this, "you clicked on Wifi status", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), WifiStatus.class);
                startActivity(intent);
            }
        });

        //Add on Click Listener on the Battery Card view
        binding.btnbatterystatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HomeScreen.this, "you clicked on Battery", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), BatteryStatus.class);
                startActivity(intent);
            }
        });

        //Add on Click Listener on the Security Status Card view
        binding.btnsecuritystatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HomeScreen.this, "you clicked on Security Status", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(),SecurityStatus.class);
                startActivity(intent);
            }
        });


        //Update RAM information and Memory Information
        Handler handler = new Handler(Looper.myLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                updateRAMinformation();
                updateMemoryinformation();
                handler.postDelayed(this,1000);
            }
        },1000);
    }

    private void updateRAMinformation() {
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);

        long totalmemory = memoryInfo.totalMem;
        long memoryuse = totalmemory - memoryInfo.availMem;
        long freememory = memoryInfo.availMem;

        int progress = (int) ((float) memoryuse/totalmemory * 100);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                RAMprogress.setProgress(progress);
                binding.txttotalRAMdigit.setText(formatsize(totalmemory/1024));
                binding.txtuseRAMdigit.setText(formatsize(memoryuse/1024));
                binding.txtremainingRAMdigit.setText(formatsize(freememory/1024));
            }
        });
    }

    private void updateMemoryinformation() {
//        Runtime runtime = Runtime.getRuntime();
//
//        long totalmemory= runtime.totalMemory();
//        long freememory = runtime.freeMemory();
//        long usedmemory = totalmemory - freememory;

        double totalmemory = getTotalInternalmemorySize()/(1024*1024*1024);
        double freememory = getAvailableInternalMemorySize()/(1024*1024*1024);
        double usedmemory = totalmemory - freememory;
//        Toast.makeText(getApplicationContext(), "used memory: "+usedmemory/(1024*1024), Toast.LENGTH_LONG ).show();
        Log.i("Logging", "used memory: "+usedmemory/1024);
//      long maxMemory = runtime.maxMemory();

        int progress = (int)((usedmemory/totalmemory) * 100);

        String memoryInfo = String.format("Total Memory: %.2f GB\n"
                        + "Used Memory: %.2f GB\n"
                        + "Free Memory: %.2f GB", totalmemory, usedmemory, freememory);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                memoryProgress.setProgress(progress);

                binding.txttotalMemorydigit.setText(String.format("%.2f GB", totalmemory));
                binding.txtuseMemorydigit.setText(String.format("%.2f GB", usedmemory));
                binding.txtremainingMemorydigit.setText(String.format("%.2f GB", freememory));


//                binding.txttotalMemorydigit.setText(formatsize(totalmemory/1024));
//                binding.txtuseMemorydigit.setText(formatsize(usedmemory/1024));
//                binding.txtremainingMemorydigit.setText(formatsize(freememory/1024));
            }
        });
    }

    private double getAvailableInternalMemorySize() {
        StatFs stat = new StatFs(Environment.getDataDirectory().getPath());
        return stat.getAvailableBytes();
    }

    private double getTotalInternalmemorySize() {
        StatFs stat = new StatFs(Environment.getDataDirectory().getPath());
        return stat.getTotalBytes();
    }

    private String formatsize(long size) {
        String suffix = null;
        if (size>=1024){
            suffix="MB";
            size/= 1024;
            if (size>=1024){
                suffix="GB";
                size/=1024;
            }
    }

    StringBuilder resultbuffer = new StringBuilder(Long.toString(size));

        int commaOffset = resultbuffer.length() - 3;
        while (commaOffset > 0){
            resultbuffer.insert(commaOffset,',');
            commaOffset -=3;
        }

        if (suffix != null) resultbuffer.append(suffix);
        return resultbuffer.toString();
    }
}