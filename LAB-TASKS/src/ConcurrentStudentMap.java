import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ConcurrentStudentMap {
    public static void main(String[] args) throws InterruptedException {
        final int THREADS = 5;
        final int RECORDS_PER_THREAD = 1000;
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        AtomicInteger retrieved = new AtomicInteger(0); // counts successful reads
        long start = System.nanoTime();
        ExecutorService executor = Executors.newFixedThreadPool(THREADS);
        for (int t = 1; t <= THREADS; t++) {
            final int threadId = t;
            executor.submit(() -> {
                for (int i = 1; i <= RECORDS_PER_THREAD; i++) {
                    map.put("Student_T" + threadId + "_" + i, 40 + (i % 60));
                }
                for (int i = 1; i <= RECORDS_PER_THREAD; i++) {
                    if (map.get("Student_T" + threadId + "_" + i) != null) {
                        retrieved.incrementAndGet();
                    }
                }
            });
        }
        executor.shutdown();
        executor.awaitTermination(60, TimeUnit.SECONDS);
        long elapsed = (System.nanoTime() - start) / 1_000_000;
        System.out.println("Number of threads : " + THREADS);
        System.out.println("Expected entries  : " + (THREADS * RECORDS_PER_THREAD));
        System.out.println("Actual entries    : " + map.size());
        System.out.println("Values retrieved  : " + retrieved.get());
        System.out.println("Execution time    : " + elapsed + " ms");
    }
}