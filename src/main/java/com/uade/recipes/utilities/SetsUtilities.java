package com.uade.recipes.utilities;

import java.util.HashSet;
import java.util.Set;

public class SetsUtilities {
    public static <T> Set<T> mergeSet(Set<T> a, Set<T> b) {
        // Creating an empty HashSet
        Set<T> mergedSet = new HashSet<T>();
        // Adding the two sets to be merged
        // into the new Set using addAll() method
        mergedSet.addAll(a);
        mergedSet.addAll(b);

        // Returning the merged set
        return mergedSet;
    }

    public static <T> Set<T> intersectionSet(Set<T> a, Set<T> b) {
        Set<T> intersectedSet = new HashSet<T>(a);

        intersectedSet.retainAll(b);

        return intersectedSet;
    }

}
