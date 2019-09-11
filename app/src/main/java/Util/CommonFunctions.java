package Util;

import java.util.ArrayList;

public class CommonFunctions {


    public static String get_id(String prefix , ArrayList<String> arrayList){

        String id;
        int next = arrayList.size();
        next++;
        id = prefix + next;
        while (arrayList.contains(id)) {
            next++;
            id = prefix + next;
        }
        return id;
    }
}
