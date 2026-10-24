import java.util.Random;
import java.util.concurrent.ForkJoinPool;

public class ForkJoinPortfolioApp {
    public static void main(String[] args) {
        int size = 10000;
        double riskCap = 800.0;

        // generate synthetic asset values
        double[] assets = new double[size];
        Random random = new Random(42);
        for (int i = 0; i < size; i++) {
            assets[i] = 100 + random.nextDouble() * 900; // 100 to 1000
        }

        // serial sum
        long s1 = System.nanoTime();
        double serialSum = 0;
        for (int i = 0; i < size; i++) {
            serialSum += assets[i];
        }
        long s2 = System.nanoTime();
        System.out.println("Serial risk score:   " + serialSum);
        System.out.println("Serial time:         " + (s2 - s1) / 1000000.0 + " ms");

        // parallel sum
        ForkJoinPool pool = new ForkJoinPool();
        long p1 = System.nanoTime();
        double parallelSum = pool.invoke(new ParallelRiskCalculator(assets, 0, size));
        long p2 = System.nanoTime();
        System.out.println("Parallel risk score: " + parallelSum);
        System.out.println("Parallel time:       " + (p2 - p1) / 1000000.0 + " ms");

        // stress test: 5% penalty on values above the cap
        long t1 = System.nanoTime();
        pool.invoke(new PortfolioThresholdTask(assets, 0, size, riskCap));
        long t2 = System.nanoTime();
        System.out.println("Stress test time:    " + (t2 - t1) / 1000000.0 + " ms");

        double afterSum = pool.invoke(new ParallelRiskCalculator(assets, 0, size));
        System.out.println("Risk score after stress test (cap " + riskCap + "): " + afterSum);

        System.out.println("Available processors: " + Runtime.getRuntime().availableProcessors());
        pool.shutdown();
    }
}
