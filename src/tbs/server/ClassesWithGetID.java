package tbs.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class ClassesWithGetID {
    public abstract String get_ID();

    public static List<String> getAndSort(List<? extends ClassesWithGetID> listToProcess) {

        //This method will retrieve all ID's in a list and sort them alphabetically.
        List<String> IDList = new ArrayList<>();

        //Run through all objects grabbing and placing the id into the generated list.

        for (ClassesWithGetID currentItem : listToProcess) {
            IDList.add(currentItem.get_ID());
        }

        //Sort list alphabetically.
        Collections.sort(IDList);
        return IDList;

    }

}
