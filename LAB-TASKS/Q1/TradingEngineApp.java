public class TradingEngineApp {
    public static void main(String[] args) {
        // Superclass reference variables holding subclass objects
        TradeEvent e1 = new TradeEvent("SYSTEM", System.currentTimeMillis(), 100.0);
        TradeEvent e2 = new FIXOrderEvent("FIX-GATEWAY", System.currentTimeMillis(), 250.50, "ORD-1001");
        TradeEvent e3 = new ReutersQuoteEvent("REUTERS-FEED", System.currentTimeMillis(), 250.0, 249.80, 250.30);

        System.out.println("=== Dispatching Events ===");
        e1.dispatch();
        System.out.println();
        e2.dispatch();
        System.out.println();
        e3.dispatch();
    }
}
