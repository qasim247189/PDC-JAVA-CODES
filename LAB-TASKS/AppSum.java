import java.util.Random;
import java.util.concurrent.ForkJoinPool;

public class AppSum {
    public static void main(String[] args) {
        int size = 20_000_000; // 20 Million elements
        long[] nums = initializeNums(size);

        // Dynamic threshold calculated by array length divided by available processors
        int processors = Runtime.getRuntime().availableProcessors();
        int threshold = size / processors;

        System.out.println("=== Question 1: Parallel Array Sum ===");
        System.out.println("Dataset Size: " + size + " long integers");
        System.out.println("Available CPU Cores: " + processors);
        System.out.println("Calculated Threshold: " + threshold + "\n");

        // 1. Sequential Loop Implementation
        SequentialSum normalSum = new SequentialSum();
        long start = System.currentTimeMillis();
        long seqResult = normalSum.sequentialSum(nums);
        long seqTime = System.currentTimeMillis() - start;
        System.out.println("Sequential Sum: " + seqResult + " | Execution Time: " + seqTime + " ms");

        // 2. Parallel Fork/Join Implementation
        ForkJoinPool pool = new ForkJoinPool(processors);
        SumTask rootTask = new SumTask(nums, 0, nums.length, threshold);
        start = System.currentTimeMillis();
        long parResult = pool.invoke(rootTask);
        long parTime = System.currentTimeMillis() - start;
        System.out.println("Parallel Sum:   " + parResult + " | Execution Time: " + parTime + " ms");
    }

    // Helper method to generate random numbers
    private static long[] initializeNums(int size) {
        Random random = new Random();
        long[] nums = new long[size];
        for (int i = 0; i < size; ++i) {
            nums[i] = random.nextInt(1000);
        }
        return nums;
    }
}