import java.util.Random;
public class SensorGridMonitorApp {
    public static void main(String[] args) throws InterruptedException {
        int size = 50000;
        int workers = 4;
        int chunk = size / workers;
        double[] sensorData = new double[size];
        Random random = new Random(7);
        for (int i = 0; i < size; i++) {
            sensorData[i] = random.nextDouble() * 100; }// 0 to 100
        ParallelMinSearch[] minTasks = new ParallelMinSearch[workers];
        ParallelMaxSearch[] maxTasks = new ParallelMaxSearch[workers];
        Thread[] minThreads = new Thread[workers];
        Thread[] maxThreads = new Thread[workers];
        long start = System.nanoTime();  // create and start min and max threads together
        for (int i = 0; i < workers; i++) {
            int s = i * chunk;
            int e = (i == workers - 1) ? size : s + chunk;
            minTasks[i] = new ParallelMinSearch(sensorData, s, e);
            maxTasks[i] = new ParallelMaxSearch(sensorData, s, e);
            minThreads[i] = new Thread(minTasks[i], "Min-Worker-" + (i + 1));
            maxThreads[i] = new Thread(maxTasks[i], "Max-Worker-" + (i + 1));
            minThreads[i].start();
            maxThreads[i].start();}// wait for all threads and combine partial results
        double globalMin = Double.MAX_VALUE;
        double globalMax = -Double.MAX_VALUE;
        for (int i = 0; i < workers; i++) {
            minThreads[i].join();
            maxThreads[i].join();
            System.out.println("Segment " + (i + 1) + " -> min: " + minTasks[i].getResult()
                    + " | max: " + maxTasks[i].getResult());
            if (minTasks[i].getResult() < globalMin) {
                globalMin = minTasks[i].getResult();}
            if (maxTasks[i].getResult() > globalMax) {
                globalMax = maxTasks[i].getResult();}}
        long end = System.nanoTime();
        System.out.println("Global minimum temperature: " + globalMin);
        System.out.println("Global peak vibration:      " + globalMax);
        System.out.println("Execution time: " + (end - start) / 1000000.0 + " ms");}}
