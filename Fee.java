package school.model;

import school.util.FileUtil;

public class Fee {
    private int id;
    private int studentId;
    private String term;       // e.g. "2026 Term 1"
    private double amount;
    private String dueDate;    // yyyy-MM-dd
    private String status;     // Paid / Unpaid
    private String paidDate;   // yyyy-MM-dd or "" if unpaid

    public Fee(int id, int studentId, String term, double amount, String dueDate, String status, String paidDate) {
        this.id = id;
        this.studentId = studentId;
        this.term = term;
        this.amount = amount;
        this.dueDate = dueDate;
        this.status = status;
        this.paidDate = paidDate;
    }

    public int getId() { return id; }
    public int getStudentId() { return studentId; }
    public String getTerm() { return term; }
    public double getAmount() { return amount; }
    public String getDueDate() { return dueDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getPaidDate() { return paidDate; }
    public void setPaidDate(String paidDate) { this.paidDate = paidDate; }

    public String toCsv() {
        return FileUtil.join(String.valueOf(id), String.valueOf(studentId), term, String.valueOf(amount),
                dueDate, status, paidDate);
    }

    public static Fee fromCsv(String line) {
        String[] f = FileUtil.split(line);
        return new Fee(Integer.parseInt(f[0]), Integer.parseInt(f[1]), f[2], Double.parseDouble(f[3]),
                f[4], f[5], f[6]);
    }

    @Override
    public String toString() {
        String paid = paidDate == null || paidDate.isEmpty() ? "-" : paidDate;
        return String.format("ID:%-4d StudentID:%-6d Term:%-14s Amount:%-10.2f Due:%-12s Status:%-8s Paid:%s",
                id, studentId, term, amount, dueDate, status, paid);
    }
}
