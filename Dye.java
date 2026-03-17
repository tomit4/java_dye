class Dye {
    public static void main(String[] args) {
        if (args.length <= 1 || args[0].charAt(0) != '-' || args[0].length() > 2) {
            System.out.println("Print Help Function Goes Here.");
            // TODO: print help function
            System.exit(0);
        }
        char colorCodeToConvert = args[0].charAt(1);
        String colorCodeConvertedTo = args[1];
        ArgumentParser.parse(colorCodeToConvert, colorCodeConvertedTo);
    }
}
