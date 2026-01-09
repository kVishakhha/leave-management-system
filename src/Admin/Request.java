package Admin;

public class Request {

    public int id;
    public String studentId;
    public String fromDate;
    public String toDate;
    public String reason;
    public String status;

    public Request(int id, String studentId, String fromDate,
                   String toDate, String reason, String status) {

        this.id = id;
        this.studentId = studentId;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.reason = reason;
        this.status = status;
    }
}
