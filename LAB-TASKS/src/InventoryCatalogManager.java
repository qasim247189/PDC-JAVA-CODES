import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ThreadLocalRandom;
public class InventoryCatalogManager {
    private static final int THREADS = 10;
    private static final int OPS_PER_THREAD = 50000;
    private static final int PRODUCTS = 1000;
    // each worker does lookups and updates on the shared map
    private static void runWorkers(Map<String, Integer> catalog) throws InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(THREADS);
        for (int t = 0; t < THREADS; t++) {
            pool.execute(() -> {
                for (int i = 0; i < OPS_PER_THREAD; i++) {
                    String id = "P" + ThreadLocalRandom.current().nextInt(PRODUCTS);
                    if (i % 2 == 0) {
                        catalog.get(id);                       // lookup
                    } else {
                        catalog.put(id, i);                    // update stock
                    }
                }
            });
        }
        pool.shutdown();
        pool.awaitTermination(1, TimeUnit.MINUTES);
    }
    private static void fillCatalog(Map<String, Integer> catalog) {
        for (int i = 0; i < PRODUCTS; i++) {
            catalog.put("P" + i, 100);
        }
    }
    private static void printResult(String name, long nanos) {
        double ms = nanos / 1000000.0;
        long totalOps = (long) THREADS * OPS_PER_THREAD;
        double throughput = totalOps / (nanos / 1000000000.0);
        System.out.println(name);
        System.out.println("  Total operations: " + totalOps);
        System.out.println("  Execution time:   " + ms + " ms");
        System.out.println("  Throughput:       " + (long) throughput + " ops/sec");
    }
    public static void benchmarkSynchronizedMap() throws InterruptedException {
        Map<String, Integer> catalog = Collections.synchronizedMap(new HashMap<>());
        fillCatalog(catalog);
        long start = System.nanoTime();
        runWorkers(catalog);
        long end = System.nanoTime();
        printResult("Collections.synchronizedMap", end - start);
    }
    public static void benchmarkConcurrentMap() throws InterruptedException {
        Map<String, Integer> catalog = new ConcurrentHashMap<>();
        fillCatalog(catalog);
        long start = System.nanoTime();
        runWorkers(catalog);
        long end = System.nanoTime();
        printResult("ConcurrentHashMap", end - start);
    }
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Threads: " + THREADS + " | Operations per thread: " + OPS_PER_THREAD);
        benchmarkSynchronizedMap();
        benchmarkConcurrentMap();
    }
}
