import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
// We extend RecursiveTask<Long> because our task needs to return a computed value.
public class FactorialCalculator extends RecursiveTask<Long> {
    private long n;
    // Constructor to pass the number
    public FactorialCalculator(long n) {
        this.n = n;}
    @Override
    protected Long compute() {
        // Base case: factorial of 0 or 1 is 1
        if (n <= 1) {
            return 1L;}
        // Create a subtask for (n - 1)
        FactorialCalculator subTask = new FactorialCalculator(n - 1);
        // fork() tells the ForkJoinPool to start working on this subtask
        subTask.fork();
        // join() waits for the subtask to finish and retrieves its result,
        // which we then multiply by our current 'n'
        return n * subTask.join();}
    public static void main(String[] args) {
        long number = 6; // Example: Calculate 6! (which is 720)
        
        System.out.println("Calculating factorial for: " + number);
        
        // Create the initial task
        FactorialCalculator mainTask = new FactorialCalculator(number);
        
        // Create the pool and execute the task
        ForkJoinPool pool = new ForkJoinPool();
        long result = pool.invoke(mainTask);
        
        System.out.println("Factorial of " + number + " is: " + result);
    }
}