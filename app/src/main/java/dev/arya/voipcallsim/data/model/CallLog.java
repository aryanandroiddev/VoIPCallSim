package dev.arya.voipcallsim.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "call_logs")
public class CallLog {
@PrimaryKey(autoGenerate = true)
    public int id;

    public String callerName;
    public long startTime;
    public long endTime;
    public String callType;
    public long duration;

    public CallLog(String callerName, long startTime, long endTime, String callType, long duration) {
        this.callerName = callerName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.callType = callType;
        this.duration = duration;
    }
}
