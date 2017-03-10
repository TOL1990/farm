package entity;

import model.entity.Field;
import org.junit.Test;
import static junit.framework.Assert.*;

/**
 * Created by Taras on 10.03.2017.
 */
public class FieldTest {
    private final int MAX_CELLS_IN_FIELD = 64;

    @Test
    public void createEmptyCellsTest()
    {
        Field field = new Field();
        field.createEmptyCells();
        assertNotNull(field.cells);
        assertEquals(field.cells.size(), MAX_CELLS_IN_FIELD);
    }
}
