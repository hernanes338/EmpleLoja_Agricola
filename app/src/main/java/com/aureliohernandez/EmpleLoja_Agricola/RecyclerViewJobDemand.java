package com.aureliohernandez.EmpleLoja_Agricola;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aureliohernandez.EmpleLoja_Agricola.Model.JobDemand;

import java.util.ArrayList;

public class RecyclerViewJobDemand extends RecyclerView.Adapter<RecyclerViewJobDemand.MyViewHolder> {
    private final RecyclerViewJobDemandInterface recyclerViewJobDemandInterface;

    Context context;
    ArrayList<JobDemand> jobDemands;

    public RecyclerViewJobDemand(Context context, ArrayList<JobDemand> jobDemands, RecyclerViewJobDemandInterface recyclerViewJobDemandInterface) {
        this.context = context;
        this.jobDemands = jobDemands;
        this.recyclerViewJobDemandInterface = recyclerViewJobDemandInterface;
    }


    @NonNull
    @Override
    public RecyclerViewJobDemand.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout (Giving a look to our rows)
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_row_job_demand, parent, false);
        return new RecyclerViewJobDemand.MyViewHolder(view, recyclerViewJobDemandInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewJobDemand.MyViewHolder holder, int position) {
        // Assigning values to the views created in the layout file based on the position of the recycler view
        holder.jobDemandTitle.setText(jobDemands.get(position).getTitle());
        holder.jobAvailable.setText(String.valueOf("Disponible desde: " + jobDemands.get(position).getAvailable_from()));
    }

    @Override
    public int getItemCount() {
        // the recycler view wants to know the number of items you want displayed
        return jobDemands.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // grabbing the views from our recycler_view_row layout file (like on create method)

        TextView jobDemandTitle, jobAvailable;
        public MyViewHolder(@NonNull View itemView, RecyclerViewJobDemandInterface recyclerViewJobDemandInterface) {
            super(itemView);

            jobDemandTitle = itemView.findViewById(R.id.jobDemandTitle);
            jobAvailable = itemView.findViewById(R.id.jobAvailable);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (recyclerViewJobDemandInterface != null) {
                        int position = getAdapterPosition();

                        if (position != RecyclerView.NO_POSITION) {
                            recyclerViewJobDemandInterface.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
