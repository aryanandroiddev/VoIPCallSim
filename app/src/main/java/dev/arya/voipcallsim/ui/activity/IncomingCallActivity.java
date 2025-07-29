package dev.arya.voipcallsim.ui.activity;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import java.util.Map;
import java.util.Random;

import dev.arya.voipcallsim.R;
import dev.arya.voipcallsim.data.db.AppDatabase;
import dev.arya.voipcallsim.data.db.CallLogDao;
import dev.arya.voipcallsim.data.model.CallLog;
import dev.arya.voipcallsim.ui.viewmodel.CallViewModel;

public class IncomingCallActivity extends AppCompatActivity {
    private Ringtone ringtone;
    private CountDownTimer timer;
    private Vibrator vibrator;
    private final long autoDismiss =10_000L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                             WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                             WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
                             WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
                             WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_incoming_call);

        TextView callerName = findViewById(R.id.caller_name);
        Button btnAnswer = findViewById(R.id.btn_answer);
        Button btnReject = findViewById(R.id.btn_reject);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.POST_NOTIFICATIONS}, 101);
            }
        }

        callerName.setText("Test Caller");

        Uri ringtoneUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        ringtone = RingtoneManager.getRingtone(this, ringtoneUri);
        ringtone.play();

        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        if(vibrator != null){
            vibrator.vibrate(autoDismiss);
        }

        btnAnswer.setOnClickListener(view -> {
            stopAlerts();
            launchCallScreen(true);
        });
        btnReject.setOnClickListener(view -> {
            stopAlerts();

            long startTime = System.currentTimeMillis();
            long endTime = startTime;
            CallLog missedLog = new CallLog("Test Caller", startTime, endTime, "Missed", 0);
            CallViewModel viewModel = new ViewModelProvider(this).get(CallViewModel.class);
            viewModel.insertCallLog(missedLog);

            showMissedCallNotification("Test Caller");
            launchCallScreen(false);
        });

        timer = new CountDownTimer(autoDismiss, 1000) {

            @Override
            public void onFinish() {
                try {
                    stopAlerts();
                    long startTime = System.currentTimeMillis();
                    long endTime = startTime;
                    CallLog missedLog = new CallLog("Test Caller", startTime, endTime, "Missed", 0);

                    CallViewModel viewModel = new ViewModelProvider(IncomingCallActivity.this).get(CallViewModel.class);

                    viewModel.insertCallLog(missedLog);

                    showMissedCallNotification("Test Caller");
                    launchCallScreen(false);
                } catch (Exception e) {
                    Log.e("IncomingCallActivity: onFinish:", "onFinish: "+ e.getMessage() );
                }
            }

            @Override
            public void onTick(long millisUntilFinished) {}
        }.start();


    }
    private void stopAlerts() {
        try {
            if (ringtone != null && ringtone.isPlaying()) ringtone.stop();
            if (vibrator != null) vibrator.cancel();
            if (timer != null) timer.cancel();
        } catch (Exception e) {
            Log.e("IncomingCallActivity: stopAlerts", "stopAlerts: "+ e.getMessage());
        }
    }
    private void launchCallScreen(boolean isAnswered) {
        try {
            Intent intent;
            if (isAnswered){
                intent = new Intent(this, OngoingCallActivity.class);
            }else {
                intent = new Intent(this, CallLogActivity.class);
            }
            startActivity(intent);
            finish();
        } catch (Exception e) {
            Log.e("IncomingCallActivity:", "launchCallScreen: "+e.getMessage() );
        }
    }

    private void showMissedCallNotification(String callerName) {
        try {
            String channelId = "missed_call_channel";
            String channelName = "Missed Call Alerts";
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel(
                        channelId,
                        channelName,
                        NotificationManager.IMPORTANCE_HIGH
                );
                channel.setDescription("Notifies when a call is missed");
                manager.createNotificationChannel(channel);
            }

            Intent logIntent = new Intent(this, CallLogActivity.class);
            PendingIntent logPendingIntent = PendingIntent.getActivity(
                    this,
                    0,
                    logIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
            );
            Intent callbackIntent = new Intent(this, MainActivity.class);
            PendingIntent callbackPendingIntent = PendingIntent.getActivity(
                    this,
                    1,
                    callbackIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
            );

            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
                    .setSmallIcon(R.drawable.ic_call_missed)
                    .setContentTitle("Missed Call from " + callerName)
                    .setContentText("Tap to view call log")
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setAutoCancel(true)
                    .setContentIntent(logPendingIntent)
                    .addAction(R.drawable.ic_call, "Call Back", callbackPendingIntent);

            manager.notify(new Random().nextInt(), builder.build());
        } catch (Exception e) {
            Log.e("IncomingCallActivity:", "showMissedCallNotification: "+ e.getMessage() );
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopAlerts();
    }
}