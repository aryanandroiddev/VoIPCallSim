package dev.arya.voipcallsim.domain.repository;

import android.content.Context;

import java.util.List;

import dev.arya.voipcallsim.data.db.AppDatabase;
import dev.arya.voipcallsim.data.model.CallLog;

public class CallRepository {
    private final AppDatabase db;

    public CallRepository(Context context) {
        this.db = AppDatabase.getInstance(context);
    }
    public List<CallLog> getCallLogs() {
        return db.callLogDao().getAllCallLogs();
    }

    public void insertCallLog(CallLog callLog) {
        db.callLogDao().insertCallLog(callLog);
    }
}
