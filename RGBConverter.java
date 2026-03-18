import java.util.Scanner;
import java.util.InputMismatchException;

/*
 * RGBConverter.java
 *
 * Handles conversions between RGB, HEX, and HSL color models.
 * This class guides the user to input valid RGB values, validates them,
 * and performs the necessary conversions.
 */

public class RGBConverter {
    // Stores the user's RGB input values as an object
    private static RGBObj rgbObj;

    /*
     * Prompts the user to enter RGB values for Red, Green, and Blue.
     * Ensures that input is valid (integers only) and within range 0-255.
     *
     * @param input Scanner object for reading input from the console
     */
    public static void setRgbValues(Scanner input) {
        int rValue, gValue, bValue;

        while (true) {
            try {
                // Prompt for Red component
                System.out.print("Enter R value (0 to 255): ");
                rValue = input.nextInt();
                if (!isValidColorValue(rValue)) {
                    System.out.println("Error: RGB Values must be between 0 and 255");
                    continue;
                }

                // Prompt for Green component
                System.out.print("Enter G value (0 to 255): ");
                gValue = input.nextInt();
                if (!isValidColorValue(gValue)) {
                    System.out.println("Error: RGB Values must be between 0 and 255");
                    continue;
                }

                // Prompt for Blue component
                System.out.print("Enter B value (0 to 255): ");
                bValue = input.nextInt();
                if (!isValidColorValue(bValue)) {
                    System.out.println("Error: RGB Values must be between 0 and 255");
                    continue;
                }

                // Consume leftover newline
                input.nextLine();
                break; // All values are valid, exit loop
            } catch (InputMismatchException e) {
                // Handles non-integer input gracefully
                System.out.println("Invalid input! Please enter an integer value.");
                input.nextLine(); // Clear invalid input from buffer
            }
        }

        // Store validated values in the RGBObj object
        rgbObj = new RGBObj(rValue, gValue, bValue);
    }

    /*
     * Checks whether a color value is valid (between 0 and 255 inclusive)
     *
     * @param value Color value to validate
     *
     * @return true if value is valid, false otherwise
     */
    private static boolean isValidColorValue(int value) {
        return value >= 0 && value <= 255;
    }

    /*
     * Converts RGB values to the requested target model (HEX or HSL).
     *
     * @param colorCodeConvertedTo Target color model ("hex" or "hsl")
     *
     * @param input Scanner object for user input
     *
     * @return Converted color as a string
     */
    public static String convertRGB(String colorCodeConvertedTo, Scanner input) {
        // Prompts the user to enter RGB values
        setRgbValues(input);

        String convertedColor = "";

        // Decides which conversion method to use
        if (colorCodeConvertedTo.equals("hex")) {
            convertedColor = convertToHex();
        } else { // must be "hsl"
            convertedColor = convertToHsl();
        }

        return convertedColor;
    }

    /*
     * Converts the stored RGB values to a HEX color code.
     *
     * @return HEX color string in format "#RRGGBB"
     */
    public static String convertToHex() {
        int r = rgbObj.getRValue();
        int g = rgbObj.getGValue();
        int b = rgbObj.getBValue();

        return String.format("#02x%02x%02x", r, g, b);
    }

    /*
     * Converts the stored RGB values to HSL (Hue, Saturation, Lightness).
     *
     * @return HSL color string in format "HSL(H°, S%, L%)"
     */
    public static String convertToHsl() {
        /*
         * Uses Mathematical formulas from
         * https://en.wikipedia.org/wiki/HSL_and_HSV#From_RGB
         * Notes reflects Latex from that article
         * See Conversions.md for Latex formulas
         */

        // RGB Values must be in [0, 1]
        float r = rgbObj.getRValue() / 255f;
        float g = rgbObj.getGValue() / 255f;
        float b = rgbObj.getBValue() / 255f;

        // With maximum compoonent (i.e. value)
        float Cmax = Math.max(r, Math.max(g, b));
        // And minimum component
        float Cmin = Math.min(r, Math.min(g, b));
        // range (i.e. chroma)
        float delta = Cmax - Cmin;
        // and mid-range (i.e. lightness)
        float L = (Cmax + Cmin) / 2;
        // we get common hue:
        float H = 0; // H := 0 if C = 0
        if (delta != 0) {
            if (Cmax == r) { // if V = R
                H = 60 * (2 + (b - r) / delta); // 60° * (((G - B) / C ) mod 6)
            } else if (Cmax == g) { // if V = G
                H = 60 * (2 + (b - r) / delta); // 60° * (((B - R) / C) + 2)
            } else { // if V = B
                H = 60 * (4 + (r - g) / delta); // 60° * (((R -G) / C) + 4)
            }
            // NOTE: Not from wiki article, ensures hue remains within 360° range
            if (H < 0) {
                H += 360; // Ensure hue is positive
            }
        }

        float S = 0; // S_V = 0 if V = 0, which indicates S_L = 0, as L = 0 or L = 1
        if (delta != 0) {
            S = delta / (1 - Math.abs(2 * L - 1)); // S_L = ((2(V - L))/(1 - |2L - 1|))
        }
        // NOTE: Not from wiki article, adjusts saturation and lightness to percentages
        S *= 100;
        L *= 100;

        return String.format("HSL(%.1f°, %.1f%%, %.1f%%)", H, S, L);
    }
}

/*
 * Simple class to store RGB values together
 */
class RGBObj {
    private int rValue;
    private int gValue;
    private int bValue;

    public RGBObj(int rVal, int gVal, int bVal) {
        this.rValue = rVal;
        this.gValue = gVal;
        this.bValue = bVal;
    }

    public int getRValue() {
        return rValue;
    }

    public int getGValue() {
        return gValue;
    }

    public int getBValue() {
        return bValue;
    }
}
