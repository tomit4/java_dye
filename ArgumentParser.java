import java.util.Scanner;

/*
 * ArgumentParser.java
 *
 * Handles the logic of delegating the conversion of one color model
 * to another. This class acts as a central hub that calls the
 * appropriate class based on user input.
 */

public class ArgumentParser {
    /*
     * Parses the source and target color models and delegates
     * the conversion to the corresponding converter class.
     *
     * @param colorCodeToConvert The color model the user wants to convert from
     * (hex, rgb, hsl)
     *
     * @param colorCodeConvertedTo The color model the user wants to convert to
     * (hex, rgb, hsl)
     *
     * @param input Scanner object for reading further user input as needed
     */
    public static void parse(String colorCodeToConvert, String colorCodeConvertedTo, Scanner input) {
        String convertedColor = "";

        // Determine which conversion method to call based on source color model
        switch (colorCodeToConvert) {
            case "hex":
                // Call HEXConverter to handle conversion from HEX to the target model
                convertedColor = HEXConverter.convertHex(colorCodeConvertedTo, input);
                break;
            case "rgb":
                // Call RGBConverter to handle conversion from RGB to the target model
                convertedColor = RGBConverter.convertRGB(colorCodeConvertedTo, input);
                break;
            case "hsl":
                // Call HSLConverter to handle conversion from HSL to the target model
                convertedColor = HSLConverter.convertHSL(colorCodeConvertedTo, input);
                break;
            default:
                // If somehow an invalid source model was passed, print help instructions
                HelpPrinter.printHelp();
        }
        // Display the final converted color to the user
        System.out.println("Converted Color: " + convertedColor);
    }
}
