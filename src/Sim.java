import java.util.LinkedList;

public class Sim {

    // Store reference to the GraphScreen
    private GraphScreen graphScreen;

    // Store size of array
    private int size;

    // Store the array to be sorted
    private int[] arr;

    // Store the algorithm
    private String algorithm;

    // Store the total number of simulations
    private int totalSimulations;

    // Store the data
    private long[] data= new long[Constants.DATA_INDICES.DATA_SIZE];

    // Store whether to show all simulations or not
    private boolean show;

    // Sim constructor
    public Sim (GraphScreen graphScreen, int size, String algorithm, int totalSimulations, boolean show) {
        this.graphScreen= graphScreen;
        this.size= size;
        this.algorithm= algorithm;
        this.totalSimulations= totalSimulations;
        this.show= show;
    }

    public void runSimulation() {
        // Variables to track running time
        long start, finish;

        // Integer array to store array to be sorted
        arr= new int[size];

        // Update and Render the Graph Screen
        graphScreen.updateRender(arr, null, data, Constants.Mode.DEFAULT);

        // Split execution based on whether to show all simulations
        if (show) {
            // Goes here if show all simulations
            for (int i= 1; i <= totalSimulations; i++) {

                // Randomize the array
                randomizeArray();

                // Update the simulation number
                data[Constants.DATA_INDICES.NUM_SIMULATIONS]= i;

                // Start tracking time and begin sorting
                start= System.nanoTime();
                sort();
                finish= System.nanoTime();

                // Update the time
                data[Constants.DATA_INDICES.NUM_TIME] += finish - start;

                // Update the graph screen
                graphScreen.updateRender(arr, null, data, Constants.Mode.DEFAULT);
            }
        } else {
            // Goes here if don't show all simulations

            // Sets show to true for a single simulation to be displayed
            show= true;

            // Randomize the array
            randomizeArray();

            // Update the Simulation Number
            data[Constants.DATA_INDICES.NUM_SIMULATIONS]= 1;

            // Sort the array
            sort();

            // Update the graph screen
            graphScreen.updateRender(arr, null, data, Constants.Mode.DEFAULT);

            // Turn show to off
            show= false;
            
            // Refresh the data array
            data= new long[Constants.DATA_INDICES.DATA_SIZE];

            // Run totalSimulations without displaying
            for (int i= 1; i <= totalSimulations; i++) {

                // Randomize the array
                randomizeArray();

                // Start tracking time and begin sorting
                start= System.nanoTime();
                sort();
                finish= System.nanoTime();

                // Update the time
                data[Constants.DATA_INDICES.NUM_TIME] += finish - start;
            }
        }

        // Update NUM_SIMULTATIONS
        data[Constants.DATA_INDICES.NUM_SIMULATIONS]= totalSimulations;

        // Update and Render the Graph Screen with mode FINISH
        graphScreen.updateRender(arr, null, data, Constants.Mode.FINISH);
    }

    // Finds the correct sorting algorithm and executes it
    private void sort() {
        if (algorithm.equals(Constants.SORTING_ALG_NAMES.BUBBLE)) {
            bubbleSort();
        } else if (algorithm.equals(Constants.SORTING_ALG_NAMES.SELECTION)) {
            selectionSort();
        } else if (algorithm.equals(Constants.SORTING_ALG_NAMES.INSERTION)) {
            insertionSort();
        } else if (algorithm.equals(Constants.SORTING_ALG_NAMES.QUICK)) {
            quickSort();
        } else if (algorithm.equals(Constants.SORTING_ALG_NAMES.MERGE)) {
            mergeSort();
        } else if (algorithm.equals(Constants.SORTING_ALG_NAMES.HEAP)) {
            heapSort();
        } else if (algorithm.equals(Constants.SORTING_ALG_NAMES.INTRO)) {
            introSort();
        } else if (algorithm.equals(Constants.SORTING_ALG_NAMES.BOZO)) {
            bozoSort();
        }
    }

    /* 
    // Optional Helper function to print array
    private void printArray() {
        for (int elt : arr)
            System.out.print(elt + " ");

        System.out.println(); 
    }
    */

    // Creates array of size 1 to n, with elements of length 1 to n
    private void randomizeArray() {

        // Creates a LinkedList to store numbers to add into arr
        LinkedList<Integer> nums= new LinkedList<Integer>();

        // fill nums with numbers
        for (int i= 1; i <= size; i++)
            nums.add(i);

        // pick a random index in nums and put it into arr backwards
        for (int i= size - 1; i >= 0; i--) {
            int index= (int) (Math.random()*(i+1));
            int element= nums.remove(index);
            arr[i]= element;
        }
    }

    // Swaps elements at indices a and b and updates graphScreen
    private boolean swap(int a, int b) {
        // Create array of pointers
        int pointers[]= {a, b};

        // Update number of swaps
        data[Constants.DATA_INDICES.NUM_SWAPS]++;

        // Update the screen before the swap
        if (show && !graphScreen.speedUp)
            graphScreen.updateRender(arr, pointers, data, Constants.Mode.SWAP);

        // Swap the elements
        int temp= arr[a];
        arr[a]= arr[b];
        arr[b]= temp;

        // Update the screen after the swap
        if (show)
            graphScreen.updateRender(arr, pointers, data, Constants.Mode.SWAP);

        // Return true as in swap successful
        return true;
    }
    
    // Compares the elements at indices a and b and updates graphScreen
    private int compare(int a, int b) {
        // Create array of pointers
        int pointers[]= {a, b};

        // Update number of comparisons
        data[Constants.DATA_INDICES.NUM_COMPARISONS]++;

        // Update the screen
        if (show && !graphScreen.speedUp)
            graphScreen.updateRender(arr, pointers, data, Constants.Mode.COMPARE);

        // Returns 0 if equal, -1 if arr[a] is less than arr[b], 1 if arr[a] is greater than arr[b]
        return (arr[a] == arr[b]) ? (0) : ((arr[a] < arr[b]) ? (-1) : (1));
    }

    // Compares the element at indices a and value v and updates graphScreen
    private int compareV(int a, int v) {
        // Create array of pointers
        int pointers[]= {a};

        // Update number of comparisons
        data[Constants.DATA_INDICES.NUM_COMPARISONS]++;

        // Update the screen
        if (show && !graphScreen.speedUp)
            graphScreen.updateRender(arr, pointers, data, Constants.Mode.COMPARE);

        // Returns 0 if equal, -1 if arr[a] is less than arr[b], 1 if arr[a] is greater than arr[b]
        return (arr[a] == v) ? (0) : ((arr[a] < v) ? (-1) : (1));
    }

    // Insert the element at index b into index a and updates graphScreen
    private void insert(int a, int b) {
        // Create array of pointers
        int pointers[]= {a, b};

        // Update number of insertions
        data[Constants.DATA_INDICES.NUM_INSERTIONS]++;

        // Update the screen
        if (show)
            graphScreen.updateRender(arr, pointers, data, Constants.Mode.INSERT);

        // Copies value in b into a
        arr[a]= arr[b];
    }

    // Insert the value v into index a and updates graphScreen
    private void insertV(int a, int v) {
        // Create array of pointers
        int pointers[]= {a};

        // Update number of insertions
        data[Constants.DATA_INDICES.NUM_INSERTIONS]++;

        // Update the screen
        if (show)
            graphScreen.updateRender(arr, pointers, data, Constants.Mode.INSERT);

        // Copies value v into a
        arr[a]= v;
    }

    // Reads the value at index a, updates graphScreen, and returns the value
    private int read(int a) {
        // Create array of pointers
        int pointers[]= {a};

        // Update number of insertions
        data[Constants.DATA_INDICES.NUM_READS]++;

        // Update the screen
        if (show && !graphScreen.speedUp)
            graphScreen.updateRender(arr, pointers, data, Constants.Mode.READ);

        // Returns value at index a
        return arr[a];
    }

    // Sorting algorithm 1: Bubble Sort
    private void bubbleSort() {
        int i, j;
        boolean swapped;
        for (i= 0; i <= size - 1; i++) {
            swapped= false;
            for (j= 0; j < size - i - 1; j++) {
                if (compare(j, j + 1) > 0)
                    swapped= swap(j, j + 1);
            }
            if (!swapped)
                break;
        }
    }
    

    // Sorting algorithm 2: Selection Sort
    private void selectionSort() {
        for (int i= 0; i < size - 1; i++) {
            // Index of minimum elt
            int min= i;

            for (int j= i + 1; j < size; j++) {
                if (compare(j, min) < 0)
                    min= j;
            }
            swap(i, min);
        }
    }
    
    // Sorting algorithm 3: Insertion Sort
    private void insertionSort() {
        for (int i= 1; i < size; i++) {
            int value= read(i);
            int j= i - 1;

            while ((j >= 0) && (compareV(j, value) > 0)) {
                insert(j + 1, j);
                j--; 
            }

            insertV(j + 1, value);
        }
    }

    // Sorting algorithm 4: Quick Sort
    private void quickSort() {
        quickSortHelper(0, size - 1);
    }

    // Quick sort recursive helper method
    void quickSortHelper(int low, int high) {
        if (low < high) {
            int partitionIndex= partition(low, high);
            quickSortHelper(low, partitionIndex - 1);
            quickSortHelper(partitionIndex + 1, high);
        }
    }

    // Partition list for quick sort
    int partition(int low, int high) {
        int pivot= read(high);
        int i= low - 1;
        for (int j= low; j <= high - 1; j++) {  
            if (compareV(j, pivot) < 0) {
                i++;
                swap(i, j);
            } 
        }
        swap(i + 1, high);
        return i + 1;
    }
 
    // Sorting algorithm 5: Merge Sort
    private void mergeSort() {
        mergeSortHelper(0, size - 1);
    }

    private void mergeSortHelper(int l, int r) {
        if (l < r) {
            // Get middle index
            int m= l + (r - l) / 2;

            // Recursive call on subarrays
            mergeSortHelper(l, m);
            mergeSortHelper(m + 1, r);

            // Merge the subarrays
            merge(l, m, r);
        }
    }

    private void merge(int l, int m, int r) {
        // Size of auxiliary arrays
        int n1= m - l + 1;
        int n2= r - m;
        
        // Create auxiliary arrays
        int[] L= new int[n1];
        int[] R= new int[n2];
 
        // Copy the values into auxiliary arrays
        for (int i= 0; i < n1; ++i)
            L[i]= read(l + i);
        for (int j= 0; j < n2; ++j)
            R[j]= read(m + 1 + j);
 
        // Initial indexes of first and second auxiliary arrays
        int i= 0, j= 0;
        
        // Write from auxiliaries into main array
        while (i < n1 && j < n2) {
            if (L[i] <= R[j])
                insertV(l++, L[i++]);
            else
                insertV(l++, R[j++]);
        }
        
        // Fill in rest of auxiliary array into main array
        while (i < n1)
            insertV(l++, L[i++]);
        while (j < n2)
            insertV(l++, R[j++]);
    }

    // Sorting algorithm 6: Heap Sort
    private void heapSort() {
        for (int i= size / 2 - 1; i >= 0; i--)
            heapify(size, i);
 
        for (int i= size - 1; i > 0; i--) {
            swap(i, 0);
            heapify(i, 0);
        }
    }
 
    private void heapify(int n, int i) {
        int largest= i; 
        int l= 2*i + 1;
        int r= 2*i + 2;

        if (l < n && compare(l, largest) > 0) 
            largest= l;
 
        if (r < n && compare(r, largest) > 0)
            largest= r;

        if (largest != i) {
            swap(i, largest);
            heapify(n, largest);
        }
    }


    // Sorting algorithm 7: Intro Sort
    private void introSort() {
        int depthLimit= (int)(2 * Math.floor(Math.log(size) / Math.log(2)));
 
        introSortHelper(0, size - 1, depthLimit);
    }

    private void introSortHelper(int begin, int end, int depthLimit) {
        if (end - begin > 16) {
            if (depthLimit == 0) {
                introSortHeapSort(begin, end);
            } else {
                introSortQuickSort(begin, end, depthLimit - 1);
            }
        } else {
            introSortInsertionSort(begin, end);
        }
    }

    private void introSortInsertionSort(int left, int right) {
        for (int i= left; i <= right; i++) {
            int value= read(i);
            int j= i;

            while (j > left && compareV(j - 1, value) > 0) {
                insert(j, j - 1);
                j--;
            }

            insertV(j, value);
        }
    }
    
    private void introSortHeapSort(int begin, int end) {
        int n= end - begin;
        int half= n / 2;
        for (int i= half; i >= 1; i--)
            introSortHeapify(i, n, begin);
 
        for (int i= n; i >= 1; i--) {
            swap(begin, begin + i);
            introSortHeapify(1, i, begin);
        }
    }

    private void introSortHeapify(int i, int n, int begin) {
        int temp= read(begin + i - 1);
        int child;
        int half= n / 2;
 
        while (i <= half) {
            child= 2 * i;
 
            if (child < n && compare(begin + child - 1, begin + child) < 0)
                    child++;
 
            if (compareV(begin + child - 1, temp) < 0)
                break;
 
            insert(begin + i - 1, begin + child - 1);
            i= child;
        }
        insertV(begin + i - 1, temp);
    }

    private void introSortQuickSort(int begin, int end, int depthLimit) {
        /*
        // Optional Median-Of-Three method for choosing pivots
        int pivot= introSortFindPivot(begin, begin + ((end - begin) / 2) + 1, end);
        swap(pivot, end);
        */

        int p= partition(begin, end); // Uses partition from Quick Sort

        introSortHelper(begin, p - 1, depthLimit);
        introSortHelper(p + 1, end, depthLimit);
    }

    /*
    // Optional method: Median-Of-Three for choosing pivot for Intro Sort
    private int introSortFindPivot(int a1, int b1, int c1) {
        int max= Math.max(Math.max(arr[a1], arr[b1]), arr[c1]);
        int min= Math.min(Math.min(arr[a1], arr[b1]), arr[c1]);
        int median= max ^ min ^ arr[a1] ^ arr[b1] ^ arr[c1];
        if (median == arr[a1])
            return a1;
        if (median == arr[b1])
            return b1;
        return c1;
    }
    */
 
    // Sorting algorithm 8: Bozo Sort
    private void bozoSort() {
        while (!checkSorted()) {
            int a, b;
            a= (int) (Math.random()*size);
            b= (int) (Math.random()*size);
            swap(a, b);
        }
    }

    private boolean checkSorted() {
        for (int i= 0; i < size - 1; i++) {
            if (compare(i, i + 1) > 0)
                return false;
        }
        return true;
    }
}