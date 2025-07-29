package dev.arya.voipcallsim.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import dev.arya.voipcallsim.ui.activity.IncomingCallActivity;

public class CallReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("CallReceiver", "Incoming call via AlarmManager");

        Intent callIntent = new Intent(context, IncomingCallActivity.class);
        callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(callIntent);
    }
}
