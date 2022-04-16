package ru.agaev.webapp.model;

import java.time.LocalDate;
import java.util.Objects;

public class ExperienceSection extends AbstractSection {
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final String company;
    private final String position;
    private final String description;

    public ExperienceSection(LocalDate startDate, LocalDate endDate, String company, String position, String description) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.company = company;
        this.position = position;
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExperienceSection that = (ExperienceSection) o;
        return Objects.equals(startDate, that.startDate) && Objects.equals(endDate, that.endDate) && Objects.equals(company, that.company) && Objects.equals(position, that.position) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startDate, endDate, company, position, description);
    }

    @Override
    public String toString() {
        return "ExperienceSection{" +
                "startDate=" + startDate +
                ", endDate=" + endDate +
                ", company='" + company + '\'' +
                ", position='" + position + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
