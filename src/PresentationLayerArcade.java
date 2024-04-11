import java.util.Scanner;

public class PresentationLayerArcade
{
    public static void main(String[] args)
    {
        Scanner userInformation = new Scanner(System.in);
        System.out.println("Enter username and password:");
        // String input
        String userName = userInformation.nextLine();
        String password = userInformation.nextLine();

        // Let's start simple. How do we connect to and access a database?
        // Well, the presentation layer can't do it. We need an instance of the DAL!
        DALArcadeGame dal = new DALArcadeGame();

        // Now we can use the dal object, so let's print
        // out some rows from the Meal table, in the MealPlanningDatabase.
        // We need to pass the dal method everything it needs to run a query, including
        // the database name, the query, and the user's sql credentials.
        if (dal.TryExecutingAQuery("ArcadeGames", "Select * from Game", userName, password))
        {
            System.out.println("Successfully connected to the database");
        }
        else
        {
            System.out.println("Failed to connect to the database");
        }

        // Let's try calling a stored procedure, and let's start simple.
        // I made a new stored procedure that just returns everything in the 
        // Recipe table, called GetRecipes. No parameters, just a simple call.
        if (dal.TryExecutingAStoredProcedure("ArcadeGames", userName, password))
        {
            System.out.println("Successfully ran a stored procedure");
        }
        else
        {
            System.out.println("Failed to run a stored procedure");
        }

        if(dal.TryExecutingAStoredProcedureWithParam("ArcadeGames", userName, password, 8,"Tetris", 
        "Tetris Company", "1979-08-04", "2022-01-15 7:34:21"))
        {
            System.out.println("Success ran stored procedure with params");
        }
        else
        {
            System.out.println("Failed to run stored procedure with params");
        }
    }  
}