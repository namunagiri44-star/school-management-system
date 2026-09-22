package school.service;

import school.model.SchoolClass;
import school.util.FileUtil;

import java.util.*;

public class ClassService {
    private static final String FILE = "classes.csv";
    private final List<SchoolClass> classes = new ArrayList<>();
    private int nextId = 1;

    public ClassService() {
        load();
    }

    private void load() {
        classes.clear();
        for (String line : FileUtil.readLines(FILE)) {
            try {
                SchoolClass c = SchoolClass.fromCsv(line);
                classes.add(c);
                if (c.getId() >= nextId) nextId = c.getId() + 1;
            } catch (Exception ignored) { }
        }
    }

    private void save() {
        List<String> lines = new ArrayList<>();
        for (SchoolClass c : classes) lines.add(c.toCsv());
        FileUtil.writeLines(FILE, lines);
    }

    public SchoolClass add(String name, String section, int classTeacherId) {
        SchoolClass c = new SchoolClass(nextId++, name, section, classTeacherId);
        classes.add(c);
        save();
        return c;
    }

    public List<SchoolClass> getAll() {
        return Collections.unmodifiableList(classes);
    }

    public Optional<SchoolClass> findById(int id) {
        return classes.stream().filter(c -> c.getId() == id).findFirst();
    }

    public boolean update(int id, String name, String section, int classTeacherId) {
        Optional<SchoolClass> opt = findById(id);
        if (opt.isEmpty()) return false;
        SchoolClass c = opt.get();
        c.setName(name);
        c.setSection(section);
        c.setClassTeacherId(classTeacherId);
        save();
        return true;
    }

    public boolean delete(int id) {
        boolean removed = classes.removeIf(c -> c.getId() == id);
        if (removed) save();
        return removed;
    }

    public boolean exists(int id) {
        return findById(id).isPresent();
    }
}
