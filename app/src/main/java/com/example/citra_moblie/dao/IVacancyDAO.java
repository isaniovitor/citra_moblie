package com.example.citra_moblie.dao;

import com.example.citra_moblie.model.Vacancy;

import java.util.List;

public interface IVacancyDAO {
    void getVacanciesFromAPI();
//    void userAppliedVacancies();
//    void userCreatedVacancies();
    List<Vacancy> getVacancies();
//    List<Vacancy> getUserAppliedVacancies();
//    List<Vacancy> getUserCreatedVacancies();
    Vacancy getVacancy(int id);
//    Vacancy getAppliedVacancy(int id);
//    Vacancy getCreatedVacancy(int id);
    boolean addVacancy(Vacancy vacancy);
    boolean editVacancy(Vacancy vacancyEdited, Vacancy oldVacancy);
    boolean removeVacancy(Vacancy vacancy);
    void setVacancies(List<Vacancy> vacancies);
}
