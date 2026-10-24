public class Node {
    private int data;
    private Node next;

    public Node(int data) {
        this.data = data;
        this.next = null;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }
    public static void main(String[] args) {
        Node node1 = new Node(10);
        Node node2 = new Node(20);
        node1.setNext(node2);

        System.out.println("Node 1 data: " + node1.getData());
        System.out.println("Node 2 data: " + node1.getNext().getData());
    }
}