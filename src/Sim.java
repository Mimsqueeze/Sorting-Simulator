import java.util.LinkedList;

public class Sim {
    Main main;

    /* Runs Simulation given: 
     - n: number of elements
     - s: sorting algorithm
    */ 
    int n;

    public void runSimulation(Main main, int n, String s) {
        int[] arr = createArray(n);

        this.n = n;
        
        this.main = main;
        main.updateUI(arr, n);

        printArray(arr);

        // find correct sorting algorithm
        if (s.equals("Bubble Sort")) {
            System.out.println("Running Bubble Sort: ");
            bubbleSort(n, arr);
        } else if (s.equals("Selection Sort")) {
            System.out.println("Running Selection Sort: ");
            selectionSort(n, arr);
        } else if (s.equals("Insertion Sort")) {
            System.out.println("Running Insertion Sort: ");
            insertionSort(n, arr);
        } else if (s.equals("Quick Sort")) {
            System.out.println("Running Quick Sort:");
            quickSort(n, arr);
        } else if (s.equals("Merge Sort")) {
            System.out.println("Running Merge Sort:");
            mergeSort(n, arr);
        } else if (s.equals("Heap Sort")) {
            System.out.println("Running Heap Sort:");
            heapSort(n, arr);
        } else if (s.equals("Intro Sort")) {
            System.out.println("Running Intro Sort:");
            introSort(n, arr);
        }
        main.updateUI(arr, n);

        printArray(arr);
    }

    // prints array
    private  void printArray(int[] arr) {
        for (int elt : arr) {
            System.out.print(elt + " ");
        } 
        System.out.println(); 
    }

    // Creates array of size 1 to n, with elements of length 1 to n
    private  int[] createArray(int n) {
        int[] arr = new int[n];

        LinkedList<Integer> nums = new LinkedList<Integer>();

        // fill nums with numbers
        for (int i = 0; i < n; i++) {
            nums.add(i+1);
        }

        // pick a random index in nums and put it into arr backwards
        for (int i = n - 1; i >= 0; i--) {
            int index = (int) (Math.random()*(i+1));
            int element = nums.remove(index);
            arr[i] = element;
        }

        return arr;
    }

    // Swaps elements at indices a and b
    private  boolean swap(int[] arr, int a, int b) {
        int pointers[] = {a, b};
        main.updateUI(arr, pointers, this.n, false);
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
        return true;
    }
    
    // Sorting algorithm 0: bubble sort
    private  void bubbleSort(int n, int[] arr) {
        int i, j;
        boolean swapped;
        for (i = 0; i < n - 1; i++) {
            swapped = false;
            for (j = 0; j < n - i - 1; j++) {
                // inspecting
                int pointers[] = {j, j + 1};
                if (arr[j] > arr[j + 1]) {
                    swapped = swap(arr, j, j+1);
                } else {
                    main.updateUI(arr, pointers, this.n, true);
                }
            }
            if (!swapped)
                break;
        }
    }
    
    // Sorting algorithm 1: selection sort
    private  void selectionSort(int n, int[] arr) {
        for (int i = 0; i < n-1; i++) {
            // Index of minimum elt
            int min = i;

            for (int j = i + 1; j < n; j++) {
                // inspecting
                int pointers[] = {j, min};
                main.updateUI(arr, pointers, this.n, true);
                if (arr[j] < arr[min]) {
                    min = j;
                }
            }
            
            swap(arr, i, min);
        }
    }

    // Sorting algorithm 2: insertion sort
    private  void insertionSort(int n, int[] arr) {
        for (int i = 1; i < n; i++) {
            int insert = arr[i];
            int j = i-1;

            while ((j >= 0) && (arr[j] > insert)) {
                // inspecting
                int pointers[] = {j, j+1};
                main.updateUI(arr, pointers, this.n, true);  
                arr[j+1] = arr[j];
                j--; 
            }  

            arr[j+1] = insert;
        }
    }

    // Sorting algorithm 3: quick sort
    private  void quickSort(int n, int[] arr) {
        quickSortHelper(arr, 0, n-1);
    }

    // Partition list for quick sort
     int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = low - 1;
        for (int j = low; j <= high - 1; j++) {
            if (arr[j] < pivot) {
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, high);
        return i + 1;
    }

    // quick sort recursive helper method
     void quickSortHelper(int[] arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);
            quickSortHelper(arr, low, pi - 1);
            quickSortHelper(arr, pi + 1, high);
        }
    }

    // Sorting algorithm 4: merge sort
    private  void mergeSort(int n, int[] arr) {
        mergeSortHelper(arr, 0, n-1);
    }

    private  void mergeSortHelper(int arr[], int l, int r) {
        if (l < r) {
            int m = l + (r - l) / 2;

            mergeSortHelper(arr, l, m);
            mergeSortHelper(arr, m + 1, r);
 
            merge(arr, l, m, r);
        }
    }

    private  void merge(int arr[], int l, int m, int r) {
        int n1 = m - l + 1;
        int n2 = r - m;
 
        int L[] = new int[n1];
        int R[] = new int[n2];
 
        // Copy the values into temporary left and right arrays
        for (int i = 0; i < n1; ++i)
            L[i] = arr[l + i];
        for (int j = 0; j < n2; ++j)
            R[j] = arr[m + 1 + j];
 
        // Initial indexes of first and second subarrays
        int i = 0, j = 0;
 
        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                arr[l] = L[i];
                i++;
            } else {
                arr[l] = R[j];
                j++;
            }
            l++;
        }
        
        while (i < n1) {
            arr[l] = L[i];
            i++;
            l++;
        }
 
        while (j < n2) {
            arr[l] = R[j];
            j++;
            l++;
        }
    }

    // Sorting algorithm 5: heap sort
    private  void heapSort(int n, int[] arr) {
        for (int i = n / 2 - 1; i >= 0; i--)
            heapify(arr, n, i);
 
        for (int i = n - 1; i > 0; i--) {
            swap(arr, i, 0);
            heapify(arr, i, 0);
        }
    }
 
    private  void heapify(int arr[], int n, int i) {
        int largest = i; 
        int l = 2*i + 1;
        int r = 2*i + 2;
 
        if (l < n && arr[l] > arr[largest])
            largest = l;
 
        if (r < n && arr[r] > arr[largest])
            largest = r;
 
        if (largest != i) {
            swap(arr, i, largest);
            heapify(arr, n, largest);
        }
    }

    
    // Sorting algorithm 6: intro sort
    private  void introSort(int n, int[] arr) {
        introSortData(arr, n);
    }

    private  void introSortMaxHeap(int[] arr, int i, int heapN, int begin) {
        int temp = arr[begin + i - 1];
        int child;
 
        while (i <= heapN / 2) {
            child = 2 * i;
 
            if (child < heapN
                && arr[begin + child - 1] < arr[begin + child])
                child++;
 
            if (temp >= arr[begin + child - 1])
                break;
 
            arr[begin + i - 1] = arr[begin + child - 1];
            i = child;
        }
        arr[begin + i - 1] = temp;
    }
 
    // Function to build the heap (rearranging the array)
    private  void introSortHeapify(int[] arr, int begin, int end, int heapN)
    {
        for (int i = (heapN) / 2; i >= 1; i--)
            introSortMaxHeap(arr, i, heapN, begin);
    }
 
    // main function to do heapsort
    private  void introSortHeapSort(int[] arr, int begin, int end)
    {
        int heapN = end - begin;
 
        // Build heap (rearrange array)
        introSortHeapify(arr, begin, end, heapN);
 
        // One by one extract an element from heap
        for (int i = heapN; i >= 1; i--) {
 
            // Move current root to end
            swap(arr, begin, begin + i);
 
            // call maxHeap() on the reduced heap
            introSortMaxHeap(arr, 1, i, begin);
        }
    }
 
    // function that implements insertion sort
    private  void introSortInsertionSort(int[] arr, int left, int right) {
        for (int i = left; i <= right; i++) {
            int key = arr[i];
            int j = i;
 
            // Move elements of arr[0..i-1], that are
            // greater than the key, to one position ahead
            // of their current position
            while (j > left && arr[j - 1] > key) {
                arr[j] = arr[j - 1];
                j--;
            }
            arr[j] = key;
        }
    }
 
    // Function for finding the median of the three elements
    private  int introSortFindPivot(int[] arr, int a1, int b1, int c1)
    {
        int max = Math.max(Math.max(arr[a1], arr[b1]), arr[c1]);
        int min = Math.min(Math.min(arr[a1], arr[b1]), arr[c1]);
        int median = max ^ min ^ arr[a1] ^ arr[b1] ^ arr[c1];
        if (median == arr[a1])
            return a1;
        if (median == arr[b1])
            return b1;
        return c1;
    }
 
    // This function takes the last element as pivot, places
    // the pivot element at its correct position in sorted
    // array, and places all smaller (smaller than pivot)
    // to the left of the pivot
    // and greater elements to the right of the pivot
    private  int introSortPartition(int[] arr, int low, int high) {
 
        // pivot
        int pivot = arr[high];
 
        // Index of smaller element
        int i = (low - 1);
        for (int j = low; j <= high - 1; j++) {
 
            // If the current element is smaller
            // than or equal to the pivot
            if (arr[j] <= pivot) {
 
                // increment index of smaller element
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, high);
        return (i + 1);
    }
 
    // The main function that implements Introsort
    // low  --> Starting index,
    // high  --> Ending index,
    // depthLimit  --> recursion level
    private  void introSortDataUtil(int[] arr, int begin, int end, int depthLimit) {
        if (end - begin > 16) {
            if (depthLimit == 0) {
                introSortHeapSort(arr, begin, end);
                return;
            }
 
            depthLimit = depthLimit - 1;
            int pivot = introSortFindPivot(arr, begin, begin + ((end - begin) / 2) + 1, end);
            swap(arr, pivot, end);
 
            // p is partitioning index,
            // arr[p] is now at right place
            int p = partition(arr, begin, end);
 
            // Separately sort elements before
            // partition and after partition
            introSortDataUtil(arr, begin, p - 1, depthLimit);
            introSortDataUtil(arr, p + 1, end, depthLimit);
        } else {
            introSortInsertionSort(arr, begin, end);
        }
    }
 
    private  void introSortData(int[] arr, int n){
        int depthLimit = (int)(2 * Math.floor(Math.log(n) / Math.log(2)));
 
        introSortDataUtil(arr, 0, n - 1, depthLimit);
    }

}
