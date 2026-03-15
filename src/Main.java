/**
 * Main.java
 * <p>
 * Driver class for the Multithreaded Sorting Application.
 * Benchmarks both single-threaded and multithreaded Merge Sort
 * across multiple input sizes and prints the execution times.
 * </p>
 *
 * @author CHHIM Soksambath
 * @version 1.0
 */
public class Main {

    /**
     * Entry point of the program.
     * Runs a warmup, then benchmarks both sorting versions
     * across all defined input sizes.
     *
     * @param args command-line arguments (unused)
     */
    public static void main(String[] args) {

        System.out.println("=== Sorting Benchmark ===\n");

        // warmup
        System.out.println("Warming up...");
        int[] warmup = generateArray(100000);
        runSingleThreadedSort(warmup.clone());
        runMultiThreadedSort(warmup.clone(), warmup.length / 2 - 1);
        System.out.println("Done!\n");

        // test sizes
        int[] sizes = {10000, 100000, 500000, 1000000, 2000000, 5000000, 10000000, 20000000, 50000000, 100000000, 200000000, 500000000};

        for (int size : sizes) {
            try {
                int[] data = generateArray(size);
                int mid = size / 2 - 1;

                // time single threaded
                long singleStart = System.currentTimeMillis();
                runSingleThreadedSort(data.clone());
                long singleTime = System.currentTimeMillis() - singleStart;

                // time multithreaded
                long multiStart = System.currentTimeMillis();
                runMultiThreadedSort(data.clone(), mid);
                long multiTime = System.currentTimeMillis() - multiStart;

                // print results
                System.out.println("Size: " + size);
                System.out.println("  Single : " + singleTime + " ms");
                System.out.println("  Multi  : " + multiTime  + " ms");
                System.out.println();

            } catch (OutOfMemoryError e) {
                System.out.println("Size: " + size);
                System.out.println("  Stopped — out of memory!");
                break;
            }
        }
    }

    /**
     * Sorts the given array using three threads —
     * two sorting threads and one merging thread.
     * Uses Thread.join() to ensure correct ordering.
     *
     * @param array the array to sort (will not be modified)
     * @param mid   the midpoint dividing left and right halves
     * @return a new sorted array containing all elements
     * @throws RuntimeException if any thread is interrupted
     */
    static int[] runMultiThreadedSort(int[] array, int mid) {
        SortingThread st0 = new SortingThread(array, 0, mid);
        SortingThread st1 = new SortingThread(array, mid + 1, array.length - 1);

        Thread thread0 = new Thread(st0);
        Thread thread1 = new Thread(st1);

        int[] result = new int[array.length];
        MergingThread mt = new MergingThread(array, result, mid);
        Thread mergeThread = new Thread(mt);

        thread0.start();
        thread1.start();

        try {
            thread0.join();
            thread1.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Sorting was interrupted", e);
        }

        mergeThread.start();

        try {
            mergeThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Merging was interrupted", e);
        }

        return result;
    }

    /**
     * Sorts the given array using a single thread with Merge Sort.
     *
     * @param array the array to sort (modified in place)
     * @return the sorted array
     */
    static int[] runSingleThreadedSort(int[] array) {
        mergeSort(array, 0, array.length - 1);
        return array;
    }

    /**
     * Recursively sorts the array segment [lo, hi] using Merge Sort.
     * Time complexity: O(n log n) in all cases.
     *
     * @param arr the array to sort
     * @param lo  left boundary (inclusive)
     * @param hi  right boundary (inclusive)
     */
    static void mergeSort(int[] arr, int lo, int hi) {
        if (lo >= hi) return;

        int mid = lo + (hi - lo) / 2;

        mergeSort(arr, lo, mid);
        mergeSort(arr, mid + 1, hi);
        merge(arr, lo, mid, hi);
    }

    /**
     * Merges two sorted halves of the array: [lo, mid] and [mid+1, hi].
     *
     * @param arr the array containing both sorted halves
     * @param lo  start of the left half
     * @param mid end of the left half
     * @param hi  end of the right half
     */
    static void merge(int[] arr, int lo, int mid, int hi) {
        int leftSize  = mid - lo + 1;
        int rightSize = hi - mid;

        int[] left  = new int[leftSize];
        int[] right = new int[rightSize];

        for (int i = 0; i < leftSize; i++) {
            left[i] = arr[lo + i];
        }
        for (int j = 0; j < rightSize; j++) {
            right[j] = arr[mid + 1 + j];
        }

        int i = 0, j = 0, k = lo;

        while (i < leftSize && j < rightSize) {
            if (left[i] <= right[j]) {
                arr[k] = left[i];
                i++;
            } else {
                arr[k] = right[j];
                j++;
            }
            k++;
        }

        while (i < leftSize)  { arr[k] = left[i];  i++; k++; }
        while (j < rightSize) { arr[k] = right[j]; j++; k++; }
    }

    /**
     * Generates an array of random integers using a fixed seed
     * so results are reproducible across runs.
     *
     * @param size number of elements to generate
     * @return array of random integers in range [0, 10000000)
     */
    static int[] generateArray(int size) {
        int[] array = new int[size];
        java.util.Random random = new java.util.Random(42);
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(10000000);
        }
        return array;
    }

    /**
     * Prints all elements of the array in a readable format.
     *
     * @param arr the array to print
     */
    static void printArray(int[] arr) {
        System.out.print("[");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]);
            if (i < arr.length - 1) System.out.print(", ");
        }
        System.out.println("]");
    }
}