package org.sorter;

import java.util.ArrayList;
import java.util.List;

public class ParseUtils {

    public static List<Integer> getListFromString(String listString){
        String[] numsAsString = listString.split(" ");

        List<Integer> vals = new ArrayList<>();

        for(String numAsString: numsAsString){
            vals.add(Integer.parseInt(numAsString));
        }

        return vals;
    }

    public static String createSendBackString(int widgetNum, List<Integer> vals){
        StringBuilder sb = new StringBuilder();
        sb.append(widgetNum + "::");
        for(int val: vals){
            sb.append(val + " ");
        }
        sb.deleteCharAt(sb.length()-1);

        return sb.toString();
    }
}
