package ru.agaev.webapp.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class Organization implements Serializable {
    private static final long serialVersionUID = 1L;

    private Link homePage;
    private List<Experience> experiences = new ArrayList<>();

    public Organization() {
    }

    public Organization(String name, String url, LocalDate startDate, LocalDate endDate, String title, String description) {
        this.homePage = new Link(name, url);
        experiences.add(new Experience(startDate, endDate, title, description));
    }

    public Organization(String name, String url, List<Experience> experiences) {
        this.homePage = new Link(name, url);
        this.experiences = experiences;
    }

    public Organization(String name, String url, Experience info) {
        this.homePage = new Link(name, url);
        this.experiences.add(info);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return Objects.equals(homePage, that.homePage) && Objects.equals(experiences, that.experiences);
    }

    @Override
    public int hashCode() {
        return Objects.hash(homePage, experiences);
    }

    @Override
    public String toString() {
        return "Organization{" +
                "homePage=" + homePage +
                "\n, intervalDates=" + experiences +
                '}';
    }
}