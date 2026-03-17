class Dye {
    public static void main(String[] args) {
        if (args.length <= 1 || args[0].charAt(0) != '-' || args[0].length() > 2) {
            HelpPrinter.printHelp();
        }
        char colorCodeToConvert = args[0].charAt(1);
        String colorCodeConvertedTo = args[1];
        ArgumentParser.parse(colorCodeToConvert, colorCodeConvertedTo);
    }
}
