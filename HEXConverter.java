import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * HEXConverter.java
 *
 * Handles conversions between HEX, RGB, and HSL color models.
 * Guides the user to input valid HEX color codes, validates them,
 * and performs the necessary conversions.
 */

public class HEXConverter {
    // Stores the user's HEX input values as an object
    private static HEXObj hexObj;

    /*
     * Prompts the user to enter a HEX color code.
     * Validates that the code is 6 hexadecimal digits (0-9, A-F).
     *
     * @param input Scanner object for reading input from the console
     */
    public static void setHexValues(Scanner input) {
        String rVal, gVal, bVal;

        while (true) {
            try {
                System.out.print("Enter A Hexadecimal Color Code: ");
                String hex = input.nextLine();

                // Remove leading '#' if present
                if (hex.startsWith("#")) {
                    hex = hex.substring(1);
                }

                // Ensure the HEX code is exactly 6 characters long
                if (hex.length() != 6) {
                    System.out.println("Invalid HEX color code. Must be 6 characters long.");
                    continue;
                }

                // Extract and validate each component
                rVal = hex.substring(0, 2);
                if (!isValidHEXValue(rVal)) {
                    System.out.println("Error: Invalid HEX color values for R value: " + rVal);
                    System.out.println("Must be hexadecimal digits (0-9, A-F).");
                    continue;
                }

                gVal = hex.substring(2, 4);
                if (!isValidHEXValue(gVal)) {
                    System.out.println("Error: Invalid HEX color values for G value: " + gVal);
                    System.out.println("Must be hexadecimal digits (0-9, A-F).");
                    continue;
                }

                bVal = hex.substring(4, 6);
                if (!isValidHEXValue(bVal)) {
                    System.out.println("Error: Invalid HEX color values for B value: " + bVal);
                    System.out.println("Must be hexadecimal digits (0-9, A-F).");
                    continue;
                }

                break; // All values valid, exit loop
            } catch (Exception e) {
                // Catch any unexpected exceptions (e.g., null input)
                System.out.println("Error: HEX values must be 0-9 or A-F.");
                continue;
            }
        }

        // Store validated values in the HEXObj object
        hexObj = new HEXObj(rVal, gVal, bVal);
    }

    /*
     * Validates a two-character HEX value (00-FF)
     *
     * @param hexValue String to validate
     *
     * @return true if valid, false otherwise
     */
    private static boolean isValidHEXValue(String hexValue) {
        String hexRegEx = "^[0-9A-Fa-f]{2}$";
        Pattern pattern = Pattern.compile(hexRegEx);
        Matcher matcher = pattern.matcher(hexValue);
        return matcher.matches();
    }

    /*
     * Converts the stored HEX values to the requested target model (RGB or HSL).
     *
     * @param colorCodeConvertedTo Target color model ("rgb" or "hsl")
     *
     * @param input Scanner object for user input (needed for future extensions)
     *
     * @return Converted color as a string
     */
    public static String convertHex(String colorCodeConvertedTo, Scanner input) {
        // Prompts the user to enter HEX values
        setHexValues(input);

        String convertedColor = "";

        if (colorCodeConvertedTo.equals("rgb")) {
            convertedColor = convertToRgb();
        } else { // must be "hsl"
            convertedColor = convertToHsl();
        }

        return convertedColor;
    }

    /*
     * Converts the stored HEX values to RGB.
     *
     * @return RGB color string in format "RGB(R, G, B)"
     */
    public static String convertToRgb() {
        String rHex = hexObj.getRByte();
        String gHex = hexObj.getGByte();
        String bHex = hexObj.getBByte();

        int r = Integer.parseInt(rHex, 16);
        int g = Integer.parseInt(gHex, 16);
        int b = Integer.parseInt(bHex, 16);

        return String.format("RGB(%d, %d, %d)", r, g, b);
    }

    /*
     * Converts the stored HEX values to HSL.
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
        String rgb = convertToRgb();
        String[] rgbComponents = rgb.split("[(),]");

        float r = Integer.parseInt(rgbComponents[1].trim()) / 255f;
        float g = Integer.parseInt(rgbComponents[2].trim()) / 255f;
        float b = Integer.parseInt(rgbComponents[3].trim()) / 255f;

        // With maximum component (i.e. value)
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
                H += 360;
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
 * Simple class to store HEX values for R, G, and B components
 */
class HEXObj {
    private String rByte;
    private String gByte;
    private String bByte;

    public HEXObj(String rVal, String gVal, String bVal) {
        this.rByte = rVal;
        this.gByte = gVal;
        this.bByte = bVal;
    }

    public String getRByte() {
        return rByte;
    }

    public String getGByte() {
        return gByte;
    }

    public String getBByte() {
        return bByte;
    }
}
