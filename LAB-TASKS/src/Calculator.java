public class Calculator {
    public int add(int a, int b) {
        return a + b;
    }

    public int subtract(int a, int b) {
        return a - b;
    }

    public int multiply(int a, int b) {
        return a * b;
    }

    public double divide(int a, int b) {
        if (b == 0) {
            throw new IllegalArgumentException("Denominator cannot be zero");
        }
        return (double) a / b;
    }
    public static void main(String[] args){
        Calculator cal = new Calculator();
        System.out.println("Addition: " + cal.add(5, 3));
        System.out.println("Subtraction: " + cal.subtract(5, 3));
        System.out.println("Multiplication: " + cal.multiply(5, 3));
        System.out.println("Division: " + cal.divide(5, 3));
    }
}