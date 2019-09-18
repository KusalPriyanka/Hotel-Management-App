package Util;

import java.util.ArrayList;
import java.util.List;

import Modal.MainMeals;

public class CommonFunctions {


    public static String get_id(String prefix , List<MainMeals> arrayList){

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
