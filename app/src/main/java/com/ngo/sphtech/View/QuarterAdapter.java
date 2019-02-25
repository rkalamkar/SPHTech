package com.ngo.sphtech.View;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ngo.sphtech.Pojo.Records;
import com.ngo.sphtech.R;

import java.util.ArrayList;

public class QuarterAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    ArrayList<Records> records;

    public QuarterAdapter(Context context, ArrayList<Records> records) {
        this.context = context;
        this.records = records;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.subitem, viewGroup, false);
        return new SubDataViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof SubDataViewHolder) {
            SubDataViewHolder subDataViewHolder = (SubDataViewHolder) viewHolder;
            subDataViewHolder.txtQuarter.setText(records.get(i).getQuarter());
            subDataViewHolder.txtData.setText(Double.toString(records.get(i).getVolume_of_mobile_data()));
        }
    }

    @Override
    public int getItemCount() {
        return records.size();
    }

    public class SubDataViewHolder extends RecyclerView.ViewHolder {

        TextView txtQuarter, txtData;

        public SubDataViewHolder(@NonNull View itemView) {
            super(itemView);
            txtQuarter = (TextView) itemView.findViewById(R.id.textViewQuarterName);
            txtData = (TextView) itemView.findViewById(R.id.textViewQuarterData);
        }
    }
}
