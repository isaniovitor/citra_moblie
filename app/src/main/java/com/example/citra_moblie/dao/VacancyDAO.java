package com.example.citra_moblie.dao;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;

import com.example.citra_moblie.helper.LoadingDialog;
import com.example.citra_moblie.model.Vacancy;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class VacancyDAO implements IVacancyDAO {
    private static Context context;
    private static VacancyDAO vacancyDAO = null;
    private List<Vacancy> vacancies = new ArrayList<>();

    // getusersAplblied, fazer edit e delete

    private VacancyDAO(Context context) {
        VacancyDAO.context = context;
    }

    public static IVacancyDAO getInstance(Context context) {
        if (vacancyDAO == null) {
            vacancyDAO = new VacancyDAO(context);
        }
        return vacancyDAO;
    }

    @Override
    public void getVacanciesFromAPI(Activity activity) {
        // LoadingDialog loadingDialog = new LoadingDialog(activity);
        // loadingDialog.startAlertDialog();

        DatabaseReference vacanciesReference = FirebaseDatabase.getInstance().getReference().child("AllVacancies");
        vacanciesReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                getVacancies().clear();
                List<Vacancy> vacancies = new ArrayList<>();

                if(snapshot.exists()){
                    for(DataSnapshot snap : snapshot.getChildren()){
                        Vacancy vacancy = snap.getValue(Vacancy.class);
                        vacancies.add(vacancy);
                    }

                    // loadingDialog.dismissAlertDialog();
                    setVacancies(vacancies);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public List<Vacancy> getVacancies() {
        return vacancies;
    }

    @Override
    public Vacancy getVacancy(int id) {
        return vacancies.get(id);
    }

    @Override
    public void addVacancy(Vacancy vacancy, OnCompleteListener onCompleteListener) {
        try{
            DatabaseReference AllVacancies =  FirebaseDatabase.getInstance().getReference();
            AllVacancies.child("AllVacancies").child(vacancy.getIdVacancy()).setValue(vacancy)
                    .addOnCompleteListener(onCompleteListener);
        }catch (Exception e){
            throw e;
        }
    }

    @Override
    public void editVacancy(Vacancy vacancyEdited, Vacancy oldVacancy) {
        //getVacanciesFromAPI();
    }

    @Override
    public void removeVacancy(Vacancy vacancy) {
        //getVacanciesFromAPI();
    }

    @Override
    public void setVacancies(List<Vacancy> vacancies) {
        this.vacancies = vacancies;
    }
}
