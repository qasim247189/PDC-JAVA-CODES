import java.util.concurrent.RecursiveTask;
public class SumTask extends RecursiveTask<Long> {
    private final long[] nums;
    private final int lowIndex;
    private final int highIndex;
    private final int threshold;
    public SumTask(long[] nums, int lowIndex, int highIndex, int threshold) {
        this.nums = nums;
        this.lowIndex = lowIndex;
        this.highIndex = highIndex;
        this.threshold = threshold;
    }
    @Override
    protected Long compute() {
        // Base case: if subarray size is less than or equal to threshold
        if (highIndex - lowIndex <= threshold) {
            long sum = 0;
            for (int i = lowIndex; i < highIndex; ++i) {
                sum += nums[i];
            }
            return sum;
        } else {
            // Divide step
            int middleIndex = (lowIndex + highIndex) / 2;
            SumTask leftSubtask = new SumTask(nums, lowIndex, middleIndex, threshold);
            SumTask rightSubtask = new SumTask(nums, middleIndex, highIndex, threshold);
            // Parallel invocation
            invokeAll(leftSubtask, rightSubtask);
            // Conquer step
            return leftSubtask.join() + rightSubtask.join();
        }
    }
}