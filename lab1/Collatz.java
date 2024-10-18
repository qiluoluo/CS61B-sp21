/** Class that prints the Collatz sequence starting from a given number.
 *  @author Ki1ro
 */
public class Collatz {

    /** Buggy implementation of nextNumber! */
    public static int nextNumber(int n) {
        if (n  == 128) {
            return 1;
        } else if (n == 5) {
            return 3 * n + 1;
        } else {
            return n * 2;
        }
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

