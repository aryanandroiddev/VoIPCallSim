package dev.arya.voipcallsim.data.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import dev.arya.voipcallsim.data.model.CallLog;

@Dao
public interface  CallLogDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCallLog(CallLog callLog);
    @Query("SELECT * FROM call_logs ORDER BY startTime DESC")
    List<CallLog> getAllCallLogs();
}
