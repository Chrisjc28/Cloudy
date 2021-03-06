package com.example.android.cloudy.adpaters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
        public TextView textViewMinTemp;
        public TextView textViewMaxTemp;
        public TextView textViewDescription;
        public ImageView weeklyForecastIcon;
        public ViewHolder(View v) {
            super(v);
            cardViewForFiveDayForecasts = (CardView) v.findViewById(R.id.forecast_card_view);
            textViewDate = (TextView) v.findViewById(R.id.text_view_current_date);
            textViewWind = (TextView) v.findViewById(R.id.text_view_wind);
            textViewMinTemp = (TextView) v.findViewById(R.id.text_view_min_temp);
            textViewMaxTemp = (TextView) v.findViewById(R.id.text_view_max_temp);
            textViewDescription = (TextView) v.findViewById(R.id.text_view_description);
            weeklyForecastIcon = (ImageView) v.findViewById(R.id.weekly_forecastIcon);
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
        holder.textViewWind.setText(String.format("The wind speed is %s", dataSet.get(position).getWind().toString()+ " KPH"));
        holder.textViewMinTemp.setText(String.format("Min " + dataSet.get(position).getTempMin() + "°C"));
        holder.textViewMaxTemp.setText(String.format("Max "+ dataSet.get(position).getTempMax() + "°C"));
        holder.textViewDescription.setText(dataSet.get(position).getDescription());


        switch (dataSet.get(position).getDescription()) {
            case "cloudy":
                holder.weeklyForecastIcon.setImageResource(R.drawable.cloudy);
                break;
            case "clear sky":
                holder.weeklyForecastIcon.setImageResource(R.drawable.sunny);
                break;
            case "mist":
                holder.weeklyForecastIcon.setImageResource(R.drawable.mist);
                break;
            case "few clouds":
                holder.weeklyForecastIcon.setImageResource(R.drawable.cloudy);
                break;
            case "scattered clouds":
                holder.weeklyForecastIcon.setImageResource(R.drawable.cloudy);
                break;
            case "overcast clouds":
                holder.weeklyForecastIcon.setImageResource(R.drawable.cloudy);
                break;
            case "heavy intensity rain":
                holder.weeklyForecastIcon.setImageResource(R.drawable.rain);
                break;
            case "light intensity shower rain":
                holder.weeklyForecastIcon.setImageResource(R.drawable.rain);
                break;
            case "shower rain":
                holder.weeklyForecastIcon.setImageResource(R.drawable.rain);
                break;
            case "light rain":
                holder.weeklyForecastIcon.setImageResource(R.drawable.rain);
                break;
            case "moderate rain":
                holder.weeklyForecastIcon.setImageResource(R.drawable.rain);
                break;
            default:
                holder.weeklyForecastIcon.setImageResource(R.drawable.cloudy);
                break;
        }

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

}
