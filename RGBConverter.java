import java.util.Scanner;

public class RGBConverter {
    private static RGBObj rgbObj;

    public static void setRgbValues(Scanner input) {
        int rValue, gValue, bValue;
        while (true) {
            System.out.print("Enter R value (0 to 255): ");
            rValue = input.nextInt();
            if (!isValidColorValue(rValue)) {
                System.out.println("Error: RGB Values must be between 0 and 255");
                continue;
            }

            System.out.print("Enter G value (0 to 255): ");
            gValue = input.nextInt();
            if (!isValidColorValue(gValue)) {
                System.out.println("Error: RGB Values must be between 0 and 255");
                continue;
            }

            System.out.print("Enter B value (0 to 255): ");
            bValue = input.nextInt();
            if (!isValidColorValue(bValue)) {
                System.out.println("Error: RGB Values must be between 0 and 255");
                continue;
            }

            input.nextLine();
            break;
        }

        rgbObj = new RGBObj(rValue, gValue, bValue);
    }

    private static boolean isValidColorValue(int value) {
        return value >= 0 && value <= 255;
    }

    public static String convertRGB(String colorCodeConvertedTo, Scanner input) {
        setRgbValues(input);

        String convertedColor = "";

        if (colorCodeConvertedTo.equals("hex")) {
            convertedColor = convertToHex();
        } else {
            convertedColor = convertToHsl();
        }

        return convertedColor;
    }

    public static String convertToHex() {
        int r = rgbObj.getRValue();
        int g = rgbObj.getGValue();
        int b = rgbObj.getBValue();

        return String.format("#02x%02x%02x", r, g, b);
    }

    public static String convertToHsl() {
        /*
         * Uses Mathematical formulas from
         * https://en.wikipedia.org/wiki/HSL_and_HSV#From_RGB
         * Notes reflects Latex from that article
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
