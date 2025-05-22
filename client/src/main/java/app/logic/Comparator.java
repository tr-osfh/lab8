package app.logic;

import java.util.PriorityQueue;
import seClasses.Dragon;

public class Comparator {
    public static boolean compare(PriorityQueue<Dragon> set1, PriorityQueue<Dragon> set2) {
        return set1.equals(set2);
    }
}