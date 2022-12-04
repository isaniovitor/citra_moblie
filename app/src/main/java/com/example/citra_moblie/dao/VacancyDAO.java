package com.example.citra_moblie.dao;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;


import androidx.annotation.NonNull;

import com.example.citra_moblie.R;
import com.example.citra_moblie.model.User;
import com.example.citra_moblie.model.Vacancy;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VacancyDAO implements IVacancyDAO {
    private static Context context;
    private static VacancyDAO vacancyDAO = null;
    private List<User> appliedUsers = new ArrayList<>();
    private List<Vacancy> vacancies = new ArrayList<>();
//    private List<Vacancy> userAppliedVacancies = new ArrayList<>();
//    private List<Vacancy> userCreatedVacancies = new ArrayList<>();
    IUserDAO userDAO = UserDAO.getInstance(context);

    private VacancyDAO(Context context) {
        VacancyDAO.context = context;

//        createHomeVacanciesMock();
//        userAppliedVacancies();
//        userCreatedVacancies();
    }

    public static IVacancyDAO getInstance(Context context) {
        if (vacancyDAO == null) {
            vacancyDAO = new VacancyDAO(context);
        }
        return vacancyDAO;
    }

    @Override
    public void getVacanciesFromAPI() {
        // Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.image1);
        DatabaseReference vacanciesReference = FirebaseDatabase.getInstance().getReference().child("AllVacancies");
        vacanciesReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                getVacancies().clear();
                List<Vacancy> vacancies = new ArrayList<>();

                if(snapshot.exists()){
                    for(DataSnapshot snap : snapshot.getChildren()){
                        Vacancy vacancy = snap.getValue(Vacancy.class);

                        // pegando os usuários
//                        DatabaseReference appliedCandidatesReference = FirebaseDatabase.getInstance().getReference().child("AllVacancies")
//                                .child(vacancy.getIdVacancy()).child("appliedCandidates");
//                        appliedCandidatesReference.addValueEventListener(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                                List<User> users = new ArrayList<>();
//
//                                if(snapshot.exists()){
//                                    for(DataSnapshot snap : snapshot.getChildren()){
//                                        User user = snap.getValue(User.class);
//                                        users.add(user);
//                                    }
//                                    vacancy.setAppliedCandidates(users);
//                                }
//                            }
//
//                            @Override
//                            public void onCancelled(@NonNull DatabaseError error) {
//
//                            }
//                        });

                        vacancies.add(vacancy);
                    }

                    setVacancies(vacancies);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

//    @Override
//    public void userAppliedVacancies() {
//        userAppliedVacancies.add(vacancies.get(0));
//        userAppliedVacancies.add(vacancies.get(1));
//        userAppliedVacancies.add(vacancies.get(2));
//    }
//
//    @Override
//    public void userCreatedVacancies() {
//        userCreatedVacancies.add(homeVacancies.get(3));
//        userCreatedVacancies.add(homeVacancies.get(4));
//    }

    @Override
    public List<Vacancy> getVacancies() {
        return vacancies;
    }

//    @Override
//    public List<Vacancy> getUserAppliedVacancies() {
//        return userAppliedVacancies;
//    }
//
//    @Override
//    public List<Vacancy> getUserCreatedVacancies() {
//        return userCreatedVacancies;
//    }

    @Override
    public Vacancy getVacancy(int id) {
        return vacancies.get(id);
    }

//    @Override
//    public Vacancy getAppliedVacancy(int id) {
//        return userAppliedVacancies.get(id);
//    }
//
//    @Override
//    public Vacancy getCreatedVacancy(int id) {
//        return userCreatedVacancies.get(id);
//    }

    @Override
    public boolean addVacancy(Vacancy vacancy) {
        try{
            DatabaseReference usersVacanciesReference = FirebaseDatabase.getInstance().getReference();
            usersVacanciesReference.child("usersVacancies").child(userDAO.getUser().getId()).
                    child(vacancy.getIdVacancy()).setValue(vacancy.getIdVacancy());

            DatabaseReference AllVacancies =  FirebaseDatabase.getInstance().getReference();
            AllVacancies.child("AllVacancies").child(vacancy.getIdVacancy()).setValue(vacancy);

            return true;
        }catch (Exception e){
            throw e;
        }
    }

    @Override
    public boolean editVacancy(Vacancy vacancyEdited, Vacancy oldVacancy) {
        vacancies.set(vacancies.indexOf(oldVacancy), vacancyEdited);
        return true;
    }

    @Override
    public boolean removeVacancy(Vacancy vacancy) {
        return vacancies.remove(vacancy);
    }

    @Override
    public void setVacancies(List<Vacancy> vacancies) {
        this.vacancies = vacancies;
    }
}
