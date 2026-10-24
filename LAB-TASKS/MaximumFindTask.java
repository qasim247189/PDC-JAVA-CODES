import java.util.concurrent.RecursiveTask;
public class MaximumFindTask extends RecursiveTask<Long> {
    private final long[] nums;
    private final int lowIndex;
    private final int highIndex;
    public MaximumFindTask(long[] nums, int lowIndex, int highIndex) {
        this.nums = nums;
        this.lowIndex = lowIndex;
        this.highIndex = highIndex;
    }
    @Override
    protected Long compute() {
        if (highIndex - lowIndex < AppMax.THRESHOLD) {
            return sequentialMaxFinding();
        } else {
            int middleIndex = (lowIndex + highIndex) / 2;
            MaximumFindTask leftSubtask = new MaximumFindTask(nums, lowIndex, middleIndex);
            MaximumFindTask rightSubtask = new MaximumFindTask(nums, middleIndex, highIndex);
            invokeAll(leftSubtask, rightSubtask);
            return Math.max(leftSubtask.join(), rightSubtask.join());
        }
    }
    private long sequentialMaxFinding() {
        long max = nums[lowIndex];
        for (int i = lowIndex; i < highIndex; ++i) {
            if (nums[i] > max) {
                max = nums[i];
            }
        }
        return max;
    }
}