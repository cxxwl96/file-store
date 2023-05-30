```java
package com.cxxwl96.codepart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import lombok.SneakyThrows;

public class MainClass {
    @SneakyThrows
    public static void main(String[] args) {
        String express = "(1+2)*3/{(1+2*3)/[3-5]-1}-5";
        Map<String, String> map = new HashMap<>();
        map.put(")", "(");
        map.put("]", "[");
        map.put("}", "{");
        Stack<String> stack = new Stack<>();
        for (int i = 0; i < express.length(); i++) {
            String ch = String.valueOf(express.charAt(i));
            if (map.containsKey(ch)) {
                List<String> list = new ArrayList<>();
                while (!stack.isEmpty() && !map.get(ch).equals(stack.peek())) {
                    list.add(stack.pop());
                }
                stack.pop();
                String eval = eval(reverse(list));
                stack.push(eval);
            } else {
                stack.push(ch);
            }
        }
        List<String> list = new ArrayList<>();
        while (!stack.isEmpty()) {
            list.add(stack.pop());
        }
        System.out.println(eval(reverse(list)));
    }

    private static String reverse(List<String> list) {
        StringBuilder sb = new StringBuilder();
        for (int i = list.size() - 1; i >= 0; i--) {
            sb.append(list.get(i));
        }
        return sb.toString();
    }

    private static String eval(String express) {
        if (isNumber(express)) {
            return express;
        }
        for (int i = 1; i < express.length(); i++) {
            if (express.charAt(i) == '*' || express.charAt(i) == '/') {
                String newExpress = evalTwoNumber(express, i, express.charAt(i));
                return eval(newExpress);
            }
        }
        for (int i = 1; i < express.length(); i++) {
            if (express.charAt(i) == '+' || express.charAt(i) == '-') {
                String newExpress = evalTwoNumber(express, i, express.charAt(i));
                return eval(newExpress);
            }
        }
        return "";
    }

    private static String evalTwoNumber(String express, int i, char letter) {
        int index1 = i - 1;
        while (index1 >= 0 && isNumber(express.charAt(index1))) {
            index1--;
        }
        if (index1 == 0 && express.charAt(index1) == '-') {
            index1--;
        }
        int num1 = Integer.parseInt(express.substring(index1 + 1, i));
        int index2 = i + 1;
        if (index2 < express.length() && express.charAt(index2) == '-') {
            index2++;
        }
        while (index2 < express.length() && isNumber(express.charAt(index2))) {
            index2++;
        }
        int num2 = Integer.parseInt(express.substring(i + 1, index2));
        int ans = 0;
        switch (letter) {
            case '+':
                ans = num1 + num2;
                break;
            case '-':
                ans = num1 - num2;
                break;
            case '*':
                ans = num1 * num2;
                break;
            case '/':
                ans = num1 / num2;
                break;
        }
        return express.substring(0, index1 + 1) + ans + express.substring(index2);
    }

    private static boolean isNumber(String text) {
        if (text == null || text.isEmpty()) {
            return false;
        }
        int i = 0;
        if (text.charAt(0) == '-' || text.charAt(0) == '+') {
            if (text.length() < 2) {
                return false;
            }
            i = 1;
        }
        for (; i < text.length(); i++) {
            char ch = text.charAt(i);
            if (ch < '0' || ch > '9') {
                return false;
            }
        }
        return true;
    }

    private static boolean isNumber(char ch) {
        return ch >= '0' && ch <= '9';
    }
}

```
