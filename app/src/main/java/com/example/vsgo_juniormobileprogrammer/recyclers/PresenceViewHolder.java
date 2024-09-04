package com.example.vsgo_juniormobileprogrammer.recyclers;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.vsgo_juniormobileprogrammer.R;

public class PresenceViewHolder
    extends RecyclerView.ViewHolder {

    public TextView dateTextView, onDutyTextView, offDutyTextView;

    View view;

    public PresenceViewHolder(View itemView){
        super(itemView);
        dateTextView = (TextView) itemView.findViewById(R.id.dateTV);
        onDutyTextView = (TextView) itemView.findViewById(R.id.onDutyTV);
        offDutyTextView = (TextView) itemView.findViewById(R.id.offDutyTV);
        view = itemView;
    }

}

