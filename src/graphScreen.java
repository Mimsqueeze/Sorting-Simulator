// package src;

import java.awt.*;

public class graphScreen extends screen {
    private int[] array, pointers = new int[0];
    private int size;
    private boolean inspecting= false;
    public void render(Graphics2D g) {
        // g.setBackground(new Color(0,0,139));
        // g.setBackground(Color.BLACK);
        g.setColor(Color.yellow);
        for (int i = 0; i < size; i++) {
            g.fillRect(i*1000/size,600-(600*array[i]/size),1000/size,(600*array[i]/size));
        }
        if (inspecting) {
            g.setColor(Color.red);
        } else {
            g.setColor(Color.black);
        }
        for (int i = 0; i < pointers.length; i++) {
            g.fillRect(pointers[i]*1000/size, 600-(600*array[pointers[i]]/size),1000/size,(600*array[pointers[i]]/size));
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
