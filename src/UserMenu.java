import java.util.Scanner;

public class UserMenu {
    private String username;
    private int computerID;

    public UserMenu (String username, int computerID) {
        this.username = username;
        this.computerID = computerID;
    }

    //Main user options menu (computer, parts, account, quit)
    public void openUserMenu() {
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
                    menu.openComputerMenu(this);
                    break;
                case "p":
                    SearchMenu menu2 = new SearchMenu();
                    menu2.openSearchMenu(this);
                    break;
                case "a":
                    this.openAccountOption();
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
    private void openAccountOption() {
        System.out.println("==User Account==");

        boolean exitLoop = false;

        while (!exitLoop) {
            System.out.printf("Username: %s\n", this.username);

            //TODO: Fetch and print password from DB
            String accPassword = "<PASSWORD>";
            System.out.printf("Username: %s\n", accPassword);


            System.out.println("Please Select one of the following options:");
            System.out.println("\tEdit Username [U] || Edit Password [P] || Return to Menu [Q]");

            String input = UserMenu.getUserInput("\n> ");
            System.out.println();

            switch (input.toLowerCase()) {
                case "u":
                    this.username = changeUsername(this.username);
                    break;
                case "p":
                    changePassword(this.username);
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
    private static String changeUsername (String origUsername) {
        String input = UserMenu.getUserInput("\nEnter a new Username: ");
        while (input.length() == 0) {
            input = UserMenu.getUserInput("");
        }

        //TODO: Check that username is not already taken
        //TODO: Change Username in DB
        //TODO: Give Proper failure/Success output
        return input;
    }

    //TODO: DB Compatible
    private static void changePassword (String username) {
        String input = UserMenu.getUserInput("\nEnter a new Password: ");
        while (input.length() == 0) {
            input = UserMenu.getUserInput("");
        }

        //TODO: Change Password in DB
        //TODO: Give Proper failure/Success output
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
