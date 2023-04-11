import java.awt.*;
import java.awt.geom.Rectangle2D;

public class graphScreen {

    // Reference to Main Program
    Main main;

    // Constants based on desired window size
    public static final int WIDTH = 1240; 
    public static final int HEIGHT = 620;
    public static final int HEADER = 75;
    public static final int BARHEIGHT = HEIGHT - HEADER;

    // Constants defining the color of the graph
    private static final Color BARCOLOR = Color.GREEN;
    private static final Color SWAPCOLOR = Color.YELLOW;
    private static final Color INSPECTCOLOR = Color.RED;
    private static final Color EMPTYCOLOR = Color.BLACK;

    // Constants defining the color and size of the UI
    private static final Color HEADERCOLOR = Color.BLACK;
    private static final Color TEXTCOLOR = Color.WHITE;

    // Constant defining creation and dimensions of the restart button
    private static Rectangle2D restart;
    
    // The array to be visualized
    private int[] array;

    // The size of the array to be visualized
    private int SIZE;

    // The array of positions to be highlighted
    private int[] pointers = new int[0];

    // Statistics to be displayed
    private int numComparisons, numSwaps, numInsertions, numSims = 1;
    private long totalTime= 0;

    // Boolean flags used in processing
    private boolean inspecting= false, finished = false;
    
    // graphScreen constructor to create a new frame to render
    graphScreen(Main main) {
        this.main = main;
    }

    // Function called to render each frame of the graph
    public void render(Graphics2D g) {

        // Fill in the rectangles (bars) for the graph
        for (int i = 0; i < SIZE; i++) {

            // Fill the bar
            g.setColor(BARCOLOR);
            g.fillRect(i*WIDTH/SIZE, HEIGHT - (BARHEIGHT*array[i]/SIZE), (WIDTH/SIZE)+1, HEADER + (BARHEIGHT*array[i]/SIZE));
           
            // Fill space above the bar
            g.setColor(EMPTYCOLOR);
            g.fillRect(i*WIDTH/SIZE, HEADER, (WIDTH/SIZE)+1, BARHEIGHT - (BARHEIGHT*array[i]/SIZE));
        }

        if (inspecting) { // inspecting
            g.setColor(INSPECTCOLOR);
            inspecting= false;
        } else { // swapping
            g.setColor(SWAPCOLOR);
        }

        // if sound enabled, split execution to play a sound while updating the highlighted positions
        if (main.soundOn) {
            for (int i = 0; i < pointers.length; i++) {
                g.fillRect(pointers[i]*WIDTH/SIZE, HEIGHT - (BARHEIGHT*array[pointers[i]]/SIZE), (WIDTH/SIZE)+1, HEADER + (BARHEIGHT*array[pointers[i]]/SIZE));
            
                Sound.makeSound(pointers[i], SIZE);
            }
        } else {
            for (int i = 0; i < pointers.length; i++)
                g.fillRect(pointers[i]*WIDTH/SIZE, HEIGHT - (BARHEIGHT*array[pointers[i]]/SIZE), (WIDTH/SIZE)+1, HEADER + (BARHEIGHT*array[pointers[i]]/SIZE));
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
        g.drawString((String) main.dropDown.getSelectedItem() + " (Run " + numSims + ")", 0, 25);

        // Display information
        g.setFont(main.myFont);

        // Splits execution based on finish status
        if (!finished) {
            g.drawString(
                "Comparisons: " + String.format("%.2f", (double) numComparisons) + " " + 
                "Swaps: "       + String.format("%.2f", (double) numSwaps) + " " +  
                "Insertions: "  + String.format("%.2f", (double) numInsertions), 0, 50);

            if (totalTime > 0)
                g.drawString("Time (nanoseconds): " + String.format("%,d", totalTime/(numSims-1)), 0, 125);

        } else {
            g.drawString(
                "Avg. Comparisons: " + String.format("%.2f", (double) numComparisons) + " " + 
                "Avg. Swaps: "       + String.format("%.2f", (double) numSwaps) + " " +  
                "Avg. Insertions: "  + String.format("%.2f", (double) numInsertions), 0, 50);

            // Y position of the restart button
            int restartY = 57;
            if (totalTime > 0) {
                restartY = 100 - 18;
                g.drawString("Avg. Time (nanoseconds): " + String.format("%,d", totalTime/(numSims-1)), 0, 75);
            }
                
            // Creates the restart button
            g.drawRect(0, restartY, 225, 25);
            g.drawString("New Simulation", 0, restartY + 18);
            restart= new Rectangle2D.Double(0, restartY, 225, 25);
        }
    }

    // Function is called when user clicks on the screen
    public void onClick(int x, int y) {

        // If the restart button is clicked, then go back to main menu screen
        if (restart.contains((double) x, (double) y))
            main.start();
    }

    public void setArrays(int[] array, int[] pointers, int size, boolean inspecting) {
        this.array = array;
        this.pointers = pointers;
        this.SIZE = size;
        this.inspecting = inspecting;
    }
    public void setArray(int[] array, int size) {
        this.array = array;
        this.SIZE = size;
    }
    public void setBox(int[] data) {
        this.numComparisons = data[0];
        this.numSwaps = data[1];
        this.numInsertions = data[2];
    }
    public void setFinish(int numSims, long totalTime) {
        finished = true;
        this.numSims = numSims;
        this.totalTime = totalTime;
    }

}
