import java.util.Scanner;

public class UserMenu {
    private String username;
    private int computerID;

    public UserMenu (String username, int computerID) {
        this.username = username;
        this.computerID = computerID;
    }

    //Main user options menu (computer, parts, account, quit)
    public void openUserMenu(DatabaseConfig dbConfig) {
        System.out.println("==User Actions==");

        boolean exitLoop = false;

        while (!exitLoop) {
            System.out.println("Please Select one of the following options:");
            System.out.println("\tManage Computers [C] || Search Parts [P] || Edit Account [A] || Logout [Q]");

            String input = UserMenu.getUserInput("\n> ");
            System.out.println();

            switch (input.toLowerCase()) {
                case "c":
                    ComputerMenu menu = new ComputerMenu();
                    menu.openComputerMenu(this, dbConfig);
                    break;
                case "p":
                    SearchMenu menu2 = new SearchMenu();
                    menu2.openSearchMenu(this, dbConfig);
                    break;
                case "a":
                    this.openAccountOption(dbConfig);
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
    private void openAccountOption(DatabaseConfig dbConfig) {
        System.out.println("==User Account==");

        boolean exitLoop = false;
        UserInteraction userInteraction = new UserInteraction(dbConfig);

        while (!exitLoop) {
            System.out.printf("Username: %s\n", this.username);

            String accPassword = userInteraction.getUserDetail(this.username, "Password");
            System.out.printf("Password: %s\n", "*".repeat(accPassword.length()));

            System.out.println("Please Select one of the following options:");
            System.out.println("\tEdit Username [U] || Edit Password [P] || Return to Menu [Q]");

            String input = UserMenu.getUserInput("\n> ");
            System.out.println();

            switch (input.toLowerCase()) {
                case "u":
                    this.username = changeUsername(dbConfig, this.username);
                    break;
                case "p":
                    changePassword(dbConfig, this.username);
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
    //Returns username that will be used henceforth
    private static String changeUsername (DatabaseConfig dbConfig, String origUsername) {
        UserInteraction userInteraction = new UserInteraction(dbConfig);

        String input = UserMenu.getUserInput("\nEnter a new Username: ");
        while (input.length() == 0) {
            input = UserMenu.getUserInput("");
        }


        //TODO: Check that username is not already taken
        //TODO: Change Username in DB
        //TODO: Give Proper failure/Success output
        userInteraction.changeUsername(origUsername, input);
        return input;
    }

    //TODO: DB Compatible
    private static void changePassword (DatabaseConfig dbConfig, String username) {
        UserInteraction userInteraction = new UserInteraction(dbConfig);

        String oldPassword = UserMenu.getUserInput("\nEnter your old/current Password: ");
        while (oldPassword.length() == 0) {
            oldPassword = UserMenu.getUserInput("");
        }

        String input = UserMenu.getUserInput("\nEnter a new Password: ");
        while (input.length() == 0) {
            input = UserMenu.getUserInput("");
        }

        //TODO: Change Password in DB
        //TODO: Give Proper failure/Success output
        userInteraction.changePassword(username, oldPassword, input);
    }

    public String getUsername() {
        return username;
    }

    public int getComputerID() {
        return computerID;
    }

    public void setComputerID(int computerID) {
        this.computerID = computerID;
    }

    public static String getUserInput(String prompt) {
        Scanner scan = new Scanner(System.in);

        System.out.print(prompt);
        String input = scan.nextLine();

        return input;
    }
}
