import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
// extend RecursiveAction because this task doesn't need to return a result, 
// it just modifies the array directly.
public class ArrayMultiplier extends RecursiveAction {
    // The threshold decides when to stop splitting. 
    // If the array chunk is 5 or smaller, we just do the math.
    private static final int THRESHOLD = 5; 
    private int[] array;
    private int multiplier;
    private int start;
    private int end;
    // Constructor to pass the array, the multiplier, and the range
    public ArrayMultiplier(int[] array, int multiplier, int start, int end) {
        this.array = array;
        this.multiplier = multiplier;
        this.start = start;
        this.end = end;
    }
    // This is the main method that ForkJoin uses to run the task
    @Override
    protected void compute() {
        // Calculate how many elements we are looking at right now
        int size = end - start;
        // If the size is small enough, just do the multiplication
        if (size <= THRESHOLD) {
            computeDirectly();
        } else {
            // Otherwise, find the middle point to split the work
            int middle = start + (size / 2);
            // Create two new subtasks for the left and right halves
            ArrayMultiplier leftTask = new ArrayMultiplier(array, multiplier, start, middle);
            ArrayMultiplier rightTask = new ArrayMultiplier(array, multiplier, middle, end);
            // This tells Java to run both tasks in parallel
            invokeAll(leftTask, rightTask);
        }
    }
    // Method to do the actual math when the chunk is small enough
    private void computeDirectly() {
        for (int i = start; i < end; i++) {
            array[i] = array[i] * multiplier;
        }
    }
    // A simple main method to test our code
    public static void main(String[] args) {
        // Create an array with 12 numbers
        int[] myArray = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        int numberToMultiply = 3;
        System.out.println("Original Array:");
        for (int num : myArray) {
            System.out.print(num + " ");
        }
        System.out.println();
        // Create the main task covering the whole array (from index 0 to length)
        ArrayMultiplier mainTask = new ArrayMultiplier(myArray, numberToMultiply, 0, myArray.length);
        
        // Create the pool and start the task
        ForkJoinPool pool = new ForkJoinPool(){};
        pool.invoke(mainTask);
        System.out.println("\nArray after multiplying by " + numberToMultiply + ":");
        for (int num : myArray) {
            System.out.print(num + " ");
        }
    }
}