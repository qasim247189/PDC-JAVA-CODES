public class TelemetryThread extends Thread {
    @Override
    public void run() {
        for (int i = 1; i <= 5; i++) {
            System.out.println("Telemetry update " + i + " [" + getName()
                    + "] CPU: " + (40 + i * 5) + "% | Memory: " + (50 + i * 3) + "%");
            try {
                Thread.sleep(800);
            } catch (InterruptedException e) {
                System.out.println("Telemetry thread interrupted");
            }
        }
    }
}
