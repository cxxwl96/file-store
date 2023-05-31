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
