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
# 矩阵乘法
```java
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int x = in.nextInt();
        int y = in.nextInt();
        int z = in.nextInt();
        int[][] arr1 = new int[x][y];
        int[][] arr2 = new int[y][z];
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                arr1[i][j] = in.nextInt();
            }
        }
        for (int i = 0; i < y; i++) {
            for (int j = 0; j < z; j++) {
                arr2[i][j] = in.nextInt();
            }
        }

        for (int i = 0; i < x; i++) {
            for (int j = 0; j < z; j++) {
                int sum = 0;
                for (int k = 0; k < y; k++) {
                    sum += arr1[i][k] * arr2[k][j];
                }
                System.out.print(sum + " ");
            }
            System.out.println();
        }
    }
}
```
# 矩阵乘法计算量估算
```java
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;

public class Main {
    private static class MAP {
        private int row;

        private int col;

        public MAP(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = Integer.parseInt(in.nextLine());
        List<MAP> maps = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            String[] split = in.nextLine().split(" ");
            maps.add(new MAP(Integer.parseInt(split[0]), Integer.parseInt(split[1])));
        }
        char[] array = in.nextLine().toCharArray();
        Map<Character, MAP> map = new HashMap<>();
        int i = 0;
        for (char ch : array) {
            if (ch != '(' && ch != ')') {
                map.put(ch, maps.get(i++));
            }
        }

        Stack<Character> stack = new Stack<>();
        int total = 0;
        for (char ch : array) {
            if (ch == ')') {
                StringBuilder sb = new StringBuilder();
                while (stack.peek() != '(') {
                    sb.append(stack.pop());
                }
                stack.pop();
                // 计算
                int num = 0;
                String s = sb.reverse().toString();
                int row = 0, col = 0;
                if (s.length() >= 2) {
                    for (i = 0; i < s.length() - 1; i++) {
                        MAP m1 = map.get(s.charAt(i));
                        MAP m2 = map.get(s.charAt(i + 1));
                        num += m1.row * m1.col * m2.col;
                        m2.row = m1.row;
                        row = m2.row;
                        col = m2.col;
                    }
                }
                if (num > 0) {
                    total += num;
                    map.put('1', new MAP(row, col));
                    stack.push('1');
                }
            } else {
                stack.push(ch);
            }
        }
        System.out.println(total);
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




