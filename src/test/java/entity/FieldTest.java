package entity;

import org.junit.Test;

/**
 * Created by Taras on 10.03.2017.
 */
public class FieldTest
{
    private final int MAX_CELLS_IN_FIELD = 64;

    @Test
    public void test() throws Exception
    {
        int homeX = 5;
        int homeY = 5;
        for (int i = homeX - 1; i < homeX + 2; i++)
        {
            for (int j = homeY - 1; j < homeY + 2; j++)
            {
                if (i == homeX && j == homeY)
                {
                    System.out.println("skip");
                }
                else
                {
                    System.out.println("homeX = " + i + " homeY = " + j);
                }
            }
        }
    }

    @Test
    public void createEmptyCellsTest()
    {
//        Field field = new Field();
//        field.createEmptyCells();
//        assertNotNull(field.cells);
//        assertEquals(field.cells.size(), MAX_CELLS_IN_FIELD);
    }
}
