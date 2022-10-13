import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.io.*;

public class NumberPlateProblem {

    public static boolean plateGeneration(String memoryTag, String date) {
        String numberPlate = memoryTag + ageIdentifier(date) + " " + randomLetters(); // Creates number plate.

        if (read(numberPlate) == false) { // Checks if the number plate already exists.
            return store(numberPlate); // Returns true if the numberPlate was successfully stored.
        }

        while (read(numberPlate) == true) { // While the current numberPlate already exists
            numberPlate = memoryTag + ageIdentifier(date) + " " + randomLetters(); // Try with new random letters
        }

        return store(numberPlate);
    }

    private static boolean read(String numberPlate) {
        try {
            BufferedReader reader;
            reader = new BufferedReader(new FileReader("existingNumberPlates.txt"));
            String line = reader.readLine();
            while (line != null) { // Reads each line of the file. Returns true if a numberPlate matches
                                   // one in the file.
                if (line.equals(numberPlate)) {
                    reader.close();
                    return true;
                }
                line = reader.readLine(); // Updates line variable with the numberPlate on the next file line
            }
            reader.close();
            return false; // Returns false as number plate does not exist, since while loop has been
                          // exited.

        } catch (FileNotFoundException j) {
            System.out.println("File does not exist. New file created with numberplate added.");
            return false;
        } catch (IOException e) {
            System.out.println("Error.");
            e.printStackTrace();
            return false;
        }

    }

    private static boolean store(String numberPlate) {
        try {
            File file = new File("existingNumberPlates.txt"); 
            FileWriter fw = new FileWriter(file, true);
            fw.write(numberPlate + "\n");  // Writes the numberPlate to a new line.
            fw.close();
            System.out.println("Success, " + numberPlate + " added.");
            return true;
        } catch (IOException e) {
            System.out.println("An error occurred trying to write to the file.");
            e.printStackTrace();
            return false;
        }
    }

    private static String ageIdentifier(String date) {
        String code = date.substring(date.length() - 2); // Last two digits of the year
        String month = date.substring(3, 5); // Month from date variable
        int monthInt = Integer.parseInt(month);
        int codeInt;

        if (monthInt >= 3 && monthInt <= 8) { // Between March & August
            return code;
        } else if (monthInt < 3) { // January/Febuary, returns last two digits + 49, since registration starts in
                                   // Febuary.
            codeInt = Integer.parseInt(code) + 49;
        } else { // September onwards, returns last two digits + 50.
            codeInt = Integer.parseInt(code) + 50;
        }

        return String.valueOf(codeInt); // Returns the 'Age Identifer'
    }

    private static String randomLetters() {
        String[] letters = { "A", "B", "C", "D", "E", "F", "G", "H", "J", "K", "L",
                "M", "N", "O", "P", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" }; // I and Q disallowed (hence
                                                                                   // missing).

        int index1 = (int) (Math.random() * letters.length); // Obtains a random index.
        int index2 = (int) (Math.random() * letters.length);
        int index3 = (int) (Math.random() * letters.length);

        return (letters[index1] + letters[index2] + letters[index3]); // Generates the 3 random letters.
    }

    public static void main(String[] args) { // Tests the 3 sets of memory tags/dates provided.
        plateGeneration("YC", "04/07/2019");
        plateGeneration("LT", "23/01/2003");
        plateGeneration("FF", "30/05/2032");

    }
}
