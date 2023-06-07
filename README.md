> mvn deploy:deploy-file -DgroupId=xxx -DartifactId=xxx -Dversion=xxx -Dpackaging=jar -DpomFile=pom.xml -Dfile=xxx.jar -DrepositoryId=xxx -Durl=http://xxx/maven2/
# 
```java
package com.cxxwl96.codepart;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 商品<name, Product>
        Map<String, Product> products = new HashMap<>();
        // 存钱盒<par, Coin>
        Map<Integer, Coin> machineCoins = new HashMap<>();
        // 用户投入的余额
        UserCoin userCoin = new UserCoin(0);

        // 初始化商品
        for (ProductAttr attr : ProductAttr.values()) {
            products.put(attr.getName(), new Product(attr, 0));
        }
        // 初始化钱箱
        for (CoinAttr attr : CoinAttr.values()) {
            machineCoins.put(attr.getPar(), new Coin(attr, 0));
        }

        String[] commands = in.nextLine().split(";");
        for (String commandLine : commands) {
            if (commandLine == null || commandLine.trim().isEmpty()) {
                continue;
            }
            char command = commandLine.charAt(0);
            String paramString = commandLine.length() > 1 ? commandLine.substring(2).trim() : "";
            exec(command, paramString, products, machineCoins, userCoin);
        }
    }

    private static void exec(char command, String paramString, Map<String, Product> products,
        Map<Integer, Coin> machineCoins, UserCoin userCoin) {
        switch (command) {
            case 'r':
                systemInit(paramString, products, machineCoins);
                break;
            case 'p':
                pay(paramString, products, machineCoins, userCoin);
                break;
            case 'b':
                buy(paramString, products, userCoin);
                break;
            case 'c':
                callback(machineCoins, userCoin);
                break;
            case 'q':
                query(paramString, products, machineCoins);
                break;
        }
    }

    // 系统初始化
    private static void systemInit(String paramString, Map<String, Product> products, Map<Integer, Coin> machineCoins) {
        String[] split = paramString.split(" ");

        String[] productNums = split[0].split("-");
        String[] coinNums = split[1].split("-");

        ProductAttr[] productAttrs = ProductAttr.values();
        for (int i = 0; i < productAttrs.length && i < productNums.length; i++) {
            int num = Integer.parseInt(productNums[i]);
            String name = productAttrs[i].getName();
            Product product = products.get(name);
            product.setNum(num);
        }

        CoinAttr[] coinAttrs = CoinAttr.values();
        for (int i = 0; i < coinAttrs.length && i < coinNums.length; i++) {
            int num = Integer.parseInt(coinNums[i]);
            int par = coinAttrs[i].getPar();
            Coin coin = machineCoins.get(par);
            coin.setNum(num);
        }
        System.out.println("S001:Initialization is successful");
    }

    // 投币
    private static void pay(String paramString, Map<String, Product> products, Map<Integer, Coin> machineCoins,
        UserCoin userCoin) {
        int coinPar = Integer.parseInt(paramString);
        // 面额是否非法
        if (!machineCoins.containsKey(coinPar)) {
            System.out.println("E002:Denomination error");
            return;
        }
        // 零钱是否充足
        Coin coin1 = machineCoins.get(1);
        Coin coin2 = machineCoins.get(2);
        if (coin1.getNum() + coin2.getNum() * 2 < coinPar) {
            System.out.println("E003:Change is not enough, pay fail");
            return;
        }
        // 商品是否全部销售完毕
        boolean notOver = products.values().stream().anyMatch(p -> p.getNum() > 0);
        if (!notOver) {
            System.out.println("E005:All the goods sold out");
            return;
        }
        // 增加用户余额
        userCoin.setCoin(userCoin.getCoin() + coinPar);
        // 钱币投入钱箱
        Coin coin = machineCoins.get(coinPar);
        coin.setNum(coin.getNum() + 1);
        System.out.println("S002:Pay success,balance=" + userCoin.getCoin());
    }

    // 购买商品
    private static void buy(String paramString, Map<String, Product> products, UserCoin userCoin) {
        // 商品是否存在
        if (!products.containsKey(paramString)) {
            System.out.println("E006:Goods does not exist");
            return;
        }
        Product product = products.get(paramString);
        // 商品的数量是否为0
        if (product.getNum() <= 0) {
            System.out.println("E007:The goods sold out");
            return;
        }
        // 投币余额是否小于待购买商品价格
        int price = product.getAttr().getPrice();
        if (userCoin.getCoin() < price) {
            System.out.println("E008:Lack of balance");
            return;
        }
        // 商品数-1
        product.setNum(product.getNum() - 1);
        // 扣除用户余额
        userCoin.setCoin(userCoin.getCoin() - price);
        System.out.println("S003:Buy success,balance=" + userCoin.getCoin());
    }

    // 退币
    private static void callback(Map<Integer, Coin> machineCoins, UserCoin userCoin) {
        if (userCoin.getCoin() == 0) {
            System.out.println("E009:Work failure");
            return;
        }
        // 对钱箱已有的钱币按面值从大到小排序
        List<Coin> coins = machineCoins.values()
            .stream()
            .filter(c -> c.getNum() > 0)
            .sorted((c1, c2) -> c2.getAttr().getPar() - c1.getAttr().getPar())
            .collect(Collectors.toList());
        int i = 0;
        // 初始化退币信息 <钱币面值, 张数>
        Map<Integer, Integer> ans = new TreeMap<>();
        for (CoinAttr attr : CoinAttr.values()) {
            ans.put(attr.getPar(), 0);
        }
        // 计算需要退多少钱币
        while (userCoin.getCoin() > 0) {
            Coin coin = coins.get(i++);
            int par = coin.getAttr().getPar();
            while (userCoin.getCoin() >= par && coin.getNum() > 0) {
                userCoin.setCoin(userCoin.getCoin() - par);
                coin.setNum(coin.getNum() - 1);
                ans.put(par, ans.get(par) + 1);
            }
        }
        for (Map.Entry<Integer, Integer> entry : ans.entrySet()) {
            System.out.printf("%d yuan coin number=%d\n", entry.getKey(), entry.getValue());
        }
    }

    // 查询
    private static void query(String paramString, Map<String, Product> products, Map<Integer, Coin> machineCoins) {
        if ("0".equals(paramString)) {
            products.values()
                .stream()
                .sorted(Comparator.comparing(Product::getNum, Comparator.reverseOrder())
                    .thenComparing(p -> p.getAttr().getName()))
                .forEach(
                    p -> System.out.printf("%s %d %d\n", p.getAttr().getName(), p.getAttr().getPrice(), p.getNum()));
        } else if ("1".equals(paramString)) {
            for (Map.Entry<Integer, Coin> entry : machineCoins.entrySet()) {
                System.out.printf("%d yuan coin number=%d\n", entry.getKey(), entry.getValue().getNum());
            }
        } else {
            System.out.println("E010:Parameter error");
        }
    }
}

enum ProductAttr {
    A1("A1", 2),
    A2("A2", 3),
    A3("A3", 4),
    A4("A4", 5),
    A5("A5", 8),
    A6("A6", 6);

    private final String name;

    private final int price;

    ProductAttr(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }
}

class Product {
    // 商品属性
    private final ProductAttr attr;

    // 商品数量
    private int num;

    public Product(ProductAttr attr, int num) {
        this.attr = attr;
        this.num = num;
    }

    public ProductAttr getAttr() {
        return attr;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}

enum CoinAttr {
    Coin1(1),
    Coin2(2),
    Coin5(5),
    Coin10(10);

    // 钱币面额
    private final int par;

    CoinAttr(int par) {
        this.par = par;
    }

    public int getPar() {
        return par;
    }
}

class Coin {
    // 钱币属性
    private final CoinAttr attr;

    // 钱币数量
    private int num;

    public Coin(CoinAttr attr, int num) {
        this.attr = attr;
        this.num = num;
    }

    public CoinAttr getAttr() {
        return attr;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}

class UserCoin {
    private int coin;

    public UserCoin(int coin) {
        this.coin = coin;
    }

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
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




