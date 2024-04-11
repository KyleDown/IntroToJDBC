import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DALGameSystem {
   private Connection getMySQLConnection(String databaseName, String user, String password) {
      try {
         return DriverManager.getConnection("jdbc:mysql://localhost:3306/" + databaseName, user, password);
      } catch (SQLException var5) {
         System.out.println("Failed to connect to the database" + var5.getMessage());
         return null;
      }
   }

   public boolean TryExecutingAQuery(String databaseName, String query, String user, String password) {
      try {
         Connection myConnection = this.getMySQLConnection(databaseName, user, password);
         if (myConnection == null) {
            System.out.println("Failed to get a connection, cannot execute query");
            return false;
         } else {
            Statement myStatement = myConnection.createStatement();
            ResultSet myRelation = myStatement.executeQuery(query);

            while(myRelation.next()) {
               String mySeriesName = myRelation.getString("SeriesName");
               int myId = myRelation.getInt("Id");
               System.out.println("Tuple Values:" + mySeriesName + "," + myId);
            }

            return true;
         }
      } catch (SQLException var10) {
         System.out.println("Epic Fail Executing a Query:" + var10.getMessage());
         return false;
      }
   }

   public boolean TryExecutingAStoredProcedure(String databaseName, String user, String password) {
      Connection myConnection = this.getMySQLConnection(databaseName, user, password);
      if (myConnection == null) {
         System.out.println("Failed to get a connection, cannot execute stored procedure");
         return false;
      } else {
         try {
            CallableStatement myStoredProcedureCall = myConnection.prepareCall("{Call GetConsoles()}");
            ResultSet myResults = myStoredProcedureCall.executeQuery();

            while(myResults.next()) {
               String myConsoleName = myResults.getString("ConsoleName");
               String myReleaseDate = myResults.getString("ReleaseDate");
               String myDeveloper = myResults.getString("Developer");
               double mySystemRevision  = myResults.getInt("SystemRevision");
               System.out.println("Tuple Values:" + myConsoleName + "," + myReleaseDate + "," + myDeveloper + ","+ mySystemRevision);
            }

            return true;
         } catch (SQLException var10) {
            System.out.println("Failed to execute stored procedure:" + var10.getMessage());
            return false;
         }
      }
   }

   public boolean TryExecutingAStoredProcedureWithParam(String databaseName, String user, String password, 
   String ConsoleName, Date ReleaseDate, String Developer, double SystemRevision) {
      Connection myConnection = this.getMySQLConnection(databaseName, user, password);
      if (myConnection == null) {
         System.out.println("Failed to obstain a valid connection. Stored procedure could not be run");
         return false;
      } else {
         try {
            CallableStatement myStoredProcedureCall = myConnection.prepareCall("{Call InsertNewConsole(?, ?, ?, ?, ?)}");
            myStoredProcedureCall.setString(1, ConsoleName);
            myStoredProcedureCall.setDate(2, ReleaseDate);
            myStoredProcedureCall.setString(3, Developer);
            myStoredProcedureCall.setDouble(3, SystemRevision);
            myStoredProcedureCall.executeQuery();
            return true;
         } catch (SQLException var11) {
            System.out.println("Failed to execute stored procedure:" + var11.getMessage());
            return false;
         }
      }
   }

   public boolean TryExecutingAStoredProcedureWithParam(String databaseName, String userName, String password, int ID,
   String ConsoleName, int i, String Developer, double SystemRevision) {
      // TODO Auto-generated method stub
      throw new UnsupportedOperationException("Unimplemented method 'TryExecutingAStoredProcedureWithParam'");
   }
}
