package com.example.citra_moblie;

import com.example.citra_moblie.model.Vacancy;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

public class VacancyValidatorTest {

    private Vacancy vacancy = new Vacancy();

    @Test
    public void testVacancyNull(){
        assertNotNull(vacancy);
    }

    @Test
    public void testSetIdVacancy(){
        String id = "-NIbme5pjRAxxn8pdoRP";
        vacancy.setIdVacancy(id);
        assertEquals("Verificando Id da Vaga", id, vacancy.getIdVacancy());
    }

    @Test
    public void testIdVacancyNull() {
        assertNull(vacancy.getIdVacancy());
    }

    @Test
    public void testIdUser(){
        String id = "-NIbme5pjRAxxn8pdoRP";
        vacancy.setIdVacancy(id);
        assertEquals("Verificando Id da Vaga", id, vacancy.getIdVacancy());
    }

    @Test
    public void testIdUserNull() {
        assertNull(vacancy.getIdUser());
    }

    @Test
    public void testSetVacancyName(){
        String name = "Pedreiro";
        vacancy.setVacancyName(name);
        assertEquals("Verificando Nome da Vaga", name, vacancy.getVacancyName());
    }

    @Test
    public void testNameVacancyNull() {
        assertNull(vacancy.getVacancyName());
    }

    @Test
    public void testSetVacancyDescription(){
        String description = "Trabalho brasal e muito esforço";
        vacancy.setVacancyDescription(description);
        assertEquals("Verificando Descrição da Vaga", description, vacancy.getVacancyDescription());
    }

    @Test
    public void testDescriptionVacancyNull() {
        assertNull(vacancy.getVacancyDescription());
    }

    @Test
    public void testSetShiftSpinner(){
        String shift = "tarde";
        vacancy.setShiftSpinner(shift);
        assertEquals("Verificando Turno da Vaga", shift, vacancy.getShiftSpinner());
    }

    @Test
    public void testShiftNull() {
        assertNull(vacancy.getShiftSpinner());
    }

    @Test
    public void testSetTypeHiringSpinner(){
        String hiring = "Temporário";
        vacancy.setTypeHiringSpinner(hiring);
        assertEquals("Verificando tipo de Contratação da Vaga", hiring, vacancy.getTypeHiringSpinner());
    }

    @Test
    public void testTypeHiringNull() {
        assertNull(vacancy.getTypeHiringSpinner());
    }

    @Test
    public void testSetSalarySpinner(){
        String salary = "1200";
        vacancy.setSalarySpinner(salary);
        assertEquals("Verificando Salario da Vaga", salary, vacancy.getSalarySpinner());
    }

    @Test
    public void testSalaryNull() {
        assertNull(vacancy.getSalarySpinner());
    }

    @Test
    public void testSetVacancyLat(){
        String lat = "-4.9691662";
        vacancy.setVacancyLat(lat);
        assertEquals("Verificando Latitude da Vaga", lat, vacancy.getVacancyLat());
    }

    @Test
    public void testSetVacancyLog(){
        String log = "-39.0185386";
        vacancy.setVacancyLog(log);
        assertEquals("Verificando Longitude da Vaga", log, vacancy.getVacancyLog());
    }

}
