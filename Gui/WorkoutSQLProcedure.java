import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.Scanner;

public class WorkoutSQLProcedure{
	
	private static String passSQL;
	
	private static boolean firstLogin = true;
	
	public static Connection connectOpenSQL() throws SQLException{
		if(firstLogin){
			System.out.println("Enter SQL Server Password:");
			
			Scanner scan = new Scanner(System.in);
			
			passSQL = scan.nextLine();
			
			firstLogin = false;
		}
		
		Connection connector = DriverManager.getConnection("jdbc:mysql://localhost:3306/workout","root",passSQL);
		
		return connector;
	}
	
	public static void addRep(int workoutId, Connection connector) throws SQLException{
		String call = "{call addRep(?)}";
		
		CallableStatement procedureCall = connector.prepareCall(call);
		
		procedureCall.setInt(1, workoutId);
		
		int affectedRows = procedureCall.executeUpdate();
		
		if (affectedRows == 0){
			throw new SQLException();
		}
	}
	public static void addWorker(String workerName, Connection connector) throws SQLException{
		String call = "{call addWorker(?)}";
		
		CallableStatement procedureCall = connector.prepareCall(call);
		
		procedureCall.setString(1, workerName);
		
		int affectedRows = procedureCall.executeUpdate();
		
		if (affectedRows == 0){
			throw new SQLException();
		}
	}
	public static void addWorkout(int workerId, int workoutNameId, int maxRepNum, Connection connector) throws SQLException{
		String call = "{call addWorkout(?,?,?)}";
		
		CallableStatement procedureCall = connector.prepareCall(call);
		
		procedureCall.setInt(1, workerId);
		procedureCall.setInt(2, workoutNameId);
		procedureCall.setInt(3, maxRepNum);
		
		int affectedRows = procedureCall.executeUpdate();
		
		if (affectedRows == 0){
			throw new SQLException();
		}
	}
	public static void addWorkoutName(String workoutName, Connection connector) throws SQLException{
		String call = "{call addWorkoutName(?)}";
		
		CallableStatement procedureCall = connector.prepareCall(call);
		
		procedureCall.setString(1, workoutName);
		
		int affectedRows = procedureCall.executeUpdate();
		
		if (affectedRows == 0){
			throw new SQLException();
		}
	}
	
	public static void delRep(int workoutId, Connection connector) throws SQLException{
		String call = "{call delRep(?)}";
		
		CallableStatement procedureCall = connector.prepareCall(call);
		
		procedureCall.setInt(1, workoutId);
		
		int affectedRows = procedureCall.executeUpdate();
		
		if (affectedRows == 0){
			throw new SQLException();
		}
	}
	
	public static ResultSet showAllUsers(Connection connector) throws SQLException{
		String call = "{call showAllUsers()}";
		
		CallableStatement procedureCall = connector.prepareCall(call);
		
		ResultSet result = procedureCall.executeQuery();
		
		return result;
	}
	public static ResultSet showWorkouts(Connection connector) throws SQLException{
		String call = "{call showWorkouts()}";
		
		CallableStatement procedureCall = connector.prepareCall(call);
		
		ResultSet result = procedureCall.executeQuery();
		
		return result;
	}
	public static ResultSet showUserWorkouts(int workerId, Connection connector) throws SQLException{
		String call = "{call showUserWorkouts(?)}";
		
		CallableStatement procedureCall = connector.prepareCall(call);
		
		procedureCall.setInt(1, workerId);
		
		ResultSet result = procedureCall.executeQuery();
		
		return result;
	}
	public static ResultSet showWorkoutReps(int workoutId, Connection connector) throws SQLException{
		String call = "{call showWorkoutReps(?)}";
		
		CallableStatement procedureCall = connector.prepareCall(call);
		
		procedureCall.setInt(1, workoutId);
		
		ResultSet result = procedureCall.executeQuery();
		
		return result;
	}
	public static ResultSet showAvailableWorkouts(Connection connector) throws SQLException{
		String call = "{call showAvailableWorkouts()}";
		
		CallableStatement procedureCall = connector.prepareCall(call);
		
		ResultSet result = procedureCall.executeQuery();
		
		return result;
	}
	public static ResultSet showUserID(String workerName, Connection connector) throws SQLException{
		String call = "{call showUserID(?)}";
		
		CallableStatement procedureCall = connector.prepareCall(call);
		
		procedureCall.setString(1, workerName);
		
		ResultSet result = procedureCall.executeQuery();
		
		return result;
	}
	public static ResultSet showNumUser(Connection connector) throws SQLException{
		String call = "{call showNumUser()}";
		
		CallableStatement procedureCall = connector.prepareCall(call);
		
		ResultSet result = procedureCall.executeQuery();
		
		return result;
	}
	public static ResultSet showWorkoutID(String workoutName, Connection connector) throws SQLException{
		String call = "{call showWorkoutID(?)}";
		
		CallableStatement procedureCall = connector.prepareCall(call);
		
		procedureCall.setString(1, workoutName);
		
		ResultSet result = procedureCall.executeQuery();
		
		return result;
	}
	public static ResultSet showWorkoutNames(Connection connector) throws SQLException{
		String call = "{call showWorkoutNames()}";
		
		CallableStatement procedureCall = connector.prepareCall(call);
		
		ResultSet result = procedureCall.executeQuery();
		
		return result;
	}
}
