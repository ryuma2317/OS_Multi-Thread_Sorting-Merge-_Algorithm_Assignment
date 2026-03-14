public class Main {
    public static void main(String[] args){
        int[] array = {7,12,19,3,18,4,2,6,15,8};
        System.out.print("Before: ");
        printArray(array);

        mergeSort(array, 0, array.length-1);

        System.out.print("\n After: ");
        printArray(array);

    }

    static void mergeSort(int[] arr, int lo, int hi){
        if(lo>=hi)
            return;

        int mid = lo +(hi-lo)/2;

        mergeSort(arr, lo, mid);

        mergeSort(arr,mid+1, hi);

        merge(arr,lo, mid, hi);
    }

    static void merge(int[] arr, int lo, int mid, int hi){
        int leftSize = mid - lo + 1;
        int rightSize = hi - mid;

        int[] left = new int[leftSize];
        int[] right = new int[rightSize];

        for(int i=0; i<leftSize; i++){
            left[i] = arr[lo+i];
        }

        for(int j=0; j<rightSize; j++){
            right[j] = arr[mid+j+1];
        }

        int i=0;
        int j=0;
        int k =lo;

        while(i<leftSize && j<rightSize){
            if(left[i] <= right[j]){
                arr[k] = left[i];
                i++;
            }else{
                arr[k] =  right[j];
                j++;
            }
            k++;
        }

        while (i<leftSize){
            arr[k] = left[i];
            i++;
            k++;
        }

        while(j<rightSize){
            arr[k] = right[j];
            j++;
            k++;
        }
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
