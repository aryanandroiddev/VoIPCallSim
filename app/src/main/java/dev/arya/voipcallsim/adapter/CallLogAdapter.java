package dev.arya.voipcallsim.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import dev.arya.voipcallsim.R;
import dev.arya.voipcallsim.data.model.CallLog;

public class CallLogAdapter extends RecyclerView.Adapter<CallLogAdapter.CallLogViewHolder> {

    private final List<CallLog> callLogs;

    public CallLogAdapter(List<CallLog> callLogs) {
        this.callLogs = callLogs;
    }

    @NonNull
    @Override
    public CallLogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_log, parent, false);
        return new CallLogViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CallLogViewHolder holder, int position) {
        CallLog callLog = callLogs.get(position);
        holder.name.setText(callLog.callerName);
        holder.type.setText(callLog.callType);
        holder.time.setText(formatDate(callLog.startTime));
        holder.duration.setText("Duration: " + callLog.duration + " sec");

    }

    @Override
    public int getItemCount() {
        return callLogs.size();
    }

     static class CallLogViewHolder extends RecyclerView.ViewHolder {
         TextView name, type, time, duration;
        public CallLogViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            type = itemView.findViewById(R.id.type);
            time = itemView.findViewById(R.id.time);
            duration = itemView.findViewById(R.id.duration);
        }
    }
    @SuppressLint("SimpleDateFormat")
    private String formatDate(long startTime) {
        return new SimpleDateFormat("dd MMM, hh:mm a").format(new Date(startTime));
    }
}
