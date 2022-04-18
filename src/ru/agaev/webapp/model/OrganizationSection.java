package ru.agaev.webapp.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OrganizationSection extends AbstractSection {
    private List<Organization> experience = new ArrayList<>();

    public OrganizationSection(List<Organization> experience) {
        this.experience = experience;
    }
    public OrganizationSection(Organization experience) {
        this.experience.add(experience);
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
