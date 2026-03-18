import java.util.Scanner;
import java.util.InputMismatchException;

public class HSLConverter {
    private static HSLObj hslObj;

    public static void setHslValues(Scanner input) {
        int hValue, sValue, lValue;

        while (true) {
            try {
                System.out.print("Enter H value (0 to 360): ");
                hValue = input.nextInt();
                if (hValue < 0 || hValue > 360) {
                    System.out.println("Error: Hue must be 0 - 360");
                    continue;
                }

                System.out.print("Enter S value (0 to 100): ");
                sValue = input.nextInt();
                if (sValue < 0 || sValue > 100) {
                    System.out.println("Saturation must be 0 - 100");
                    continue;
                }

                System.out.print("Enter L value (0 to 100): ");
                lValue = input.nextInt();
                if (lValue < 0 || lValue > 100) {
                    System.out.println("Lightness must be 0 - 100");
                    continue;
                }

                input.nextLine();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter an integer value");
                input.nextLine();
            }
        }

        hslObj = new HSLObj(hValue, sValue, lValue);
    }

    public static String convertHSL(String colorCodeConvertedTo, Scanner input) {
        setHslValues(input);

        String convertedColor = "";

        if (colorCodeConvertedTo.equals("hex")) {
            convertedColor = convertToHex();
        } else {
            convertedColor = convertToRgb();
        }

        return convertedColor;
    }

    public static String convertToHex() {
        // NOTE: HSL to HEX requires first converting HSL to RGB, then RGB to HEX
        String rgb = convertToRgb();

        int r = Integer.parseInt(rgb.split(",")[0].split("\\(")[1].trim());
        int g = Integer.parseInt(rgb.split(",")[1].trim());
        int b = Integer.parseInt(rgb.split(",")[2].split("\\)")[0].trim());

        return String.format("#%02X%02X%02X", r, g, b);
    }

    public static String convertToRgb() {
        /*
         * Uses Mathematical formulas from
         * https://en.wikipedia.org/wiki/HSL_and_HSV#HSL_to_RGB
         * Notes reflects Latex from that article
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

        // Massive Piecewise Function for HSL to RGB Formula
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
