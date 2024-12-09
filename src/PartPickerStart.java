public class PartPickerStart {

    public static void main(String[] args) {
        if (args.length != 4) {
            System.err.println("Usage: java PartPickerStart <url> <username> <password> <sqlFilePath>");
            return;
        }

        // NOTE: these strings are for initializing the db. The url, username, and password are stored in DatabaseConfig
        // Possibly refactor later, but for now, this is fine.
        String baseUrl = args[0]; // NOTE: This is the base URL without the database name, due to init setup code
        String username = args[1];
        String password = args[2];
        String sqlFilePath = args[3];

        // Save the database configuration (url, user, pass) for later use
        DatabaseConfig dbConfig = new DatabaseConfig(baseUrl, username, password);

        // Initialize the database (requires baseURL specifically)
        InitializeDatabase.initializeDatabase(baseUrl, username, password, sqlFilePath);

        //TODO: Inform if arguments or connection invalid.

        //Prompt User Login/Register/Quit in a NEW Method
        openStartMenu(dbConfig); // Pass the dbConfig object to the method (this could be done differently if needed)


    }


    private static void openStartMenu(DatabaseConfig dbConfig) {
        System.out.println("==Welcome to the PC Builder Application==");
        boolean exitLoop = false;

        while (!exitLoop) {
            System.out.println("Please Select one of the following options:");
            System.out.println("\tLogin [L] || Register [R] || Quit [Q]");

            String input = UserMenu.getUserInput("\n> ");
            System.out.println();

            switch (input.toLowerCase()) {
                case "l":
                    openLoginOption(dbConfig);
                    break;
                case "r":
                    openRegisterOption(dbConfig);
                    break;
                case "q":
                    exitLoop = true;
                    break;
                default:
                    System.out.println("Error: System Input not recognized!\n");
            }
        }
    }

    //TODO: DB Compatible
    private static void openLoginOption(DatabaseConfig dbConfig) {
        boolean exitLoop = false;
        // Create UserInteraction object to interact with the database
        UserInteraction userInteraction = new UserInteraction(dbConfig);

        while (!exitLoop) {
            String inputUsername = UserMenu.getUserInput("Input Username: ");
            String inputPassword = UserMenu.getUserInput("Input Password: ");
            System.out.println();

            boolean loginValid = userInteraction.validateLogin(inputUsername, inputPassword);

            if (loginValid) {
                int computerID = userInteraction.getUserComputerId(inputUsername);
                if (computerID != -1) {
                    // Create menu object and go to user menu
                    UserMenu userMenu = new UserMenu(inputUsername, computerID);
                    userMenu.openUserMenu(dbConfig);
                    exitLoop = true;
                } else {
                    System.out.println("Error: No computer found for the user.");
                }
            }
            else {
                System.out.println("Given Username and Password do not match.");
                boolean exitLoop2 = false;

                while(!exitLoop2) {
                    System.out.println("Please Select one of the following options:");
                    System.out.println("\tRetry Login [L] || Return to Start Menu [Q]");

                    String input = UserMenu.getUserInput("\n> ");

                    switch (input.toLowerCase()) {
                        case "l":
                            exitLoop2 = true;
                            break;
                        case "q":
                            exitLoop2 = true;
                            exitLoop = true;
                            break;
                        default:
                            System.out.println("Error: System Input not recognized!\n");
                    }
                }
            }


        }
    }

    //TODO: DB Compatible
    //TODO: Username/Password Length Checks
    private static void openRegisterOption(DatabaseConfig dbConfig) {
        boolean exitLoop = false;
        UserInteraction userInteraction = new UserInteraction(dbConfig);

        while (!exitLoop) {
            String inputUsername = UserMenu.getUserInput("Please enter a Username: ");
            //TODO: Check if Username is taken or input is empty
            if(inputUsername.isEmpty()){
                System.out.println("Error: Username cannot be empty.");
                continue;
            }


            String inputPassword = UserMenu.getUserInput("Please enter a Password: ");
            if(inputPassword.isEmpty()) {
                System.out.println("Error: Password cannot be empty.");
                continue;
            }


            String inputComputerName = UserMenu.getUserInput("Please enter a Name for your first Computer: ");
            if(inputComputerName.isEmpty()){
                System.out.println("Error: Computer Name cannot be empty.");
                continue;
            }

            boolean registrationSuccess = userInteraction.registerUser(inputUsername, inputPassword, inputComputerName);

            if (registrationSuccess) {
                exitLoop = true;
            } else {
                System.out.println("Error: Registration failed. Please try again.");
            }


        }
    }
}
