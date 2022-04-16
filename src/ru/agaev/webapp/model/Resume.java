package ru.agaev.webapp.model;

import java.util.*;

/**
 * Initial resume class
 */
public class Resume implements Comparable<Resume> {
    // Unique identifier
    private final String uuid;
    private String fullName;
    private Map<ContactType, String> contacts = new HashMap<>();
    private Map<SectionType, AbstractSection> sections = new HashMap<>();
    private Map<SectionType, List<ExperienceSection>> experience = new HashMap<>();


    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public void addContacts(ContactType contactType, String info) {
        contacts.put(contactType, info);
    }

    public void getContacts(ContactType contactType) {
        contacts.get(contactType);
    }

    public void addSections(SectionType sectionType, AbstractSection section) {
        sections.put(sectionType, section);
    }

    public void getSections(SectionType contactType) {
        sections.get(contactType);
    }

    public void addExperience(SectionType sectionType, List<ExperienceSection> list) {
        experience.put(sectionType, list);
    }

    public void getExperience(SectionType sectionType) {
        experience.get(sectionType);
    }

    public String getUuid() {
        return uuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return Objects.equals(uuid, resume.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }

    @Override
    public String toString() {
        return "Resume{" +
                "uuid='" + uuid + '\'' +
                ", fullName='" + fullName + '\'' +
                '}' + contacts + " " + sections;
    }

    @Override
    public int compareTo(Resume o) {
        if (uuid.length() > o.getUuid().length()) {
            return 1;
        } else if (uuid.length() < o.getUuid().length()) {
            return -1;
        }
        return uuid.compareTo(o.uuid);
    }


}
