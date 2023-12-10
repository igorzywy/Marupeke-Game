import com.sun.org.apache.bcel.internal.generic.ATHROW;
import org.junit.Assert;
import org.junit.Test;
import org.junit.internal.runners.statements.ExpectException;

import static org.junit.Assert.*;

public class MarupekeGridTest {
    @Test
    public void gridTest() {
        MarupekeGrid g = new MarupekeGrid(6,6);

        assertEquals(6, g.getGridW());
        assertEquals(6, g.getGridL());
        //min grid size should be 3
        MarupekeGrid g1 = new MarupekeGrid(2, 2);

        assertEquals(3, g1.getGridW());
        assertEquals(3, g1.getGridL());
        //max grid size should be 10
        MarupekeGrid g2 = new MarupekeGrid(11, 12);

        assertEquals(10, g2.getGridW());
        assertEquals(10, g2.getGridL());


    }
    @Test
    public void setSolidTest() {
        MarupekeGrid g = new MarupekeGrid(5, 5);
        assertTrue(g.setSolid(3, 4));
        assertFalse(g.setSolid(3, 4));
    }

    @Test
    public void setXTest() {
        MarupekeGrid g = new MarupekeGrid(5, 5);
        //checking if the same tile can be edited which it shouldn't be
        assertTrue(g.setX(2, 3, false));
        assertFalse(g.setX(2, 3, false));
        assertTrue(g.setX(4, 4, false));
        assertFalse(g.setX(4, 4, false));
        //checking full grid
        MarupekeGrid g2 = new MarupekeGrid(6, 6);

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                assertTrue(g2.setX(j, i, false));

            }

        }
        boolean[][] b = g2.getEditable();
        //testing to see if all of the grid is not editable
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                assertFalse(b[j][i]);
            }

        }
    }

    @Test
    public void unmarkTest() {
        MarupekeGrid g = new MarupekeGrid(5,5);
        g.markX(0,0);
        String expected = "X____\n" +
                "_____\n" +
                "_____\n" +
                "_____\n" +
                "_____\n";
        assertEquals(expected, g.toString());
        assertTrue(g.getEditablePart(0,0));
        g.unmark(0,0);
        String expected2 = "_____\n" +
                "_____\n" +
                "_____\n" +
                "_____\n" +
                "_____\n";
        assertEquals(expected2, g.toString());
        assertTrue(g.getEditablePart(0,0));
    }

    @Test
    public void randomPuzzle() {
        //checking if the return null statement works

        try {
            MarupekeGrid g = MarupekeGrid.randomPuzzle(5, 10, 11, 120);
            assertFalse(true);
        }catch (TooManyMarkedSquares e) {
            assertTrue(true);
        }
        try{
            MarupekeGrid g1 = MarupekeGrid.randomPuzzle(5, 2, 2, 3);
            assertTrue(true);
        }catch (TooManyMarkedSquares e) {
            assertFalse(true);
        }


        int size = 5;
        //counts if the number of Xs Os and #s is the same in the grid as the input
        try{
            MarupekeGrid g2 = MarupekeGrid.randomPuzzle(size, 2, 3, 2);
            char[][] c = g2.getGrid();
            int fCount = 0;
            int xCount = 0;
            int oCount = 0;
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {

                    if (c[i][j] == '#') {
                        fCount++;
                    }
                    if (c[i][j] == 'X') {
                        xCount++;
                    }
                    if (c[i][j] == 'O') {
                        oCount++;
                    }
                }
            }
            assertEquals(2, fCount);
            assertEquals(3, xCount);
            assertEquals(2, oCount);
        }catch (TooManyMarkedSquares e) {
            assertTrue(true);
        }

        int size2 = 10;
        try{
            MarupekeGrid g3 = MarupekeGrid.randomPuzzle(size2, 1, 4, 5);
            char[][] c2 = g3.getGrid();
            int fCount2 = 0;
            int xCount2 = 0;
            int oCount2 = 0;
            for (int i = 0; i < size2; i++) {
                for (int j = 0; j < size2; j++) {

                    if (c2[i][j] == '#') {
                        fCount2++;
                    }
                    if (c2[i][j] == 'X') {
                        xCount2++;
                    }
                    if (c2[i][j] == 'O') {
                        oCount2++;
                    }
                }
            }
            assertEquals(1, fCount2);
            assertEquals(4, xCount2);
            assertEquals(5, oCount2);
        }catch (TooManyMarkedSquares e) {
            assertTrue(true);
        }




    }
    @Test
    public void setOTest() {
        MarupekeGrid g = new MarupekeGrid(5, 5);
        assertTrue(g.setO(2, 2, false));
        assertFalse(g.setO(2, 2, false));
        assertFalse(g.setX(2, 2, false));
        assertFalse(g.setSolid(2, 2));
    }

    @Test
    public void toStringTest() {
        MarupekeGrid g = new MarupekeGrid(5,5);
        g.setX(0,0,false);
        g.setSolid(4, 4);
        g.setO(0, 4, false);
        g.setX(4, 0, false);
        String expected = "X___O\n" +
                "_____\n" +
                "_____\n" +
                "_____\n" +
                "X___#\n";
        //checks to see if the string expected is the same as the toString() return
        assertEquals(expected, g.toString());
        MarupekeGrid g2 = new MarupekeGrid(5, 5);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 2; j++) {
                g2.setX(i, j, false);
                g2.setSolid(j, i);
            }
        }
        String expected2 = "XX##_\n" +
                "#X##_\n" +
                "XX___\n" +
                "XX___\n" +
                "_____\n";
        assertEquals(expected2, g2.toString());
        MarupekeGrid g3 = new MarupekeGrid(5, 5);
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                g3.setSolid(i, j);

            }

        }
        g3.setX(4, 4, false);
        g3.setO(0, 0, false);
        //checking if the grid has changed after it was set solid which it shouldn't have
        String expected3 = "#####\n" +
                "#####\n" +
                "#####\n" +
                "#####\n" +
                "#####\n";
        assertEquals(expected3, g3.toString());
        String notExpected1 = "O####\n" +
                "#####\n" +
                "#####\n" +
                "#####\n" +
                "####X\n";
        assertNotEquals(notExpected1, g3.toString());
    }

    @Test
    public void markXTest() {
        MarupekeGrid g = new MarupekeGrid(5,5);
        g.markX(0,0);
        String expected = "X____\n" +
                "_____\n" +
                "_____\n" +
                "_____\n" +
                "_____\n";
        assertEquals(expected, g.toString());
        assertTrue(g.getEditablePart(0,0));
    }

    @Test
    public void markOTest() {
        MarupekeGrid g = new MarupekeGrid(5,5);
        g.markO(0,0);
        String expected = "O____\n" +
                "_____\n" +
                "_____\n" +
                "_____\n" +
                "_____\n";
        assertEquals(expected, g.toString());
        assertTrue(g.getEditablePart(0,0));
    }

    @Test
    public void isLegalTest() {
        MarupekeGrid g = new MarupekeGrid(5, 5);
        g.setX(4,0,false);
        g.setX(4,1,false);
        g.setX(4,2,false);

        assertFalse(g.isLegalGrid());

        MarupekeGrid g1 = new MarupekeGrid(5, 5);
        g1.setX(4,0,false);
        g1.setX(4,1,false);
        g1.setO(4,2,false);

        assertTrue(g1.isLegalGrid());

        MarupekeGrid g2 = new MarupekeGrid(2,2);
        assertTrue(g2.isLegalGrid());

        MarupekeGrid g3 = new MarupekeGrid(5, 5);
        g3.setO(2,0,false);
        g3.setO(3,1,false);
        g3.setO(4,2,false);

        assertFalse(g3.isLegalGrid());
        MarupekeGrid g4 = new MarupekeGrid(10,10);
        g4.setO(2,4,false);
        g4.setO(3,3,false);
        g4.setO(4,2,false);

        assertFalse(g4.isLegalGrid());

        MarupekeGrid g5 = new MarupekeGrid(6,6);
        g5.setX(0,2,false);
        g5.setX(1,2,false);
        g5.setX(2,2,false);

        assertFalse(g5.isLegalGrid());
    }

}