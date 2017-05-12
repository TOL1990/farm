package com.test.Area.dao;

import com.test.Area.entity.Area;

/**
 * Created by Taras
 */
public interface  AreaDao {
   
    Area getAreaById(long areaId);
    Area getAreaByCoordinates(int x, int y);
    Area getAreaByFieldId(long fieldId);    
  //  AreaCell getAreaCellByFieldId(long fielId);
    

}
