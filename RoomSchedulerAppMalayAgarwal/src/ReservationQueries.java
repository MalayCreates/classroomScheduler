
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;


public class ReservationQueries {
    
    private static Connection connection;
    private static PreparedStatement addReservation;
    private static PreparedStatement getReservationList;
    private static PreparedStatement getRoomList;
    private static ResultSet resultSet;
    
    public static ArrayList<ReservationEntry> getReservationsByDate(Date date) {
        connection = DBConnection.getConnection();
        ArrayList<ReservationEntry> reservation = new ArrayList<>();
        try
        {
            getReservationList = connection.prepareStatement("select faculty, room, seats, timestamp from reservations where date = ?");
            getReservationList.setDate(1, date);
            resultSet = getReservationList.executeQuery();
            
            while(resultSet.next())
            {
                ReservationEntry reserve = new ReservationEntry(resultSet.getString(1), resultSet.getString(2), date, resultSet.getInt(3), resultSet.getTimestamp(4));
                reservation.add(reserve);
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return reservation;
    }
    
    public static ArrayList<RoomEntry> getRoomsReservedByDate(Date date) {
        connection = DBConnection.getConnection();
        ArrayList<RoomEntry> rooms = new ArrayList<RoomEntry>();
        System.out.println("DATE " + date);
        try
        {
            getRoomList = connection.prepareStatement("select room, seats from reservations where date = ?");
            getRoomList.setDate(1, date);
            resultSet = getRoomList.executeQuery();
            
            while(resultSet.next())
            {
                RoomEntry room = new RoomEntry(resultSet.getString(1), resultSet.getInt(2));
                rooms.add(room);
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return rooms;
    }
    
    public static boolean addReservationEntry(String faculty, Date date, int seats) {
        // declare a timestamp field.
        java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());

        connection = DBConnection.getConnection();
        ArrayList<RoomEntry> rooms = RoomQueries.getAllPossibleRooms();
        for (int i = 0; i < rooms.size(); i++) {
            RoomEntry room = rooms.get(i);
            if (room.getSeats() >= seats) {
                ArrayList<RoomEntry> roomsReserved = getRoomsReservedByDate(date);
                System.out.println("SIZE" + roomsReserved.size());
                if (roomsReserved.isEmpty() || !roomsReserved.contains(room)) {
                    try
                    {
                        addReservation = connection.prepareStatement("insert into reservations (faculty, room, date, seats, timestamp) values (?,?,?,?,?)");
                        addReservation.setString(1, faculty);
                        addReservation.setString(2, room.getName());
                        addReservation.setDate(3, date);
                        addReservation.setInt(4, room.getSeats());
                        addReservation.setTimestamp(5, currentTimestamp);
                        addReservation.executeUpdate();
                        return true;

                    }
                    catch(SQLException sqlException)
                    {
                        sqlException.printStackTrace();
                    }
                    return true;
                }
            }
        }
        return false;
    }
    
    public static void cancelReservation(String faculty, Date date) {
        connection = DBConnection.getConnection();
        int count = 0;
        try
        {
            getRoomList = connection.prepareStatement("delete from reservations where faculty = ? and date = ?");
            getRoomList.setString(1, faculty);
            getRoomList.setDate(2, date);
            count = getRoomList.executeUpdate();
            
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
    
    public static ArrayList<ReservationEntry> getReservationsByFaculty(String faculty) {
        connection = DBConnection.getConnection();
        ArrayList<ReservationEntry> reservation = new ArrayList<ReservationEntry>();
        try
        {
            getReservationList = connection.prepareStatement("select room, date, seats, timestamp from reservations where faculty = ?");
            getReservationList.setString(1, faculty);
            resultSet = getReservationList.executeQuery();
            
            while(resultSet.next())
            {
                
                ReservationEntry reserve = new ReservationEntry(faculty, resultSet.getString(1), resultSet.getDate(2), resultSet.getInt(3), resultSet.getTimestamp(4));
                reservation.add(reserve);
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
        return reservation;
    }
    
    public static void deleteReservation(String room) {
        connection = DBConnection.getConnection();
        int count = 0;
        try
        {
            getRoomList = connection.prepareStatement("delete from reservations where room = ?");
            getRoomList.setString(1, room);
            count = getRoomList.executeUpdate();
            
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
    
}
