import model.dao.impl.AreaDaoImpl;
import model.dao.utils.DaoUtils;
import model.entity.Area;
import model.entity.AreaCell;
import model.entity.AreaType;
import model.entity.Field;
import model.service.GameService;
import model.service.propertyconfig.AreaService;
import model.service.propertyconfig.QueryConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Taras on 26.04.2017.
 */
public class MainForTest {
    public static void main(String[] args) {

        AreaService service = new AreaService();
        List<Area> areaList = service.getAllAreas();
      //  Field field = new Field();
        //field.setId(24);
       //Area area =  service.getArea(field);
        //System.out.println("area - " + area);
    }
}
