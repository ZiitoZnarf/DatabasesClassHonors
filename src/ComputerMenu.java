public class ComputerMenu {

    public void openComputerMenu(UserMenu menuInfo) {
        //display current computer in diff method
        //loop through selection options
        System.out.println("==Computer Actions==");

        boolean exitLoop = false;

        while (!exitLoop) {
            displayComputerInformation(menuInfo.getComputerID());

            System.out.println("Please Select one of the following options:");
            System.out.println("\tChange Computer Name [N] || Edit Computer Parts [P] || Switch Active Computer [S]" +
                               "\t   Delete a Computer [D] ||      Return to Menu [Q] || ");

            String input = UserMenu.getUserInput("\n> ");
            System.out.println();

            switch (input.toLowerCase()) {
                case "n":
                    changeComputerName(menuInfo.getComputerID());
                    break;
                case "p":
                    editComputerParts(menuInfo.getComputerID());
                    break;
                case "s":
                    //Attempts to change to a different computer, and returns that value for application
                    menuInfo.setComputerID(switchActiveComputer(menuInfo.getUsername()));
                    break;
                case "d":
                    deleteComputer(menuInfo.getUsername());
                    break;
                case "q":
                    exitLoop = true;
                    break;
                default:
                    System.out.println("Error: System Input not recognized!\n");
            }
        }
    }

    //TODO: DB Compatible (Get computer info from DB)
    private static void displayComputerInformation(int computerID) {
        System.out.println("IMPLEMENT COMPUTER INFO IN DB");
    }

    private static void changeComputerName(int computerID) {
        //TODO
    }

    private static void editComputerParts(int computerID) {
        //TODO
    }

    //Returns new active computerID
    private static int switchActiveComputer(String username) {
        //TODO
        return -1;
    }

    private static void deleteComputer(String username) {
        //TODO
    }
}
