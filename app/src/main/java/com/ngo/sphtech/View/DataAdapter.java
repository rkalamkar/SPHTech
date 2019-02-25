package com.ngo.sphtech.View;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ngo.sphtech.MainActivity;
import com.ngo.sphtech.Pojo.Records;
import com.ngo.sphtech.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class DataAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    TreeMap<String, Double> list;
    HashMap<String, ArrayList<Records>> quarterList;
    String[] years;

    public DataAdapter(Context context, TreeMap<String, Double> list, HashMap<String, ArrayList<Records>> quarterList) {
        this.context = context;
        this.list = list;
        this.quarterList = quarterList;
        years = list.keySet().toArray(new String[list.size()]);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
        return new DataViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof DataViewHolder) {
            DataViewHolder dataViewHolder = (DataViewHolder) viewHolder;
            dataViewHolder.txtYear.setText(years[position]);
            dataViewHolder.txtData.setText(Double.toString(list.get(years[position])));
            QuarterAdapter quarterAdapter = new QuarterAdapter(context, quarterList.get(years[position]));
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            dataViewHolder.recyclerView.setLayoutManager(linearLayoutManager);
            dataViewHolder.recyclerView.setAdapter(quarterAdapter);

            if (position != 0) {
                if (list.get(years[position]) > list.get(years[position - 1])) {
                    dataViewHolder.imgIndicator.setImageResource(R.drawable.ic_arrow_upward_black_24dp);
                } else {
                    dataViewHolder.imgIndicator.setImageResource(R.drawable.ic_arrow_downward_black_24dp);
                }
                dataViewHolder.imgIndicator.setVisibility(View.VISIBLE);
            } else {
                dataViewHolder.imgIndicator.setVisibility(View.INVISIBLE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class DataViewHolder extends RecyclerView.ViewHolder {
        TextView txtYear, txtData;
        ImageView imgIndicator;
        RecyclerView recyclerView;

        public DataViewHolder(@NonNull View itemView) {
            super(itemView);
            txtYear = (TextView) itemView.findViewById(R.id.textViewYear);
            txtData = (TextView) itemView.findViewById(R.id.textViewData);
            imgIndicator = (ImageView) itemView.findViewById(R.id.imageViewIndicator);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.quarterRecyclerView);

            imgIndicator.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String message = "", title = "";
                    if (list.get(years[getAdapterPosition()]) > list.get(years[(getAdapterPosition() - 1)])) {
                        title = "Mobile data usage increased";
                        double diff = list.get(years[getAdapterPosition()]) - list.get(years[(getAdapterPosition() - 1)]);
                        message = diff + " PB increased data as compare to " + years[(getAdapterPosition() - 1)] + " year";
                    } else {
                        title = "Mobile data usage decreased";
                        double diff = list.get(years[(getAdapterPosition() - 1)]) - list.get(years[getAdapterPosition()]);
                        message = diff + " PB decreased data as compare to " + years[(getAdapterPosition() - 1)] + " year";
                    }
                    MainActivity.showDialogView(title, message);
                }
            });
        }
    }
}
