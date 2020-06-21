
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class Dates {
    private static Connection connection;
    private static PreparedStatement addDate;
    private static PreparedStatement getDateList;
    private static ResultSet resultSet;
    
    public static boolean addDate(Date date)
    {
        connection = DBConnection.getConnection();
        try
        {
            addDate = connection.prepareStatement("insert into dates (date) values (?)");
            addDate.setDate(1, date);
            addDate.executeUpdate();
            return true;
            
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return false;
        
    }
    
    public static ArrayList<Date> getAllDates()
    {
        connection = DBConnection.getConnection();
        ArrayList<Date> dates = new ArrayList<Date>();
        try
        {
            getDateList = connection.prepareStatement("select date from dates order by date");
            resultSet = getDateList.executeQuery();
            
            while(resultSet.next())
            {
                dates.add(resultSet.getDate(1));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return dates;
        
    }
}
