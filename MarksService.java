package school.service;

import school.model.Marks;
import school.util.FileUtil;

import java.util.*;

public class MarksService {
    private static final String FILE = "marks.csv";
    private final List<Marks> records = new ArrayList<>();
    private int nextId = 1;

    public MarksService() {
        load();
    }

    private void load() {
        records.clear();
        for (String line : FileUtil.readLines(FILE)) {
            try {
                Marks m = Marks.fromCsv(line);
                records.add(m);
                if (m.getId() >= nextId) nextId = m.getId() + 1;
            } catch (Exception ignored) { }
        }
    }

    private void save() {
        List<String> lines = new ArrayList<>();
        for (Marks m : records) lines.add(m.toCsv());
        FileUtil.writeLines(FILE, lines);
    }

    public Marks add(int studentId, int subjectId, String examType, double marksObtained, double maxMarks) {
        Marks m = new Marks(nextId++, studentId, subjectId, examType, marksObtained, maxMarks);
        records.add(m);
        save();
        return m;
    }

    public List<Marks> getAll() {
        return Collections.unmodifiableList(records);
    }

    public List<Marks> findByStudent(int studentId) {
        List<Marks> result = new ArrayList<>();
        for (Marks m : records) {
            if (m.getStudentId() == studentId) result.add(m);
        }
        return result;
    }

    public Optional<Marks> findById(int id) {
        return records.stream().filter(m -> m.getId() == id).findFirst();
    }

    public boolean update(int id, double marksObtained, double maxMarks) {
        Optional<Marks> opt = findById(id);
        if (opt.isEmpty()) return false;
        Marks m = opt.get();
        m.setMarksObtained(marksObtained);
        m.setMaxMarks(maxMarks);
        save();
        return true;
    }

    public boolean delete(int id) {
        boolean removed = records.removeIf(m -> m.getId() == id);
        if (removed) save();
        return removed;
    }

    /** Average percentage across all of a student's recorded marks. */
    public double averagePercentage(int studentId) {
        List<Marks> list = findByStudent(studentId);
        if (list.isEmpty()) return 0;
        double sum = 0;
        for (Marks m : list) sum += m.getPercentage();
        return sum / list.size();
    }
}
