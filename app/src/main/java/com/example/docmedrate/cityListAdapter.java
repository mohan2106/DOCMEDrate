package com.example.docmedrate;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class cityListAdapter extends RecyclerView.Adapter<cityListAdapter.ViewHolder> {
    private Context context;
    private ArrayList<City_name> cityArray;

    public cityListAdapter(Context context, ArrayList<City_name> cityList) {
        this.context = context;
        this.cityArray=cityList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v=LayoutInflater.from(context).inflate(R.layout.single_city_name,viewGroup,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        City_name ne=cityArray.get(i);
        final String name=ne.getName();
        viewHolder.city.setText(ne.getName());
        viewHolder.city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(context,SearchDoctor.class);
                intent.putExtra("City_name",name);
                context.startActivity(intent);
                ((Activity)(context)).finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return cityArray.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView city;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            city=(TextView)itemView.findViewById(R.id.city_text);
        }
    }
    public void filterList(ArrayList<City_name> filteredList) {
        cityArray = filteredList;
        notifyDataSetChanged();
    }
}
