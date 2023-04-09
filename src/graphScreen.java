// package src;
import javax.swing.*;
import java.awt.*;

public class graphScreen extends Screen {
    public static final int WIDTH = 1240, HEIGHT = 620;

    private int[] array, pointers = new int[0];
    private int size;
    private boolean inspecting= false;

    public void render(Graphics2D g) {
        // g.setBackground(new Color(0,0,139));
        // g.setBackground(Color.BLACK);

        g.setColor(Color.green);
        for (int i = 0; i < size; i++) {
            g.fillRect(i*WIDTH/size,HEIGHT-(HEIGHT*array[i]/size),(WIDTH/size)+1,(HEIGHT*array[i]/size));
        }
        if (inspecting) {
            g.setColor(Color.red);
        } else {
            g.setColor(Color.black);
        }
        for (int i = 0; i < pointers.length; i++) {
            g.fillRect(pointers[i]*WIDTH/size, HEIGHT-(HEIGHT*array[pointers[i]]/size),(WIDTH/size)+1,(HEIGHT*array[pointers[i]]/size));
        }
        g.setColor(Color.black);
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
