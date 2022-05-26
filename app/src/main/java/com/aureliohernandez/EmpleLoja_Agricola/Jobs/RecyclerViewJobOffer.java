package com.aureliohernandez.EmpleLoja_Agricola.Jobs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aureliohernandez.EmpleLoja_Agricola.Model.JobOffer;
import com.aureliohernandez.EmpleLoja_Agricola.R;

import java.util.ArrayList;

public class RecyclerViewJobOffer extends RecyclerView.Adapter<RecyclerViewJobOffer.MyViewHolder> {
    String euro = "\u20ac";
    private final RecyclerViewJobOfferInterface recyclerViewJobOfferInterface;

    Context context; // necesario para darle contenido a la vista
    ArrayList<JobOffer> jobOffers; // contiene el modelo de datos

    public RecyclerViewJobOffer (Context context, ArrayList<JobOffer> jobOffers, RecyclerViewJobOfferInterface recyclerViewJobOfferInterface) {
        this.context = context;
        this.jobOffers = jobOffers;
        this.recyclerViewJobOfferInterface = recyclerViewJobOfferInterface;
    }


    @NonNull
    @Override
    public RecyclerViewJobOffer.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Creacion del aspecto de los elementos
        // genera la vista de cada elemento
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_row_job_offer, parent, false);
        return new RecyclerViewJobOffer.MyViewHolder(view, recyclerViewJobOfferInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewJobOffer.MyViewHolder holder, int position) {
        // Asignar valores a la vistas creadas basadas en la posicion del recycler view
        // cambiar los datos dependiendo de la posicion del elemento
        holder.jobOfferTitle.setText(jobOffers.get(position).getTitle());
        holder.jobSalary.setText("Salario/hora: " +String.valueOf(jobOffers.get(position).getSalary_hour()) + euro);
        holder.jobStart.setText(String.valueOf("Inicio: " + jobOffers.get(position).getStart_date()));
    }

    @Override
    public int getItemCount() {
        // Obtener el numero de elementos a mostrar en total
        return jobOffers.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // Obtener los elementos de la vista del elemento y asignar sus valores a variables

        TextView jobOfferTitle, jobSalary, jobStart;
        public MyViewHolder(@NonNull View itemView, RecyclerViewJobOfferInterface recyclerViewJobOfferInterface) {
            super(itemView);

            jobOfferTitle = itemView.findViewById(R.id.jobOfferTitle);
            jobSalary = itemView.findViewById(R.id.jobSalary);
            jobStart = itemView.findViewById(R.id.jobStart);

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
