import java.util.Scanner;
import java.util.Set;

class Dye {
    private static final Set<String> ACCEPTABLE_COLOR_CODES = Set.of("hex", "rgb", "hsl");

    public static void main(String[] args) {
        HelpPrinter.printHelp();
        Scanner input = new Scanner(System.in);
        while (true) {
            System.out.print("Enter a color model (rgb, hex, hsl): ");
            String colorCodeToConvert = input.nextLine();

            if (colorCodeToConvert.equals("q") || colorCodeToConvert.equals("quit")) {
                break;
            }

            if (colorCodeToConvert.equals("h") || colorCodeToConvert.equals("help")) {
                HelpPrinter.printHelp();
                continue;
            }

            System.out.print("Enter a color model to convert to (rgb, hex, hsl): ");
            String colorCodeConvertedTo = input.nextLine();

            if (colorCodeConvertedTo.equals("q") || colorCodeConvertedTo.equals("quit")) {
                break;
            }

            if (colorCodeConvertedTo.equals("h") || colorCodeConvertedTo.equals("help")) {
                HelpPrinter.printHelp();
                continue;
            }

            if (!ACCEPTABLE_COLOR_CODES.contains(colorCodeConvertedTo)) {
                System.out.println("Not a recognized color code, please use rgb, hex, or hsl");
                HelpPrinter.printHelp();
                continue;
            }

            if (colorCodeToConvert.equals(colorCodeConvertedTo)) {
                System.out.println("Given Color Codeds are the same, no conversion needed");
                HelpPrinter.printHelp();
                continue;
            }

            ArgumentParser.parse(colorCodeToConvert, colorCodeConvertedTo, input);
        }
        System.out.println("Goodbye!");
        input.close();
        System.exit(0);
    }
}
