package pgdp.file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class TextFileUtility {
    private final List<Path> inputPaths;
    private final Path outputFile ;

    public TextFileUtility(String[] args) throws InvalidCommandLineArgumentException {
        this.inputPaths = new ArrayList();
        try {
            inputPaths.add(Path.of(String.valueOf(Arrays.stream(args)
                    .map(file -> file.substring(file.lastIndexOf("=") + 1)))));
            if (Arrays.stream(args).anyMatch(string -> string.contains("o"))) {//If there is the Option "-o<DATEI>"
                this.outputFile = Path.of(Arrays.stream(args).filter(string -> string.contains("o"))
                        .findAny().toString().split("=")[1].split("]")[0]);//Stream that finds the string that contains "=",
                                                                                      //takes the name of the file after that and excludes the "]"
            } else {
                this.outputFile = null;
            }
        } catch (Exception e) {
            throw new InvalidCommandLineArgumentException();
        }
    }

    public static String parseOption(String[] args, String option, String defaultValue) {
        if (Arrays.stream(args).anyMatch(string -> string.contains(option))){//Searches the args to se if there is the option
            try {
                return Arrays.stream(args).filter(string -> string.contains(option))
                        .findAny().toString().split("=")[1].split("]")[0];//Same way as in the TextFileUtility
            } catch (ArrayIndexOutOfBoundsException E){
                return defaultValue;
            }
        }
        return defaultValue;
    }

    public void output(String string){
        try{
            if(outputFile == null){
                System.out.println(string);
            } else{
                if(inputPaths.contains(Path.of(string))){
                    int index = inputPaths.indexOf(Path.of(string));
                    inputPaths.set(index, Path.of(string));
                } else{
                    inputPaths.add(Path.of(string));
                }
            }
        } catch (Exception e){
            System.err.println("Error: Could not output the file content");
        }
    }

    public abstract String applyToFile(Path file);

    public void applyToAll(){
        StringBuilder builder = new StringBuilder();
        for (Path inputPath : inputPaths) {
            try {
                Files.walk(inputPath)
                        .forEach(path -> builder.append(applyToFile(path)));
                output(builder.toString());
            } catch (IOException E) {
                System.out.println("Error in applying to all files");
            }
        }
    }
}
