package Util;

import java.util.ArrayList;
import java.util.List;

import Modal.EM_HallManagement;
import Modal.MainMeals;
import Modal.ShortEats;

public class CommonFunctions {


    public static String get_id(String prefix , List<MainMeals> arrayList){

        List<String> list = new ArrayList<>();
        for (MainMeals mm:arrayList){
            list.add(mm.getId());
        }

        String id;
        int next = arrayList.size();
        next++;
        id = prefix + next;
        while (list.contains(id)) {
            next++;
            id = prefix + next;
        }
        return id;
    }

    public static String get_EM_Hall_Id(String prefix , List<EM_HallManagement> arrayList){

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


    public static String get_short_eats_id(String prefix , List<ShortEats> arrayList){

        List<String> list = new ArrayList<>();
        for (ShortEats se:arrayList){
            list.add(se.getId());
        }

        String id;
        int next = arrayList.size();
        next++;
        id = prefix + next;
        while (list.contains(id)) {
            next++;
            id = prefix + next;
        }
        return id;
    }










}
