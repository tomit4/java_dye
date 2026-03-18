import java.util.Scanner;

public class ArgumentParser {
    public static void parse(String colorCodeToConvert, String colorCodeConvertedTo, Scanner input) {
        String convertedColor = "";
        switch (colorCodeToConvert) {
            case "hex":
                convertedColor = HEXConverter.convertHex(colorCodeConvertedTo, input);
                break;
            case "rgb":
                convertedColor = RGBConverter.convertRGB(colorCodeConvertedTo, input);
                break;
            case "hsl":
                convertedColor = HSLConverter.convertHSL(colorCodeConvertedTo, input);
                break;
            default:
                HelpPrinter.printHelp();
        }
        System.out.println("Converted Color: " + convertedColor);
    }
}
