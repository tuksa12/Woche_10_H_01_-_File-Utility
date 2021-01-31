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
            List<String> lineListNotDuplicated = Files.readAllLines(file);
            List<String> lineList = Files.readAllLines(file);
            for (int i = 0; i < lineList.size(); i++) {
                if(!lineListNotDuplicated.contains(lineList.get(i))){//If the second list doesn't have the line, add
                    lineListNotDuplicated.add(lineList.get(i));
                }
            }
            for (int i = 0; i < lineListNotDuplicated.size(); i++) {
                result.append(lineListNotDuplicated.get(i));
                if(i < lineListNotDuplicated.size() - 1){//Last line, so that a new line is not added
                    result.append("\n");
                }
            }
        } catch (IOException e) {
            System.out.println("Error: Could not read the file.");
        }
        return "Uniq on file " + file.toString()+ ":\n" +result.toString();
    }

    public static void main(String[] args){
            try {
                Uniq newUniq = new Uniq(args);
                newUniq.applyToAll();
            } catch (InvalidCommandLineArgumentException E){
                System.out.println("Error: Invalid Command Line");
            }

    }
}
