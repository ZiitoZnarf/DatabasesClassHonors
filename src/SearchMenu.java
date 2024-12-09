import java.util.ArrayList;
import java.sql.*;

public class SearchMenu {

    public void openSearchMenu(UserMenu menuInfo, DatabaseConfig dbConfig) {
        boolean exitLoop = false;
        ArrayList<Integer> partIds = new ArrayList<>();
        boolean isMotherboard = false;

        //Conduct Search Parameters (in diff method probs) and return array of part ids
        //Prompt about options in a loop
        //exit when you quit
        while(!exitLoop) {
            boolean exitLoop2 = false;

            partIds = new ArrayList<>();

            try {
                isMotherboard = executeSearch(partIds, dbConfig);
            }
            catch (Exception ex) {
                System.out.println("Error: Invalid Search Arguments Entered!");
                partIds = new ArrayList<>();
            }

            while(!exitLoop2) {

                System.out.println("\nPlease Select one of the following options:");
                System.out.println("\tAdd a Part to Computer [A] || Search Filters [S] || Return to Menu [Q]");
                //System.out.println("\tSearch Filters [S] || Return to Menu [Q]");

                String input = UserMenu.getUserInput("\n> ");

                switch (input.toLowerCase()) {
                    case "a":
                        addItemToComputer(partIds, isMotherboard, menuInfo, dbConfig);
                        break;
                    case "s":
                        exitLoop2 = true;
                        break;
                    case "q":
                        exitLoop = true;
                        exitLoop2 = true;
                        break;
                    default:
                        System.out.println("Error: System Input not recognized!\n");
                }
            }
        }

    }

    //True means that the ids are motherboard ids
    private static boolean executeSearch (ArrayList<Integer> partIDs, DatabaseConfig dbConfig) throws Exception {
        boolean canPass = false;
        boolean isMotherboard = false;        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String query = "";

        System.out.println("==Please Enter Your Search Filters==");
        System.out.println("For all filters following the Part Type, you may simply hit the [ENTER] key\nto skip.");

        //Get Part Type as Input
        System.out.println("\nSelect a Part Type out of the following:");
        System.out.println("\t  Motherboard [M] ||        RAM/Memory [R] ||          Storage [S]\n" +
                "\tCPU/Processor [C] || GPU/Graphics Card [G] || PSU/Power Supply [P]");

        String inputType = null;
        while (!canPass) {
            inputType = UserMenu.getUserInput("\n> ");
            if (inputType.toLowerCase().equals("m") || inputType.toLowerCase().equals("r") ||inputType.toLowerCase().equals("s") ||
                    inputType.toLowerCase().equals("c") ||inputType.toLowerCase().equals("g") ||inputType.toLowerCase().equals("p"))
                canPass = true;
        }


        //Get Part Name as Input
        System.out.println("\nEnter the Name of the part.");
        String inputName = UserMenu.getUserInput("> ");

        //Get Part Min Price as Input
        System.out.println("\nEnter the Minimum Price of the part.");
        String inputMinPrice = UserMenu.getUserInput("> ");

        //Get Part Max Price as input
        System.out.println("\nEnter the Maximum Price of the part.");
        String inputMaxPrice = UserMenu.getUserInput("> ");

        //The code splits off here depending on the inputType
        //It gathers part specific parameters
        switch (inputType.toLowerCase()) {
            case "m":
                isMotherboard = true;
                //Get Memory Slots as input
                System.out.println("\nEnter the number of Memory Slots on the Motherboard.");
                String inputMemSlots = UserMenu.getUserInput("> ");


                query = "SELECT * FROM MOTHERBOARD WHERE 1=1";
                if (!inputName.isEmpty())
                    query += " AND MotherboardName LIKE '%" + inputName + "%'";
                if (!inputMinPrice.isEmpty())
                    query += " AND MotherboardPrice >= " + Double.parseDouble(inputMinPrice);
                if (!inputMaxPrice.isEmpty())
                    query += " AND MotherboardPrice <= " + Double.parseDouble(inputMaxPrice);
                if (!inputMaxPrice.isEmpty())
                    query += " AND MemorySlots = " + Integer.parseInt(inputMemSlots);


                break;
            case "r":
                //Get min size as input
                System.out.println("\nEnter the minimum size, in Gigabytes (GB), of the RAM.");
                String inputMinSize = UserMenu.getUserInput("> ");

                //Get max size as input
                System.out.println("\nEnter the maximum size, in Gigabytes (GB), of the RAM.");
                String inputMaxSize = UserMenu.getUserInput("> ");

                //Get min speed as input
                System.out.println("\nEnter the minimum speed of the RAM.");
                String inputMinSpeed = UserMenu.getUserInput("> ");

                //Get max speed as input
                System.out.println("\nEnter the maximum speed of the RAM.");
                String inputMaxSpeed = UserMenu.getUserInput("> ");

                //Get min modules as input
                System.out.println("\nEnter the minimum number of modules of RAM.");
                String inputMinModules = UserMenu.getUserInput("> ");

                //Get max modules as input
                System.out.println("\nEnter the maximum number of modules of RAM.");
                String inputMaxModules = UserMenu.getUserInput("> ");


                query = "SELECT * FROM PART, RAM WHERE PART.PartID = RAM.PartID";
                if (!inputName.isEmpty())
                    query += " AND PartName LIKE '%" + inputName + "%'";
                if (!inputMinPrice.isEmpty())
                    query += " AND PartPrice >= " + Double.parseDouble(inputMinPrice);
                if (!inputMaxPrice.isEmpty())
                    query += " AND PartPrice <= " + Double.parseDouble(inputMaxPrice);
                if (!inputMinSize.isEmpty())
                    query += " AND Size >= " + Integer.parseInt(inputMinSize);
                if (!inputMaxSize.isEmpty())
                    query += " AND Size <= " + Integer.parseInt(inputMaxSize);
                if (!inputMinSpeed.isEmpty())
                    query += " AND Speed >= " + Integer.parseInt(inputMinSpeed);
                if (!inputMaxSpeed.isEmpty())
                    query += " AND Speed <= " + Integer.parseInt(inputMaxSpeed);


                break;
            case "s":
                //Get max capacity as input

                //Get min capacity as input
                System.out.println("\nEnter the minimum capacity of the Storage.");
                String inputMinCapacity = UserMenu.getUserInput("> ");

                System.out.println("\nEnter the maximum capacity of the Storage.");
                String inputMaxCapacity = UserMenu.getUserInput("> ");

                //Get storage type as input
                System.out.println("\nEnter the type of Storage.");
                String inputStorageType = UserMenu.getUserInput("> ");

                //Get    input
                System.out.println("\nEnter the Interface for the Storage.");
                String inputInterface = UserMenu.getUserInput("> ");

                //Get    input
                System.out.println("\nEnter the Form Factor for the Storage.");
                String inputFormFactor = UserMenu.getUserInput("> ");



                query = "SELECT * FROM PART, STORAGE WHERE PART.PartID = STORAGE.PartID";
                if (!inputName.isEmpty())
                    query += " AND PartName LIKE '%" + inputName + "%'";
                if (!inputMinPrice.isEmpty())
                    query += " AND PartPrice >= " + Double.parseDouble(inputMinPrice);
                if (!inputMaxPrice.isEmpty())
                    query += " AND PartPrice <= " + Double.parseDouble(inputMaxPrice);
                if (!inputMinCapacity.isEmpty())
                    query += " AND Capacity >= " + Integer.parseInt(inputMinCapacity);
                if (!inputMaxCapacity.isEmpty())
                    query += " AND Capacity <= " + Integer.parseInt(inputMaxCapacity);
                if (!inputStorageType.isEmpty())
                    query += " AND Type LIKE '%" + inputStorageType + "%'";
                if (!inputInterface.isEmpty())
                    query += " AND Interface LIKE '%" + inputInterface +"%'";
                if (!inputFormFactor.isEmpty())
                    query += " AND FormFactor LIKE '%" + inputFormFactor + "%'";

                break;
            case "c":
                System.out.println("\nEnter the minimum core count of the CPU.");
                String inputMinCores = UserMenu.getUserInput("> ");

                System.out.println("\nEnter the maximum core count of the CPU.");
                String inputMaxCores = UserMenu.getUserInput("> ");

                System.out.println("\nEnter the minimum Base Core Speed of the CPU.");
                String inputMinBase = UserMenu.getUserInput("> ");

                System.out.println("\nEnter the maximum Base Core Speed of the CPU.");
                String inputMaxBase = UserMenu.getUserInput("> ");

                System.out.println("\nEnter the minimum Boost Core Speed of the CPU.");
                String inputMinBoost = UserMenu.getUserInput("> ");

                System.out.println("\nEnter the maximum Boost Core Speed of the CPU.");
                String inputMaxBoost = UserMenu.getUserInput("> ");


                query = "SELECT * FROM PART, PROCESSOR WHERE PART.PartID = PROCESSOR.PartID";
                if (!inputName.isEmpty())
                    query += " AND PartName LIKE '%" + inputName + "%'";
                if (!inputMinPrice.isEmpty())
                    query += " AND PartPrice >= " + Double.parseDouble(inputMinPrice);
                if (!inputMaxPrice.isEmpty())
                    query += " AND PartPrice <= " + Double.parseDouble(inputMaxPrice);
                if (!inputMinCores.isEmpty())
                    query += " AND CoreCount >= " + Integer.parseInt(inputMinCores);
                if (!inputMaxCores.isEmpty())
                    query += " AND CoreCount <= " + Integer.parseInt(inputMaxCores);
                if (!inputMinBase.isEmpty())
                    query += " AND CoreClkSpd >= " + Integer.parseInt(inputMinBase);
                if (!inputMaxBase.isEmpty())
                    query += " AND CoreClkSpd <= " + Integer.parseInt(inputMaxBase);
                if (!inputMinBoost.isEmpty())
                    query += " AND BoostClkSpd >= " + Integer.parseInt(inputMinBoost);
                if (!inputMaxBoost.isEmpty())
                    query += " AND BoostClkSpd <= " + Integer.parseInt(inputMaxBoost);

                break;
            case "g":
                System.out.println("\nEnter the chipset of the GPU.");
                String inputChipset = UserMenu.getUserInput("> ");

                System.out.println("\nEnter the minimum length of the GPU.");
                String inputMinLength = UserMenu.getUserInput("> ");

                System.out.println("\nEnter the maximum length of the GPU.");
                String inputMaxLength = UserMenu.getUserInput("> ");

                System.out.println("\nEnter the minimum memory of the GPU.");
                String inputMinMemory = UserMenu.getUserInput("> ");

                System.out.println("\nEnter the maximum memory of the GPU.");
                String inputMaxMemory = UserMenu.getUserInput("> ");

                query = "SELECT * FROM PART, GRAPHICS_CARD WHERE PART.PartID = GRAPHICS_CARD.PartID";
                if (!inputName.isEmpty())
                    query += " AND PartName LIKE '%" + inputName + "%'";
                if (!inputMinPrice.isEmpty())
                    query += " AND PartPrice >= " + Double.parseDouble(inputMinPrice);
                if (!inputMaxPrice.isEmpty())
                    query += " AND PartPrice <= " + Double.parseDouble(inputMaxPrice);
                if (!inputChipset.isEmpty())
                    query += " AND Chipset LIKE '%" + inputChipset + "%'";
                if (!inputMinLength.isEmpty())
                    query += " AND Length >= " + Integer.parseInt(inputMinLength);
                if (!inputMaxLength.isEmpty())
                    query += " AND Length <= " + Integer.parseInt(inputMaxLength);
                if (!inputMinMemory.isEmpty())
                    query += " AND Memory >= " + Integer.parseInt(inputMinMemory);
                if (!inputMaxMemory.isEmpty())
                    query += " AND Memory <= " + Integer.parseInt(inputMaxMemory);

                break;
            case "p":
                System.out.println("\nEnter the minimum wattage of the PSU.");
                String inputMinWattage = UserMenu.getUserInput("> ");

                System.out.println("\nEnter the maximum wattage of the PSU.");
                String inputMaxWattage = UserMenu.getUserInput("> ");

                System.out.println("\nEnter the minimum efficency rating of the PSU.");
                String inputMinRating = UserMenu.getUserInput("> ");

                System.out.println("\nIs the PSU Modular (Y/N).");
                String inputModular = UserMenu.getUserInput("> ");

                query = "SELECT * FROM PART, POWER_SUPPLY WHERE PART.PartID = POWER_SUPPLY.PartID";
                if (!inputName.isEmpty())
                    query += " AND PartName LIKE '%" + inputName + "%'";
                if (!inputMinPrice.isEmpty())
                    query += " AND PartPrice >= " + Double.parseDouble(inputMinPrice);
                if (!inputMaxPrice.isEmpty())
                    query += " AND PartPrice <= " + Double.parseDouble(inputMaxPrice);
                if (!inputMinWattage.isEmpty())
                    query += " AND Wattage >= " + Integer.parseInt(inputMinWattage);
                if (!inputMaxWattage.isEmpty())
                    query += " AND Wattage <= " + Integer.parseInt(inputMaxWattage);
                if (!inputMinRating.isEmpty())
                    query += " AND EfficiencyRating >= " + Integer.parseInt(inputMinRating);
                if (inputModular.equalsIgnoreCase("y"))
                    query += " AND Modular = True";
                if (inputModular.equalsIgnoreCase("n"))
                    query += " AND Modular = False";

                break;
        }

        query += ";";

        conn = DriverManager.getConnection(dbConfig.getJdbcUrl(), dbConfig.getUsername(), dbConfig.getPassword());
        stmt = conn.createStatement();
        rs = stmt.executeQuery(query);

        ResultSetMetaData metaData = rs.getMetaData();
        int numColumns = metaData.getColumnCount();
        System.out.printf("%-24s", "NumberID");

        for (int i = 2; i <= numColumns; i++) {
            System.out.printf("%-24s", metaData.getColumnLabel(i));
        }


        System.out.println();

        Object obj = null;
        int rowNum = 1;
        while(rs.next()) {
            partIDs.add(rs.getInt(1));
            System.out.printf("%-24s", rowNum++);
            for (int i = 2; i <= numColumns; i++) {
                obj = rs.getObject(i);
                if (obj != null) {
                    System.out.printf("%-24s", obj.toString());
                }
                else {
                    System.out.printf("%-24s", "");
                }
            }
            System.out.println();
        }


        rs.close();
        stmt.close();
        conn.close();
        return isMotherboard;
    }

    private static void addItemToComputer(ArrayList<Integer> partIDs, boolean isMotherboard, UserMenu menuInfo, DatabaseConfig dbConfig) {
        System.out.println("Please Enter the NumberId of the item you wish to add: ");
        int inputNum = -1;

        while(inputNum < 1 || inputNum > partIDs.size()) {
            String temp = UserMenu.getUserInput("");
            inputNum = Integer.parseInt(temp);
        }

        System.out.println("Please Enter the quantity of the item you wish to add: ");

        int inputCount = -1;
        while(inputCount < 1 || inputNum > partIDs.size()) {
            String temp = UserMenu.getUserInput("");
            inputCount = Integer.parseInt(temp);
        }



        try {
            Connection conn = DriverManager.getConnection(dbConfig.getJdbcUrl(), dbConfig.getUsername(), dbConfig.getPassword());
            Statement stmt = conn.createStatement();
            if (isMotherboard)
                stmt.executeUpdate("INSERT INTO CONTAINS_MB VALUES (" + menuInfo.getComputerID() + ", " + partIDs.get(inputNum - 1) + ");");
            else
                stmt.executeUpdate("INSERT INTO CONTAINS_PART VALUES (" + menuInfo.getComputerID() + ", " + partIDs.get(inputNum - 1) + ", " + inputCount + ");");

            stmt.close();
            conn.close();
        }
        catch (SQLException exc) {
            System.out.println("ERROR: Unable to add Item to Computer");
            //exc.printStackTrace();
        }

    }
}
