package school.service;

import school.model.Teacher;
import school.util.FileUtil;

import java.util.*;

public class TeacherService {
    private static final String FILE = "teachers.csv";
    private final List<Teacher> teachers = new ArrayList<>();
    private int nextId = 1;

    public TeacherService() {
        load();
    }

    private void load() {
        teachers.clear();
        for (String line : FileUtil.readLines(FILE)) {
            try {
                Teacher t = Teacher.fromCsv(line);
                teachers.add(t);
                if (t.getId() >= nextId) nextId = t.getId() + 1;
            } catch (Exception ignored) { }
        }
    }

    private void save() {
        List<String> lines = new ArrayList<>();
        for (Teacher t : teachers) lines.add(t.toCsv());
        FileUtil.writeLines(FILE, lines);
    }

    public Teacher add(String name, String specialization, String contact, String address) {
        Teacher t = new Teacher(nextId++, name, specialization, contact, address);
        teachers.add(t);
        save();
        return t;
    }

    public List<Teacher> getAll() {
        return Collections.unmodifiableList(teachers);
    }

    public Optional<Teacher> findById(int id) {
        return teachers.stream().filter(t -> t.getId() == id).findFirst();
    }

    public List<Teacher> searchByName(String keyword) {
        List<Teacher> result = new ArrayList<>();
        String k = keyword.toLowerCase();
        for (Teacher t : teachers) {
            if (t.getName().toLowerCase().contains(k)) result.add(t);
        }
        return result;
    }

    public boolean update(int id, String name, String specialization, String contact, String address) {
        Optional<Teacher> opt = findById(id);
        if (opt.isEmpty()) return false;
        Teacher t = opt.get();
        t.setName(name);
        t.setSpecialization(specialization);
        t.setContact(contact);
        t.setAddress(address);
        save();
        return true;
    }

    public boolean delete(int id) {
        boolean removed = teachers.removeIf(t -> t.getId() == id);
        if (removed) save();
        return removed;
    }

    public boolean exists(int id) {
        return findById(id).isPresent();
    }
}
