public class SequentialSum {
    public long sequentialSum(long[] nums) {
        long sum = 0;
        for (long num : nums) {
            sum += num;
        }
        return sum;
    }
}