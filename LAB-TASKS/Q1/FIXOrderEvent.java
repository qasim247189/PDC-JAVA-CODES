public class FIXOrderEvent extends TradeEvent {
    private String clOrdId;

    public FIXOrderEvent(String senderId, long timestamp, double basePrice, String clOrdId) {
        super(senderId, timestamp, basePrice);
        this.clOrdId = clOrdId;
    }

    @Override
    public void dispatch() {
        double fee = basePrice * 0.0015; // 0.15% exchange fee
        double total = basePrice + fee;
        System.out.println("FIX Order | Sender: " + senderId
                + " | ClOrdID: " + clOrdId
                + " | Timestamp: " + timestamp);
        System.out.println("  Stock purchase request at price: " + basePrice);
        System.out.println("  Exchange fee (0.15%): " + fee);
        System.out.println("  Total cost: " + total);
    }
}
