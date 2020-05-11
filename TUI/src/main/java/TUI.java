
import java.util.Scanner;

/**
 * @author Rasmus Sander Larsen
 */
public class TUI {
    //-------------------------- Fields --------------------------

    private final String tuiHeader =
            "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n" +
            "~~~~~~~~~~~~~~~~ DEEPFLIGHT - GAMEAPI ~~~~~~~~~~~~~~~~\n" +
            "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n";

    private final String menuHeader =
            "~~~~~~~~~~~~~~~~~~~~~~~~~ MENU ~~~~~~~~~~~~~~~~~~~~~~~\n";


    public static boolean testing = true;
    private boolean running = true;

    private TuiFunctions functions;

    // ----------------------- Constructor -------------------------

    public TUI () {
        functions = new TuiFunctions();
    }

    // ------------------------ Properties -------------------------

    // region Properties


    // endregion

    //---------------------- Public Methods -----------------------

    public void startTUI() {
        printHeader();

        while (running) {
            printMenu();
            int menuIndexSelected = readMenuInput();
            runFunction(menuIndexSelected);
        }
    }


    //---------------------- Support Methods ----------------------

    private void printHeader () {
        System.out.println(tuiHeader);
    }

    private void printMenu () {
        StringBuilder menuBuilder = new StringBuilder();
        menuBuilder.append(menuHeader);
        menuBuilder.append("\t\t1)\t\t Find Track by ID\n");
        menuBuilder.append("\t\t2)\t\t Find Planet by ID\n");
        menuBuilder.append("\t\t3)\t\t Find ALL Planets\n");
        menuBuilder.append("\t\t4)\t\t Find Current Round\n");
        menuBuilder.append("\t\t5)\t\t Find Previous Round\n");
        menuBuilder.append("\t\t6)\t\t Find ALL Rounds\n");
        menuBuilder.append("\n");
        menuBuilder.append("\t\t666)\t Exit\n");

        menuBuilder.append("Enter the function you wish to use:");

        System.out.println(menuBuilder.toString());

    }

    private int readMenuInput () {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        int menuIndex;

        try {
            menuIndex = Integer.parseInt(input);
        } catch (Exception e) {
            System.out.println("The entered input is not valid. Try again.");
            menuIndex = readMenuInput();
        }
        return menuIndex;
    }

    private void runFunction(int selectedMenuIndex) {
        switch (selectedMenuIndex) {
            case 1:
                functions.trackFromId();
                break;

            case 2:
                functions.planetFromId();
                break;

            case 3:
                functions.planetsALL();
                break;

            case 4:
                functions.roundCurrent();
                break;

            case 5:
                functions.roundPrevious();
                break;

            case 6:
                functions.roundAll();
                break;


            case 666:
                running = false;
                System.out.println("Bye bye!");
                break;

            default:
                System.out.println("There is no function for the entered value: " + selectedMenuIndex);
                break;
        }
    }

}
