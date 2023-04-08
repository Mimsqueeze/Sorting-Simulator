import java.util.LinkedList;

public class sort {
    public static void main(String[] args) {
        runSimulation(10, 1);
    }

    /* Runs Simulation given: 
     - n: number of elements
     - s: sorting algorithm
    */ 
    public static void runSimulation(int n, int s) {
        int[] arr = createArray(n);
        printArray(arr);

        // find correct sorting algorithm
        if (s == 0) {
            bubbleSort(n, arr);
        } else if (s == 1) {
            selectionSort(n, arr);
        }

        printArray(arr);
    }

    // prints array
    private static void printArray(int[] arr) {
        for (int elt : arr) {
            System.out.print(elt + " ");
        } 
        System.out.println(); 
    }

    // Creates array of size 1 to n, with elements of length 1 to n
    private static int[] createArray(int n) {
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
    private static boolean swap(int[] arr, int a, int b) {
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
        return true;
    }
    
    // Sorting algorithm 0: bubble sort
    private static void bubbleSort(int n, int[] arr) {
        int i, j;
        boolean swapped;
        for (i = 0; i < n - 1; i++) {
            swapped = false;
            for (j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1])
                    swapped = swap(arr, j, j+1);
            }
            if (!swapped)
                break;
        }
    }
    
    // Sorting algorithm 1: selection sort
    private static void selectionSort(int n, int[] arr) {
        for (int i = 0; i < n-1; i++) {
            // Index of minimum elt
            int min = i;

            for (int j = i + 1; j < n; j++) {
                if (arr[j] < arr[min])
                    min = j;
            }
            
            swap(arr, i, min);
        }
    }
}
