package pgdp.file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//todo Fehlerbehandlung na maioria dos metodos

public abstract class TextFileUtility {
    private final List<Path> inputPaths;
    private final Path outputFile ;

    public TextFileUtility(String[] args) throws InvalidCommandLineArgumentException {
        this.inputPaths = new ArrayList();
        try {
            inputPaths.add(Path.of(String.valueOf(Arrays.stream(args)
                    .map(file -> file.substring(file.lastIndexOf("=") + 1)))));
            if (Arrays.asList(args).contains("o")) {
                this.outputFile = Path.of(String.valueOf(Arrays.stream(args)
                        .map(file -> file.substring(file.lastIndexOf("=") + 1))));
            } else {
                this.outputFile = null;
            }
        } catch (Exception e) {
            throw new InvalidCommandLineArgumentException();
        }
    }

    public static String parseOption(String[] args, String option, String defaultValue) {
        if (Arrays.asList(args).contains(option)) {
            return Arrays.stream(args).filter(string -> string.contains(option)).toString().split("=")[1];
        }
        return defaultValue;
    }

    public void output(String string){
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
    }

    public abstract String applyToFile(Path file);

    public void applyToAll(){
        for (Path inputPath : inputPaths) {
            try {
                Files.walk(inputPath)
                        .forEach(path -> System.out.println(applyToFile(path)));
            } catch (IOException E) {
                System.out.println("Error in applyToAll");
            }
        }
    }
}
