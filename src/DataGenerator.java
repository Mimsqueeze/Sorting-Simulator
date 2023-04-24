import java.io.FileWriter;

/* Use this file to generate a custom dataset. */

public class DataGenerator {
    public static void main(String[] args) {
        FileWriter write= null;
        try {
            write = new FileWriter("data.txt");
            
            // Generates list of numbers 1000 to 1 (reverse sorted order)
            for (int i= 1000; i >= 1; i--) {
                write.write(String.valueOf(i));
                write.append(" ");
            }
            write.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
    }
}
