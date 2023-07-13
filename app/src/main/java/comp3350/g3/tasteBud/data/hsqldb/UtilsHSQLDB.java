package comp3350.g3.tasteBud.data.hsqldb;

import comp3350.g3.tasteBud.object.Ingredient;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class UtilsHSQLDB {
    public static final String FIELD_SEP = "\n";

    public static String encodeString(List<String> stringList) {
        if (stringList == null) {throw new IllegalArgumentException("String list must not be null");}

        // repeatedly add the elements, separated by \n
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<stringList.size(); i++) {
            sb.append(stringList.get(i));
            if (i != stringList.size() - 1) {
                sb.append(FIELD_SEP);
            }
        }
        return sb.toString();
    }


}
