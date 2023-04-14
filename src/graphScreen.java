import java.awt.*;
import java.awt.geom.Rectangle2D;

public class GraphScreen {

    // Reference to Main Program
    Main main;

    // Reference to Graphics2D used by Main
    Graphics2D graphics;

    // Constants based on desired window size
    public static final int WIDTH= 1240; 
    public static final int HEIGHT= 620;
    public static final int HEADER= 75;
    public static final int BARHEIGHT= HEIGHT - HEADER;
    
    // Constants defining the color of the graph
    private static final Color BARCOLOR= Color.GREEN;
    private static final Color SWAPCOLOR= Color.RED;
    private static final Color COMPARECOLOR= Color.YELLOW;
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

    // Boolean flags used in processing
    
    private Constants.Mode mode;
    
    // graphScreen constructor to create a new frame to render
    GraphScreen(Main main, Graphics2D graphics, int size) {
        this.main= main;
        this.graphics= graphics;
        this.SIZE= size;
    }

    // Function called to render each frame of the graph
    public void render() {

        // Fill in the rectangles (bars) for the graph
        for (int i= 0; i < SIZE; i++) {

            // Fill the bar
            graphics.setColor(BARCOLOR);
            graphics.fillRect(i*WIDTH/SIZE, HEIGHT - (BARHEIGHT*array[i]/SIZE), (WIDTH/SIZE)+1, HEADER + (BARHEIGHT*array[i]/SIZE));
           
            // Fill space above the bar
            graphics.setColor(EMPTYCOLOR);
            graphics.fillRect(i*WIDTH/SIZE, HEADER, (WIDTH/SIZE)+1, BARHEIGHT - (BARHEIGHT*array[i]/SIZE));
        }
        if (mode != Constants.Mode.DEFAULT && mode != Constants.Mode.FINISH) {
            if (mode == Constants.Mode.COMPARE) { // comparing
                graphics.setColor(COMPARECOLOR);
            } else if (mode == Constants.Mode.SWAP) { // swapping
                graphics.setColor(SWAPCOLOR);
            }
            // if sound enabled, split execution to play a sound while updating the highlighted positions
            if (main.soundOn) {
                for (int i= 0; i < pointers.length; i++) {
                    graphics.fillRect(pointers[i]*WIDTH/SIZE, HEIGHT - (BARHEIGHT*array[pointers[i]]/SIZE), (WIDTH/SIZE)+1, HEADER + (BARHEIGHT*array[pointers[i]]/SIZE));
                
                    Sound.makeSound(pointers[i], SIZE);
                }
            } else {
                for (int i= 0; i < pointers.length; i++)
                    graphics.fillRect(pointers[i]*WIDTH/SIZE, HEIGHT - (BARHEIGHT*array[pointers[i]]/SIZE), (WIDTH/SIZE)+1, HEADER + (BARHEIGHT*array[pointers[i]]/SIZE));
            }
        }
        // Fill Header Information
        displayInformation();
    }

    private void displayInformation() {
        // Fill header
        graphics.setColor(HEADERCOLOR);
        graphics.fillRect(0, 0, WIDTH, HEADER);

        // Display title
        graphics.setColor(TEXTCOLOR);
        graphics.setFont(main.titleFont);
        graphics.drawString((String) main.dropDown.getSelectedItem() + " (Run " + data[Constants.DATA_INDICES.NUM_SIMULATIONS] + ")", 0, 25);

        // Display information
        graphics.setFont(main.myFont);

        // Splits execution based on finish status
        if (mode != Constants.Mode.FINISH) {
            // Unfinished, so display current information
            graphics.drawString(
                "Comparisons: " + String.format("%.2f", (double) data[Constants.DATA_INDICES.NUM_COMPARISONS]) + " " + 
                "Swaps: "       + String.format("%.2f", (double) data[Constants.DATA_INDICES.NUM_SWAPS]) + " " +  
                "Insertions: "  + String.format("%.2f", (double) data[Constants.DATA_INDICES.NUM_INSERTIONS]), 0, 50);

            if (data[Constants.DATA_INDICES.NUM_TIME] > 0)
                graphics.drawString("Time (nanoseconds): " + String.format("%,d", data[Constants.DATA_INDICES.NUM_TIME]/(data[Constants.DATA_INDICES.NUM_SIMULATIONS]-1)), 0, 125);

        } else {
            // Finished, so display information and render the restart button
            graphics.drawString(
                "Avg. Comparisons: " + String.format("%.2f", (double) data[Constants.DATA_INDICES.NUM_COMPARISONS]) + " " + 
                "Avg. Swaps: "       + String.format("%.2f", (double) data[Constants.DATA_INDICES.NUM_SWAPS]) + " " +  
                "Avg. Insertions: "  + String.format("%.2f", (double) data[Constants.DATA_INDICES.NUM_INSERTIONS]), 0, 50);

            // Y position of the restart button
            int restartYPosition= 57;
            if (data[Constants.DATA_INDICES.NUM_TIME] > 0) {
                restartYPosition= 100 - 18;
                graphics.drawString("Avg. Time (nanoseconds): " + String.format("%,d", data[Constants.DATA_INDICES.NUM_TIME]/(data[Constants.DATA_INDICES.NUM_SIMULATIONS]-1)), 0, 75);
            }
                
            // Creates the restart button
            graphics.drawRect(0, restartYPosition, 225, 25);
            graphics.drawString("New Simulation", 0, restartYPosition + 18);
            restart= new Rectangle2D.Double(0, restartYPosition, 225, 25);
        }
    }

    // Function is called when user clicks on the screen
    public void onClick(int x, int y) {

        // If the restart button is clicked, then go back to main menu screen
        if (restart.contains((double) x, (double) y))
            main.start();
    }

    public void updateRender(int[] array, int[] pointers, long[] data, Constants.Mode mode) {
        this.array= array;
        this.pointers= pointers;
        this.data= data;        
        this.mode= mode;
        render();
        try {
            Thread.sleep(4);
         } catch (InterruptedException e) { }
    }
}
