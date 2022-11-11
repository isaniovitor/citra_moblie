package com.example.citra_moblie.model;

import android.graphics.Bitmap;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.citra_moblie.R;

import java.io.Serializable;
import java.util.List;

public class Vacancy implements Serializable {
    private Bitmap vacancyImage;
    private String vacancyName;
    private String vacancyDescription;
    private String shiftSpinner;
    private String typeHiringSpinner;
    private String salarySpinner;
    private List<User> appliedCandidates;

    public Vacancy() {
    }

    public Vacancy(Bitmap vacancyImage, String vacancyName, String vacancyDescription, String shiftSpinner, String typeHiringSpinner, String salarySpinner, List<User> appliedCandidates) {
        this.vacancyImage = vacancyImage;
        this.vacancyName = vacancyName;
        this.shiftSpinner = shiftSpinner;
        this.vacancyDescription = vacancyDescription;
        this.typeHiringSpinner = typeHiringSpinner;
        this.salarySpinner = salarySpinner;
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

    public List<User> getAppliedCandidates() {
        return appliedCandidates;
    }

    public void setAppliedCandidates(List<User> appliedCandidates) {
        this.appliedCandidates = appliedCandidates;
    }
}
