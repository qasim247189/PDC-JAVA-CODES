public class AsyncWebEngineApp {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Default thread name: " + Thread.currentThread().getName());
        TelemetryThread telemetry = new TelemetryThread();
        telemetry.setName("Telemetry-Worker");
        Thread logThread = new Thread(new LogProcessorTask());
        logThread.setName("Log-Worker");
        telemetry.start();
        logThread.start();
        System.out.println("Telemetry-Worker alive: " + telemetry.isAlive());
        System.out.println("Log-Worker alive: " + logThread.isAlive());
        telemetry.join();
        logThread.join();
        System.out.println("Telemetry-Worker alive: " + telemetry.isAlive());
        System.out.println("Log-Worker alive: " + logThread.isAlive());
        System.out.println("All threads finished.");
    }
}
