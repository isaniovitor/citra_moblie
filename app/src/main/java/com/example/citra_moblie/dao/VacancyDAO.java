package com.example.citra_moblie.dao;

import android.content.Context;
import android.widget.Toast;


import com.example.citra_moblie.model.Vacancy;

import java.util.ArrayList;
import java.util.List;

public class VacancyDAO implements IVacancyDAO {
    private static Context context;
    private static VacancyDAO vacancyDAO = null;
    private List<Vacancy> homeVacancies = new ArrayList<>();
    private List<Vacancy> userAppliedVacancies = new ArrayList<>();
    private List<Vacancy> userCreatedVacancies = new ArrayList<>();

    private VacancyDAO(Context context) {
        VacancyDAO.context = context;
        createHomeVacanciesMock();
        userAppliedVacancies();
        userCreatedVacancies();
    }

    public static IVacancyDAO getInstance(Context context) {
        if (vacancyDAO == null) {
            vacancyDAO = new VacancyDAO(context);
//            createHomeVacanciesMock();
//            userAppliedVacancies();
//            userCreatedVacancies();
        }
        return vacancyDAO;
    }

    @Override
    public void createHomeVacanciesMock() {
        Vacancy vacancy = new Vacancy(null, "Pedreiro",
                "ser um bom pedreito", "manhã", "CLT", "2000");
        homeVacancies.add(vacancy);

        vacancy = new Vacancy(null, "Pedreiro bom",
                "ser um bom pedreito", "tarde", "CLT", "1000");
        homeVacancies.add(vacancy);

        vacancy = new Vacancy(null, "Pedreiro do brabo",
                "ser um bom pedreito", "tarde", "SDE", "1200");
        homeVacancies.add(vacancy);

        vacancy = new Vacancy(null, "Pedreiro Eiro",
                "ser um bom pedreito", "tarde", "STE", "3000");
        homeVacancies.add(vacancy);
        vacancy = new Vacancy(null, "Pedro eiro",
                "ser um bom pedreito", "noite", "STE", "2400");
        homeVacancies.add(vacancy);
    }

    @Override
    public void userAppliedVacancies() {
        Vacancy vacancy = new Vacancy(null, "Pedreiro",
                "ser um bom pedreito", "manhã", "CLT", "2000");
        userAppliedVacancies.add(vacancy);

        vacancy = new Vacancy(null, "Pedreiro bom",
                "ser um bom pedreito", "tarde", "CLT", "1000");
        userAppliedVacancies.add(vacancy);

        vacancy = new Vacancy(null, "Pedreiro do brabo",
                "ser um bom pedreito", "tarde", "SDE", "1200");
        userAppliedVacancies.add(vacancy);
    }

    @Override
    public void userCreatedVacancies() {
        Vacancy vacancy = new Vacancy(null, "Pedreiro Eiro",
                "ser um bom pedreito", "tarde", "STE", "3000");
        userCreatedVacancies.add(vacancy);
        vacancy = new Vacancy(null, "Pedro eiro",
                "ser um bom pedreito", "noite", "STE", "2400");
        userCreatedVacancies.add(vacancy);
    }

    @Override
    public List<Vacancy> getHomeVacancies() {
        return homeVacancies;
    }

    @Override
    public List<Vacancy> getUserAppliedVacancies() {
        return userAppliedVacancies;
    }

    @Override
    public List<Vacancy> getUserCreatedVacancies() {
        return userCreatedVacancies;
    }

    @Override
    public Vacancy getVacancy(int id) {
        return homeVacancies.get(id);
    }

    @Override
    public Vacancy getAppliedVacancy(int id) {
        return userAppliedVacancies.get(id);
    }

    @Override
    public Vacancy getCreatedVacancy(int id) {
        return userCreatedVacancies.get(id);
    }

    @Override
    public boolean addVacancy(Vacancy vacancy) {
        return userCreatedVacancies.add(vacancy);
    }

    @Override
    public boolean editVacancy(Vacancy vacancyEdited, Vacancy oldVacancy) {
        userCreatedVacancies.set(homeVacancies.indexOf(oldVacancy), vacancyEdited);
        return true;
    }

    @Override
    public boolean removeVacancy(Vacancy vacancy) {
        return userCreatedVacancies.remove(vacancy);
    }

    public void setVacancies(List<Vacancy> vacancies) {
        this.homeVacancies = vacancies;
    }
}
