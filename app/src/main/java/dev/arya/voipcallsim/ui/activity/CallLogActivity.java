package dev.arya.voipcallsim.ui.activity;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import dev.arya.voipcallsim.R;
import dev.arya.voipcallsim.adapter.CallLogAdapter;
import dev.arya.voipcallsim.data.db.AppDatabase;
import dev.arya.voipcallsim.ui.viewmodel.CallViewModel;

public class CallLogActivity extends AppCompatActivity {
    private RecyclerView logRecycler;
    private CallViewModel viewModel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_call_log);
        logRecycler = findViewById(R.id.logRecycler);
        logRecycler.setLayoutManager(new LinearLayoutManager(this));

        viewModel = new ViewModelProvider(this).get(CallViewModel.class);

        viewModel.getCallLogsLiveData().observe(this, logs -> {
            logRecycler.setAdapter(new CallLogAdapter(logs));
        });

    }
}