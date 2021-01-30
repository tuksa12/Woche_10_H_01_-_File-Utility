package pgdp.file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Uniq extends TextFileUtility{

    public Uniq(String[] args) throws InvalidCommandLineArgumentException {
        super(args);
    }

    @Override
    public String applyToFile(Path file) {
        StringBuilder result = new StringBuilder();
        try {
//            Set content = new HashSet();
            Scanner scanner = new Scanner(file);

//            Files.walk(file).forEach(files -> {
//                if(Files.isRegularFile(file)){
//                    try {
//                        content.add(Files.readString(file));
//                    } catch (IOException e) {
//                        System.out.println("Error: Could not read the file.");
//                    }
//                }
//            });
            while (scanner.hasNextLine()){
                if(result.toString().lines().noneMatch(line -> line.contains(scanner.nextLine()))){
                    result.append("\n").append(scanner.nextLine());
                }
            }
        } catch (IOException e) {
            System.out.println("Error: Could not read the file.");
        }
        return "Uniq on file " + file.toString()+ ":\n" +result.toString();
    }

    public static void main(String[] args){
        if(Arrays.stream(args).anyMatch(string -> string.contains("Uniq"))){
            try {
                Uniq newUniq = new Uniq(args);
                newUniq.applyToAll();
            } catch (InvalidCommandLineArgumentException E){
                System.out.println("Error: Invalid Command Line");
            }
        }
    }
}
