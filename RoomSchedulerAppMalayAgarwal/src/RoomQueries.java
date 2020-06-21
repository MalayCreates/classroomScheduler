
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class RoomQueries {
    
    private static Connection connection;
    private static PreparedStatement addRoom;
    
    private static PreparedStatement getRoomList;
    private static ResultSet resultSet;
    
    public static ArrayList<RoomEntry> getAllPossibleRooms() {
        connection = DBConnection.getConnection();
        ArrayList<RoomEntry> rooms = new ArrayList<RoomEntry>();
        try
        {
            getRoomList = connection.prepareStatement("select name, seats from rooms order by seats");
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
    
    public static void addRoom(String room, int seats) {
        connection = DBConnection.getConnection();
        try
        {
            addRoom= connection.prepareStatement("insert into rooms (name, seats) values (?,?)");
            addRoom.setString(1, room);
            addRoom.setInt(2, seats);
            addRoom.executeUpdate();

        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
    
    public static void dropRoom(String room) {
        connection = DBConnection.getConnection();
        ArrayList<WaitlistEntry> waitlist = new ArrayList<>();
        int count = 0;
        try
        {
            getRoomList = connection.prepareStatement("delete from rooms where name = ?");
            getRoomList.setString(1, room);
            count = getRoomList.executeUpdate();
            
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
    
}
