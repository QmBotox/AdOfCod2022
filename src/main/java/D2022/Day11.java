package D2022;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Day11 {
    private static String PATH = "C:\\Users\\arina\\IdeaProjects\\untitled2\\src\\main\\resources\\22_11.1.txt";

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.lines(Paths.get(PATH)).collect(Collectors.toList());
        System.out.println(part1(lines));
        System.out.println(part2(lines));
    }

    private static String part1(List<String> strings) {
        List<String> trueMove = new ArrayList<>();
        List<String> falseMove = new ArrayList<>();
        List<Monkey> monkeys = new ArrayList<>();
        int currentMonkey = 0;
        for (String string : strings) {
            String[] word = string.split(" ");
            if (word[0].equals("Monkey")) {
                Monkey monkey = new Monkey();
                monkeys.add(monkey);
                monkeys.get(currentMonkey).items = new ArrayList<>();
                currentMonkey++;
            }
        }
        currentMonkey = 0;
        for (String string : strings) {
            String[] word = string.split(" ");
            if (string.length() > 1 && word[0].equals("")) {
                if (word[2].equals("Starting")) {
                    for (int i = 4; i < string.split(" ").length; i++) {
                        monkeys.get(currentMonkey).items.add(word[i].replaceAll(",", ""));
                    }
                }
                for (int i = 0; i < monkeys.get(currentMonkey).items.size(); i++) {
                    if (word[2].equals("Operation:")) {
                        String newWorry = operation(monkeys.get(currentMonkey).items.get(i), word[6], word[7]);
                        monkeys.get(currentMonkey).items.set(i, newWorry);
                    }
                    if (word[2].equals("Test:")) {
                        if (test(monkeys.get(currentMonkey).items.get(i), word[5])) {
                            trueMove.add(monkeys.get(currentMonkey).items.get(i));
                        } else {
                            falseMove.add(monkeys.get(currentMonkey).items.get(i));
                        }
                    }
                    if (word.length > 5 && word[5].equals("true:") && trueMove.size() > 0) {
                        monkeys.get(Integer.parseInt(word[9])).items.addAll(trueMove);
                        trueMove.removeAll(trueMove);
                    }
                    if (word.length > 5 && word[5].equals("false:") && falseMove.size() > 0) {
                        monkeys.get(Integer.parseInt(word[9])).items.addAll(falseMove);
                        falseMove.removeAll(falseMove);
                        monkeys.get(currentMonkey).items.removeAll(monkeys.get(currentMonkey).items);
                        currentMonkey++;
                        break;
                    }
                }
            }
        }
        for (int round = 0; round<19; round++) {
            for (String string : strings) {
                String[] word = string.split(" ");
                if (word[0].equals("Monkey")){
                    currentMonkey = Integer.parseInt(word[1].replaceAll(":",""));
                }
                if (word.length > 5 && !word[2].equals("Starting"))
                    for (int i = 0; i < monkeys.get(currentMonkey).items.size(); i++) {
                        if (word[2].equals("Operation:")) {
                            String newWorry = operation(monkeys.get(currentMonkey).items.get(i), word[6], word[7]);
                            monkeys.get(currentMonkey).items.set(i, newWorry);
                        }
                        if (word[2].equals("Test:")) {
                            if (test(monkeys.get(currentMonkey).items.get(i), word[5])) {
                                trueMove.add(monkeys.get(currentMonkey).items.get(i));
                            } else {
                                falseMove.add(monkeys.get(currentMonkey).items.get(i));
                            }
                        }
                        if (word.length > 5 && word[5].equals("true:") && trueMove.size() > 0) {
                            monkeys.get(Integer.parseInt(word[9])).items.addAll(trueMove);
                            trueMove.removeAll(trueMove);
                        }
                        if (word.length > 5 && word[5].equals("false:") && falseMove.size() > 0) {
                            monkeys.get(Integer.parseInt(word[9])).items.addAll(falseMove);
                            falseMove.removeAll(falseMove);
                            monkeys.get(currentMonkey).items.removeAll(monkeys.get(currentMonkey).items);
                            break;
                        }
                    }
            }
        }
        System.out.println(monkeys.get(0).items);
        System.out.println(monkeys.get(1).items);
        return "";
    }

    private static String part2(List<String> strings) {
        return "";
    }

    private static class Monkey {
        List<String> items;
    }

    private static boolean test(String worry, String divisible) {
        if (Integer.parseInt(worry) % Integer.parseInt(divisible) == 0) {
            return true;
        }
        return false;
    }

    private static String operation(String old, String oper, String change) {
        int newWorry = 0;
        if (change.equals("old")) {
            change = old;
        }
        switch (oper) {
            case "*" -> newWorry = Integer.parseInt(old) * Integer.parseInt(change);
            case "+" -> newWorry = Integer.parseInt(old) + Integer.parseInt(change);
        }
        newWorry = Math.round(newWorry / 3);
        return Integer.toString(newWorry);
    }
}

