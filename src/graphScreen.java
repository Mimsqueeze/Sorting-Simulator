// package src;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;

 public class graphScreen {

    // Reference to Main Program
    Main main;

    // Constants based on desired window size
    public static final int WIDTH = 1240; 
    public static final int HEIGHT = 620;
    public static final int HEADER = 75;
    public static final int BARHEIGHT = HEIGHT - HEADER;

    // Constant defining dimensions of the restart button
    private static final Rectangle2D restart = new Rectangle2D.Double(0, 132, 225, 25);
    
    
    private int[] array, pointers = new int[0];
    private int size, numComparisons, numSwaps, numInsertions, numSims = 1;
    private long totalTime;
    private boolean inspecting= false, finish = false;
    
    graphScreen(Main main) {
        this.main = main;
    }

    // Function called to render each frame of the graph
    public void render(Graphics2D g) {

        // Fill in the rectangles (bars) for the graph
        for (int i = 0; i < size; i++) {

            // fill bottom
            g.setColor(Color.green);
            g.fillRect(i*WIDTH/size, HEIGHT - (BARHEIGHT*array[i]/size), (WIDTH/size)+1, HEADER + (BARHEIGHT*array[i]/size));
           
            // fill top
            g.setColor(Color.black);
            g.fillRect(i*WIDTH/size, HEADER, (WIDTH/size)+1, BARHEIGHT - (BARHEIGHT*array[i]/size));
            
        }

        if (inspecting) { // inspecting
            g.setColor(Color.red);
            inspecting= false;
        } else { // swapping
            g.setColor(Color.yellow);
        }

        if (main.soundOn) {
            try {
                for (int i = 0; i < pointers.length; i++) {
                    g.fillRect(pointers[i]*WIDTH/size, HEIGHT - (BARHEIGHT*array[pointers[i]]/size), (WIDTH/size)+1, HEADER + (BARHEIGHT*array[pointers[i]]/size));
                
                    byte[] buf = new byte[2];
                    int frequency = 4000;
                    AudioFormat af = new AudioFormat((float) frequency, 16, 1, true, false);
                
                    SourceDataLine sdl = AudioSystem.getSourceDataLine(af);
                    sdl.open();
                    sdl.start();
 
                    double level = ((double) pointers[i])/size;
                    int offset = (int)(392*level);
                    int pitch = 392 + offset;
        
                    for (int j = 0; j < 25 * (float) frequency / 1000; j++) { //1000 ms in 1 second
                        float numberOfSamplesToRepresentFullSin= (float) frequency / pitch;
                        double angle = j / (numberOfSamplesToRepresentFullSin/ 2.0) * Math.PI;  // /divide with 2 since sin goes 0PI to 2PI
                        short a = (short) (Math.sin(angle) * 32767);  //32767 - max value for sample to take (-32767 to 32767)
                        buf[0] = (byte) (a & 0xFF); //write 8bits ________WWWWWWWW out of 16
                        buf[1] = (byte) (a >> 8); //write 8bits WWWWWWWW________ out of 16
                        sdl.write(buf, 0, 2);
                    }
                    sdl.drain();
                    sdl.stop();
                }
                        

            } catch (Exception e) {}
        } else {
            for (int i = 0; i < pointers.length; i++) {
                g.fillRect(pointers[i]*WIDTH/size, HEIGHT - (BARHEIGHT*array[pointers[i]]/size), (WIDTH/size)+1, HEADER + (BARHEIGHT*array[pointers[i]]/size));
            }
        }

        g.setColor(Color.black);
        g.fillRect(0, 0, WIDTH, HEADER);

        g.setColor(Color.white);

        g.setFont(main.titleFont);
        g.drawString((String) main.dropDown.getSelectedItem() + " (Run " + numSims + ")", 0, 25);

        g.setFont(main.myFont);

        g.drawString("Comparisons: " + String.format("%.2f", (double)numComparisons) + " " + 
        "Swaps: " + String.format("%.2f", (double)numSwaps) + " " +  
        "Insertions: " + String.format("%.2f", (double)numInsertions), 0, 50);
        if (totalTime != 0) {
            g.drawString("Avg. Time (nanoseconds): " + String.format("%,d", totalTime/(numSims-1)), 0, 125);
        }

        if (finish) {
            g.setColor(Color.black);
            g.fillRect(0, 0, WIDTH, HEADER);
            g.setColor(Color.WHITE);
            g.setFont(main.titleFont);
            g.drawString((String) main.dropDown.getSelectedItem() + " (" + numSims + " run" + ((numSims == 1) ? ")" : "s)"), 0, 25);
            g.setFont(main.myFont);
            g.drawString("Avg. Comparisons: " + String.format("%.2f", numComparisons/(double)numSims) + " " +
            "Avg. Swaps: " + String.format("%.2f", numSwaps/(double)numSims)+ " " + 
            "Avg. Insertions: " + String.format("%.2f", numInsertions/(double)numSims), 0, 50);
            g.drawRect(0, 132, 225, 25);
            g.drawString("New Simulation", 0, 150);
            if (totalTime != 0) {
                g.drawString("Avg. Time (nanoseconds): " + String.format("%,d", totalTime/(numSims-1)), 0, 125);
            }
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
    public void setBox(int[] data) {
        this.numComparisons = data[0];
        this.numSwaps = data[1];
        this.numInsertions = data[2];
    }
    public void setFinish(int numSims, long totalTime) {
        finish = true;
        this.numSims = numSims;
        this.totalTime = totalTime;
    }
}
