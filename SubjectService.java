package school.service;

import school.model.Subject;
import school.util.FileUtil;

import java.util.*;

public class SubjectService {
    private static final String FILE = "subjects.csv";
    private final List<Subject> subjects = new ArrayList<>();
    private int nextId = 1;

    public SubjectService() {
        load();
    }

    private void load() {
        subjects.clear();
        for (String line : FileUtil.readLines(FILE)) {
            try {
                Subject s = Subject.fromCsv(line);
                subjects.add(s);
                if (s.getId() >= nextId) nextId = s.getId() + 1;
            } catch (Exception ignored) { }
        }
    }

    private void save() {
        List<String> lines = new ArrayList<>();
        for (Subject s : subjects) lines.add(s.toCsv());
        FileUtil.writeLines(FILE, lines);
    }

    public Subject add(String name, String code, String classId, int teacherId) {
        Subject s = new Subject(nextId++, name, code, classId, teacherId);
        subjects.add(s);
        save();
        return s;
    }

    public List<Subject> getAll() {
        return Collections.unmodifiableList(subjects);
    }

    public Optional<Subject> findById(int id) {
        return subjects.stream().filter(s -> s.getId() == id).findFirst();
    }

    public List<Subject> findByClassId(String classId) {
        List<Subject> result = new ArrayList<>();
        for (Subject s : subjects) {
            if (s.getClassId().equalsIgnoreCase(classId)) result.add(s);
        }
        return result;
    }

    public boolean update(int id, String name, String code, String classId, int teacherId) {
        Optional<Subject> opt = findById(id);
        if (opt.isEmpty()) return false;
        Subject s = opt.get();
        s.setName(name);
        s.setCode(code);
        s.setClassId(classId);
        s.setTeacherId(teacherId);
        save();
        return true;
    }

    public boolean delete(int id) {
        boolean removed = subjects.removeIf(s -> s.getId() == id);
        if (removed) save();
        return removed;
    }

    public boolean exists(int id) {
        return findById(id).isPresent();
    }
}
