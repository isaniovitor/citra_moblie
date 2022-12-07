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
import com.example.citra_moblie.model.User;
import com.example.citra_moblie.model.Vacancy;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CandidateRecyclerViewAdapter extends RecyclerView.Adapter<CandidateRecyclerViewAdapter.MyViewHolder> {
    private List<User> candidates;

    public CandidateRecyclerViewAdapter(List<User> candidates) {
        this.candidates = candidates;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView candidateImage;
        TextView candidateName;
        TextView candidateEmail;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            candidateImage = itemView.findViewById(R.id.userImage);
            candidateName = itemView.findViewById(R.id.userName);
            candidateEmail = itemView.findViewById(R.id.userEmail);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vacancy = LayoutInflater.from(parent.getContext()).inflate(R.layout.candidates_adapter, parent, false);
        return new MyViewHolder(vacancy);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        User candidate = candidates.get(position);

        if (candidate.getImage() != null) {
            Picasso.get().load(Uri.parse(candidate.getImage()))
                    .into(holder.candidateImage);
        }

        holder.candidateName.setText(candidate.getName());
        holder.candidateEmail.setText(candidate.getEmail());
    }

    @Override
    public int getItemCount() {
        return candidates.size();
    }
}
