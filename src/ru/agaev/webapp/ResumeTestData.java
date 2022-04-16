package ru.agaev.webapp;

import ru.agaev.webapp.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ResumeTestData {
    public static void main(String[] args) {
        List<String> achievement = Arrays.asList("Организация команды и успешная реализация Java проектов для сторонних заказчиков: приложения автопарк на стеке Spring Cloud/микросервисы, система мониторинга показателей спортсменов на Spring Boot, участие в проекте МЭШ на Play-2, многомодульный Spring Boot + Vaadin проект для комплексных DIY смет", "С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 3500 выпускников.", "Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        List<String> qualifications = Arrays.asList("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2", "Version control: Subversion, Git, Mercury, ClearCase, Perforce", "DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle, MySQL, SQLite, MS SQL, HSQLDB");
        Resume resume = new Resume("Григорий Кислин");
        ExperienceSection experienceSection1 = new ExperienceSection(LocalDate.parse("2013-10-01"), LocalDate.now(), "Java Online Projects", "Автор проекта.", "Создание, организация и проведение Java онлайн проектов и стажировок.");
        ExperienceSection experienceSection2 = new ExperienceSection(LocalDate.parse("2014-10-01"), LocalDate.parse("2016-01-01"), "Wrike", "Старший разработчик (backend)", "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.");

        List<ExperienceSection> experience = new ArrayList<>();
        experience.add(experienceSection1);
        experience.add(experienceSection2);

        resume.addContacts(ContactType.PHONE, "+7(921) 855-0482");
        resume.addContacts(ContactType.SKYPE, "skype:grigory.kislin");
        resume.addContacts(ContactType.EMAIL, "gkislin@yandex.ru");
        resume.addContacts(ContactType.LINKEDIN, "https://www.linkedin.com/in/gkislin");
        resume.addContacts(ContactType.GITHUB, "https://github.com/gkislin");
        resume.addContacts(ContactType.STACKOVERFLOW, "https://stackoverflow.com/users/548473/grigory-kislin");
        resume.addContacts(ContactType.HOMEPAGE, "http://gkislin.ru/");


        resume.addSections(SectionType.OBJECTIVE, new TextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям"));
        resume.addSections(SectionType.PERSONAL, new TextSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры."));
        resume.addSections(SectionType.ACHIEVEMENT, new ListSection(achievement));
        resume.addSections(SectionType.QUALIFICATIONS, new ListSection(qualifications));

        resume.addExperience(SectionType.EXPERIENCE, experience);


        System.out.println(resume);


    }
}