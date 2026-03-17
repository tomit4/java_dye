import java.util.Set;

public class ArgumentParser {

    private static final Set<String> ACCEPTABLE_COLOR_CODES = Set.of("hex", "rgb", "hsl");

    public static String parse(char colorCodeToConvert, String colorCodeConvertedTo) {
        if (!ACCEPTABLE_COLOR_CODES.contains(colorCodeConvertedTo)) {
            System.out.println("Print Help Function Goes Here.");
            // TODO: print help function
            System.exit(0);
        }
        String convertedColor = "";
        switch (colorCodeToConvert) {
            case 'x':
                if (colorCodeConvertedTo.equals("hex")) {
                    System.out.println("-x and hex are the same color code, no conversion needed.");
                    System.out.println("Print Help Function Goes Here.");
                    // TODO: print help function
                }
                convertedColor = HEXConverter.convertHex(colorCodeConvertedTo);
                break;
            case 'r':
                if (colorCodeConvertedTo.equals("rgb")) {
                    System.out.println("-r and rgb are the same color code, no conversion needed.");
                    System.out.println("Print Help Function Goes Here.");
                    // TODO: print help function
                }
                convertedColor = RGBConverter.convertRGB(colorCodeConvertedTo);
                break;
            case 'h':
                if (colorCodeConvertedTo.equals("hsl")) {
                    System.out.println("-h and hsl are the same color code, no conversion needed.");
                    System.out.println("Print Help Function Goes Here.");
                    // TODO: print help function
                }
                convertedColor = HSLConverter.convertHSL(colorCodeConvertedTo);
                break;
            default:
                System.out.println("Print Help Function Goes Here.");
                // TODO: print help function
                System.exit(0);
        }
        return convertedColor;
    }

}
