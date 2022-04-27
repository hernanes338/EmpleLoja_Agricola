package com.aureliohernandez.EmpleLoja_Agricola;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aureliohernandez.EmpleLoja_Agricola.Model.JobOffer;

import java.util.ArrayList;

public class RecyclerViewJobOffer extends RecyclerView.Adapter<RecyclerViewJobOffer.MyViewHolder> {
    private final RecyclerViewJobOfferInterface recyclerViewJobOfferInterface;

    Context context;
    ArrayList<JobOffer> jobOffers;

    public RecyclerViewJobOffer (Context context, ArrayList<JobOffer> jobOffers, RecyclerViewJobOfferInterface recyclerViewJobOfferInterface) {
        this.context = context;
        this.jobOffers = jobOffers;
        this.recyclerViewJobOfferInterface = recyclerViewJobOfferInterface;
    }


    @NonNull
    @Override
    public RecyclerViewJobOffer.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout (Giving a look to our rows)
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_row, parent, false);
        return new RecyclerViewJobOffer.MyViewHolder(view, recyclerViewJobOfferInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewJobOffer.MyViewHolder holder, int position) {
        // Assigning values to the views created in the layout file based on the position of the recycler view
        holder.jobName.setText(jobOffers.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        // the recycler view wants to know the number of items you want displayed
        return jobOffers.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // grabbing the views from our recycler_view_row layout file (like on create method)

        TextView jobName;
        public MyViewHolder(@NonNull View itemView, RecyclerViewJobOfferInterface recyclerViewJobOfferInterface) {
            super(itemView);

            jobName = itemView.findViewById(R.id.jobName);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (recyclerViewJobOfferInterface != null) {
                        int position = getAdapterPosition();

                        if (position != RecyclerView.NO_POSITION) {
                            recyclerViewJobOfferInterface.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
