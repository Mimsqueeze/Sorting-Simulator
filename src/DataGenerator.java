import java.io.FileWriter;
import java.io.IOException;

public class DataGenerator {
    public static void main(String[] args) {
        FileWriter write= null;
        try {
            write = new FileWriter("data.txt");
        
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
