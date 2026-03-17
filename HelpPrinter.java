public class HelpPrinter {
    public static void printHelp() {
        System.out.println("Dye: A Color Conversion Tool for");
        System.out.print("Hexadecimal, RGB, and HSL color conversions.\n");
        System.out.println("Usage: Dye [-x|-r|-h] [color_code]");
        System.out.println(" -x [color_code]");
        System.out.println(" -r [color_code]");
        System.out.println(" -h [color_code]");
        System.out.println("Examples:");
        System.out.println("Dye -x rgb");
        System.out.println("Dye -x hsl");
        System.out.println("Dye -r hex");
        System.out.println("Dye -r hsl");
        System.out.println("Dye -h rgb");
        System.out.println("Dye -h hex");
        System.exit(0);
    }
}
