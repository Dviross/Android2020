package com.bnm.lavy.stepmory;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.widget.Toast;

public class BroadcastBattery extends BroadcastReceiver {
    private boolean msgFlag;
    Context context;

    public BroadcastBattery(Context context) {
        msgFlag = false;
        this.context = context;
      }

    @Override
    public void onReceive(Context context, Intent intent) {
        int batteryLevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int batteryStatus = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        if (!msgFlag && batteryLevel <= 20) {
            msgFlag = true;
            Toast.makeText(context, "Low battery:" + batteryLevel + "%", Toast.LENGTH_LONG).show();
            //sendNotification( batteryLevel );
        }
//        else {
//            if (batteryStatus == BatteryManager.BATTERY_STATUS_CHARGING)
//                msgFlag = false;  
//        }
    }
}
