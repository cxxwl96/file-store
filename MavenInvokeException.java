
/**
 * MavenInvokeException
 *
 * @author cxxwl96
 * @since 2022/11/7
 */
public class MavenInvokeException extends Exception{
    public MavenInvokeException() {
    }

    public MavenInvokeException(String message) {
        super(message);
    }

    public MavenInvokeException(String message, Throwable cause) {
        super(message, cause);
    }

    public MavenInvokeException(Throwable cause) {
        super(cause);
    }

    public MavenInvokeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
