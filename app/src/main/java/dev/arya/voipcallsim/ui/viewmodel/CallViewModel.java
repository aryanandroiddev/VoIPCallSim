package dev.arya.voipcallsim.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import dev.arya.voipcallsim.data.model.CallLog;
import dev.arya.voipcallsim.domain.repository.CallRepository;

public class CallViewModel extends AndroidViewModel {
    private final CallRepository callRepository;
    private final MutableLiveData<List<CallLog>> callLogsLiveData = new MutableLiveData<>();


    public CallViewModel(@NonNull Application application) {
        super(application);
        this.callRepository = new CallRepository(application);
        loadCallData();
    }
    public LiveData<List<CallLog>> getCallLogsLiveData() {
        return callLogsLiveData;
    }

    private void loadCallData() {
        Executors.newSingleThreadExecutor().execute(() -> {
            List<CallLog> callLogs = callRepository.getCallLogs();
            callLogsLiveData.postValue(callLogs);
        });
    }
    public void insertCallLog(CallLog callLog) {
        Executors.newSingleThreadExecutor().execute(() -> {
            callRepository.insertCallLog(callLog);
            loadCallData();
        });
    }
}
