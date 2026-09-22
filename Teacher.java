package school.model;

import school.util.FileUtil;

public class Teacher {
    private int id;
    private String name;
    private String specialization;
    private String contact;
    private String address;

    public Teacher(int id, String name, String specialization, String contact, String address) {
        this.id = id;
        this.name = name;
        this.specialization = specialization;
        this.contact = contact;
        this.address = address;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getSpecialization() { return specialization; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }
    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String toCsv() {
        return FileUtil.join(String.valueOf(id), name, specialization, contact, address);
    }

    public static Teacher fromCsv(String line) {
        String[] f = FileUtil.split(line);
        return new Teacher(Integer.parseInt(f[0]), f[1], f[2], f[3], f[4]);
    }

    @Override
    public String toString() {
        return String.format("ID:%-4d Name:%-20s Specialization:%-15s Contact:%-15s Address:%s",
                id, name, specialization, contact, address);
    }
}
