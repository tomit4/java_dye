import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HEXConverter {
    private static HEXObj hexObj;

    public static void setHexValues(Scanner input) {
        String rVal, gVal, bVal;

        while (true) {
            try {
                System.out.print("Enter A Hexadecimal Color Code: ");
                String hex = input.nextLine();

                if (hex.startsWith("#")) {
                    hex = hex.substring(1);
                }

                if (hex.length() != 6) {
                    System.out.println("Invalid HEX color code. Must be 6 characters long.");
                    continue;
                }

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

                break;
            } catch (Exception e) {
                System.out.println("Error: HEX values must be 0-9 or A-F.");
                continue;
            }
        }

        hexObj = new HEXObj(rVal, gVal, bVal);
    }

    private static boolean isValidHEXValue(String hexValue) {
        String hexRegEx = "^[0-9A-Fa-f]{2}$";
        Pattern pattern = Pattern.compile(hexRegEx);
        Matcher matcher = pattern.matcher(hexValue);
        return matcher.matches();
    }

    public static String convertHex(String colorCodeConvertedTo, Scanner input) {
        setHexValues(input);

        String convertedColor = "";

        if (colorCodeConvertedTo.equals("rgb")) {
            convertedColor = convertToRgb();
        } else {
            convertedColor = convertToHsl();
        }

        return convertedColor;
    }

    public static String convertToRgb() {
        String rHex = hexObj.getRByte();
        String gHex = hexObj.getGByte();
        String bHex = hexObj.getBByte();

        int r = Integer.parseInt(rHex, 16);
        int g = Integer.parseInt(gHex, 16);
        int b = Integer.parseInt(bHex, 16);

        return String.format("RGB(%d, %d, %d)", r, g, b);
    }

    public static String convertToHsl() {
        /*
         * Uses Mathematical formulas from
         * https://en.wikipedia.org/wiki/HSL_and_HSV#From_RGB
         * Notes reflects Latex from that article
         */
        String rgb = convertToRgb();
        String[] rgbComponents = rgb.split("[(),]");

        float r = Integer.parseInt(rgbComponents[1].trim()) / 255f;
        float g = Integer.parseInt(rgbComponents[2].trim()) / 255f;
        float b = Integer.parseInt(rgbComponents[3].trim()) / 255f;

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
