import java.util.Random;
import java.util.concurrent.ForkJoinPool;
public class AppMerge {
    public static void main(String[] args) {
        System.out.println("=== Question 3: Merge Sort Benchmarks ===\n");
        // Small Dataset Test (10,000 elements)
        runMergeSortBenchmark(10_000, "Small Dataset (10,000 Elements)");
        // Large Dataset Test (10,000,000 elements)
        runMergeSortBenchmark(10_000_000, "Large Dataset (10,000,000 Elements)");
    }
    private static void runMergeSortBenchmark(int size, String label) {
        System.out.println("--- Benchmark: " + label + " ---");
        int[] numsSeq = initializeNums(size);
        int[] numsPar = numsSeq.clone();
        // Sequential Sort[cite: 1]
        SequentialMergesort mergesort = new SequentialMergesort(numsSeq);
        long start = System.currentTimeMillis();
        mergesort.mergeSort(numsSeq);
        long seqTime = System.currentTimeMillis() - start;
        System.out.println("Time taken with sequential sort: " + seqTime + " ms");
        // Parallel Sort[cite: 1]
        MergeSortTask rootTask = new MergeSortTask(numsPar);
        ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
        start = System.currentTimeMillis();
        pool.invoke(rootTask);
        long parTime = System.currentTimeMillis() - start;
        System.out.println("Time taken with parallel sort:   " + parTime + " ms\n");
    }
    private static int[] initializeNums(int size) {
        Random random = new Random();
        int[] nums = new int[size];
        for (int i = 0; i < nums.length; ++i) {
            nums[i] = random.nextInt(1000);
        }
        return nums;
    }
}