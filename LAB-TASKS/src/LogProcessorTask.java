public class LogProcessorTask implements Runnable {
    @Override
    public void run() {
        for (int i = 1; i <= 5; i++) {
            System.out.println("Log iteration " + i + " running in thread: "
                    + Thread.currentThread().getName());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("Log thread interrupted");
            }
        }
    }
}
