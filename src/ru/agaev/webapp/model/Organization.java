package ru.agaev.webapp.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * gkislin
 * 19.07.2016
 */
public class Organization {
    private final Link homePage;
    private List<OrganizationInfo> organizationInfos = new ArrayList<>();

    public Organization(String name, String url, LocalDate startDate, LocalDate endDate, String title, String description) {
        this.homePage = new Link(name, url);
        organizationInfos.add(new OrganizationInfo(startDate, endDate, title, description));
    }

    public Organization(String name, String url, List<OrganizationInfo> organizationInfos) {
        this.homePage = new Link(name, url);
        this.organizationInfos = organizationInfos;
    }

    public Organization(String name, String url, OrganizationInfo info) {
        this.homePage = new Link(name, url);
        this.organizationInfos.add(info);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return Objects.equals(homePage, that.homePage) && Objects.equals(organizationInfos, that.organizationInfos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(homePage, organizationInfos);
    }

    @Override
    public String toString() {
        return "Organization{" +
                "homePage=" + homePage +
                "\n, intervalDates=" + organizationInfos +
                '}';
    }
}