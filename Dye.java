import java.util.Scanner;
import java.util.Set;

/*
 * Dye.java
 *
 * Main program entry point for the Dye color conversion tool.
 * Allows users to convert between RGB, HEX, and HSL models.
 */

class Dye {
    // A set of acceptable color model strings
    private static final Set<String> ACCEPTABLE_COLOR_CODES = Set.of("hex", "rgb", "hsl");

    public static void main(String[] args) {
        // Print initial help instructions for user
        HelpPrinter.printHelp();
        // Scanner object to read user input from console
        Scanner input = new Scanner(System.in);

        // Infinite loop to continually prompt user for conversions
        while (true) {
            // Prompt for the source color model
            System.out.print("Enter a color model (rgb, hex, hsl): ");
            String colorCodeToConvert = input.nextLine();

            // Check for quit commands
            if (colorCodeToConvert.equals("q") || colorCodeToConvert.equals("quit")) {
                break;
            }

            // Check for help commands
            if (colorCodeToConvert.equals("h") || colorCodeToConvert.equals("help")) {
                HelpPrinter.printHelp();
                continue;
            }

            // Prompt for the target color model
            System.out.print("Enter a color model to convert to (rgb, hex, hsl): ");
            String colorCodeConvertedTo = input.nextLine();

            // Check for quit commands
            if (colorCodeConvertedTo.equals("q") || colorCodeConvertedTo.equals("quit")) {
                break;
            }

            // Check for help commands
            if (colorCodeConvertedTo.equals("h") || colorCodeConvertedTo.equals("help")) {
                HelpPrinter.printHelp();
                continue;
            }

            // Validate that the target color model is one of the accepted options
            if (!ACCEPTABLE_COLOR_CODES.contains(colorCodeConvertedTo)) {
                System.out.println("Not a recognized color code, please use rgb, hex, or hsl");
                HelpPrinter.printHelp();
                continue;
            }

            // Check if the source and target color models are the same
            if (colorCodeToConvert.equals(colorCodeConvertedTo)) {
                System.out.println("Given Color Codeds are the same, no conversion needed");
                HelpPrinter.printHelp();
                continue;
            }

            // Delegate conversion logic to the ArgumentParser class
            ArgumentParser.parse(colorCodeToConvert, colorCodeConvertedTo, input);
        }

        // Print exit message and close resources
        System.out.println("Goodbye!");
        input.close();
        System.exit(0);
    }
}
