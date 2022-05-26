package com.aureliohernandez.EmpleLoja_Agricola.Jobs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aureliohernandez.EmpleLoja_Agricola.Model.JobDemand;
import com.aureliohernandez.EmpleLoja_Agricola.R;

import java.util.ArrayList;

public class RecyclerViewJobDemand extends RecyclerView.Adapter<RecyclerViewJobDemand.MyViewHolder> {
    private final RecyclerViewJobDemandInterface recyclerViewJobDemandInterface;

    Context context; // necesario para darle contenido a la vista
    ArrayList<JobDemand> jobDemands; // contiene el modelo de datos

    public RecyclerViewJobDemand(Context context, ArrayList<JobDemand> jobDemands, RecyclerViewJobDemandInterface recyclerViewJobDemandInterface) {
        this.context = context;
        this.jobDemands = jobDemands;
        this.recyclerViewJobDemandInterface = recyclerViewJobDemandInterface;
    }


    @NonNull
    @Override
    public RecyclerViewJobDemand.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Creacion del aspecto de los elementos
        // genera la vista de cada elemento
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_row_job_demand, parent, false);
        return new RecyclerViewJobDemand.MyViewHolder(view, recyclerViewJobDemandInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewJobDemand.MyViewHolder holder, int position) {
        // Asignar valores a la vistas creadas basadas en la posicion del recycler view
        // cambiar los datos dependiendo de la posicion del elemento
        holder.jobDemandTitle.setText(jobDemands.get(position).getTitle());
        holder.jobAvailable.setText(String.valueOf("Disponible desde: " + jobDemands.get(position).getAvailable_from()));
    }

    @Override
    public int getItemCount() {
        // Obtener el numero de elementos a mostrar en total
        return jobDemands.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // Obtener los elementos de la vista del elemento y asignar sus valores a variables

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
