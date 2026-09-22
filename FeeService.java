package school.service;

import school.model.Fee;
import school.util.FileUtil;

import java.util.*;

public class FeeService {
    private static final String FILE = "fees.csv";
    private final List<Fee> records = new ArrayList<>();
    private int nextId = 1;

    public FeeService() {
        load();
    }

    private void load() {
        records.clear();
        for (String line : FileUtil.readLines(FILE)) {
            try {
                Fee f = Fee.fromCsv(line);
                records.add(f);
                if (f.getId() >= nextId) nextId = f.getId() + 1;
            } catch (Exception ignored) { }
        }
    }

    private void save() {
        List<String> lines = new ArrayList<>();
        for (Fee f : records) lines.add(f.toCsv());
        FileUtil.writeLines(FILE, lines);
    }

    public Fee add(int studentId, String term, double amount, String dueDate) {
        Fee f = new Fee(nextId++, studentId, term, amount, dueDate, "Unpaid", "");
        records.add(f);
        save();
        return f;
    }

    public List<Fee> getAll() {
        return Collections.unmodifiableList(records);
    }

    public List<Fee> findByStudent(int studentId) {
        List<Fee> result = new ArrayList<>();
        for (Fee f : records) {
            if (f.getStudentId() == studentId) result.add(f);
        }
        return result;
    }

    public List<Fee> findUnpaid() {
        List<Fee> result = new ArrayList<>();
        for (Fee f : records) {
            if (f.getStatus().equalsIgnoreCase("Unpaid")) result.add(f);
        }
        return result;
    }

    public Optional<Fee> findById(int id) {
        return records.stream().filter(f -> f.getId() == id).findFirst();
    }

    public boolean markPaid(int id, String paidDate) {
        Optional<Fee> opt = findById(id);
        if (opt.isEmpty()) return false;
        Fee f = opt.get();
        f.setStatus("Paid");
        f.setPaidDate(paidDate);
        save();
        return true;
    }

    public boolean delete(int id) {
        boolean removed = records.removeIf(f -> f.getId() == id);
        if (removed) save();
        return removed;
    }

    public double totalDueForStudent(int studentId) {
        double total = 0;
        for (Fee f : findByStudent(studentId)) {
            if (f.getStatus().equalsIgnoreCase("Unpaid")) total += f.getAmount();
        }
        return total;
    }
}
