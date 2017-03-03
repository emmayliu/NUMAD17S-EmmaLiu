package edu.neu.madcourse.numad17s_emmaliu.wordGame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * Created by emma on 2/27/17.
 * I was trying to put word into board by generating path in real time, but it's time consuming and
 * hurt app performance. So I decided to write this separate class to generate all
 * possible valid path
 * and randomly return 9 path for assigned word into board
 * For each start point from 0 - 8, there is at least 32 ways to arrange letters in one grid.
 */

public class CalculatePath {
    private static Map<Integer, ArrayList<Integer>> map;

    public static void populateMap () {
        map = new HashMap<>();
        map.put(0, new ArrayList<>(Arrays.asList(1, 3, 4)));
        map.put(1, new ArrayList<>(Arrays.asList(0, 2, 3, 4, 5)));
        map.put(2, new ArrayList<>(Arrays.asList(1, 4, 5)));
        map.put(3, new ArrayList<>(Arrays.asList(0, 1, 4, 6, 7)));
        map.put(4, new ArrayList<>(Arrays.asList(0, 1, 2, 3, 5, 6, 7, 8)));
        map.put(5, new ArrayList<>(Arrays.asList(1, 2, 4, 7, 8)));
        map.put(6, new ArrayList<>(Arrays.asList(3, 4, 7)));
        map.put(7, new ArrayList<>(Arrays.asList(3, 4, 5, 6, 8)));
        map.put(8, new ArrayList<>(Arrays.asList(4, 5, 7)));
    }

    public static ArrayList<String> generatePath() {
        populateMap();
        ArrayList<String> result = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            ArrayList<String> paths = new ArrayList<>();
            ArrayList<Integer> path = new ArrayList<>();
            Set<Integer> set = new HashSet<>();
            set.add(i);
            path.add(i);
            generatePathHelper(map, set, i, paths, path);
            int number = pickNumber();
            result.add(paths.get(number));
        }
        return result;
    }

    public static int pickNumber() {
        Random random = new Random();
        int number = random.nextInt(32) + 0;
        return number;
    }

    public static void generatePathHelper(Map<Integer, ArrayList<Integer>> map, Set<Integer> set,
                                          int pos, ArrayList<String> paths,
                                          ArrayList<Integer> path) {
        if (path.size() == 9) {
            StringBuilder sb = new StringBuilder();
            for (int i : path) {
                sb.append(i);
            }
            String s = sb.toString();
            paths.add(s);
            return;
        }

        ArrayList<Integer> neighbors = map.get(pos);
        for (int neighbor : neighbors) {
            if (!set.contains(neighbor)) {
                path.add(neighbor);
                set.add(neighbor);
                generatePathHelper(map, set, neighbor, paths, path);
                path.remove(path.size() - 1);
                set.remove(neighbor);
            }
        }
    }
    public static void main(String[] args) {
        generatePath();
    }
}
