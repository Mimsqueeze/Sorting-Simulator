import java.awt.*;
import java.awt.geom.Rectangle2D;

public class graphScreen {

    // Reference to Main Program
    Main main;

    // Constants based on desired window size
    public static final int WIDTH= 1240; 
    public static final int HEIGHT= 620;
    public static final int HEADER= 75;
    public static final int BARHEIGHT= HEIGHT - HEADER;
    
    // Constants defining the color of the graph
    private static final Color BARCOLOR= Color.GREEN;
    private static final Color SWAPCOLOR= Color.YELLOW;
    private static final Color INSPECTCOLOR= Color.RED;
    private static final Color EMPTYCOLOR= Color.BLACK;

    // Constants defining the color and size of the UI
    private static final Color HEADERCOLOR= Color.BLACK;
    private static final Color TEXTCOLOR= Color.WHITE;

    // Constant defining creation and dimensions of the restart button
    private static Rectangle2D restart;
    
    // The array to be visualized
    private int[] array;

    // The size of the array to be visualized
    private int SIZE;

    // The array of positions to be highlighted
    private int[] pointers= new int[0];

    // Statistics to be displayed
    private long[] data;

    // Enum to store te indiceso o f data in the array data
    private final static class DATA_INDICES {
        static final short NUM_COMPARISONS=  0;
        static final short NUM_SWAPS=        1;
        static final short NUM_INSERTIONS=   2;
        static final short NUM_TIME=         3;
        static final short NUM_SIMULATIONS=  4;
    };

    // Boolean flags used in processing
    
    private State state= State.NEITHER;
    private boolean finished= false;
    
    // graphScreen constructor to create a new frame to render
    graphScreen(Main main, int size) {
        this.main= main;
        this.SIZE= size;
    }

    // Function called to render each frame of the graph
    public void render(Graphics2D g) {

        // Fill in the rectangles (bars) for the graph
        for (int i= 0; i < SIZE; i++) {

            // Fill the bar
            g.setColor(BARCOLOR);
            g.fillRect(i*WIDTH/SIZE, HEIGHT - (BARHEIGHT*array[i]/SIZE), (WIDTH/SIZE)+1, HEADER + (BARHEIGHT*array[i]/SIZE));
           
            // Fill space above the bar
            g.setColor(EMPTYCOLOR);
            g.fillRect(i*WIDTH/SIZE, HEADER, (WIDTH/SIZE)+1, BARHEIGHT - (BARHEIGHT*array[i]/SIZE));
        }
        if (state != State.NEITHER) {
            if (state == State.INSPECTING) { // inspecting
                g.setColor(INSPECTCOLOR);
            } else if (state == State.SWAPPING) { // swapping
                g.setColor(SWAPCOLOR);
            }
            // if sound enabled, split execution to play a sound while updating the highlighted positions
            if (main.soundOn) {
                for (int i= 0; i < pointers.length; i++) {
                    g.fillRect(pointers[i]*WIDTH/SIZE, HEIGHT - (BARHEIGHT*array[pointers[i]]/SIZE), (WIDTH/SIZE)+1, HEADER + (BARHEIGHT*array[pointers[i]]/SIZE));
                
                    Sound.makeSound(pointers[i], SIZE);
                }
            } else {
                for (int i= 0; i < pointers.length; i++)
                    g.fillRect(pointers[i]*WIDTH/SIZE, HEIGHT - (BARHEIGHT*array[pointers[i]]/SIZE), (WIDTH/SIZE)+1, HEADER + (BARHEIGHT*array[pointers[i]]/SIZE));
            }
        }
        // Fill Header
        displayInformation(g);
    }

    private void displayInformation(Graphics2D g) {

        // Fill header
        g.setColor(HEADERCOLOR);
        g.fillRect(0, 0, WIDTH, HEADER);

        // Display title
        g.setColor(TEXTCOLOR);
        g.setFont(main.titleFont);
        g.drawString((String) main.dropDown.getSelectedItem() + " (Run " + data[DATA_INDICES.NUM_SIMULATIONS] + ")", 0, 25);

        // Display information
        g.setFont(main.myFont);

        // Splits execution based on finish status
        if (!finished) {
            g.drawString(
                "Comparisons: " + String.format("%.2f", (double) data[DATA_INDICES.NUM_COMPARISONS]) + " " + 
                "Swaps: "       + String.format("%.2f", (double) data[DATA_INDICES.NUM_SWAPS]) + " " +  
                "Insertions: "  + String.format("%.2f", (double) data[DATA_INDICES.NUM_INSERTIONS]), 0, 50);

            if (data[DATA_INDICES.NUM_TIME] > 0)
                g.drawString("Time (nanoseconds): " + String.format("%,d", data[DATA_INDICES.NUM_TIME]/(data[DATA_INDICES.NUM_SIMULATIONS]-1)), 0, 125);

        } else {
            g.drawString(
                "Avg. Comparisons: " + String.format("%.2f", (double) data[DATA_INDICES.NUM_COMPARISONS]) + " " + 
                "Avg. Swaps: "       + String.format("%.2f", (double) data[DATA_INDICES.NUM_SWAPS]) + " " +  
                "Avg. Insertions: "  + String.format("%.2f", (double) data[DATA_INDICES.NUM_INSERTIONS]), 0, 50);

            // Y position of the restart button
            int restartYPosition= 57;
            if (data[DATA_INDICES.NUM_TIME] > 0) {
                restartYPosition= 100 - 18;
                g.drawString("Avg. Time (nanoseconds): " + String.format("%,d", data[DATA_INDICES.NUM_TIME]/(data[DATA_INDICES.NUM_SIMULATIONS]-1)), 0, 75);
            }
                
            // Creates the restart button
            g.drawRect(0, restartYPosition, 225, 25);
            g.drawString("New Simulation", 0, restartYPosition + 18);
            restart= new Rectangle2D.Double(0, restartYPosition, 225, 25);
        }
    }

    // Function is called when user clicks on the screen
    public void onClick(int x, int y) {

        // If the restart button is clicked, then go back to main menu screen
        if (restart.contains((double) x, (double) y))
            main.start();
    }

    public void updateInformation(int[] array, int[] pointers, long[] data, State state) {
        this.array= array;
        this.pointers= pointers;
        this.data= data;        
        this.state= state;
    }
}
