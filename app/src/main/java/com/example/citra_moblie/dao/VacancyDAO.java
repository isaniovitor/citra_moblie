package com.example.citra_moblie.dao;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;


import com.example.citra_moblie.R;
import com.example.citra_moblie.model.User;
import com.example.citra_moblie.model.Vacancy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VacancyDAO implements IVacancyDAO {
    private static Context context;
    private static VacancyDAO vacancyDAO = null;
    private List<User> appliedUsers = new ArrayList<>();
    private List<Vacancy> homeVacancies = new ArrayList<>();
    private List<Vacancy> userAppliedVacancies = new ArrayList<>();
    private List<Vacancy> userCreatedVacancies = new ArrayList<>();

    private VacancyDAO(Context context) {
        VacancyDAO.context = context;

        User user = new User(null, "alice Maria", "aliceMaria@com",
                "12/02/2002", "02193243234", "alice");
        appliedUsers.add(user);

        user = new User(null, "Davilo", "daviloAlo@com",
                "12/02/2002", "02193243234", "alice");
        appliedUsers.add(user);

        user = new User(null, "Ewados", "ewadosJunios@com",
                "12/02/2002", "02193243234", "alice");
        appliedUsers.add(user);

        user = new User(null, "Isanus", "isanusVitors@com",
                "12/02/2002", "02193243234", "alice");
        appliedUsers.add(user);

        user = new User(null, "Falcs alcs", "falconato@com",
                "12/02/2002", "02193243234", "alice");
        appliedUsers.add(user);

        createHomeVacanciesMock();
        userAppliedVacancies();
        userCreatedVacancies();
    }

    public static IVacancyDAO getInstance(Context context) {
        if (vacancyDAO == null) {
            vacancyDAO = new VacancyDAO(context);
        }
        return vacancyDAO;
    }

    @Override
    public void createHomeVacanciesMock() {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.image1);
        Vacancy vacancy = new Vacancy(bitmap, "Técnico em Segurança do Trabalho - Grupo MAST",
                "Realizar visitas ao site do cliente, preenchendo o check list com os dados do " +
                        "cliente e discriminando a atividade. Elaborar e/ou atualizar o PCMSO de acordo com o PGR " +
                        "do cliente e seguindo as diretrizes e procedimentos da área de Segurança do Trabalho e " +
                        "realizando o lançamento de todos os documentos no sistema interno da empresa. Realizar o " +
                        "dimensionamento do SESMT e CIPA do cliente, incluindo a realização do processo completo da" +
                        " CIPA. Manter contato com o cliente, dirimindo dúvidas e buscando ações de melhoria às suas " +
                        "necessidades.",
                "manhã", "CTI", "2000",
                Arrays.asList(appliedUsers.get(0), appliedUsers.get(2), appliedUsers.get(1), appliedUsers.get(3)));
        homeVacancies.add(vacancy);

        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.image2);
        vacancy = new Vacancy(bitmap, "Engenheiro Civil - Grupo FNM Engenharia",
                "As atividades contemplam a gestão de equipes, planejamento das atividades, execução de " +
                        "obras de reformas, construção, fiscalização e acompanhamento de obras e cronogramas e execução de " +
                        "relatórios. Formação exigida: Superior em Engenharia Civil e/ou Arquitetura e Urbanismo. Conhecimentos " +
                        "imprescindíveis: Pacote Office e AutoCad - Intermediário",
                "tarde", "CTI", "1000",
                Arrays.asList(appliedUsers.get(4), appliedUsers.get(2), appliedUsers.get(1)));
        homeVacancies.add(vacancy);

        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.image3);
        vacancy = new Vacancy(bitmap, "Mestre de obras - FXKAP Infraestrutura", "Elaborar planos e cronogramas; -" +
                "Analisar a viabilidade de projetos e tarefas; - Orientar a equipe de trabalho; - Controlar o uso de materiais e a " +
                "disponibilidade de máquinas, equipamentos e pessoal; - Assessorar as atividades de todas as partes envolvidas em uma obra; - " +
                "Gerenciar resíduos e detritos, assim como seu descarte ou reciclagem; - Delegar tarefas; - Inspecionar a execução de tarefas;",
                "tarde", "CTD", "1200",
                Arrays.asList(appliedUsers.get(3), appliedUsers.get(2), appliedUsers.get(0)));
        homeVacancies.add(vacancy);

        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.image4);
        vacancy = new Vacancy(bitmap, "Técnico de Edificações - CAMBERT Engenharia",
                "Realizam levantamentos topográficos e planialtimétricos. Desenvolvem e legalizam projetos de edificações sob " +
                        "supervisão de um engenheiro civil; planejam a execução, orçam e providenciam suprimentos e supervisionam a execução de " +
                        "obras e serviços.",
                "tarde", "Estágio", "3000", Arrays.asList(appliedUsers.get(1), appliedUsers.get(3)));
        homeVacancies.add(vacancy);

        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.image5);
        vacancy = new Vacancy(bitmap, "Engenheiro Civil - FNM ",
                "Realizam levantamentos topográficos e planialtimétricos. Desenvolvem e legalizam projetos de edificações sob " +
                        "supervisão de um engenheiro civil; planejam a execução, orçam e providenciam suprimentos e supervisionam a execução de " +
                        "obras e serviços.",
                "noite", "Temporário", "2400", Arrays.asList(appliedUsers.get(2), appliedUsers.get(3)));
        homeVacancies.add(vacancy);
    }

    @Override
    public void userAppliedVacancies() {
        userAppliedVacancies.add(homeVacancies.get(0));
        userAppliedVacancies.add(homeVacancies.get(1));
        userAppliedVacancies.add(homeVacancies.get(2));
    }

    @Override
    public void userCreatedVacancies() {
        userCreatedVacancies.add(homeVacancies.get(3));
        userCreatedVacancies.add(homeVacancies.get(4));
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
