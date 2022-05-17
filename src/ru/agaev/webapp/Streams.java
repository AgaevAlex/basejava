package ru.agaev.webapp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Streams {
    public static void main(String[] args) {
        int[] i = {9, 8, 8, 8, 9, 9, 9, 9, 8, 1};
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        System.out.println(minValue(i));
        System.out.println(oddOrEven(list).toString());
    }

    public static int minValue(int[] values) {
        return Arrays.stream(values).distinct().sorted().reduce(0, (acc, b) -> 10 * acc + b);
    }

/*    public static List<Integer> oddOrEven(List<Integer> integers) {
        return  integers.stream().reduce(Integer::sum)
                .map(i -> i % 2 == 0 ?
                        integers.stream().filter(a -> a % 2 == 0).collect(Collectors.toList())
                        : integers.stream().filter(a -> a % 2 != 0).collect(Collectors.toList())).orElse(new ArrayList<>());
    }*/

    /*    public static List<Integer> oddOrEven(List<Integer> integers) {
            int count = integers.stream().mapToInt(Integer::intValue).sum() % Map<Boolean, List<Integer>> abcd = 2;
            return integers.stream().filter(i -> i % 2 != count).collect(Collectors.toList());
        }*/
    public static List<Integer> oddOrEven(List<Integer> integers) {
        //        Map<Boolean, List<Integer>> abcd = integers.stream().map(i -> i % 2 == 0 ? abcd.put(true, i) : abcd.put(false, i));
        Map<Boolean, List<Integer>> abcd = integers.stream().collect(Collectors.partitioningBy(i -> i % 2 == 0));
        return abcd.get(false).size() % 2 == 0 ? abcd.get(false) : abcd.get(true);
    }

}

