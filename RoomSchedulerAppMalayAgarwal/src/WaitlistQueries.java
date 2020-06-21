
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;


public class WaitlistQueries {
    
    private static PreparedStatement addWaitlist;
    private static ResultSet resultSet;
    private static Connection connection;
    private static PreparedStatement getWaitlistList;
    
    public static ArrayList<WaitlistEntry> getAllWaitlist() {
        connection = DBConnection.getConnection();
        ArrayList<WaitlistEntry> waitlist = new ArrayList<>();
        try
        {
            getWaitlistList = connection.prepareStatement("select faculty, date, seats, timestamp from waitlist order by date, timestamp");
           
            resultSet = getWaitlistList.executeQuery();
            
            while(resultSet.next())
            {
                WaitlistEntry entry = new WaitlistEntry(resultSet.getString(1), resultSet.getDate(2), resultSet.getInt(3), resultSet.getTimestamp(4));
                waitlist.add(entry);
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return waitlist;
    }
    
    public static ArrayList<WaitlistEntry> getWaitlistByDate(Date date) {
        connection = DBConnection.getConnection();
        ArrayList<WaitlistEntry> waitlist = new ArrayList<>();
        try
        {
            getWaitlistList = connection.prepareStatement("select faculty, seats, timestamp from waitlist where date = ?");
            getWaitlistList.setDate(1, date);
            resultSet = getWaitlistList.executeQuery();
            
            while(resultSet.next())
            {
                WaitlistEntry entry = new WaitlistEntry(resultSet.getString(1), date, resultSet.getInt(2), resultSet.getTimestamp(3));
                waitlist.add(entry);
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return waitlist;
    }
    
    public static ArrayList<WaitlistEntry> getWaitlistByFaculty(String faculty) {
        connection = DBConnection.getConnection();
        ArrayList<WaitlistEntry> waitlist = new ArrayList<>();
        try
        {
            getWaitlistList = connection.prepareStatement("select date, seats, timestamp from waitlist where faculty = ?");
            getWaitlistList.setString(1, faculty);
            resultSet = getWaitlistList.executeQuery();
            
            while(resultSet.next())
            {
                WaitlistEntry entry = new WaitlistEntry(faculty, resultSet.getDate(1), resultSet.getInt(2), resultSet.getTimestamp(3));
                waitlist.add(entry);
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return waitlist;
    }
    
    public static void addWaitlistEntry(String faculty, Date date, int seats) {
        // declare a timestamp field.
        java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());

        connection = DBConnection.getConnection();
        ArrayList<RoomEntry> rooms = RoomQueries.getAllPossibleRooms();
        try {
            addWaitlist = connection.prepareStatement("insert into waitlist (faculty, date, seats, timestamp) values (?,?,?,?)");
            addWaitlist.setString(1, faculty);
            addWaitlist.setDate(2, date);
            addWaitlist.setInt(3, seats);
            addWaitlist.setTimestamp(4, currentTimestamp);
            addWaitlist.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
         
    }
    
    public static void cancelWaitlistEntry(String faculty, Date date) {
        connection = DBConnection.getConnection();
        int count = 0;
        try
        {
            getWaitlistList = connection.prepareStatement("delete from waitlist where faculty = ? and date = ?");
            getWaitlistList.setString(1, faculty);
            getWaitlistList.setDate(2, date);
            count = getWaitlistList.executeUpdate();
            
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
    
    public static void deleteWaitlistEntry(String faculty, Date date) {
        connection = DBConnection.getConnection();
        ArrayList<WaitlistEntry> waitlist = new ArrayList<>();
        int count = 0;
        try
        {
            getWaitlistList = connection.prepareStatement("delete from waitlist where faculty = ? and date = ?");
            getWaitlistList.setString(1, faculty);
            getWaitlistList.setDate(2, date);
            count = getWaitlistList.executeUpdate();
            
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
}
