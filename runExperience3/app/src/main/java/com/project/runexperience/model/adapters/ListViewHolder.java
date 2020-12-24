package com.project.runexperience.model.adapters;

import android.view.View;
import android.widget.TextView;

import com.project.runexperience.R;

import androidx.recyclerview.widget.RecyclerView;

public class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public TextView txtMeters, txtInicio, txtFinal,txtType;

    public ItemClickListner listner;


    public ListViewHolder(View itemView)
    {
        super(itemView);

        txtMeters = (TextView) itemView.findViewById(R.id.TotalMeters);
        txtInicio = (TextView) itemView.findViewById(R.id.time);
        txtFinal = (TextView) itemView.findViewById(R.id.pid);
        //txtType = (TextView) itemView.findViewById(R.id.t);
    }

    public void setItemClickListner(ItemClickListner listner)
    {
        this.listner = listner;
    }

    @Override
    public void onClick(View view)
    {
        listner.onClick(view, getAdapterPosition(), false);
    }
}
