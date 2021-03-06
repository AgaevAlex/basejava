package ru.agaev.webapp.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class OrganizationSection extends Section implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<Organization> experience = new ArrayList<>();

    public OrganizationSection() {
    }

    public OrganizationSection(List<Organization> experience) {
        this.experience = experience;
    }

    public OrganizationSection(Organization experience) {
        this.experience.add(experience);
    }

    public List<Organization> getExperience() {
        return experience;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrganizationSection that = (OrganizationSection) o;
        return Objects.equals(experience, that.experience);
    }

    @Override
    public int hashCode() {
        return Objects.hash(experience);
    }

    @Override
    public String toString() {
        return "Experience{" +
                "experience=" + experience +
                '}';
    }
}
