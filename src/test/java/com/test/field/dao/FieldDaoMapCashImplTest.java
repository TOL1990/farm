package com.test.field.dao;

import com.test.core.ConstCollections;
import com.test.field.entity.Cell;
import org.junit.Test;

import java.util.Map;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

/**
 * Created by Taras.
 */
public class FieldDaoMapCashImplTest
{
//    @Before
//    public void setup()
//    {
//
//    }
//
//    @Test
//    public void getBuildingWithIdTest()
//    {
//        FieldDaoMapCashImpl daoImpl = new FieldDaoMapCashImpl();
//        assertNotNull(daoImpl.getAllBuildings());
//        assertTrue(daoImpl.getAllBuildings().size() > 1 );
//    }

    @Test
    public void getCellsByFieldId()
    {
        FieldDaoMapCashImpl daoImpl = new FieldDaoMapCashImpl();
        Map<Integer, Map<Integer, Cell>> cellsList = daoImpl.getCellsByFieldId(22);
        assertEquals(8, cellsList.size()); //�������� ���������� �����

        for (Map.Entry<Integer, Map<Integer, Cell>> entry : cellsList.entrySet())//�������� ���������� ��������
        {
            assertEquals(8, entry.getValue().size());
        }

        assertNotNull(cellsList.get(1).get(1)); // ��������� ��� ������ �����������.
    }

    @Test // ������ ����� �.�. � ���� ����� � ������������ �� ���� ������� ������������
    public void getAllPlantsTest()
    {
        FieldDaoMapCashImpl daoImpl = new FieldDaoMapCashImpl();
        assertNotNull(ConstCollections.availablePlants);
            
        int listSize = ConstCollections.availablePlants.size();
        assertTrue(listSize > 1);
    }

    @Test  // ������ ����� �.�. � ���� ����� � ������������ �� ���� ������� ������������
    public void getAllBuildingsTest()
    {
        FieldDaoMapCashImpl daoImpl = new FieldDaoMapCashImpl();
        assertNotNull(ConstCollections.availableBuildings);
        int listSize = ConstCollections.availableBuildings.size();
        assertTrue(listSize > 1);
    }
}
