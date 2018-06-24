package tbs.server;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;

public class FileManagement {
    private String path;

    public FileManagement(String path) {
        this.path = path;
    }

    public String checkAndConvert(List<Theatre> theatreList, HashMap<String, Theatre> theatreHashMap) {

        // Import csv to the theatre List
        try {

            //Create a file input then reads it line by line
            FileInputStream theFileInput = new FileInputStream(path);
            InputStreamReader anInputReader = new InputStreamReader(theFileInput);
            BufferedReader aBufferedReader = new BufferedReader(anInputReader);
            String currentLine;

            while ((currentLine = aBufferedReader.readLine()) != null) {

                //Split line whenever a "\t" is found in the line
                String[] splitLine = currentLine.split("\t");
                //Check to ensure the line meets the requirements.
                String lineCheckOut = lineCheck(splitLine);

                //Add line only if its correct otherwise return the error encountered.
                //Correct formats of the code will return "" and this if statement checks whether or not that is the case.
                if (lineCheckOut.length() != 0) {
                    return lineCheckOut;
                } else {
                    //Adds a new theatre to the theatre list if successful.
                    theatreList.add(new Theatre(splitLine, theatreHashMap));
                }

            }

        } catch (IOException e) {
            return "ERROR: File not found";
        }

        return "";
    }

    private String lineCheck(String[] toCheck) {

        //Runs through the array performing various checks to ensure the format is correct.
        if (toCheck.length != 4 || !toCheck[0].equals("THEATRE") || !intCheck(toCheck)) {
            //Ensure there are 4 elements in one line and that the first column contains the string theatre.
            return "ERROR: File Format Incorrect.";
        } else {
            return "";
        }

    }

    private Boolean intCheck(String[] toCheck) {

        //Runs through the string array to ensure that all integers are where they are supposed to be.
        for (int i = 2; i <= 3; i++) {
            String currentString = toCheck[i];

            try {
                Integer.valueOf(currentString);
            } catch (NumberFormatException e) {
                return false;
            }

        }
        return true;
    }

}
