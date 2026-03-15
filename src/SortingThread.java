/**
 * SortingThread.java
 * <p>
 * Represents a thread responsible for sorting one half of the
 * shared array using Merge Sort. Each instance is assigned a
 * specific range [startIndex, endIndex] and sorts that segment.
 * </p>
 *
 * <p>Thread Safety Note: This thread only accesses its assigned
 * index range. Since no two SortingThreads are ever given
 * overlapping ranges, there are no data races and no locks
 * are needed.</p>
 *
 * @author CHHIM Soksambath
 * @version 1.0
 */
public class SortingThread implements Runnable {

    /** The shared array containing all integers to be sorted. */
    private int[] array;

    /** Inclusive start index of this thread's assigned range. */
    private int startIndex;

    /** Inclusive end index of this thread's assigned range. */
    private int endIndex;

    /**
     * Constructs a SortingThread for the given range of the shared array.
     *
     * @param array      the shared array to sort (must not be null)
     * @param startIndex inclusive start index of this thread's range
     * @param endIndex   inclusive end index of this thread's range
     */
    public SortingThread(int[] array, int startIndex, int endIndex) {
        this.array      = array;
        this.startIndex = startIndex;
        this.endIndex   = endIndex;
    }

    /**
     * Entry point for the thread.
     * Sorts the assigned range of the shared array using Merge Sort.
     */
    @Override
    public void run() {
        // Thread safety note:
        // This thread only accesses indices [startIndex, endIndex]
        // of the shared array. Since no two SortingThreads are ever
        // assigned overlapping ranges, there are no data races and
        // no synchronization (locks) are needed.
        mergeSort(array, startIndex, endIndex);
    }

    /**
     * Recursively sorts the array segment [lo, hi] using Merge Sort.
     * Time complexity: O(n log n) in all cases.
     *
     * @param arr the array to sort
     * @param lo  left boundary (inclusive)
     * @param hi  right boundary (inclusive)
     */
    private void mergeSort(int[] arr, int lo, int hi) {
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
    private void merge(int[] arr, int lo, int mid, int hi) {
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

        int i = 0;
        int j = 0;
        int k = lo;

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

        while (i < leftSize) {
            arr[k] = left[i];
            i++;
            k++;
        }

        while (j < rightSize) {
            arr[k] = right[j];
            j++;
            k++;
        }
    }

    /**
     * Returns the inclusive start index assigned to this thread.
     *
     * @return start index
     */
    public int getStartIndex() {
        return startIndex;
    }

    /**
     * Returns the inclusive end index assigned to this thread.
     *
     * @return end index
     */
    public int getEndIndex() {
        return endIndex;
    }
}