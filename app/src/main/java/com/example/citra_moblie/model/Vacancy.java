package com.example.citra_moblie.model;

import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.citra_moblie.R;

import java.io.Serializable;
import java.net.URL;
import java.util.List;

public class Vacancy implements Serializable {
    private String idVacancy;
    private String idUser;
    private String vacancyImage;
    private String vacancyName;
    private String vacancyDescription;
    private String shiftSpinner;
    private String typeHiringSpinner;
    private String salarySpinner;
    private String vacancyLat;
    private String vacancyLog;
//    private List<User> appliedCandidates;

    public Vacancy() {

    }

    public Vacancy(String idVacancy, String idUser, String vacancyImage, String vacancyName, String vacancyDescription, String shiftSpinner,
                   String typeHiringSpinner, String salarySpinner, String vacancyLat, String vacancyLog) {
        this.idVacancy = idVacancy;
        this.idUser = idUser;
        this.vacancyImage = vacancyImage;
        this.vacancyName = vacancyName;
        this.shiftSpinner = shiftSpinner;
        this.vacancyDescription = vacancyDescription;
        this.typeHiringSpinner = typeHiringSpinner;
        this.salarySpinner = salarySpinner;
        this.vacancyLat = vacancyLat;
        this.vacancyLog = vacancyLog;
//        this.appliedCandidates = appliedCandidates;
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

    public String getVacancyImage() {
        return vacancyImage;
    }

    public void setVacancyImage(String vacancyImage) {
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

//    public List<User> getAppliedCandidates() {
//        return appliedCandidates;
//    }
//
//    public void setAppliedCandidates(List<User> appliedCandidates) {
//        this.appliedCandidates = appliedCandidates;
//    }
}
