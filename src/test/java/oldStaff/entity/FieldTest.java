package oldStaff.entity;

import com.test.field.entity.Cell;
import com.test.field.entity.EmptyCell;
import com.test.field.entity.Field;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Taras.
 */
public class FieldTest
{
    Field field;

    @Before
    public void createField()
    {
        field = new Field();
        Cell emptyCell = new EmptyCell(2, 2);
        // Cell plantCell = new Plant();

        Map<Integer, Map<Integer, Cell>> cells = new ConcurrentHashMap<>();
        Map<Integer, Cell> mapY = new ConcurrentHashMap<>();
        cells.put(1, mapY);
        mapY.put(1, emptyCell);
    }

    @Test
    public void createEmptyCells() throws Exception
    {
    }

    @Test
    public void getCell() throws Exception
    {

    }

    @Test
    public void setCell() throws Exception
    {
        field.setCell(new EmptyCell(3,3),3, 3);
        Assert.assertEquals(field.getCells().size(), 3);
    }

}