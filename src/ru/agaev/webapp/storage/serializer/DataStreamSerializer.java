package ru.agaev.webapp.storage.serializer;

import ru.agaev.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements StreamSerializer {

    @Override
    public void doWrite(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            Map<ContactType, String> contacts = resume.getContacts();
            writeWithExeption(contacts.entrySet(), dos, entry -> {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            });

            Map<SectionType, Section> sections = resume.getSections();
            writeWithExeption(sections.entrySet(), dos, entry -> {
                SectionType sectionType = entry.getKey();
                dos.writeUTF(entry.getKey().name());
                switch (sectionType) {
                    case OBJECTIVE:
                    case PERSONAL:
                        dos.writeUTF(((TextSection) entry.getValue()).getText());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        List<String> list = ((ListSection) entry.getValue()).getList();
                        writeWithExeption(list, dos, dos::writeUTF);
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        writeWithExeption(((OrganizationSection) entry.getValue()).getExperience(), dos, entry1 -> {
                            Link link = entry1.getHomePage();
                            dos.writeUTF(link.getName());
                            dos.writeUTF(link.getUrl());
                            writeWithExeption(entry1.getExperiences(), dos, entry2 -> {
                                dos.writeUTF(String.valueOf(entry2.getStartDate()));
                                dos.writeUTF(String.valueOf(entry2.getEndDate()));
                                dos.writeUTF(entry2.getTitle());
                                dos.writeUTF(entry2.getDescription());
                            });
                        });
                        break;
                }
            });
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            Resume resume = new Resume(dis.readUTF(), dis.readUTF());
            readWithExeption(dis, () -> resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF()));
            readWithExeption(dis, () -> {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                switch (sectionType) {
                    case OBJECTIVE:
                    case PERSONAL:
                        resume.addSection(sectionType, new TextSection(dis.readUTF()));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        resume.addSection(sectionType, new ListSection(collectionWithExeption(dis, dis::readUTF)));
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        resume.addSection(sectionType, new OrganizationSection(collectionWithExeption(dis, () -> {
                            Organization organization = new Organization();
                            organization.setHomePage(new Link(dis.readUTF(), dis.readUTF()));
                            organization.setExperiences(collectionWithExeption(dis, () -> new Experience(LocalDate.parse(dis.readUTF()), LocalDate.parse(dis.readUTF()), dis.readUTF(), dis.readUTF())));
                            return organization;
                        })));
                }

            });
            return resume;
        }
    }

//    private List<Organization> deserializeOrganization(DataInputStream dis) throws IOException {
//        List<Organization> organizations = new ArrayList<>();
//        int counterOrganizations = dis.readInt();
//        for (int i = 0; i < counterOrganizations; i++) {
//            Organization organization = new Organization();
//            organization.setHomePage(new Link(dis.readUTF(), dis.readUTF()));
//            organization.setExperiences(deserializeExperience(dis));
//            organizations.add(organization);
//        }
//        return organizations;
//    }
//
//    private List<String> deserializeList(DataInputStream dis) throws IOException {
//        int counterSection = dis.readInt();
//        List<String> list = new ArrayList<>();
//        for (int i = 0; i < counterSection; i++) {
//            list.add(dis.readUTF());
//        }
//        return list;
//    }

//    private List<Experience> deserializeExperience(DataInputStream dis) throws IOException {
//        List<Experience> list = new ArrayList<>();
//        int countExperience = dis.readInt();
//        for (int i = 0; i < countExperience; i++) {
//            list.add(new Experience(LocalDate.parse(dis.readUTF()), LocalDate.parse(dis.readUTF()), dis.readUTF(), dis.readUTF()));
//        }
//        return list;
//    }

    private <T> void writeWithExeption(Collection<T> collection, DataOutputStream dos, WriteCollection<T> writeCollection) throws IOException {
        dos.writeInt(collection.size());
        for (T t : collection) {
            writeCollection.write(t);
        }
    }

    private <T> void readWithExeption(DataInputStream dis, ReadCollection<T> readCollection) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            readCollection.read();
        }
    }

    private <T> List<T> collectionWithExeption(DataInputStream dis, ReturnCollection<T> returnCollection) throws IOException {
        List<T> list = new ArrayList<>();
        int counter = dis.readInt();
        for (int i = 0; i < counter; i++) {
            list.add(returnCollection.read());
        }
        return list;
    }

    interface WriteCollection<T> {
        void write(T t) throws IOException;
    }

    interface ReadCollection<T> {
        void read() throws IOException;
    }

    interface ReturnCollection<T> {
        T read() throws IOException;
    }
}