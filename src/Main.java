public class Main {
    public static void main(String[] args){
        int[] array = {7,12,19,3,18,4,2,6,15,8};
        if (array == null) {
            throw new IllegalArgumentException("Array must not be null");
        }
        System.out.print("Before: ");
        printArray(array);

        int mid = array.length / 2;

        // BUG — both threads get the same indices!
        SortingThread st0 = new SortingThread(array, 0, mid - 1);
        SortingThread st1 = new SortingThread(array, mid, array.length - 1);

        Thread thread0 = new Thread(st0);
        Thread thread1 = new Thread(st1);

        thread0.start();
        thread1.start();

        try {
            thread0.join();
            thread1.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.print("\n After: ");
        printArray(array);

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
