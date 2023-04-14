public class CustomSim {
    /*  Write your sorting algorithm here.
        Use these public methods from Sim to modify the array:
            public boolean swap(int a, int b); 
                - Swaps elements at indices a and b
                - Always returns true.
            public int compare(int a, int b); 
                - Compares the elements at indices a and b.
                - Returns -1 if arr[a] < arr[b], 0 if arr[a] == arr[b], 1 if arr[a] > arr[b]
            public int compareV(int a, int v);
                - Compares the element at index a with the value v
                - Returns -1 if arr[a] < v, 0 if arr[a] == v, 1 if arr[a] > v
            public void insert(int a, int b);
                - Copies the element at index b into index a
            public void insertV(int a, int v);
                - Copies the value v into index a
            public int read(int a);
                - Returns the value at index a
    */ 
    public static void customSort(Sim sim, int size) {
        // Example: Bubble Sort
        int i, j;
        boolean swapped;
        for (i= 0; i <= size - 1; i++) {
            swapped= false;
            for (j= 0; j < size - i - 1; j++) {
                if (sim.compare(j, j + 1) > 0)
                    swapped= sim.swap(j, j + 1);
            }
            if (!swapped)
                break;
        }
    }
}
