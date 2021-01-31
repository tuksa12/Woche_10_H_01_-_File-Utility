package pgdp.file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Fold extends TextFileUtility{
    private final int width;

    public Fold(String[] args) throws InvalidCommandLineArgumentException {
        super(args);
        this.width = Integer.parseInt(parseOption(args,"w","80"));

    }

    @Override
    public String applyToFile(Path file) {
        StringBuilder result = new StringBuilder();
        try {
            Files.walk(file)
                    .forEach(files -> {//For each file
                        try {
                            Files.lines(files).forEach(word -> {//For each line
                                if(result.length() < width && result.append(word).length() < width){
                                    result.append(word);
                                } else{
                                    result.append("\n");
                                }
                            });
                        } catch (IOException e) {
                           System.err.println("Error: Could not read the file");
                        }
                    });
        } catch (IOException e) {
            System.err.println("Error: Could not walk through the file");
        }
        return "Fold on file" + file.toString() +"\n" + result.toString();
    }

    public static void main(String[] args){
        try {
            Fold newFold = new Fold(args);
            newFold.applyToAll();
        } catch (InvalidCommandLineArgumentException E){
            System.out.println("Error: Invalid Command Line");
        }
    }

}
