import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DALArcadeGame {
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
               String myGameName = myRelation.getString("GameName");
               int myId = myRelation.getInt("Id");
               System.out.println("Tuple Values:" + myGameName + "," + myId);
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
            CallableStatement myStoredProcedureCall = myConnection.prepareCall("{Call GetRecipes()}");
            ResultSet myResults = myStoredProcedureCall.executeQuery();

            while(myResults.next()) {
               String myFavoriteGame = myResults.getString("FavoriteGame");
               String myUserName = myResults.getString("UserName");
               int userId = myResults.getInt("Id");
               System.out.println("Tuple Values:" + myFavoriteGame + "," + myUserName + "," + userId);
            }

            return true;
         } catch (SQLException var10) {
            System.out.println("Failed to execute stored procedure:" + var10.getMessage());
            return false;
         }
      }
   }

   public boolean TryExecutingAStoredProcedureWithParam(String databaseName, String user, String password, 
   int Id, String GameName, String DeveloperName, Date ReleaseDate, Date LastMaintenanceWindow) {
      Connection myConnection = this.getMySQLConnection(databaseName, user, password);
      if (myConnection == null) {
         System.out.println("Failed to obstain a valid connection. Stored procedure could not be run");
         return false;
      } else {
         try {
            CallableStatement myStoredProcedureCall = myConnection.prepareCall("{Call InsertNewRecipe(?, ?, ?, ?, ?)}");
            myStoredProcedureCall.setInt(1, Id);
            myStoredProcedureCall.setString(2, DeveloperName);
            myStoredProcedureCall.setDate(3, ReleaseDate);
            myStoredProcedureCall.setDate(4, LastMaintenanceWindow);
            myStoredProcedureCall.executeQuery();
            return true;
         } catch (SQLException var11) {
            System.out.println("Failed to execute stored procedure:" + var11.getMessage());
            return false;
         }
      }
   }

   public boolean TryExecutingAStoredProcedureWithParam(String databaseName, String userName, String password, int id,
         String gameName, String developerName, String string, String string2) {
      // TODO Auto-generated method stub
      throw new UnsupportedOperationException("Unimplemented method 'TryExecutingAStoredProcedureWithParam'");
   }
}
