package pgdp.file;

import java.nio.file.Path;

public class Fold extends TextFileUtility{

    public Fold(String[] args) throws InvalidCommandLineArgumentException {
        super(args);
    }

    @Override
    public String applyToFile(Path file) {
        return null;
    }
}
