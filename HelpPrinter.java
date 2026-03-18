/*
 * HelpPrinter.java
 *
 * Provides a simple method to display the help instructions for the Dye program.
 * Explains usage, input formats, and how to quit or request help.
 */
public class HelpPrinter {
    /*
     * Prints the full help message to the console.
     * Includes:
     * - Description of the program
     * - Example usage
     * - Commands to view help again
     * - Commands to quit
     */
    public static void printHelp() {
        // Decorative header
        System.out.println("===============================================");
        System.out.println("Dye: A Color Conversion Tool for");
        System.out.print("Hexadecimal, RGB, and HSL color conversions.\n");
        System.out.println("===============================================");

        // Example usage section
        System.out.println("Example Usage:");
        System.out.println("Convert Hexadecimal to RGB:");
        System.out.print("Enter a color model (rgb, hex, hsl): hex");
        System.out.print("Enter a color model to convert to (rgb, hex, hsl): rgb");
        System.out.println("===============================================");

        // How to view help again
        System.out.println("To see this help message again, at any point type:");
        System.out.println("h");
        System.out.println("help");
        System.out.println("===============================================");

        // How to quit the program
        System.out.println("To quit, at any point type:");
        System.out.println("q");
        System.out.println("quit");
    }
}
