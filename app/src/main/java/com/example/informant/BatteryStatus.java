package com.example.informant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;

import com.example.informant.databinding.ActivityBatteryStatusBinding;

public class BatteryStatus extends AppCompatActivity {
    ActivityBatteryStatusBinding binding;
    CardView cardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityBatteryStatusBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        cardView=findViewById(R.id.cardviewbattery);

        binding.batterystatusstitle.setText("Battery Status");
        binding.imageView.setImageResource(R.drawable.battery_symbol);

        dispalyBatterystatus();
    }

    private void dispalyBatterystatus() {
       // BatteryManager batteryManager = (BatteryManager) getSystemService(Context.BATTERY_SERVICE);
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = registerReceiver(null, intentFilter);

        if (batteryStatus != null){
            int batteryLevel = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL,-1);
            int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE,-1);

            float batteryInpercent = (batteryLevel / (float) scale) * 100;

            int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
            boolean isCharging = (status == BatteryManager.BATTERY_STATUS_CHARGING) || (status == BatteryManager.BATTERY_STATUS_FULL);

            //now we need the information for the battery
            String batteryinfo = "BatteryLevel: "+batteryInpercent+ "\n"
                    +"Battery Scale: "+scale+"\n"
                    +"Battery Charging: "+ (isCharging ? "Yes":"No");

            binding.txtbatterystatus.setText(batteryinfo);
        }
        else
        {
            binding.txtbatterystatus.setText("Unable to check the battery");
        }
    }
}