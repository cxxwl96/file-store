```java
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import nl.flotsam.xeger.Xeger;

/**
 * 随机字符生成工具
 */
public class RandomStringUtil {
    private static final Map<String, Xeger> XEGER_MAP = new ConcurrentHashMap<>();

    /**
     * 根据正则表达式生成随机字符
     *
     * @param pattern 正则表达式
     * @return 随机字符
     */
    public static String next(String pattern) {
        Xeger xeger;
        if (XEGER_MAP.containsKey(pattern)) {
            xeger = XEGER_MAP.get(pattern);
        } else {
            xeger = new Xeger(pattern);
            XEGER_MAP.put(pattern, xeger);
        }
        return xeger.generate();
    }
}

```
