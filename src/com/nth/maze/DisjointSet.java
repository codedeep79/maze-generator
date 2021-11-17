package com.nth.maze;

/**
 *
 * Created by HAU TRUNG NGUYEN <haunt.hcm2015@gmail.com> on Nov 13, 2021
 */
public class DisjointSet {

    private final int[] array;
    private final int[] arrayOfSizes;
    private int size;

    DisjointSet(int capacity) {
        array = new int[capacity];

        for (int i = 0; i < array.length; i++) {
            array[i] = i;
        }

        arrayOfSizes = new int[capacity];

        for (int i = 0; i < arrayOfSizes.length; i++) {
            arrayOfSizes[i] = 1;
        }

        size = capacity;
    }

    int size() {
        return size;
    }

    private int find(int item) {
        if (array[item] == item) {
            return item;
        } else {
            return array[item] = find(array[item]);
        }
    }

    void union(int item1, int item2) {

        int set1 = find(item1);
        int set2 = find(item2);

        if (isConnected(item1, item2)) {
            return;
        }

        if (arrayOfSizes[set1] > arrayOfSizes[set2]) {
            array[set2] = array[set1];
            arrayOfSizes[set1] = arrayOfSizes[set1] + arrayOfSizes[set2];
        } else {
            array[set1] = array[set2];
            arrayOfSizes[set2] = arrayOfSizes[set1] + arrayOfSizes[set2];
        }

        size--;
    }

    boolean isConnected(int item1, int item2) {
        return find(item1) == find(item2);
    }

}
