import java.util.Random;
import java.util.concurrent.ForkJoinPool;

public class AppPoolScaling {
    public static void main(String[] args) {
        int size = 30_000_000; // 30 Million elements
        long[] nums = initializeNums(size);
        int maxCores = Runtime.getRuntime().availableProcessors();
        System.out.println("=== Question 4: ForkJoinPool Thread Capacity Scaling ===");
        System.out.println("Dataset Size: " + size + " elements");
        System.out.println("Max Available CPU Cores: " + maxCores + "\n");
        int[] threadCapacities = {1, 2, 4, maxCores};
        for (int threads : threadCapacities) {
            // Adjust threshold for current active thread pool capacity
            AppMax.THRESHOLD = size / threads;
            ForkJoinPool pool = new ForkJoinPool(threads);
            MaximumFindTask findTask = new MaximumFindTask(nums, 0, nums.length);
            long start = System.currentTimeMillis();
            long maxVal = pool.invoke(findTask);
            long executionTime = System.currentTimeMillis() - start;
            double throughput = (double) size / executionTime; // elements processed per millisecond
            System.out.printf("Capacity: %2d Thread(s) | Time: %5d ms | Throughput: %,10.2f elem/ms | Max Found: %d\n",
                    threads, executionTime, throughput, maxVal);
            pool.shutdown();
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