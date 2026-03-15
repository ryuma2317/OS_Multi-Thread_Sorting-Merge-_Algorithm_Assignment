/**
 * MergingThread.java
 * <p>
 * Represents a thread responsible for merging two sorted halves
 * of the input array into a separate result array.
 * This thread must only be started after both SortingThreads
 * have finished to guarantee correct output.
 * </p>
 *
 * @author CHHIM Soksambath
 * @version 1.0
 */
public class MergingThread implements Runnable {

    /** The input array containing two sorted halves. */
    private int[] inputArray;

    /** The output array where the merged result will be written. */
    private int[] resultArray;

    /** The midpoint index dividing the left and right halves. */
    private int mid;

    /**
     * Constructs a MergingThread.
     *
     * @param inputArray  the array with two sorted halves (must not be null)
     * @param resultArray the output array to write merged result into (must not be null)
     * @param mid         index of the last element of the left half
     */
    public MergingThread(int[] inputArray, int[] resultArray, int mid) {
        this.inputArray  = inputArray;
        this.resultArray = resultArray;
        this.mid         = mid;
    }

    /**
     * Entry point for the thread.
     * Merges the two sorted halves of inputArray into resultArray.
     */
    @Override
    public void run() {
        merge(inputArray, resultArray, 0, mid, inputArray.length - 1);
    }

    /**
     * Merges two sorted segments of src into dst.
     * Left segment: [lo, mid], Right segment: [mid+1, hi].
     *
     * @param src the source array with two sorted halves
     * @param dst the destination array to write merged result into
     * @param lo  start of the left half (inclusive)
     * @param mid end of the left half (inclusive)
     * @param hi  end of the right half (inclusive)
     */
    private void merge(int[] src, int[] dst, int lo, int mid, int hi) {
        int i = lo;      // pointer for left half
        int j = mid + 1; // pointer for right half
        int k = lo;      // pointer for destination array

        while (i <= mid && j <= hi) {
            if (src[i] <= src[j]) {
                dst[k] = src[i];
                i++;
            } else {
                dst[k] = src[j];
                j++;
            }
            k++;
        }

        // Copy leftover left elements
        while (i <= mid) {
            dst[k] = src[i];
            i++;
            k++;
        }

        // Copy leftover right elements
        while (j <= hi) {
            dst[k] = src[j];
            j++;
            k++;
        }
    }

    /**
     * Returns the result array containing the fully merged sorted output.
     *
     * @return the sorted result array
     */
    public int[] getResultArray() {
        return resultArray;
    }
}