package com.example.informant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;

import com.example.informant.databinding.ActivityWifiStatusBinding;

public class WifiStatus extends AppCompatActivity {
    ActivityWifiStatusBinding binding;
    CardView cardView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityWifiStatusBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        cardView=findViewById(R.id.cardviewwifi);

        binding.txtwifistatus.setText("Wifi Status");
        binding.imageView.setImageResource(R.drawable.wifi_symbol);

        displaywifiInfo();
    }

    private void displaywifiInfo() {
        WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);

        if (wifiManager != null && wifiManager.isWifiEnabled()) {
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();

            String ssid = wifiInfo.getSSID();
            String bssid = wifiInfo.getBSSID();
            long net_id = wifiInfo.getNetworkId();
            SupplicantState supplicant_state= wifiInfo.getSupplicantState();
            int rssi = wifiInfo.getRssi();
            long speed = wifiInfo.getLinkSpeed();
            int frequency = wifiInfo.getFrequency();

            String wifitotxt = "SSID: " + ssid + "\n"
                    + "BSSID: " + bssid + "\n"
                    + "Network ID: "+net_id+" \n"
                    + "RSSI: " + rssi + "\n"
                    + "Supplicant State: "+supplicant_state+" \n"
                    + "Link Speed: " + speed + "Mbps" + "\n"
                    + "frequency" + frequency;

            binding.txtwifistatus.setText(wifitotxt);
        }
        else
        {
            binding.txtwifistatus.setText("Wifi is disabled, Please connect");
        }
    }
}