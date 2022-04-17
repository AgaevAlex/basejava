package ru.agaev.webapp.model;

import java.util.List;
import java.util.Objects;

public class Experience extends AbstractSection {
    private final List<ExperienceSection> experience;

    public Experience(List<ExperienceSection> experience) {
        this.experience = experience;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Experience that = (Experience) o;
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
