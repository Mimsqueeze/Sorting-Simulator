import java.awt.*;
import java.awt.geom.Rectangle2D;


public class GraphScreen {

    // Reference to Main Program
    Main main;

    // Reference to Graphics2D used by Main
    Graphics2D graphics;

    // Debugging: Speed up option
    boolean speedUp= false;
    
    // Constants defining the color of the graph
    private static final Color BARCOLOR= Color.GREEN;
    private static final Color SWAPCOLOR= Color.RED;
    private static final Color COMPARECOLOR= Color.YELLOW;
    private static final Color INSERTCOLOR= Color.ORANGE;
    private static final Color READCOLOR= Color.BLUE;
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

    // Largest element of the array for scaling rectangles
    private int LARGEST;

    // The array of positions to be highlighted
    private int[] pointers= new int[0];

    // Statistics to be displayed
    private long[] data;

    // Render mode
    private Constants.Mode mode;

    // Whether of not to wait for click to continue next step
    private boolean wait;
    // graphScreen constructor to create a new frame to render
    GraphScreen(Main main, int size, boolean wait) {
        this.main= main;
        this.SIZE= size;
        this.LARGEST= size;
        this.wait= wait;
    }

    // Sets the size of the array
    public void setSize(int size) {
        SIZE= size;
    }

    // Sets the largest element of the array
    public void setLargest(int largest) {
        LARGEST= largest;
    }
    
    // Function called to render each frame of the graph
    public void render() {
        this.graphics= (Graphics2D) main.panel.getGraphics();

        // Fill in the rectangles (bars) for the graph
        for (int i= 0; i < SIZE; i++) {

            // Fill the bar
            graphics.setColor(BARCOLOR);
            graphics.fillRect(i*Constants.SCREEN_SIZES.WIDTH/SIZE, Constants.SCREEN_SIZES.HEIGHT - (Constants.SCREEN_SIZES.BARHEIGHT*array[i]/LARGEST), (Constants.SCREEN_SIZES.WIDTH/SIZE)+1, Constants.SCREEN_SIZES.HEADER + (Constants.SCREEN_SIZES.BARHEIGHT*array[i]/LARGEST));
           
            // Fill space above the bar
            graphics.setColor(EMPTYCOLOR);
            graphics.fillRect(i*Constants.SCREEN_SIZES.WIDTH/SIZE, Constants.SCREEN_SIZES.HEADER, (Constants.SCREEN_SIZES.WIDTH/SIZE)+1, Constants.SCREEN_SIZES.BARHEIGHT - (Constants.SCREEN_SIZES.BARHEIGHT*array[i]/LARGEST));
        }
        if (mode != Constants.Mode.DEFAULT && mode != Constants.Mode.FINISH) {
            if (mode == Constants.Mode.COMPARE) { // Comparing
                // System.out.println("Comparing");
                graphics.setColor(COMPARECOLOR);
            } else if (mode == Constants.Mode.SWAP) { // Swapping
                // System.out.println("Swapping");
                graphics.setColor(SWAPCOLOR);
            } else if (mode == Constants.Mode.INSERT) { // Inserting
                // System.out.println("Inserting");
                graphics.setColor(INSERTCOLOR);
            } else if (mode == Constants.Mode.READ) { // Reading
                // System.out.println("Reading");
                graphics.setColor(READCOLOR);
            }
            // If sound enabled, split execution to play a sound while updating the highlighted positions
            if (main.soundOn) {
                for (int i= 0; i < pointers.length; i++) {
                    graphics.fillRect(pointers[i]*Constants.SCREEN_SIZES.WIDTH/SIZE, Constants.SCREEN_SIZES.HEIGHT - (Constants.SCREEN_SIZES.BARHEIGHT*array[pointers[i]]/SIZE), (Constants.SCREEN_SIZES.WIDTH/SIZE)+1, Constants.SCREEN_SIZES.HEADER + (Constants.SCREEN_SIZES.BARHEIGHT*array[pointers[i]]/SIZE));
                
                    Sound.makeSound(pointers[i], SIZE);
                }
            } else {
                for (int i= 0; i < pointers.length; i++)
                    graphics.fillRect(pointers[i]*Constants.SCREEN_SIZES.WIDTH/SIZE, Constants.SCREEN_SIZES.HEIGHT - (Constants.SCREEN_SIZES.BARHEIGHT*array[pointers[i]]/SIZE), (Constants.SCREEN_SIZES.WIDTH/SIZE)+1, Constants.SCREEN_SIZES.HEADER + (Constants.SCREEN_SIZES.BARHEIGHT*array[pointers[i]]/SIZE));
            }
        }
        
        // Fill Header Information
        displayInformation();
        
        //
        if (wait && mode != Constants.Mode.DEFAULT && mode != Constants.Mode.FINISH) {
            try {
                System.in.read();
                System.in.read();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void displayInformation() {
        // Fill header
        graphics.setColor(HEADERCOLOR);
        graphics.fillRect(0, 0, Constants.SCREEN_SIZES.WIDTH, Constants.SCREEN_SIZES.HEADER);

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
        } else {
            // Finished, so display information and render the restart button
            graphics.drawString(
                "Avg. Comparisons: " + String.format("%.2f", (double) data[Constants.DATA_INDICES.NUM_COMPARISONS] / data[Constants.DATA_INDICES.NUM_SIMULATIONS]) + " " + 
                "Avg. Swaps: "       + String.format("%.2f", (double) data[Constants.DATA_INDICES.NUM_SWAPS] / data[Constants.DATA_INDICES.NUM_SIMULATIONS]) + " " +  
                "Avg. Insertions: "  + String.format("%.2f", (double) data[Constants.DATA_INDICES.NUM_INSERTIONS] / data[Constants.DATA_INDICES.NUM_SIMULATIONS]), 0, 50);

            // Y position of the restart button
            int restartYPosition= 57;
            if (data[Constants.DATA_INDICES.NUM_TIME] > 0) {
                restartYPosition= 100 - 18;
                graphics.drawString("Avg. Time (nanoseconds): " + String.format("%,d", data[Constants.DATA_INDICES.NUM_TIME] / data[Constants.DATA_INDICES.NUM_SIMULATIONS]), 0, 75);
            }
                
            // Creates the restart button
            graphics.drawRect(0, restartYPosition, 225, 25);
            graphics.drawString("New Simulation", 0, restartYPosition + 18);
            restart= new Rectangle2D.Double(0, restartYPosition, 225, 25);
        }
    }

    // Function is called when user clicks on the screen
    public void onClick(int x, int y) {
        if (mode != Constants.Mode.WAITING)
            render();
        // If the restart button is clicked, then go back to main menu screen
        if (mode == Constants.Mode.FINISH && restart.contains((double) x, (double) y)) {
            mode= Constants.Mode.WAITING;
            main.start();
        }
    }

    public void updateRender(int[] array, int[] pointers, long[] data, Constants.Mode mode) {
        this.array= array;
        this.pointers= pointers;
        this.data= data;        
        this.mode= mode;
        render();
    }
}
