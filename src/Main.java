public class Main {
    public static void main(String[] args){
        int[] array = {7,12,19,3,18,4,2,6,15,8};
        if (array == null) {
            throw new IllegalArgumentException("Array must not be null");
        }
        System.out.print("Before: ");
        printArray(array);

        int mid = array.length / 2 - 1;

        // WARMUP RUN — result thrown away
        runMultiThreadedSort(array.clone(), mid);
        System.out.println("\nWarmup done — JVM is ready");

        // REAL TIMED RUN
        long multiStart = System.currentTimeMillis();
        int[] result = runMultiThreadedSort(array.clone(), mid);
        long multiEnd = System.currentTimeMillis();

        System.out.print("\n After: ");
        printArray(result);

        System.out.println("\n\nExecution Time:");
        System.out.println("Multithreaded: " + (multiEnd - multiStart) + " ms");
    }

    // ADDED INTO NEW METHOD
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

    static void printArray(int[] arr){
        System.out.print("[");
        for(int i=0; i< arr.length; i++){
            System.out.print(arr[i]);
            if(i< arr.length-1)
                System.out.print(", ");
        }
        System.out.print("]");
    }
}