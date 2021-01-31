package pgdp.file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;


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
        StringBuilder result = new StringBuilder();
        for (int index = 0; index < content.size(); index++) {
            result.append(content.get(index));//StringBuilder to add concatenate all strings in the files
        }
        return result.toString();
    }

    public static void main(String[] args){
            try {
                Cat newCat = new Cat(args);
                newCat.applyToAll();
            } catch (InvalidCommandLineArgumentException E){
                System.out.println("Error: Invalid Command Line");
            }

    }
}
