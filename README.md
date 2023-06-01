# 配置文件恢复
```java
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        Map<List<String>, String> map = new HashMap<>();
        map.put(Arrays.asList("reset"), "reset what");
        map.put(Arrays.asList("reset", "board"), "board fault");
        map.put(Arrays.asList("board", "add"), "where to add");
        map.put(Arrays.asList("board", "delete"), "no board at all");
        map.put(Arrays.asList("reboot", "backplane"), "impossible");
        map.put(Arrays.asList("backplane", "abort"), "install first");
        Set<List<String>> keys = map.keySet();
        Scanner in = new Scanner(System.in);
        while (in.hasNextLine()) {
            String line = in.nextLine();
            String[] commands = line.split(" ");
            List<List<String>> findKeys = new ArrayList<>();
            for (List<String> key : keys) {
                if (match(key, commands)) {
                    findKeys.add(key);
                }
            }
            if (findKeys.size() == 1) {
                System.out.println(map.get(findKeys.get(0)));
            } else {
                System.out.println("unknown command");
            }
        }
    }

    private static boolean match(List<String> key, String[] commands) {
        if (key.size() == commands.length) {
            for (int i = 0; i < commands.length; i++) {
                if (commands[i].isEmpty() || !key.get(i).startsWith(commands[i])) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
```
# 24点游戏算法
```java
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        double[] nums = new double[4];
        char[] letters = {'+', '-', '*', '/'};
        for (int i = 0; i < 4; i++) {
            nums[i] = in.nextInt();
        }
        ArrayList<String> list = new ArrayList<>();
        list.add(String.valueOf(nums[0]));
        System.out.println(calc(nums, 1, letters, nums[0], list));
    }

    private static boolean calc(double[] nums, int a, char[] letters, double ans, ArrayList<String> list) {
        if (ans > 24) {
            return false;
        }
        if (a >= 4) {
            if (ans == 24) {
                System.out.println(list);
            }
            return ans == 24;
        }
        for (char letter : letters) {
            for (int i = a; i < nums.length; i++) {
                switch (letter) {
                    case '+':
                        ans += nums[i];
                        break;
                    case '-':
                        ans -= nums[i];
                        break;
                    case '*':
                        ans *= nums[i];
                        break;
                    case '/':
                        ans /= nums[i];
                        break;
                }
                list.add(String.valueOf(letter));
                list.add(String.valueOf(nums[i]));
                if (calc(nums, i + 1, letters, ans, list)) {
                    return true;
                }
                list.remove(list.size() - 1);
                list.remove(list.size() - 1);
            }
        }
        return false;
    }
}
```
# 成绩排序
```java
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    private static class Score {
        private String name;

        private int score;

        public Score(String name, int score) {
            this.name = name;
            this.score = score;
        }

        public String getName() {
            return name;
        }

        public Score setName(String name) {
            this.name = name;
            return this;
        }

        public int getScore() {
            return score;
        }

        public Score setScore(int score) {
            this.score = score;
            return this;
        }
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int k = in.nextInt(); // 0 降序 1 升序
        List<Score> list = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            list.add(new Score(in.next(), in.nextInt()));
        }
        if (k == 0) {
            list = list.stream().sorted((score1, score2) -> score2.score - score1.score).collect(Collectors.toList());
        } else {
            list = list.stream().sorted(Comparator.comparing(Score::getScore)).collect(Collectors.toList());
        }
        for (Score score : list) {
            System.out.println(score.name + " " + score.score);
        }
    }
}
```
# 
```java

```
# 
```java

```
# 
```java

```
# 
```java

```
# 
```java

```
# 
```java

```




