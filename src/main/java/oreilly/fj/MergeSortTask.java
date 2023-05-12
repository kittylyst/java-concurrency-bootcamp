package oreilly.fj;

import java.util.Arrays;
import java.util.concurrent.RecursiveAction;

public final class MergeSortTask extends RecursiveAction {
    private final int[] array;
    private final int low;
    private final int high;
    private static final int THRESHOLD = 8;

    public MergeSortTask(int[] array, int low, int high) {
        this.array = array;
        this.low = low;
        this.high = high;
    }
    @Override
    protected void compute() {
        if (high - low <= THRESHOLD) {
            Arrays.sort(array, low, high);
        } else {
            int middle = low + ((high - low) >> 1);
            // Execute the subtasks and wait for them to finish
            invokeAll(new MergeSortTask(array, low, middle),
                    new MergeSortTask(array, middle, high));
            // Then merge the results
            merge(middle);
        }
    }

    /**
     * Merges the two sorted arrays this.low, middle - 1 and middle, this.high - 1
     * @param middle the index in the array where the second sorted list begins
     */
    private void merge(int middle) {
        if (array[middle - 1] < array[middle]) {
            return; // the arrays are already correctly sorted, so we can skip the merge
        }
        int[] copy = new int[high - low];
        System.arraycopy(array, low, copy, 0, copy.length);
        int copyLow = 0;
        int copyHigh = high - low;
        int copyMiddle = middle - low;
        for (int i = low, p = copyLow, q = copyMiddle; i < high; i++) {
            if (q >= copyHigh || (p < copyMiddle && copy[p] < copy[q]) ) {
                array[i] = copy[p++];
            } else {
                array[i] = copy[q++];
            }
        }
    }
}
