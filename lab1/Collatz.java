/** Class that prints the Collatz sequence starting from a given number.
 *  @author Ki1ro
 */
public class Collatz {

    /** Buggy implementation of nextNumber! */
    public static int nextNumber(int n) {
        if (x % 2 == 0) {
            x = x / 2;
        } else {
            x = 3 * x + 1;
        }
        return x;
    }

    public static void main(String[] args) {
        int x = 5;
        System.out.print(n + " ");
        while (x != 1) {
            x = nextNumber(x);
            System.out.print(x + " ");
        }
        System.out.println();
    }
}

