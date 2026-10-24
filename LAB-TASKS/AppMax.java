import java.util.Random;
import java.util.concurrent.ForkJoinPool;
public class AppMax {
    public static int THRESHOLD = 0;
    public static void main(String[] args) {
        int size = 50_000_000; // 50 Million elements
        long[] nums = initializeNums(size);
        int cores = Runtime.getRuntime().availableProcessors();
        System.out.println("=== Question 2: Threshold Variation Benchmarks (50M Elements) ===");
        System.out.println("Available CPU Cores: " + cores + "\n");
        // Baseline: Single-threaded Sequential Search
        SequentialMaxFind normalMaxFind = new SequentialMaxFind();
        long start = System.currentTimeMillis();
        long seqMax = normalMaxFind.sequentialMaxFinding(nums, nums.length);
        long seqTime = System.currentTimeMillis() - start;
        System.out.println("1. Single-threaded Sequential Search");
        System.out.println("   Max Found: " + seqMax + " | Time: " + seqTime + " ms\n");
        // Fixed Thresholds & Dynamic Threshold
        int[] testThresholds = {1_000, 50_000, 1_000_000, size / cores};
        String[] labels = {"Fixed Threshold (1,000)", "Fixed Threshold (50,000)", "Fixed Threshold (1,000,000)", "Dynamic Threshold (N / cores)"};
        ForkJoinPool forkJoinPool = new ForkJoinPool(cores);
        for (int i = 0; i < testThresholds.length; i++) {
            THRESHOLD = testThresholds[i];
            MaximumFindTask findTask = new MaximumFindTask(nums, 0, nums.length);
            start = System.currentTimeMillis();
            long parMax = forkJoinPool.invoke(findTask);
            long parTime = System.currentTimeMillis() - start;
            System.out.println("2." + (i + 1) + " Parallel Max - " + labels[i]);
            System.out.println("     Threshold Value: " + THRESHOLD);
            System.out.println("     Max Found: " + parMax + " | Time Taken: " + parTime + " ms");
        }
 }
    private static long[] initializeNums(int size) {
        Random random = new Random();
        long[] nums = new long[size];
        for (int i = 0; i < size; ++i) {
            nums[i] = random.nextInt(1000);
        }
        return nums;
    }
}