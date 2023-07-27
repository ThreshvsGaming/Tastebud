package comp3350.g3.tasteBud.data.hsqldb;

import java.util.List;

public class UtilitiesForDB {
    public static final String FIELD_SEP = "\n";


    public static String encoding(List<String> stringList) {
        StringBuilder sb = new StringBuilder();

        int size = stringList.size();
        int i = 0;

        while (size > 0) {
            sb.append(stringList.get(i));

            size--;
            i++;

            if (size > 0) {
                sb.append(FIELD_SEP);
            }
        }

        return sb.toString();
    }

}
