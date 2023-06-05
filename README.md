# 记负均正
```java
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int a = 0; // 负数个数
        int b = 0; // 正整数个数
        int sum = 0;
        for (int i = 0; i < n; i++) {
            int num = in.nextInt();
            if (num < 0) {
                a++;
            } else if (num > 0) {
                b++;
                sum += num;
            }
        }
        if (b == 0) {
            System.out.println(a + " 0.0");
        } else {
            double val = sum * 1.0 / b;
            System.out.printf("%d %.1f", a, val);
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




