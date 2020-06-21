
import java.sql.Date;
import java.sql.Timestamp;


public class WaitlistEntry {
    
    private String faculty;
    private Date date;
    private int seats;
    private Timestamp timestamp;
    
    public WaitlistEntry(String faculty, Date date, int seats, Timestamp timestamp) {
        this.faculty = faculty;
        this.seats = seats;
        this.timestamp = timestamp;
        this.date = date;
      
    }
    
    public String getFaculty() {
        return this.faculty;
    }
    
    public Date getDate() {
        return this.date;
    }
    
    public int getSeats() {
        return this.seats;
    }
    
    public Timestamp getTimestamp() {
        return this.timestamp;
    }
    
    
    
}
