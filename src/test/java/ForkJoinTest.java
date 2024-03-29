import ForkJoinAlternative.Core;
import ForkJoinAlternative.NumberGroup;
import org.junit.jupiter.api.RepeatedTest;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class ForkJoinTest {

    @RepeatedTest(100)
    void forkJoinTest(){
        int[][] intMatrix= {
                {2,0,5,3,0,8,4,0,9},
                {0,7,0,0,0,0,0,5,0},
                {9,0,4,0,0,0,6,0,7},
                {5,0,0,0,4,0,0,0,2},
                {0,0,0,5,0,7,0,0,0},
                {6,0,0,0,3,0,0,0,8},
                {4,0,6,0,0,0,8,0,1},
                {0,2,0,0,0,0,0,6,0},
                {8,0,1,2,0,9,7,0,4}
        };

        ForkJoinAlternative.Core core = new Core(intMatrix);
        NumberGroup testRow1 = core.getRow(1);
        NumberGroup testRow2 = core.getRow(2);
        NumberGroup testRow3 = core.getRow(3);
        NumberGroup testRow4 = core.getRow(4);
        NumberGroup testRow5 = core.getRow(5);
        NumberGroup testRow6 = core.getRow(6);
        NumberGroup testRow7 = core.getRow(7);
        NumberGroup testRow8 = core.getRow(8);
        NumberGroup testRow9 = core.getRow(9);
        NumberGroup testColumn1 = core.getColumn(1);
        NumberGroup testColumn2 = core.getColumn(2);
        NumberGroup testColumn3 = core.getColumn(3);
        NumberGroup testColumn4 = core.getColumn(4);
        NumberGroup testColumn5 = core.getColumn(5);
        NumberGroup testColumn6 = core.getColumn(6);
        NumberGroup testColumn7 = core.getColumn(7);
        NumberGroup testColumn8 = core.getColumn(8);
        NumberGroup testColumn9 = core.getColumn(9);
        NumberGroup testBlock1  = core.getBlock(1);
        NumberGroup testBlock2  = core.getBlock(2);
        NumberGroup testBlock3  = core.getBlock(3);
        NumberGroup testBlock4  = core.getBlock(4);
        NumberGroup testBlock5  = core.getBlock(5);
        NumberGroup testBlock6  = core.getBlock(6);
        NumberGroup testBlock7  = core.getBlock(7);
        NumberGroup testBlock8  = core.getBlock(8);
        NumberGroup testBlock9  = core.getBlock(9);



        assertThat(testRow1.getGroupAsArray()).containsExactly(2,0,5,3,0,8,4,0,9);
        assertThat(testRow2.getGroupAsArray()).containsExactly(0,7,0,0,0,0,0,5,0);
        assertThat(testRow3.getGroupAsArray()).containsExactly(9,0,4,0,0,0,6,0,7);
        assertThat(testRow4.getGroupAsArray()).containsExactly(5,0,0,0,4,0,0,0,2);
        assertThat(testRow5.getGroupAsArray()).containsExactly(0,0,0,5,0,7,0,0,0);
        assertThat(testRow6.getGroupAsArray()).containsExactly(6,0,0,0,3,0,0,0,8);
        assertThat(testRow7.getGroupAsArray()).containsExactly(4,0,6,0,0,0,8,0,1);
        assertThat(testRow8.getGroupAsArray()).containsExactly(0,2,0,0,0,0,0,6,0);
        assertThat(testRow9.getGroupAsArray()).containsExactly(8,0,1,2,0,9,7,0,4);
        assertThat(testColumn1.getGroupAsArray()).containsExactly(2,0,9,5,0,6,4,0,8);
        assertThat(testColumn2.getGroupAsArray()).containsExactly(0,7,0,0,0,0,0,2,0);
        assertThat(testColumn3.getGroupAsArray()).containsExactly(5,0,4,0,0,0,6,0,1);
        assertThat(testColumn4.getGroupAsArray()).containsExactly(3,0,0,0,5,0,0,0,2);
        assertThat(testColumn5.getGroupAsArray()).containsExactly(0,0,0,4,0,3,0,0,0);
        assertThat(testColumn6.getGroupAsArray()).containsExactly(8,0,0,0,7,0,0,0,9);
        assertThat(testColumn7.getGroupAsArray()).containsExactly(4,0,6,0,0,0,8,0,7);
        assertThat(testColumn8.getGroupAsArray()).containsExactly(0,5,0,0,0,0,0,6,0);
        assertThat(testColumn9.getGroupAsArray()).containsExactly(9,0,7,2,0,8,1,0,4);
        assertThat(testBlock1.getGroupAsArray()).containsExactly(2,0,5,0,7,0,9,0,4);
        assertThat(testBlock2.getGroupAsArray()).containsExactly(3,0,8,0,0,0,0,0,0);
        assertThat(testBlock3.getGroupAsArray()).containsExactly(4,0,9,0,5,0,6,0,7);
        assertThat(testBlock4.getGroupAsArray()).containsExactly(5,0,0,0,0,0,6,0,0);
        assertThat(testBlock5.getGroupAsArray()).containsExactly(0,4,0,5,0,7,0,3,0);
        assertThat(testBlock6.getGroupAsArray()).containsExactly(0,0,2,0,0,0,0,0,8);
        assertThat(testBlock7.getGroupAsArray()).containsExactly(4,0,6,0,2,0,8,0,1);
        assertThat(testBlock8.getGroupAsArray()).containsExactly(0,0,0,0,0,0,2,0,9);
        assertThat(testBlock9.getGroupAsArray()).containsExactly(8,0,1,0,6,0,7,0,4);

        core.start();
        System.out.println(
                Arrays.toString(core.getRow(1).getGroupAsArray()) + "\n"+
                        Arrays.toString(core.getRow(2).getGroupAsArray()) + "\n"+
                        Arrays.toString(core.getRow(3).getGroupAsArray()) + "\n"+
                        Arrays.toString(core.getRow(4).getGroupAsArray()) + "\n"+
                        Arrays.toString(core.getRow(5).getGroupAsArray()) + "\n"+
                        Arrays.toString(core.getRow(6).getGroupAsArray()) + "\n"+
                        Arrays.toString(core.getRow(7).getGroupAsArray()) + "\n"+
                        Arrays.toString(core.getRow(8).getGroupAsArray()) + "\n"+
                        Arrays.toString(core.getRow(9).getGroupAsArray()) + "\n");

        assertThat(testRow1.getGroupAsArray()).containsExactly(2, 6, 5, 3, 7, 8, 4, 1, 9);
        assertThat(testRow2.getGroupAsArray()).containsExactly(1, 7, 8, 4, 9, 6, 2, 5, 3);
        assertThat(testRow3.getGroupAsArray()).containsExactly(9, 3, 4, 1, 2, 5, 6, 8, 7);
        assertThat(testRow4.getGroupAsArray()).containsExactly(5, 8, 9, 6, 4, 1, 3, 7, 2);
        assertThat(testRow5.getGroupAsArray()).containsExactly(3, 4, 2, 5, 8, 7, 1, 9, 6);
        assertThat(testRow6.getGroupAsArray()).containsExactly(6, 1, 7, 9, 3, 2, 5, 4, 8);
        assertThat(testRow7.getGroupAsArray()).containsExactly(4, 9, 6, 7, 5, 3, 8, 2, 1);
        assertThat(testRow8.getGroupAsArray()).containsExactly(7, 2, 3, 8, 1, 4, 9, 6, 5);
        assertThat(testRow9.getGroupAsArray()).containsExactly(8, 5, 1, 2, 6, 9, 7, 3, 4);





    }
}
