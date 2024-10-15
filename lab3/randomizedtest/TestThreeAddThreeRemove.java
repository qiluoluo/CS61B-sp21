package randomizedtest;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Array;

public class TestThreeAddThreeRemove {
    @Test
    public void testThreeAddThreeRemove() {
        AListNoResizing<Integer> aListNoResizing = new AListNoResizing<>();
        BuggyAList<Integer> buggyAList = new BuggyAList<>();
        int[] testArray = {4, 5, 6};
        for (int test: testArray) {
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
}
