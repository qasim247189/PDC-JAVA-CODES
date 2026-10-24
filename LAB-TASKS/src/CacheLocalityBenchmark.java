public class CacheLocalityBenchmark {
    static final int N = 2000;
    static double[][] matrix = new double[N][N];
    static double traverseRowMajor() {
        double sum = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                sum += matrix[i][j];
            }
        }
        return sum;
    }
    static double traverseColumnMajor() {
        double sum = 0;
        for (int j = 0; j < N; j++) {
            for (int i = 0; i < N; i++) {
                sum += matrix[i][j];
            }
        }
        return sum;
    }
    public static void main(String[] args) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                matrix[i][j] = 1.0;
            }
        }
        int runs = 10;
        double rowTotal = 0;
        double colTotal = 0;
        System.out.println("Run | Row-Major (ms) | Column-Major (ms)");
        for (int r = 1; r <= runs; r++) {
            long t1 = System.nanoTime();
            double rowSum = traverseRowMajor();
            long t2 = System.nanoTime();
            double colSum = traverseColumnMajor();
            long t3 = System.nanoTime();
            double rowMs = (t2 - t1) / 1000000.0;
            double colMs = (t3 - t2) / 1000000.0;
            rowTotal += rowMs;
            colTotal += colMs;
            System.out.println(r + "   | " + rowMs + " | " + colMs
                    + "   (sums: " + rowSum + ", " + colSum + ")");
        }
        double rowAvg = rowTotal / runs;
        double colAvg = colTotal / runs;
        System.out.println("Average Row-Major:    " + rowAvg + " ms");
        System.out.println("Average Column-Major: " + colAvg + " ms");
        System.out.println("Speedup (Column / Row): " + (colAvg / rowAvg) + "x");
    }
}
