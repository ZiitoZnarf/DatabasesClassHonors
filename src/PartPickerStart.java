public class PartPickerStart {

    public static void main(String[] args) {
        //TODO: Gather Database info from args right here


        //TODO: Inform if arguments or connection invalid.

        //Prompt User Login/Register/Quit in a NEW Method
        openStartMenu();

        //TODO: Error Handling
        //TODO: Close Database Connection
    }


    private static void openStartMenu() {
        System.out.println("==Welcome to the PC Builder Application==");
        boolean exitLoop = false;

        while (!exitLoop) {
            System.out.println("Please Select one of the following options:");
            System.out.println("\tLogin [L] || Register [R] || Quit [Q]");

            String input = UserMenu.getUserInput("\n> ");
            System.out.println();

            switch (input.toLowerCase()) {
                case "l":
                    openLoginOption();
                    break;
                case "r":
                    openRegisterOption();
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
    private static void openLoginOption() {
        boolean exitLoop = false;

        while (!exitLoop) {
            String inputUsername = UserMenu.getUserInput("Input Username: ");
            String inputPassword = UserMenu.getUserInput("Input Password: ");
            System.out.println();

            //TODO: CHECK HERE IF USERNAME/PASSWORD IS VALID
            String loginValid = "user";

            if (loginValid.equals("user")) {
                //TODO: Fetch first computer for given username
                int computerID = 1;

                //Create menu object and go to user menu
                UserMenu userMenu = new UserMenu(inputUsername, computerID);
                userMenu.openUserMenu();
                exitLoop = true;
            }
            else if (loginValid.equals("admin")){
                AdminMenu adminMenu = new AdminMenu();
                adminMenu.openAdminMenu();
                exitLoop = true;
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
    private static void openRegisterOption() {
        boolean exitLoop = false;

        while (!exitLoop) {
            String inputUsername = UserMenu.getUserInput("Please enter a Username: ");
            //TODO: Check if Username is taken or input is empty


            String inputPassword = UserMenu.getUserInput("Please enter a Password: ");
            //TODO: Check if password is empty


            System.out.println();
            //TODO: Add new user to system

            String inputComputerName = UserMenu.getUserInput("Please enter a Name for your first Computer: ");
            //TODO: Add Computer to System Managed by new user

            exitLoop = true;
        }
    }
}
