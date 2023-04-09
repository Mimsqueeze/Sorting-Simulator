// package src;
import javax.swing.*;
import java.awt.*;

public class graphScreen extends Screen {
    public static final int WIDTH = 1240, HEIGHT = 620;

    private int[] array, pointers = new int[0];
    private int size;
    private boolean inspecting= false;
    Thread sound = new Sound();

    public void render(Graphics2D g) {
        // g.setBackground(new Color(0,0,139));
        // g.setBackground(Color.BLACK);

        for (int i = 0; i < size; i++) {
            g.setColor(Color.green);
            g.fillRect(i*WIDTH/size, HEIGHT - (HEIGHT*array[i]/size), (WIDTH/size)+1, (HEIGHT*array[i]/size));
            g.setColor(Color.black);
            g.fillRect(i*WIDTH/size, 0, (WIDTH/size)+1, HEIGHT - (HEIGHT*array[i]/size));
        }
        if (inspecting) { // inspecting
            g.setColor(Color.red);
            inspecting= false;
        } else { // swapping
            g.setColor(Color.yellow);
        }
        for (int i = 0; i < pointers.length; i++) {
            g.fillRect(pointers[i]*WIDTH/size, HEIGHT-(HEIGHT*array[pointers[i]]/size), (WIDTH/size)+1, (HEIGHT*array[pointers[i]]/size));
            /*try {
                Sound.makeSound(size, array[pointers[i]]);
            } catch (Exception e) {}
            */
        }
    }
    public void onClick(int x, int y) {
        return;
    }
    public void setArrays(int[] array, int[] pointers, int size, boolean inspecting) {
        this.array = array;
        this.pointers = pointers;
        this.size = size;
        this.inspecting = inspecting;
    }
    public void setArray(int[] array, int size) {
        this.array = array;
        this.size = size;
    }
}
