# 挑7
```java
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int num = 0;
        for (int i = 7; i <= n; i++) {
            if (i % 7 == 0 || String.valueOf(i).contains("7")) {
                num++;
            }
        }
        System.out.println(num);
    }
}
```
# 完全数计算
```java
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int num = 0;
        for (int i = 1; i <= n; i++) {
            int sum = 0;
            for (int j = 1; j < i; j++) {
                if (i % j == 0) {
                    sum += j;
                }
            }
            if (sum == i) {
                num++;
            }
        }
        System.out.println(num);
    }
}
```
# 高精度整数加法
```java
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        char[] array1 = in.nextLine().toCharArray();
        char[] array2 = in.nextLine().toCharArray();
        int i1 = array1.length - 1;
        int i2 = array2.length - 1;
        StringBuilder sb = new StringBuilder();
        int k = 0;
        while (i1 >= 0 && i2 >= 0) {
            int num1 = array1[i1--] - '0';
            int num2 = array2[i2--] - '0';
            int num = num1 + num2 + k;
            sb.append(((char) (num % 10 + '0')));
            k = num / 10;
        }
        while (i1 >= 0) {
            int num = array1[i1--] - '0' + k;
            sb.append(((char) (num % 10 + '0')));
            k = num / 10;
        }
        while (i2 >= 0) {
            int num = array2[i2--] - '0' + k;
            sb.append(((char) (num % 10 + '0')));
            k = num / 10;
        }
        sb.append(k);
        System.out.println(sb.reverse());
    }
}
```
# 输入n个整数，输出最小的m个整数
```java
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int m = in.nextInt();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = in.nextInt();
        }
        Arrays.sort(arr);
        for (int i = 0; i < m; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }
}
```
# 找出字符串中第一次只出现一次的字符
+ 方法一
```java
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String line = in.nextLine();
        char[] array = line.toCharArray();
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < array.length; i++) {
            if (set.contains(i)) {
                continue;
            }
            boolean same = false;
            for (int j = 0; j < array.length; j++) {
                if (i == j || set.contains(j)) {
                    continue;
                }
                if (array[i] == array[j]) {
                    set.add(j);
                    same = true;
                }
            }
            if (!same) {
                System.out.println(array[i]);
                return;
            }
        }
        System.out.println(-1);
    }
}
```
+ 方法二
```java
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String line = in.nextLine();
        char[] array = line.toCharArray();
        Map<Character, Integer> map = new LinkedHashMap<>();
        for (char ch : array) {
            if (map.containsKey(ch)) {
                map.put(ch, map.get(ch) + 1);
            } else {
                map.put(ch, 1);
            }
        }
        for (Map.Entry<Character, Integer> entry : map.entrySet()) {
            if (entry.getValue() == 1) {
                System.out.println(entry.getKey());
                return;
            }
        }
        System.out.println(-1);
    }
}
```
# 查找组成一个偶数组成的两个素数只差
```java
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int num = in.nextInt();
        int a = num / 2;
        int b = num / 2;
        while (a > 1 && b < num) {
            if (suShu(a) && suShu(b)) {
                break;
            }
            a--;
            b++;
        }
        System.out.println(a);
        System.out.println(b);
    }

    private static boolean suShu(int num) {
        for (int i = 2; i < num; i++) {
            if (num % i == 0) {
                return false;
            }
        }
        return true;
    }
}
```
# 放苹果
```java
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int m = in.nextInt(); // 苹果
        int n = in.nextInt(); // 盘子
        HashSet<String> ans = new HashSet<>();
        fun(m, n, new int[n], 0, 0, ans);
        System.out.println(ans.size());
    }

    /**
     * @param m 苹果个数
     * @param n 盘子个数
     * @param arr 每个盘子存放苹果的个数
     * @param index 盘子索引
     * @param curM 已放苹果个数
     * @param ans 结果
     */
    private static void fun(int m, int n, int[] arr, int index, int curM, HashSet<String> ans) {
        if (index >= n) {
            if (curM == m) {
                int[] temp = new int[arr.length];
                System.arraycopy(arr, 0, temp, 0, arr.length);
                Arrays.sort(temp);
                ans.add(Arrays.toString(temp));
            }
            return;
        }
        for (int i = 0; i <= m; i++) {
            arr[index] = i;
            fun(m, n, arr, index + 1, i + curM, ans);
        }
    }
}
```

