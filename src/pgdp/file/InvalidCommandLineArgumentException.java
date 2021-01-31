package pgdp.file;

public class InvalidCommandLineArgumentException extends Exception{
    public InvalidCommandLineArgumentException(){
        super("Error: Invalid Command Line Argument");
    }
}
