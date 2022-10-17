package com.example.citra_moblie.model;

import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;

public class Vacancy implements Serializable {

    private String vacancyImage;
    private String vacancyName;
    private String vacancyDescription;

    public Vacancy() {
    }

    public Vacancy(String vacancyImage, String vacancyName, String vacancyDescription) {
        this.vacancyImage = vacancyImage;
        this.vacancyName = vacancyName;
        this.vacancyDescription = vacancyDescription;
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

    public String getVacancyDescription() {
        return vacancyDescription;
    }

    public void setVacancyDescription(String vacancyDescription) {
        this.vacancyDescription = vacancyDescription;
    }
}
