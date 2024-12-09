import java.sql.*;
import java.util.ArrayList;

public class ComputerMenu {

    public void openComputerMenu(UserMenu menuInfo, DatabaseConfig dbConfig) {
        //display current computer in diff method
        //loop through selection options
        System.out.println("==Computer Actions==");

        boolean exitLoop = false;

        while (!exitLoop) {
            displayComputerInformation(menuInfo.getComputerID(), dbConfig);

            System.out.println("Please Select one of the following options:");
            //System.out.println("\tChange Computer Name [N] || Remove Computer Part [R] || Switch Active Computer [S]\n" +
            //                   "\t   Delete a Computer [D] ||       Return to Menu [Q] || ");
            System.out.println("\tChange Computer Name [N] || Remove Computer Part [R] || Return to Menu [Q]");

            String input = UserMenu.getUserInput("\n> ");
            System.out.println();

            switch (input.toLowerCase()) {
                case "n":
                    changeComputerName(menuInfo.getComputerID(), dbConfig);
                    break;
                case "r":
                    editComputerParts(menuInfo.getComputerID(), dbConfig);
                    break;
                case "s":
                    //Attempts to change to a different computer, and returns that value for application
                    //menuInfo.setComputerID(switchActiveComputer(menuInfo.getUsername()));
                    break;
                case "d":
                    //deleteComputer(menuInfo.getUsername());
                    break;
                case "q":
                    exitLoop = true;
                    break;
                default:
                    System.out.println("Error: System Input not recognized!\n");
            }
        }
    }


    private static void displayComputerInformation(int computerID, DatabaseConfig dbConfig) {


        try {
            Connection conn = DriverManager.getConnection(dbConfig.getJdbcUrl(), dbConfig.getUsername(), dbConfig.getPassword());
            Statement stmt = conn.createStatement();
            ResultSet rs;
            int rowCount = 1;

            rs = stmt.executeQuery("SELECT ComputerName FROM COMPUTER WHERE ComputerID = " + computerID + ";");

            if (rs.next())
                System.out.printf("Computer Name: %s\n\n", rs.getString(1));

            rs = stmt.executeQuery("SELECT MotherboardName FROM CONTAINS_MB C, MOTHERBOARD M WHERE" +
                    " C.ComputerID = " + computerID + " AND C.MotherboardID = M.MotherboardID;");

            System.out.printf("%-10s", "PartNum");
            System.out.printf("%-30s", "PartName");
            System.out.printf("%-10s\n", "Quantity");

            if (rs.next()) {
                System.out.printf("%-10s", rowCount++);
                System.out.printf("%-30s", rs.getString(1));
                System.out.printf("%-10s\n", "1");
            }

            rs = stmt.executeQuery("SELECT PartName, Quantity FROM CONTAINS_PART C, PART P WHERE" +
                    " C.ComputerID = " + computerID + " AND C.PartID = P.PartID;");

            while (rs.next()) {
                System.out.printf("%-10s", rowCount++);
                System.out.printf("%-30s", rs.getString(1));
                System.out.printf("%-10s\n", rs.getInt(2));
            }

            rs.close();
            stmt.close();
            conn.close();

        }
        catch (SQLException exc) {
            System.out.print("Unable to Display Computer Contents");
        }

    }

    private static void changeComputerName(int computerID, DatabaseConfig dbConfig) {
        String input = UserMenu.getUserInput("\nPlease enter a new Computer name: ");
        if (input.isEmpty()) {
            System.out.println("Computer Name Cannot be Empty!");
            return;
        }
        try {
            Connection conn = DriverManager.getConnection(dbConfig.getJdbcUrl(), dbConfig.getUsername(), dbConfig.getPassword());
            Statement stmt = conn.createStatement();

            stmt.executeUpdate("UPDATE COMPUTER SET ComputerName = '" + input + "' WHERE ComputerID = " + computerID + ";");

            stmt.close();
            conn.close();
        }
        catch (SQLException exc) {
            System.out.print("Error: Unable to Change Computer Name.");
        }
    }

    private static void editComputerParts(int computerID, DatabaseConfig dbConfig) {
        ArrayList<Integer> partIDs = new ArrayList<>();
        try {
            Connection conn = DriverManager.getConnection(dbConfig.getJdbcUrl(), dbConfig.getUsername(), dbConfig.getPassword());
            Statement stmt = conn.createStatement();
            ResultSet rs;
            int rowCount = 1;

            rs = stmt.executeQuery("SELECT MotherboardName, M.MotherboardID FROM CONTAINS_MB C, MOTHERBOARD M WHERE" +
                    " C.ComputerID = " + computerID + " AND C.MotherboardID = M.MotherboardID;");

            System.out.printf("%-10s", "PartNum");
            System.out.printf("%-30s", "PartName");
            System.out.printf("%-10s\n", "Quantity");

            if (rs.next()) {
                System.out.printf("%-10s", rowCount++);
                System.out.printf("%-30s", rs.getString(1));
                System.out.printf("%-10s\n", "1");
                partIDs.add(rs.getInt(2));
            } else {
                partIDs.add(null);
            }

            rs = stmt.executeQuery("SELECT PartName, Quantity, C.PartID FROM CONTAINS_PART C, PART P WHERE" +
                    " C.ComputerID = " + computerID + " AND C.PartID = P.PartID;");

            while (rs.next()) {
                System.out.printf("%-10s", rowCount++);
                System.out.printf("%-30s", rs.getString(1));
                System.out.printf("%-10s\n", rs.getInt(2));
                partIDs.add(rs.getInt(3));
            }

            rs.close();
            stmt.close();
            conn.close();

        }
        catch (SQLException exc) {
            System.out.print("Unable to Display Computer Contents");
            //exc.printStackTrace();
            return;
        }

        String input = UserMenu.getUserInput("Please Enter The Part Number To Remove (or press \n[ENTER] to cancel): ");
        int num = -1;

        try {
            num = Integer.parseInt(input);
        }
        catch (NumberFormatException exc) {
            return;
        }

        if (partIDs.get(0) != null) {
            num--;
        }

        if (num == 0) {
            try {
                Connection conn = DriverManager.getConnection(dbConfig.getJdbcUrl(), dbConfig.getUsername(), dbConfig.getPassword());
                Statement stmt = conn.createStatement();

                stmt.executeUpdate("DELETE FROM CONTAINS_MB WHERE ComputerID = " + computerID +
                        " AND MotherboardID = " + partIDs.get(num) + ";");

                stmt.close();
                conn.close();
            }
            catch (SQLException exc) {
                System.out.print("Error: Unable to Delete Part.");
            }
        }
        else {
            try {
                Connection conn = DriverManager.getConnection(dbConfig.getJdbcUrl(), dbConfig.getUsername(), dbConfig.getPassword());
                Statement stmt = conn.createStatement();

                stmt.executeUpdate("DELETE FROM CONTAINS_PART WHERE ComputerID = " + computerID +
                        " AND PartID = " + partIDs.get(num) + ";");

                stmt.close();
                conn.close();
            }
            catch (SQLException exc) {
                System.out.print("Error: Unable to Delete Part.");
            }
        }


    }

    //Returns new active computerID
    private static int switchActiveComputer(String username) {

        return -1;
    }

    private static void deleteComputer(String username) {

    }
}
