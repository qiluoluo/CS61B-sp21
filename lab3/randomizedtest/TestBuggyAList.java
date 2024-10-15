package randomizedtest;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Assert;
import org.junit.Test;

import javax.sound.midi.Soundbank;

import static org.junit.Assert.*;

/**
 * Created by hug.
 */
public class TestBuggyAList {
  // YOUR TESTS HERE
  @Test
  public void testThreeAddThreeRemove() {
      AListNoResizing<Integer> aListNoResizing = new AListNoResizing<>();
      BuggyAList<Integer> buggyAList = new BuggyAList<>();
      int[] testArray = {4, 5, 6};
      for (int test : testArray) {
          aListNoResizing.addLast(test);
          buggyAList.addLast(test);
      }
      int[] resOfNoResizing = new int[testArray.length];
      int[] resOfBuggy = new int[testArray.length];
      for (int i = 0; i < testArray.length; i++) {
          resOfNoResizing[i] = aListNoResizing.removeLast();
          resOfBuggy[i] = buggyAList.removeLast();
      }
      Assert.assertArrayEquals(resOfNoResizing, resOfBuggy);
  }
  @Test
  public void randomizedTest() {
      AListNoResizing<Integer> L = new AListNoResizing<>();
      BuggyAList<Integer> R = new BuggyAList<>();

      int N = 50000;
      for (int i = 0; i < N; i += 1) {
          int operationNumber = StdRandom.uniform(0, 4);
          if (operationNumber == 0) {
              // addLast
              int randVal = StdRandom.uniform(0, 100);
              L.addLast(randVal);
              R.addLast(randVal);
          } else if (operationNumber == 1) {
              // size
              int sizeOfL = L.size();
              int sizeOfR = R.size();
              Assert.assertEquals(sizeOfL, sizeOfR);
          } else if (operationNumber == 2) {
              // getLast
              if (L.size() != 0) {
                  int lastValOfL = L.getLast();
                  int lastValOfR = R.getLast();
                  Assert.assertEquals(lastValOfL, lastValOfR);
              }
          } else if (operationNumber == 3) {
              // removeLast
              if (L.size() != 0) {
                  int lastValOfL = L.removeLast();
                  int lastValOfR = R.removeLast();
                  Assert.assertEquals(lastValOfL, lastValOfR);
              }
          }
      }
  }
}
