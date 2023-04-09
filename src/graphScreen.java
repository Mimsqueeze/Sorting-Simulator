// package src;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

 public class graphScreen extends Main {
    Main main;
    public static final int WIDTH = 1240, HEIGHT = 620;
    private static final Rectangle2D restart = new Rectangle2D.Double(50, 0, 50, 50);
    private int[] array, pointers = new int[0];
    private int size, numComparisons, numSwaps;
    private boolean inspecting= false, finish = false;
    
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
        try {
            byte[] buf = new byte[2];
            int frequency = 44100; //44100 sample points per 1 second
            AudioFormat af = new AudioFormat((float) frequency, 16, 1, true, false);
        
            SourceDataLine sdl = AudioSystem.getSourceDataLine(af);
            sdl.open();
            sdl.start();
            
            for (int i = 0; i < pointers.length; i++) {
                g.fillRect(pointers[i]*WIDTH/size, HEIGHT-(HEIGHT*array[pointers[i]]/size), (WIDTH/size)+1, (HEIGHT*array[pointers[i]]/size));

                double level = ((double) pointers[i])/size;
                int offset = (int)(392*level);
                int pitch = 392 + offset;
    
                for (int j = 0; j < 25 * (float) 44100 / 1000; j++) { //1000 ms in 1 second
                    float numberOfSamplesToRepresentFullSin= (float) frequency / pitch;
                    double angle = j / (numberOfSamplesToRepresentFullSin/ 2.0) * Math.PI;  // /divide with 2 since sin goes 0PI to 2PI
                    short a = (short) (Math.sin(angle) * 32767);  //32767 - max value for sample to take (-32767 to 32767)
                    buf[0] = (byte) (a & 0xFF); //write 8bits ________WWWWWWWW out of 16
                    buf[1] = (byte) (a >> 8); //write 8bits WWWWWWWW________ out of 16
                    sdl.write(buf, 0, 2);
                }
            }
                    
            sdl.drain();
            sdl.stop();
        } catch (Exception e) {}

        g.setColor(Color.white);
        g.drawRect(0,0, 50, 100);
        g.drawString("Comparisons: " + numComparisons + " Swaps: " + numSwaps, 0,100);
        if (finish) {
            g.drawRect(50, 0, 50,50);
            g.drawString("New Simulation", 50, 50);
        }
    }
    public void onClick(int x, int y) {
        if (restart.contains((double) x, (double) y)) {
            main.start();
        }
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
    public void setBox(int numComparisons, int numSwaps) {
        this.numComparisons = numComparisons;
        this.numSwaps = numSwaps;
    }
    public void setFinish() {
        finish = true;
    }
}
