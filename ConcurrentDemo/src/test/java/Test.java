public class Test {
    public static void main(String[] args) {
        System.out.println(Integer.numberOfLeadingZeros(16) | (1 << (16 - 1)));
    }
}
