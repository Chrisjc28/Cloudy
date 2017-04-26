package com.example.android.cloudy.adpaters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.cloudy.R;
import com.example.android.cloudy.data.model.remote.ForecastHolder;

import java.util.ArrayList;

/**
 * Created by ccu17 on 24/04/2017.
 */

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ViewHolder> {

    private ArrayList<ForecastHolder> dataSet;

    public ForecastAdapter(ArrayList<ForecastHolder> dataSet) {
        this.dataSet = dataSet;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CardView cardViewForFiveDayForecasts;
        public TextView textViewDate;
        public TextView textViewWind;
        public TextView textViewMaxTemp;
        public TextView textViewMinTemp;
        public TextView textViewDescription;
        public ViewHolder(View v) {
            super(v);
            cardViewForFiveDayForecasts = (CardView) v.findViewById(R.id.forecast_card_view);
            textViewDate = (TextView) v.findViewById(R.id.text_view_current_date);
            textViewWind = (TextView) v.findViewById(R.id.text_view_wind);
            textViewMinTemp = (TextView) v.findViewById(R.id.text_view_min_temp);
            textViewMaxTemp = (TextView) v.findViewById(R.id.text_view_max_temp);
            textViewDescription = (TextView) v.findViewById(R.id.text_view_description);
        }
    }

    @Override
    public ForecastAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.forecast_recycler_card_view, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ForecastAdapter.ViewHolder holder, int position) {
        holder.textViewDate.setText(dataSet.get(position).getDate());
        holder.textViewWind.setText(String.format("Wind speeds equal to %s", dataSet.get(position).getWind() + "mph"));
        holder.textViewMinTemp.setText(String.format("Min temp %s", dataSet.get(position).getTempMin()));
        holder.textViewMaxTemp.setText(String.format("Max temp %s", dataSet.get(position).getTempMax()));
        holder.textViewDescription.setText(String.format("The Forecast is %s", dataSet.get(position).getDescription()));

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

}
