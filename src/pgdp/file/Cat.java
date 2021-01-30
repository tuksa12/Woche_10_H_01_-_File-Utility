package pgdp.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class Cat extends TextFileUtility{

    public Cat(String[] args) throws InvalidCommandLineArgumentException {
        super(args);
    }

    @Override
    public String applyToFile(Path file) {
        ArrayList<String> content = new ArrayList<>();
        try {
            Files.walk(file).forEach(files -> {
                if(Files.isRegularFile(file)){
                    try {
                        content.add(Files.readString(file));
                    } catch (IOException e) {
                        System.out.println("Error: Could not read the file.");
                    }
                }
            });
        } catch (IOException e) {
            System.out.println("Error: Could not read the file.");
        }
        String result = "";
        for (int index = 0; index < content.size(); index++) {
            result = result + content.get(index);
        }
        return result;
    }

    public static void main(String[] args){
        if(Arrays.stream(args).anyMatch(string -> string.contains("Cat"))){
            try {
                Cat newCat = new Cat(args);
                newCat.applyToAll();
                System.out.println();
            } catch (InvalidCommandLineArgumentException E){
                System.out.println("Error: Invalid Command Line");
            }
        }
    }
}
