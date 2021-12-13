package datastructures.sorting;

import datastructures.stack.Stack;

public class Sorting {

    int[] nums;

    public int[] getNums() {
        return nums;
    }

    public void setNums(int[] nums) {
        this.nums = nums;
    }

    public Sorting(int[] nums) {
        this.nums = nums;
    }

    public Sorting() {}

    public static void main(String[] args) {
        int[] a = {10,9,8,7,6,5,4,3,2,1};
        int[] b = a;
        bubbleSort(b, 1);
        printArray(b);
        b = new int[]{10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
        insertionSort(b,2);
        printArray(b);
        System.out.println(binarySearch(b,9));
        b = new int[]{10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
        selectionSort(b,2);
        printArray(b);
        System.out.println(binarySearch(b,5));
        b = new int[]{10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
        mergeSort(b,0,9, 2);
        printArray(b);
        b = new int[]{10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
        quickSort(b,0,9, 4);
        printArray(b);
    }

    public static void printArray(int[] a) {
        for (int num: a) {
            System.out.print(num + " ");
        }
        System.out.println();
    }

    public static int binarySearch(int[] a, int target) {
        return binarySearchUtil(a, target, 0, a.length-1);
    }
    static int binarySearchUtil(int[] a, int target, int leftIndex, int rightIndex) {
        if (leftIndex <= rightIndex) {
            int midIndex = leftIndex + (rightIndex - leftIndex)/2;

            if (target == a[midIndex]) {
                return midIndex;
            } else if (target < a[midIndex]) {
                return binarySearchUtil(a, target, leftIndex, midIndex-1);
            } else {
                return binarySearchUtil(a, target, midIndex+1, rightIndex);
            }
        }
        //return -1;
        return leftIndex; //index which has element just higher than the given number
    }

    public static void bubbleSort(int[] a, int approach) {
        int n = a.length;

        if (approach == 1) {
            for (int i = 0; i <= n-2; i ++) {
                for (int j = 0; j <= n-2; j++) {
                    if (a[j] > a[j+1]) {
                        //swap
                        a[j+1] = a[j+1] + a[j] ;
                        a[j] = a[j+1] - a[j];
                        a[j+1] = a[j+1] - a[j];
                    }
                }
            }
        } else if (approach == 2) {
            for (int i = 0; i <= n-2; i ++) {
                for (int j = 0; j <= n-2-i; j++) {
                    if (a[j] > a[j+1]) {
                        //swap
                        a[j+1] = a[j+1] + a[j] ;
                        a[j] = a[j+1] - a[j];
                        a[j+1] = a[j+1] - a[j];
                    }
                }
            }
        } else if (approach == 3){
            for (int i = 0; i <= n-2; i ++) {
                boolean swapped = false;
                for (int j = 0; j <= n-2-i; j++) {
                    if (a[j] > a[j+1]) {
                        //swap
                        a[j+1] = a[j+1] + a[j] ;
                        a[j] = a[j+1] - a[j];
                        a[j+1] = a[j+1] - a[j];
                        swapped = true;
                    }
                }
                if (!swapped) break;
            }
        } else {
            bubbleSortRec(a, n);
        }
    }

    static void bubbleSortRec(int[] a, int n) {
        if (n==1) return;
        for (int j = 0; j <= n-2; j++) {
            if (a[j] > a[j+1]) {
                int temp = a[j+1];
                a[j+1] = a[j];
                a[j] = temp;
            }
        }
        bubbleSortRec(a, n-1);
    }

    public static void insertionSort(int[] a, int approach) {
        //when i = 0, inner while loop is skipped
        if (approach == 1) {
            for (int i = 1; i <= a.length - 1; i++) {
                int j = i;
                while (j >= 1 && a[j-1] > a[j]) {
                    int temp = a[j-1];
                    a[j-1] = a[j];
                    a[j] = temp;
                    j--;
                }
            }
        } else {
            for (int i = 1; i <= a.length - 1; i++) {

                int insertIndex = i;
                int elemToInsert = a[i];
                if (elemToInsert < a[i-1]) {
                    insertIndex = binarySearchUtil(a, elemToInsert, 0, i-1);
                }
                int j = i-1;
                while (j >= insertIndex) {
                    a[j+1] = a[j];
                    j--;
                }
                a[insertIndex] = elemToInsert;
            }
        }
    }

    public static void selectionSort(int[] a, int approach) {
        if (approach == 1) {
            for (int i=0; i <= a.length-2; i++) {
                int currentMinIdx = i;
                for (int j = i+1; j <= a.length-1; j++) {
                    if (a[j] < a[currentMinIdx]) {
                        currentMinIdx = j;
                    }
                }
                if (currentMinIdx > i) {
                    int temp = a[currentMinIdx];
                    a[currentMinIdx] = a[i];
                    a[i] = temp;
                }
            }
        } else {
            //stable selection sort
            for (int i=0; i <= a.length-2; i++) {
                int currentMinIdx = i;
                for (int j = i+1; j <= a.length-1; j++) {
                    if (a[j] < a[currentMinIdx]) {
                        currentMinIdx = j;
                    }
                }

                int min = a[currentMinIdx];
                int j = currentMinIdx;
                while (j >= i+1 ) {
                    a[j] = a[j-1];
                    j--;
                }
                a[i] = min;
            }
        }

    }

    public static void mergeSort(int[] a, int leftIdx, int rightIdx, int approach) {

        if (approach == 1) {
            if (leftIdx < rightIdx) {
                int midIdx = leftIdx + (rightIdx - leftIdx)/2;

                mergeSort(a, leftIdx, midIdx, 1);
                mergeSort(a, midIdx+1, rightIdx, 1);

                merge(a, leftIdx, midIdx, rightIdx);
            }
        } else {
            mergeSortIterative(a);
        }
    }

    private static void merge(int[] a, int leftIdx, int midIdx, int rightIdx) {

        int[] leftArray = new int[midIdx - leftIdx + 1];
        int[] rightArray= new int[rightIdx - midIdx];

        for (int i = 0; i <= leftArray.length - 1; i++) {
            leftArray[i] = a[leftIdx + i];
        }

        for (int i = 0; i <= rightArray.length - 1; i++) {
            rightArray[i] = a[midIdx + 1 + i];
        }

        int lIdx = 0;
        int rIdx = 0;
        int resIdx = leftIdx;

        while (lIdx <= leftArray.length-1 && rIdx <= rightArray.length-1) {
            if (leftArray[lIdx] < rightArray[rIdx]) {
                a[resIdx] = leftArray[lIdx];
                lIdx++;
            } else {
                a[resIdx] = rightArray[rIdx];
                rIdx++;
            }
            resIdx++;
        }

        while (lIdx <= leftArray.length-1) {
            a[resIdx] = leftArray[lIdx];
            resIdx++;
            lIdx++;
        }

        while (rIdx <= rightArray.length-1) {
            a[resIdx] = rightArray[rIdx];
            resIdx++;
            rIdx++;
        }
    }

    private static void mergeSortIterative(int[] a) {
        if (a.length <= 1) {
            return;
        }

        int midIdx =  (a.length - 1)/2;

        int[] left = new int[midIdx + 1];
        int[] right = new int[a.length - midIdx - 1];
        for (int i = 0; i < left.length; i++) {
            left[i] = a[i];
        }
        for (int i = 0; i < right.length; i++) {
            right[i] = a[midIdx + 1 + i];
        }
        mergeSortIterative(left);
        mergeSortIterative(right);

        int i = 0, j = 0, k = 0;
        while (i < left.length && j < right.length) {
            if (left[i] < right[j]) {
                a[k] = left[i];
                i++;
            } else {
                a[k] = right[j];
                j++;
            }
            k++;
        }

        while (i < left.length) {
            a[k] = left [i];
            i++;
            k++;
        }

        while (j < right.length) {
            a[k] = right[j];
            j++;
            k++;
        }
    }

    public static void quickSort(int[] a, int leftIdx, int rightIdx, int approach){
        if (approach == 1) {
            if (leftIdx < rightIdx) {
                int pivotIdx = partition(a, leftIdx, rightIdx);
                quickSort(a, leftIdx, pivotIdx - 1, 1);
                quickSort(a, pivotIdx + 1, rightIdx, 1);
            }
        } else if (approach == 2) {

            /**
             * approach 2 tail-end optimisation
             */
            while (leftIdx < rightIdx) {
                int pivotIdx = partition(a, leftIdx, rightIdx);
                quickSort(a, leftIdx, pivotIdx-1, 2);
                leftIdx = pivotIdx+1;
            }

        } else if (approach == 3) {
            /**
             * approach 3 tail-end further optimisation
             */
            while (leftIdx < rightIdx) {
                int pivotIdx = partition(a, leftIdx, rightIdx);
                if (rightIdx - (pivotIdx+1) > pivotIdx - 1 - leftIdx) {
                    quickSort(a, leftIdx, pivotIdx-1, 3);
                    leftIdx = pivotIdx+1;
                } else {
                    quickSort(a, pivotIdx+1, rightIdx, 3);
                    rightIdx = pivotIdx-1;
                }
            }
        } else {

            Stack<Integer> s = new Stack<>();
            s.push(rightIdx);
            s.push(leftIdx);

            while (!s.empty()) {
                leftIdx= s.pop();
                rightIdx = s.pop();

                int pivotIdx = partition(a, leftIdx, rightIdx);

                if (pivotIdx+1 < rightIdx) {
                    s.push(rightIdx);
                    s.push(pivotIdx+1);
                }
                if (leftIdx < pivotIdx - 1) {
                    s.push(pivotIdx-1);
                    s.push(leftIdx);
                }
            }
        }
    }

    private static int partition(int[] a, int leftIdx, int rightIdx) {
        int pivot = a[rightIdx]; //can be randomElement;

        int leftWall = leftIdx-1;
        for (int i = leftIdx; i <= rightIdx - 1; i++) {
            if (a[i] <= pivot) {
                leftWall++;

                int temp = a[leftWall];
                a[leftWall] = a[i];
                a[i] = temp;
            }
        }

        int pivotIdx = leftWall+1;

        int temp = a[pivotIdx];
        a[pivotIdx] = a[rightIdx];
        a[rightIdx] = temp;

        return pivotIdx;
    }

}
