package com.example.citra_moblie.dao;

import android.app.Activity;

import com.example.citra_moblie.model.Vacancy;
import com.google.android.gms.tasks.OnCompleteListener;

import java.util.List;

public interface IVacancyDAO {
    void getVacanciesFromAPI(Activity activity);
//    void userAppliedVacancies();
//    void userCreatedVacancies();
    List<Vacancy> getVacancies();
//    List<Vacancy> getUserAppliedVacancies();
//    List<Vacancy> getUserCreatedVacancies();
    Vacancy getVacancy(int id);
//    Vacancy getAppliedVacancy(int id);
//    Vacancy getCreatedVacancy(int id);
    void addVacancy(Vacancy vacancy, OnCompleteListener onCompleteListener);
    void editVacancy(Vacancy vacancyEdited, Vacancy oldVacancy);
    void removeVacancy(Vacancy vacancy);
    void setVacancies(List<Vacancy> vacancies);
}
