package D2022;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day11 {
    private static String PATH = "C:\\Users\\arina\\IdeaProjects\\untitled2\\src\\main\\resources\\22_11.1.txt";

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.lines(Paths.get(PATH)).collect(Collectors.toList());
        System.out.println(part1(lines));
        System.out.println(part2(lines));
    }

    static Monkey parse(List<String> monkey){
        Monkey result = new Monkey();
        for (int i = 0; i<monkey.size(); i++){
            String[] word = monkey.get(i).split(" ");
            if (word.length > 2) {
                if (word[2].equals("Starting")) {
                    for (int j = 4; j < monkey.get(i).split(" ").length; j++) {
                        result.items.add(Long.valueOf(word[j].replaceAll(",", "")));
                    }
                }
                if (word[2].equals("Operation:")) {
                    result.operation = word[6];
                    result.change = word[7];
                }
                if (word[2].equals("Test:")) {
                    result.divisible = Long.valueOf(word[5]);
                }
                if (word.length > 5 && word[5].equals("true:")) {
                    result.ifTrue = Integer.parseInt(word[9]);
                }
                if (word.length > 5 && word[5].equals("false:")) {
                    result.ifFalse = Integer.parseInt(word[9]);
                }
                result.times = 0;
            }
        }
        return result;
    }
    private static String part1(List<String> strings) {
        /*    List<Monkey> monkeys = new ArrayList<>();
        int currentMonkey = 0;
        for (int i = 0; i<strings.size(); i = i+7){
            List<String> monkey = new ArrayList<>();
            for(int j = 0; j<6; j++) {
                monkey.add(strings.get(i+j));
            }
            monkeys.add(parse(monkey));
        }
        for (int i = 0; i<20; i++){
            for (Monkey monkey: monkeys){
                monkey.turn(monkeys);
            }
        }
        long[] bussines = new long[monkeys.size()];
        for (int i = 0; i<monkeys.size(); i++){
            bussines[i] = monkeys.get(i).times;
        }
        Arrays.sort(bussines);

         */
        return "";//Long.toString(bussines[monkeys.size()-1]*bussines[monkeys.size()-2]);
    }

    private static String part2(List<String> strings) {
        List<Monkey> monkeys = new ArrayList<>();
        int currentMonkey = 0;
        for (int i = 0; i<strings.size(); i = i+7){
            List<String> monkey = new ArrayList<>();
            for(int j = 0; j<6; j++) {
                monkey.add(strings.get(i+j));
            }
            monkeys.add(parse(monkey));
        }
        for (int i = 0; i<1000; i++){
            for (Monkey monkey: monkeys){
                monkey.turn2(monkeys);
            }
        }
        long[] bussines = new long[monkeys.size()];
        for (int i = 0; i<monkeys.size(); i++){
            bussines[i] = monkeys.get(i).times;
            System.out.println(bussines[i]);
        }

        Arrays.sort(bussines);
        return "";//Long.toString(bussines[monkeys.size()-1]*bussines[monkeys.size()-2]);
    }

    private static class Monkey {


        List<Long> items = new ArrayList<>();
        String operation;
        String change;
        Long divisible;
        int ifTrue;
        int ifFalse;

        long times;

        private long operation(long value) {
            times++;
            long newWorry = 0;
            if (change.equals("old")) {
                return value * value;
            }
            switch (operation) {
                case "*" -> newWorry = value * Integer.parseInt(change);
                case "+" -> newWorry = value + Integer.parseInt(change);
            }
            return newWorry;
        }

        private boolean test(long value) {
            if (value % divisible == 0) {
                return true;
            }
            return false;
        }

        void turn(List<Monkey> monkeys) {
            for (long item : items) {
                long newValue = operation(item);
                newValue = newValue / 3;
                if (test(newValue)) {
                    monkeys.get(ifTrue).items.add(newValue);
                } else {
                    monkeys.get(ifFalse).items.add(newValue);
                }
            }
            items.removeAll(items);
        }
        void turn2(List<Monkey> monkeys) {
            for (long item : items) {
                long newValue = operation(item);
               // newValue = newValue / 3;
                if (test(newValue)) {
                    monkeys.get(ifTrue).items.add(newValue);
                } else {
                    monkeys.get(ifFalse).items.add(newValue);
                }
            }
            items.removeAll(items);
        }
    }
}

