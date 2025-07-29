package dev.arya.voipcallsim.ui.activity;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import dev.arya.voipcallsim.R;
import dev.arya.voipcallsim.databinding.ActivityMainBinding;
import dev.arya.voipcallsim.receiver.CallReceiver;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnScheduleCall.setOnClickListener(view -> scheduleCall());
    }

    @SuppressLint("ScheduleExactAlarm")
    private void scheduleCall() {
        try {
            long triggerTime = System.currentTimeMillis() + 5000;

            Intent intent = new Intent(this, CallReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(
                    this, 100, intent, PendingIntent.FLAG_IMMUTABLE
            );
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                if (!alarmManager.canScheduleExactAlarms()) {
                    Intent permissionIntent = new Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM);
                    startActivity(permissionIntent);
                    Toast.makeText(this, "Please allow exact alarm permission", Toast.LENGTH_LONG).show();
                    return;
                }
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent);
            } else {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent);
            }

            Toast.makeText(this, "Call scheduled in 5 seconds", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.d("MainActivity", "scheduleCall: "+ e.getMessage());
        }
    }
}