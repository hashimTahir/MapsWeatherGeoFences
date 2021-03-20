/*
 * Copyright (c) 2021/  3/ 20.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.Helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ListUtils {
    public static List<String> hConvertArrayToArrayList(String[] stringArray) {
        return Arrays.asList(stringArray);
    }

    public static String[] hCombineStringArrays(String[] a, String[] b) {
        int length = a.length + b.length;
        String[] result = new String[length];
        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        return result;
    }

    public static void removeAllSubList(List<?> list, List<?> subList) {
        // find first occurrence of the subList in the list, O(nm)
        int i = Collections.indexOfSubList(list, subList);
        // if found
        if (i != -1) {
            // bulk remove, O(m)
            list.subList(i, i + subList.size()).clear();
            // recurse with the rest of the list
            removeAllSubList(list.subList(i, list.size()), subList);
        }
    }

    public static <T> List<T> hMergeLists(List<T>... lists) {
        return new ArrayList<T>() {{
            for (List<T> l : lists)
                addAll(l);
        }};
    }


}
