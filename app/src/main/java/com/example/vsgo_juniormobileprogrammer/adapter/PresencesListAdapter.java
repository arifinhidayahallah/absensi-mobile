package com.example.vsgo_juniormobileprogrammer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vsgo_juniormobileprogrammer.R;
import com.example.vsgo_juniormobileprogrammer.models.PresenceModel;
import com.example.vsgo_juniormobileprogrammer.recyclers.PresenceViewHolder;

import java.util.Collections;
import java.util.List;

public class PresencesListAdapter extends RecyclerView.Adapter<PresenceViewHolder> {

    List<PresenceModel> list = Collections.emptyList();
    public PresencesListAdapter(List<PresenceModel> list){
        this.list = list;
    }

    @NonNull
    @Override
    public PresenceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View presencesView = layoutInflater.inflate(R.layout.history_presences, parent, false);
        return new PresenceViewHolder(presencesView);
    }

    @Override
    public void onBindViewHolder(@NonNull PresenceViewHolder holder, int position) {
        final int index = holder.getBindingAdapterPosition();
        holder.dateTextView.setText(list.get(position).getDate());
        holder.onDutyTextView.setText(list.get(position).getOnDuty());
        holder.offDutyTextView.setText(list.get(position).getOffDuty());

        holder.itemView.setOnClickListener(v -> {
            System.out.println("ID Absensi : "+list.get(position).getId());
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
