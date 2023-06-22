package comp3350.g3.tasteBud.data.hsqldb;

import comp3350.g3.tasteBud.object.Ingredient;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class UtilsHSQLDB {
    public static final String FIELD_SEP = "\n";

    /**
     * Returns a string for correct insertion into data backend.
     * Encoded strings lists are simply joined with return-characters.
     *
     */
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


    /**
     * Returns a list from select results from data backend.
     * Lists are decoded by splitting on separation characters.
     */

    public static ArrayList<String> decodeString(String str) {
        if (str == null) {
            throw new IllegalArgumentException("String must not be null");
        }

        if (str.length() == 0) {
            return new ArrayList<>();
        } else {
            String[] stringArray = str.split(FIELD_SEP);
            return new ArrayList<>(Arrays.asList(stringArray));
        }
    }
}
