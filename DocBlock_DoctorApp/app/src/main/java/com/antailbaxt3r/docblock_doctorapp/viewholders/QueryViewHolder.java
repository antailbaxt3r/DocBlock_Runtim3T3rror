package com.antailbaxt3r.docblock_doctorapp.viewholders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.antailbaxt3r.docblock_doctorapp.R;

public class QueryViewHolder extends RecyclerView.ViewHolder {

    TextView problem, duration, sender;
    String UID;
    public QueryViewHolder(@NonNull View itemView) {
        super(itemView);

        problem = itemView.findViewById(R.id.problem_tv);
        duration = itemView.findViewById(R.id.duration_tv);
        sender = itemView.findViewById(R.id.sender_name_tv);

    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public TextView getProblem() {
        return problem;
    }

    public void setProblem(TextView problem) {
        this.problem = problem;
    }

    public TextView getDuration() {
        return duration;
    }

    public void setDuration(TextView duration) {
        this.duration = duration;
    }

    public TextView getSender() {
        return sender;
    }

    public void setSender(TextView sender) {
        this.sender = sender;
    }
}
