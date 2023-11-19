package com.example.informant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.KeyguardManager;
import android.content.Context;
import android.os.Bundle;

import com.example.informant.databinding.ActivitySecurityStatusBinding;

public class SecurityStatus extends AppCompatActivity {
    ActivitySecurityStatusBinding binding;

    CardView cardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySecurityStatusBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        cardView=findViewById(R.id.cardviewsecurity);
        binding.txtSecuritystatus.setText("Security Status");
        binding.imageView.setImageResource(R.drawable.security_symbol);

        getSecurityUpdates();
    }

    private void getSecurityUpdates() {

        //we have check the device encryption
        if (isDeviceEncrypted()){
            binding.txtSecuritystatus.setText("Your device is encrypted \n");
        }
        else
        {
            binding.txtSecuritystatus.setText("Your device is not encrypted \n");
        }

        //we have to check the screen lock
        if (isScreenLockset()){
            binding.txtSecuritystatus.append("Your screen lock is set \n");
        }
        else
        {
            binding.txtSecuritystatus.append("Your screen lock is not set \n");
        }
    }

    private boolean isScreenLockset() {
        KeyguardManager keyguardManager = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
        return  keyguardManager.isKeyguardSecure();
    }

    private boolean isDeviceEncrypted() {
        KeyguardManager keyguardManager = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
        return keyguardManager.isDeviceSecure();
    }
}