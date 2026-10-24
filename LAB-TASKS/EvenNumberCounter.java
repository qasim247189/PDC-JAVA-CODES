import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
public class EvenNumberCounter extends RecursiveTask<Integer> {
    private static final int THRESHOLD = 10;
    private int[] array;
    private int start;
    private int end;
    public EvenNumberCounter(int[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;}
    @Override
    protected Integer compute() {
        int size = end - start;
        if (size <= THRESHOLD) {
            int count = 0;
            for (int i = start; i < end; i++) {
                // Check if the number is even
                if (array[i] % 2 == 0) {
                    count++;}}
            return count;} else {
            int middle = start + (size / 2);
            EvenNumberCounter leftTask = new EvenNumberCounter(array, start, middle);
            EvenNumberCounter rightTask = new EvenNumberCounter(array, middle, end);
            leftTask.fork();
            int rightResult = rightTask.compute();
            int leftResult = leftTask.join();
            return leftResult + rightResult;}}
    public static void main(String[] args) {
        // Create an array with 20 numbers
        int[] myArray = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};
        EvenNumberCounter mainTask = new EvenNumberCounter(myArray, 0, myArray.length);
        ForkJoinPool pool = new ForkJoinPool();
        int totalEvens = pool.invoke(mainTask);
        System.out.println("Total even numbers in the array: " + totalEvens);}}