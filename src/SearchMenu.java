import java.util.ArrayList;

public class SearchMenu {

    public void openSearchMenu(UserMenu menuInfo) {
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
                isMotherboard = executeSearch(partIds);
            }
            catch (Exception ex) {
                System.out.println("Error: Invalid Search Arguments Entered!");
                partIds = new ArrayList<>();
            }

            while(!exitLoop2) {

                System.out.println("\nPlease Select one of the following options:");
                System.out.println("\tAdd a Part to Computer [A] || Search Filters [S] || Return to Menu [Q]");

                String input = UserMenu.getUserInput("\n> ");

                switch (input.toLowerCase()) {
                    case "a":
                        addItemToComputer(partIds, isMotherboard);
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
    private static boolean executeSearch (ArrayList<Integer> partIDs) {
        boolean canPass = false;
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
                //Get Memory Slots as input
                System.out.println("\nEnter the number of Memory Slots on the Motherboard.");
                String inputMemSlots = UserMenu.getUserInput("> ");

                //TODO: Execute Select Query Here

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


                //TODO: Execute Select Query Here


                break;
            case "s":
                //Get max capacity as input
                System.out.println("\nEnter the maximum capacity of the Storage.");
                String inputMaxCapacity = UserMenu.getUserInput("> ");

                //Get min capacity as input
                System.out.println("\nEnter the minimum capacity of the Storage.");
                String inputMinCapacity = UserMenu.getUserInput("> ");

                //Get storage type as input
                System.out.println("\nEnter the type of Storage.");
                String inputStorageType = UserMenu.getUserInput("> ");

                //Get    input
                System.out.println("\nEnter the Interface for the Storage.");
                String inputInterface = UserMenu.getUserInput("> ");

                //Get    input
                System.out.println("\nEnter the Form Factor for the Storage.");
                String inputFormFactor = UserMenu.getUserInput("> ");

                //TODO: Execute Select Query Here

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


                //TODO: Execute Select query Here

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

                //TODO: Execute Select Query Here

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

                //TODO Execute Query Here

                break;
        }

        return true;
    }

    private static void addItemToComputer(ArrayList<Integer> partIDs, boolean isMotherboard) {
        System.out.println("Please Enter the NumberId of the item you wish to add: ");
        int inputNum = -1;

        while(inputNum < 1 || inputNum > partIDs.size()) {
            String temp = UserMenu.getUserInput("");
            inputNum = Integer.parseInt(temp);
        }

        System.out.println("Please Enter the quantity of the item you wish to add: ");
        String temp = UserMenu.getUserInput("");
        int inputCount = Integer.parseInt(temp);


        //TODO: Add part to system

    }
}
