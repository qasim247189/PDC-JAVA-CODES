public class ParallelMinSearch extends SensorSearchEngine {
    public ParallelMinSearch(double[] sensorData, int startIdx, int endIdx) {
        super(sensorData, startIdx, endIdx);
    }

    @Override
    public void processGrid() {
        double min = sensorData[startIdx];
        for (int i = startIdx + 1; i < endIdx; i++) {
            if (sensorData[i] < min) {
                min = sensorData[i];
            }
        }
        result = min;
    }
}
