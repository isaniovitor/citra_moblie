package com.example.citra_moblie.adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.citra_moblie.R;
import com.example.citra_moblie.model.Vacancy;
import com.squareup.picasso.Picasso;

import java.util.List;

public class VacancyRecyclerViewAdapter extends RecyclerView.Adapter<VacancyRecyclerViewAdapter.MyViewHolder> {
    private List<Vacancy> vacancies;

    public VacancyRecyclerViewAdapter(List<Vacancy> vacancies) {
        this.vacancies = vacancies;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView vacancyImage;
        TextView vacancyName;
        TextView vacancySalary;
        TextView vacancyShift;
        TextView vacancyTypeHiring;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            vacancyImage = itemView.findViewById(R.id.vacancyImage);
            vacancyName = itemView.findViewById(R.id.vacancyName);
            vacancySalary = itemView.findViewById(R.id.vacancySalary);
            vacancyShift = itemView.findViewById(R.id.vacancyShift);
            vacancyTypeHiring = itemView.findViewById(R.id.vacancyTypeHiring);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vacancy = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_vacancies_adapter, parent, false);
        return new MyViewHolder(vacancy);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Vacancy vacancy = vacancies.get(position);

        if (vacancy.getVacancyImage() != null) {
            Picasso.get().load(Uri.parse(vacancy.getVacancyImage()))
                    .into(holder.vacancyImage);
        }

        holder.vacancyName.setText(vacancy.getVacancyName());
        holder.vacancySalary.setText(vacancy.getSalarySpinner());
        holder.vacancyShift.setText(vacancy.getShiftSpinner());
        holder.vacancyTypeHiring.setText(vacancy.getTypeHiringSpinner());
    }

    @Override
    public int getItemCount() {
        return vacancies.size();
    }

    public void setVacancies(List<Vacancy> vacancies) {
        this.vacancies = vacancies;
        notifyDataSetChanged();
    }
}
