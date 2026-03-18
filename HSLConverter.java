import java.util.Scanner;
import java.util.InputMismatchException;

/*
 * HSLConverter.java
 *
 * Handles the conversions between HSL, RGB, and HEX color models.
 * Guides the user to input valid HSL values, validates them,
 * and performs the necessary conversions.
 */

public class HSLConverter {
    // Stores the user's HSL input values as an object
    private static HSLObj hslObj;

    /*
     * Prompts the user to enter HSL values for Hue, Saturation, and Lightness.
     * Ensures that input is valid (integers only) and within proper ranges:
     * Hue: 0-360
     * Saturation: 0-100
     * Lightness: 0-100
     *
     * @param input Scanner object for reading input from the console
     */
    public static void setHslValues(Scanner input) {
        int hValue, sValue, lValue;

        while (true) {
            try {
                // Prompt for Hue component
                System.out.print("Enter H value (0 to 360): ");
                hValue = input.nextInt();
                if (hValue < 0 || hValue > 360) {
                    System.out.println("Error: Hue must be 0 - 360");
                    continue;
                }

                // Prompt for Saturation component
                System.out.print("Enter S value (0 to 100): ");
                sValue = input.nextInt();
                if (sValue < 0 || sValue > 100) {
                    System.out.println("Saturation must be 0 - 100");
                    continue;
                }

                // Prompt for Lightness component
                System.out.print("Enter L value (0 to 100): ");
                lValue = input.nextInt();
                if (lValue < 0 || lValue > 100) {
                    System.out.println("Lightness must be 0 - 100");
                    continue;
                }

                // Consume leftover newline
                input.nextLine();
                break; // All values are valid, exit loop
            } catch (InputMismatchException e) {
                // Handle non-integer input gracefully
                System.out.println("Invalid input! Please enter an integer value");
                input.nextLine();
            }
        }

        // Store validated values in the HSLObj object
        hslObj = new HSLObj(hValue, sValue, lValue);
    }

    /*
     * Converts the stored HSL values to the requested target model (RGB or HEX).
     *
     * @param colorCodeConvertedTo Target color model ("rgb" or "hex")
     *
     * @param input Scanner object for user input
     *
     * @return Converted color as a string
     */
    public static String convertHSL(String colorCodeConvertedTo, Scanner input) {
        // Prompts the user to enter HSL values
        setHslValues(input);

        String convertedColor = "";

        // Decide which conversion method to use
        if (colorCodeConvertedTo.equals("hex")) {
            convertedColor = convertToHex();
        } else { // must be "rgb"
            convertedColor = convertToRgb();
        }

        return convertedColor;
    }

    /*
     * Converts stored HSL values to HEX.
     * HSL is first converted to RGB, then RGB is converted to HEX.
     *
     * @return HEX color string in format "#RRGGBB"
     */
    public static String convertToHex() {
        // Convert HSL to RGB first
        String rgb = convertToRgb();

        // Extract individual RGB components from the string
        int r = Integer.parseInt(rgb.split(",")[0].split("\\(")[1].trim());
        int g = Integer.parseInt(rgb.split(",")[1].trim());
        int b = Integer.parseInt(rgb.split(",")[2].split("\\)")[0].trim());

        // Format as HEX string
        return String.format("#%02X%02X%02X", r, g, b);
    }

    /*
     * Converts the stored HSL values to RGB
     * (Hue, Saturation, Lightness -> Red, Green, Blue)
     *
     * @return RGB color string in format "RGB(R, G, B)"
     */
    public static String convertToRgb() {
        /*
         * Uses Mathematical formulas from
         * https://en.wikipedia.org/wiki/HSL_and_HSV#HSL_to_RGB
         * Notes reflects Latex from that article
         * See Conversions.md for Latex formulas
         */

        int h = hslObj.getHValue();
        int s = hslObj.getSValue();
        int l = hslObj.getLValue();

        float S = s / 100f; // S_L in [0, 1]
        float L = l / 100f; // L in [0, 1]

        float C = (1 - Math.abs(2 * L - 1)) * S; // C = (1 -|2L - 1|) * S_L

        float HPrime = h / 60f; // H' = H/60°
        float X = C * (1 - Math.abs((HPrime % 2) - 1)); // X = C * (1 - |H' mod 2 - 1|)

        float R1 = 0, G1 = 0, B1 = 0;

        // Piecewise Function for HSL -> RGB conversion
        if (HPrime >= 0 & HPrime < 1) {
            R1 = C;
            G1 = X;
            B1 = 0;
        } else if (HPrime >= 1 && HPrime < 2) {
            R1 = X;
            G1 = C;
            B1 = 0;
        } else if (HPrime >= 2 && HPrime < 3) {
            R1 = 0;
            G1 = C;
            B1 = X;
        } else if (HPrime >= 3 && HPrime < 4) {
            R1 = 0;
            G1 = X;
            B1 = C;
        } else if (HPrime >= 4 && HPrime < 5) {
            R1 = X;
            G1 = 0;
            B1 = C;
        } else if (HPrime >= 5 && HPrime < 6) {
            R1 = C;
            G1 = 0;
            B1 = X;
        }

        float m = L - (C / 2); // m = L - (C - 2), exact same as wiki

        // NOTE: same as wiki, but multiplied by 255 to keep in expected range
        int r = (int) ((R1 + m) * 255);
        int g = (int) ((G1 + m) * 255);
        int b = (int) ((B1 + m) * 255);

        return String.format("RGB(%d, %d, %d)", r, g, b);
    }
}

/*
 * Simple class to store HSL values together
 */
class HSLObj {
    private int hValue;
    private int sValue;
    private int lValue;

    public HSLObj(int hVal, int sVal, int lVal) {
        this.hValue = hVal;
        this.sValue = sVal;
        this.lValue = lVal;
    }

    public int getHValue() {
        return hValue;
    }

    public int getSValue() {
        return sValue;
    }

    public int getLValue() {
        return lValue;
    }
}
