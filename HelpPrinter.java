public class HelpPrinter {
    public static void printHelp() {
        System.out.println("===============================================");
        System.out.println("Dye: A Color Conversion Tool for");
        System.out.print("Hexadecimal, RGB, and HSL color conversions.\n");
        System.out.println("===============================================");
        System.out.println("Example Usage:");
        System.out.println("Convert Hexadecimal to RGB:");
        System.out.print("Enter a color model (rgb, hex, hsl): hex");
        System.out.print("Enter a color model to convert to (rgb, hex, hsl): rgb");
        System.out.println("===============================================");
        System.out.println("To see this help message again, at any point type:");
        System.out.println("h");
        System.out.println("help");
        System.out.println("===============================================");
        System.out.println("To quit, at any point type:");
        System.out.println("q");
        System.out.println("quit");
    }
}
