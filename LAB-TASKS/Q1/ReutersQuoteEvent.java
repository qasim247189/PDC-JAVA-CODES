public class ReutersQuoteEvent extends TradeEvent {
    private double bidPrice;
    private double askPrice;

    public ReutersQuoteEvent(String senderId, long timestamp, double basePrice,
                             double bidPrice, double askPrice) {
        super(senderId, timestamp, basePrice);
        this.bidPrice = bidPrice;
        this.askPrice = askPrice;
    }

    @Override
    public void dispatch() {
        double spread = askPrice - bidPrice;
        System.out.println("Reuters Quote | Sender: " + senderId
                + " | Timestamp: " + timestamp);
        System.out.println("  Bid: " + bidPrice + " | Ask: " + askPrice);
        System.out.println("  Bid-Ask Spread: " + spread);
    }
}
