package ru.agaev.webapp.storage.serializer;

import ru.agaev.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements StreamSerializer {
    @Override
    public void doWrite(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            Map<ContactType, String> contancts = resume.getContacts();
            dos.writeInt(contancts.size());
            for (Map.Entry<ContactType, String> entry : resume.getContacts().entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }
            Map<SectionType, Section> sections = resume.getSections();
            dos.writeInt(sections.size());
            for (Map.Entry<SectionType, Section> entry : resume.getSections().entrySet()) {
                Class<? extends Section> clazz = entry.getValue().getClass();
                if (clazz.equals(TextSection.class)) {
                    dos.writeUTF(entry.getKey().name());
                    dos.writeUTF(entry.getValue().toString());
                }
                if (clazz.equals(ListSection.class)) {
                    dos.writeUTF(entry.getKey().name());
                    List<String> list = ((ListSection) entry.getValue()).getList();
                    dos.writeInt(list.size());
                    for (String s : list) {
                        dos.writeUTF(s);
                    }
                }
                if (clazz.equals(OrganizationSection.class)) {
                    dos.writeUTF(entry.getKey().name());
                    List<Organization> organizations = ((OrganizationSection) entry.getValue()).getExperience();
                    dos.writeInt(organizations.size());
                    organizations.forEach(organization -> serializeOrganizations(organization, dos));
                }
            }
        }
    }

    private void serializeOrganizations(Organization organization, DataOutputStream dos) {
        Link link = organization.getHomePage();
        try {
            dos.writeUTF(link.getName());
            dos.writeUTF(link.getUrl());
            serializeExperience(organization.getExperiences(), dos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void serializeExperience(List<Experience> experiences, DataOutputStream dos) throws IOException {
        dos.writeInt(experiences.size());
        for (Experience experience : experiences) {
            dos.writeUTF(String.valueOf(experience.getStartDate()));
            dos.writeUTF(String.valueOf(experience.getEndDate()));
            dos.writeUTF(experience.getTitle());
            dos.writeUTF(experience.getDescription());
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            Resume resume = new Resume(dis.readUTF(), dis.readUTF());
            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }
            size = dis.readInt();
            for (int i = 0; i < size; i++) {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                switch (sectionType) {
                    case OBJECTIVE:
                    case PERSONAL:
                        resume.addSection(sectionType, new TextSection(dis.readUTF()));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        resume.addSection(sectionType, new ListSection(deserializeList(dis)));
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        resume.addSection(sectionType, new OrganizationSection(deserializeOrganization(dis)));
                        break;
                }

            }
            return resume;

        }
    }

    private List<Organization> deserializeOrganization(DataInputStream dis) throws IOException {
        List<Organization> organizations = new ArrayList<>();
        int counterOrganizations = dis.readInt();
        for (int i = 0; i < counterOrganizations; i++) {
            Organization organization = new Organization();
            organization.setHomePage(new Link(dis.readUTF(), dis.readUTF()));
            organization.setExperiences(deserializeExperience(dis));
            organizations.add(organization);
        }

        return organizations;
    }

    private List<String> deserializeList(DataInputStream dis) throws IOException {
        int counterSection = dis.readInt();
        List<String> list = new ArrayList<>();
        for (int i = 0; i < counterSection; i++) {
            list.add(dis.readUTF());
        }
        return list;
    }

    private List<Experience> deserializeExperience(DataInputStream dis) throws IOException {
        List<Experience> list = new ArrayList<>();
        int countExperience = dis.readInt();
        for (int i = 0; i < countExperience; i++) {
            list.add(new Experience(LocalDate.parse(dis.readUTF()), LocalDate.parse(dis.readUTF()), dis.readUTF(), dis.readUTF()));
        }
        return list;
    }
}
