package com.example.broadcasttest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class LocalReceiver extends BroadcastReceiver {
    private String backData;

    @Override
    public void onReceive(Context context, Intent intent) {
        backData = intent.getStringExtra("data");
    }

    public String getBackData() {
        return backData;
    }
}
