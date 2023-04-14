import java.util.LinkedList;

public class Sim {
    private GraphScreen graphScreen;
    private int size;
    private String algorithm;
    private int totalSimulations;
    private int[] arr;
    private long[] data= new long[Constants.DATA_SIZE];

    public Sim (GraphScreen graphScreen, int size, String algorithm, int totalSimulations) {
        this.graphScreen= graphScreen;
        this.size= size;
        this.algorithm= algorithm;
        this.totalSimulations= totalSimulations;
    }

    public void runSimulation() {
        // Variables to track running time
        long finish, start;

        // Integer array to store array to be sorted
        arr= new int[size];

        // Randomizes the array's elements
        randomizeArray();

        // Update and Render the Graph Screen
        graphScreen.updateRender(arr, null, data, Constants.Mode.DEFAULT);

        /*
        if (show) {
            for (int i= 0; i < numSims - 1; i++) {
                arr= createArray(n);
                bubbleSort(n, arr);
            }
        } else {
            for (int i= 0; i < numSims - 1; i++) {
                arr= createArray(n);
                start= System.nanoTime();
                _bubbleSort(n, arr);
                finish= System.nanoTime();
                totalSortingTime += finish - start;
            }
        }
        */

        // Find correct sorting algorithm and execute it
        if (algorithm.equals("Bubble Sort")) {
            bubbleSort();
        } else if (algorithm.equals("Selection Sort")) {
            selectionSort();
        } else if (algorithm.equals("Insertion Sort")) {
            insertionSort();
        } else if (algorithm.equals("Quick Sort")) {
            quickSort();
        } /* else if (algorithm.equals("Merge Sort")) {
            mergeSort();
        } else if (algorithm.equals("Heap Sort")) {
            heapSort();
        } else if (algorithm.equals("Intro Sort")) {
            introSort();
        } else if (algorithm.equals("Bogo Sort")) {
            bogoSort();
        }
        */

        // Update and Render the Graph Screen with mode FINISH
        graphScreen.updateRender(arr, null, data, Constants.Mode.FINISH);
    }

    // Helper function to print array
    private void printArray() {
        for (int elt : arr)
            System.out.print(elt + " ");

        System.out.println(); 
    }

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
        graphScreen.updateRender(arr, pointers, data, Constants.Mode.SWAP);

        // Swap the elements
        int temp= arr[a];
        arr[a]= arr[b];
        arr[b]= temp;

        // Update the screen after the swap
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

    // Sorting algorithm 3: quick sort
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
    /* 
    // Sorting algorithm 4: merge sort
    private void mergeSort(int n, int[] arr) {
        mergeSortHelper(arr, 0, n-1);
    }

    private void mergeSortHelper(int[] arr, int l, int r) {
        if (l < r) {
            int m= l + (r - l) / 2;

            mergeSortHelper(arr, l, m);
            mergeSortHelper(arr, m + 1, r);
 
            merge(arr, l, m, r);
        }
    }

    private void merge(int[] arr, int l, int m, int r) {
        int n1= m - l + 1;
        int n2= r - m;
 
        int[] L= new int[n1];
        int[] R= new int[n2];
 
        // Copy the values into temporary left and right arrays
        for (int i= 0; i < n1; ++i)
            L[i]= arr[l + i];
        for (int j= 0; j < n2; ++j)
            R[j]= arr[m + 1 + j];
 
        // Initial indexes of first and second subarrays
        int i= 0, j= 0;
 
        while (i < n1 && j < n2) {
            // inspecting
            data[0]++;
            int[] pointers= {l};
            main.updateUI(arr, pointers, this.n, Constants.INSPECTING, data); 
            if (L[i] <= R[j]) {
                data[2]++;
                arr[l]= L[i];
                i++;
            } else {
                data[2]++;
                arr[l]= R[j];
                j++;
            }
            l++;
        }
        
        while (i < n1) {
            // inspecting
            data[0]++;
            int[] pointers= {l};
            main.updateUI(arr, pointers, this.n, Constants.INSPECTING, data); 
            arr[l]= L[i];
            i++;
            l++;
        }
 
        while (j < n2) {
            // inspecting
            data[0]++;
            int[] pointers= {l};
            main.updateUI(arr, pointers, this.n, Constants.INSPECTING, data); 
            arr[l]= R[j];
            j++;
            l++;
        }
    }

    // Sorting algorithm 5: heap sort
    private void heapSort(int n, int[] arr) {
        for (int i= n / 2 - 1; i >= 0; i--)
            heapify(arr, n, i, n);
 
        for (int i= n - 1; i > 0; i--) {
            swap(arr, i, 0);
            heapify(arr, i, 0, n);
        }
    }
 
    private void heapify(int[] arr, int n, int i, int size) {
        int largest= i; 
        int l= 2*i + 1;
        int r= 2*i + 2;

        int[] pointers= null;

        if (l < n) {
            data[0]++;
            pointers= new int[]{l, i};
            if (arr[l] > arr[largest]) 
                largest= l;
        }
 
        if (r < n) {
            data[0]++;
            if (pointers == null) {
                pointers= new int[]{r, i};
            } else {
                pointers= new int[]{l, r, i};
            }
            if (arr[r] > arr[largest])
                largest= r;
        }

        if (pointers == null) {
            pointers= new int[]{i};
        }

        main.updateUI(arr, pointers, this.n, Constants.INSPECTING, data); 

        if (largest != i) {
            swap(arr, i, largest);
            heapify(arr, n, largest, size);
        }
    }

    
    // Sorting algorithm 6: intro sort
    private void introSort(int n, int[] arr) {
        introSortData(arr, n);
    }

    private void introSortMaxHeap(int[] arr, int i, int heapN, int begin) {
        int temp= arr[begin + i - 1];
        int child;
 
        while (i <= heapN / 2) {
            child= 2 * i;
 
            if (child < heapN) {
                if (arr[begin + child - 1] < arr[begin + child])
                    child++;
                // inspecting
                data[0]++;
                int pointers[]= {begin + child - 1, begin + child};
                main.updateUI(arr, pointers, this.n, Constants.INSPECTING, data); 
            } 
 
            if (temp >= arr[begin + child - 1])
                break;
 
            arr[begin + i - 1]= arr[begin + child - 1];
            i= child;
        }
        arr[begin + i - 1]= temp;
    }
 
    private void introSortHeapify(int[] arr, int begin, int end, int heapN)
    {
        for (int i= (heapN) / 2; i >= 1; i--)
            introSortMaxHeap(arr, i, heapN, begin);
    }
 
    private void introSortHeapSort(int[] arr, int begin, int end)
    {
        int heapN= end - begin;
 
        introSortHeapify(arr, begin, end, heapN);
 
        for (int i= heapN; i >= 1; i--) {
            swap(arr, begin, begin + i);
 
            introSortMaxHeap(arr, 1, i, begin);
        }
    }
 
    private void introSortInsertionSort(int[] arr, int left, int right) {
        for (int i= left; i <= right; i++) {
            int key= arr[i];
            int j= i;

            while (j > left && arr[j - 1] > key) {
                data[0]++;
                int[] pointers= {j, j - 1};
                main.updateUI(arr, pointers, this.n, Constants.INSPECTING, data);

                data[2]++;
                arr[j]= arr[j - 1];
                j--;
            }
            arr[j]= key;
        }
    }
 
    private int introSortFindPivot(int[] arr, int a1, int b1, int c1)
    {
        int max= Math.max(Math.max(arr[a1], arr[b1]), arr[c1]);
        int min= Math.min(Math.min(arr[a1], arr[b1]), arr[c1]);
        int median= max ^ min ^ arr[a1] ^ arr[b1] ^ arr[c1];
        if (median == arr[a1])
            return a1;
        if (median == arr[b1])
            return b1;
        return c1;
    }
 
    private int introSortPartition(int[] arr, int low, int high) {
 
        int pivot= arr[high];
 
        int i= (low - 1);
        for (int j= low; j <= high - 1; j++) {
            if (arr[j] <= pivot) {
                i++;
                swap(arr, i, j);
            } else {
                data[0]++;
                int[] pointers= {j, j+1};
                main.updateUI(arr, pointers, this.n, Constants.INSPECTING, data);
            }
        }
        swap(arr, i + 1, high);
        return (i + 1);
    }
 
    private void introSortDataUtil(int[] arr, int begin, int end, int depthLimit) {
        if (end - begin > 16) {
            if (depthLimit == 0) {
                introSortHeapSort(arr, begin, end);
                return;
            }
 
            depthLimit= depthLimit - 1;
            int pivot= introSortFindPivot(arr, begin, begin + ((end - begin) / 2) + 1, end);
            swap(arr, pivot, end);
 
            int p= introSortPartition(arr, begin, end);
 
            introSortDataUtil(arr, begin, p - 1, depthLimit);
            introSortDataUtil(arr, p + 1, end, depthLimit);
        } else {
            introSortInsertionSort(arr, begin, end);
        }
    }
 
    private void introSortData(int[] arr, int n){
        int depthLimit= (int)(2 * Math.floor(Math.log(n) / Math.log(2)));
 
        introSortDataUtil(arr, 0, n - 1, depthLimit);
    }

    // Sorting algorithm 7: bogo sort
    private void bogoSort(int n, int[] arr) {
        while (!checkSorted(n, arr)) {
            int a, b;
            a= (int) (Math.random()*n);
            b= (int) (Math.random()*n);
            swap(arr, a, b);
        }
    }

    private boolean checkSorted(int n, int[] arr) {
        for (int i= 0; i < n - 1; i++) {
            data[0]++;
            int[] pointers= {i, i+1};
            main.updateUI(arr, pointers, this.n, Constants.INSPECTING, data);
            if (arr[i] > arr[i + 1]) {
                return false;
            }
        }
        return true;
    }
    */
}