public class SortingThread implements Runnable{
    private int[] array;
    private int startIndex;
    private int endIndex;

    //constructor
    public SortingThread(int[] array, int startIndex, int endIndex){
        this.array =  array;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    @Override
    public void run(){
        mergeSort(array, startIndex, endIndex);
    }

    private void mergeSort(int[] arr, int lo, int hi) {
        if (lo >= hi) return;

        int mid = lo + (hi - lo) / 2;

        mergeSort(arr, lo, mid);
        mergeSort(arr, mid + 1, hi);
        merge(arr, lo, mid, hi);
    }

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

    // Getters
    public int getStartIndex() { return startIndex; }
    public int getEndIndex()   { return endIndex;   }

}
