import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SynchronizedStudentMap {
    public static void main(String[] args) throws InterruptedException {
        final int THREADS = 3;
        final int RECORDS_PER_THREAD = 100;

        // 1. Create a HashMap and wrap it in a synchronized map
        Map<String, Integer> map = Collections.synchronizedMap(new HashMap<>());

        // 2. ExecutorService with three threads
        ExecutorService executor = Executors.newFixedThreadPool(THREADS);

        // 3. Each task inserts 100 unique records (unique keys via thread id)
        for (int t = 1; t <= THREADS; t++) {
            final int threadId = t;
            executor.submit(() -> {
                for (int i = 1; i <= RECORDS_PER_THREAD; i++) {
                    map.put("Student_T" + threadId + "_" + i, 50 + (i % 50));
                }
            });
        }
        executor.shutdown();
        if (!executor.awaitTermination(30, TimeUnit.SECONDS)) {
            System.out.println("Warning: tasks did not finish in time.");
        }
        System.out.println("Expected entries: " + (THREADS * RECORDS_PER_THREAD));
        System.out.println("Actual entries  : " + map.size());
    }
}