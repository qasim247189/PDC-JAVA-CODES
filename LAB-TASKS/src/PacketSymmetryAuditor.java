public class PacketSymmetryAuditor {
    public static void main(String[] args) {
        String[] packets = {"A1B2B1A", "NET-TEN", "HELLO", "12321"};

        for (String payload : packets) {
            System.out.println("Auditing packet: " + payload);
            TransactionStack stack = new TransactionStack(payload.length());
            RequestQueue queue = new RequestQueue(payload.length());
            // push into stack and enqueue into queue at the same time
            for (int i = 0; i < payload.length(); i++) {
                char c = payload.charAt(i);
                stack.push(c);
                queue.enqueue(c);
            }
            // pop and dequeue together and compare
            boolean symmetric = true;
            while (!stack.isEmpty() && !queue.isEmpty()) {
                char fromStack = stack.pop();
                char fromQueue = queue.dequeue();
                System.out.println("  Stack: " + fromStack + " | Queue: " + fromQueue);
                if (fromStack != fromQueue) {
                    symmetric = false;
                }
            }
            if (symmetric) {
                System.out.println("  Result: VERIFIED - packet is symmetric (palindrome)");
            } else {
                System.out.println("  Result: NOT symmetric");
            }
            System.out.println();
        }
    }
}
