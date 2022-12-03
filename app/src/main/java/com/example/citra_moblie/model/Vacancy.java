package com.example.citra_moblie.model;

import android.graphics.Bitmap;

import com.example.citra_moblie.helper.FirebaseHelper;
import com.google.firebase.database.DatabaseReference;

import java.io.Serializable;
import java.util.List;

public class Vacancy implements Serializable {
    private String idVacancy;
    private String idUser;
    private Bitmap vacancyImage;
    private String vacancyName;
    private String vacancyDescription;
    private String shiftSpinner;
    private String typeHiringSpinner;
    private String salarySpinner;
    private String vacancyLat;
    private String vacancyLog;
    private List<User> appliedCandidates;

    public Vacancy() {
        DatabaseReference reference = FirebaseHelper.getDatabaseReference();
        this.setIdVacancy(reference.push().getKey());
    }

    public void salvar() {
        DatabaseReference reference = FirebaseHelper.getDatabaseReference()
                .child("vacancies")
                .child(FirebaseHelper.getIdFirebase())
                .child(this.getIdVacancy());
        reference.setValue(this);

        DatabaseReference vacancies_geral = FirebaseHelper.getDatabaseReference()
                .child("vacancies_geral")
                .child(this.getIdVacancy());
        vacancies_geral.setValue(this);
    }

    public void deletar() {
        DatabaseReference reference = FirebaseHelper.getDatabaseReference()
                .child("vacancies")
                .child(FirebaseHelper.getIdFirebase())
                .child(this.getIdVacancy());
        reference.removeValue();

        DatabaseReference vacancies_geral = FirebaseHelper.getDatabaseReference()
                .child("vacancies_geral")
                .child(this.getIdVacancy());
        vacancies_geral.removeValue();
    }

    public Vacancy(Bitmap vacancyImage, String vacancyName, String vacancyDescription, String shiftSpinner,
                   String typeHiringSpinner, String salarySpinner, String vacancyLat, String vacancyLog, List<User> appliedCandidates) {
        this.vacancyImage = vacancyImage;
        this.vacancyName = vacancyName;
        this.shiftSpinner = shiftSpinner;
        this.vacancyDescription = vacancyDescription;
        this.typeHiringSpinner = typeHiringSpinner;
        this.salarySpinner = salarySpinner;
        this.vacancyLat = vacancyLat;
        this.vacancyLog = vacancyLog;
        this.appliedCandidates = appliedCandidates;
    }

    public Bitmap getVacancyImage() {
        return vacancyImage;
    }

    public void setVacancyImage(Bitmap vacancyImage) {
        this.vacancyImage = vacancyImage;
    }

    public String getVacancyName() {
        return vacancyName;
    }

    public void setVacancyName(String vacancyName) {
        this.vacancyName = vacancyName;
    }

    public String getShiftSpinner() {
        return shiftSpinner;
    }

    public void setShiftSpinner(String shiftSpinner) {
        this.shiftSpinner = shiftSpinner;
    }

    public String getVacancyDescription() {
        return vacancyDescription;
    }

    public void setVacancyDescription(String vacancyDescription) {
        this.vacancyDescription = vacancyDescription;
    }

    public String getTypeHiringSpinner() {
        return typeHiringSpinner;
    }

    public void setTypeHiringSpinner(String typeHiringSpinner) {
        this.typeHiringSpinner = typeHiringSpinner;
    }

    public String getSalarySpinner() {
        return salarySpinner;
    }

    public void setSalarySpinner(String salatySpinner) {
        this.salarySpinner = salatySpinner;
    }

    public String getVacancyLat() {
        return vacancyLat;
    }

    public void setVacancyLat(String vacancyLat) {
        this.vacancyLat = vacancyLat;
    }

    public String getVacancyLog() {
        return vacancyLog;
    }

    public void setVacancyLog(String vacancyLog) {
        this.vacancyLog = vacancyLog;
    }

    public List<User> getAppliedCandidates() {
        return appliedCandidates;
    }

    public void setAppliedCandidates(List<User> appliedCandidates) {
        this.appliedCandidates = appliedCandidates;
    }

    public String getIdVacancy() {
        return idVacancy;
    }

    public void setIdVacancy(String idVacancy) {
        this.idVacancy = idVacancy;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }
}
