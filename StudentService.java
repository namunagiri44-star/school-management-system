package school.service;

import school.model.Student;
import school.util.FileUtil;

import java.util.*;

public class StudentService {
    private static final String FILE = "students.csv";
    private final List<Student> students = new ArrayList<>();
    private int nextId = 1;

    public StudentService() {
        load();
    }

    private void load() {
        students.clear();
        for (String line : FileUtil.readLines(FILE)) {
            try {
                Student s = Student.fromCsv(line);
                students.add(s);
                if (s.getId() >= nextId) nextId = s.getId() + 1;
            } catch (Exception ignored) { }
        }
    }

    private void save() {
        List<String> lines = new ArrayList<>();
        for (Student s : students) lines.add(s.toCsv());
        FileUtil.writeLines(FILE, lines);
    }

    public Student add(String name, int age, String gender, String classId, String contact, String address) {
        Student s = new Student(nextId++, name, age, gender, classId, contact, address);
        students.add(s);
        save();
        return s;
    }

    public List<Student> getAll() {
        return Collections.unmodifiableList(students);
    }

    public Optional<Student> findById(int id) {
        return students.stream().filter(s -> s.getId() == id).findFirst();
    }

    public List<Student> findByClassId(String classId) {
        List<Student> result = new ArrayList<>();
        for (Student s : students) {
            if (s.getClassId().equalsIgnoreCase(classId)) result.add(s);
        }
        return result;
    }

    public List<Student> searchByName(String keyword) {
        List<Student> result = new ArrayList<>();
        String k = keyword.toLowerCase();
        for (Student s : students) {
            if (s.getName().toLowerCase().contains(k)) result.add(s);
        }
        return result;
    }

    public boolean update(int id, String name, int age, String gender, String classId, String contact, String address) {
        Optional<Student> opt = findById(id);
        if (opt.isEmpty()) return false;
        Student s = opt.get();
        s.setName(name);
        s.setAge(age);
        s.setGender(gender);
        s.setClassId(classId);
        s.setContact(contact);
        s.setAddress(address);
        save();
        return true;
    }

    public boolean delete(int id) {
        boolean removed = students.removeIf(s -> s.getId() == id);
        if (removed) save();
        return removed;
    }

    public boolean exists(int id) {
        return findById(id).isPresent();
    }
}
