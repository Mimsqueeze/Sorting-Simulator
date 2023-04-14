public final class Constants {
    // Private constructor to prevent instantiation
    private Constants(){}

    // Enum to declare different states of rendering the graph
    public static enum Mode {DEFAULT, COMPARE, SWAP, INSERT, READ, FINISH};

    // Class to store screen sizes
    public final static class SCREEN_SIZES {
        public static final int WIDTH= 1200; 
        public static final int HEIGHT= 600;
        public static final int HEADER= 75;
        public static final int BARHEIGHT= HEIGHT - HEADER;
    }

    // Class to store sorting algorithm names
    public final static class SORTING_ALG_NAMES {
        public static final String BUBBLE= "Bubble Sort";
        public static final String SELECTION= "Selection Sort";
        public static final String INSERTION= "Insertion Sort"; 
        public static final String QUICK= "Quick Sort";
        public static final String MERGE= "Merge Sort";
        public static final String HEAP= "Heap Sort";
        public static final String INTRO= "Intro Sort";
        public static final String BOZO= "Bozo Sort";
    }

    // Class to store indices of data
    public final static class DATA_INDICES {
        static final short NUM_COMPARISONS=  0;
        static final short NUM_SWAPS=        1;
        static final short NUM_INSERTIONS=   2;
        static final short NUM_READS=        3;
        static final short NUM_TIME=         4;
        static final short NUM_SIMULATIONS=  5;

        // Not an index, is the size of the data array
        static final int DATA_SIZE= 6;
    };
}