package dev.arya.voipcallsim.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import dev.arya.voipcallsim.R;
import dev.arya.voipcallsim.data.db.AppDatabase;
import dev.arya.voipcallsim.data.model.CallLog;
import dev.arya.voipcallsim.service.CallService;
import dev.arya.voipcallsim.ui.viewmodel.CallViewModel;

public class OngoingCallActivity extends AppCompatActivity {
    private TextView callTimer;
    private Button btnEndCall;
    private int seconds = 0;
    private Handler handler;
    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ongoing_call);
        callTimer = findViewById(R.id.call_timer);
        btnEndCall = findViewById(R.id.btn_end_call);

        startService(new Intent(this, CallService.class));

        handler = new Handler(Looper.getMainLooper());
        runnable = new Runnable() {

            @SuppressLint("DefaultLocale")
            @Override
            public void run() {
                seconds++;
                int minutes = seconds / 60;
                int remainingSeconds = seconds % 60;
                callTimer.setText(String.format("%02d:%02d", minutes, remainingSeconds));
                handler.postDelayed(this, 1000);
            }
        };
        handler.post(runnable);

        btnEndCall.setOnClickListener(view -> endCall());

    }

    private void endCall() {
        try {
            stopService(new Intent(this, CallService.class));
            handler.removeCallbacks(runnable);
            long endTime = System.currentTimeMillis();
            long startTime = endTime - (seconds * 1000L);

            CallLog answeredLog = new CallLog("Test Caller", startTime, endTime, "Answered", seconds);
            CallViewModel viewModel = new ViewModelProvider(this).get(CallViewModel.class);
            viewModel.insertCallLog(answeredLog);
            finish();
        } catch (Exception e) {
            Log.e("OutgoingCallActivity:", "endCall: "+ e.getMessage() );
        }
    }
}