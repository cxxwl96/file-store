import com.cxxwl96.loadclass.exception.MavenInvokeException;

import org.apache.maven.shared.invoker.DefaultInvocationRequest;
import org.apache.maven.shared.invoker.DefaultInvoker;
import org.apache.maven.shared.invoker.InvocationRequest;
import org.apache.maven.shared.invoker.InvocationResult;
import org.apache.maven.shared.invoker.Invoker;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.JarClassLoader;
import lombok.extern.slf4j.Slf4j;

/**
 * MainClass
 *
 * @author cxxwl96
 * @since 2022/11/7
 */
@Slf4j
public class MainClass {
    private final static String LOCAL_REPO = "local/repo";

    public static void main(String[] args) throws Exception {
        // 执行maven依赖下载
        mavenInvoke();
        // 过滤下载的jar文件

        List<File> jarFiles = FileUtil.loopFiles(new File(LOCAL_REPO))
            .stream()
            .filter(file -> file.getPath().toLowerCase().endsWith(FileUtil.JAR_FILE_EXT))
            .collect(Collectors.toList());
        log.info("Jar file num: {}", jarFiles.size());
        // 加载jar文件到加载器中
        JarClassLoader loader = new JarClassLoader();
        for (File jarFile : jarFiles) {
            loader.addJar(jarFile);
        }
    }

    public static void mavenInvoke() throws MavenInvokeException {
        try {
            log.info("Maven invoke...");
            InvocationRequest request = new DefaultInvocationRequest();
            // 设置pom文件路径
            request.setPomFile(new File("pom.xml"));
            // 执行的maven命令
            request.setGoals(Collections.singletonList("compile"));

            Invoker invoker = new DefaultInvoker();
            // maven安装路径
            // invoker.setMavenHome(new File(SystemUtil.get("MAVEN_HOME")));
            invoker.setMavenHome(new File("plugin/apache-maven-3.5.2"));
            // 设置仓库地址
            File repoDir = new File(LOCAL_REPO);
            if (!repoDir.exists()) {
                repoDir.mkdirs();
            }
            invoker.setLocalRepositoryDirectory(repoDir);
            // 日志处理
            // invoker.setLogger(new PrintStreamLogger(System.err, InvokerLogger.ERROR));
            // 重写maven输出显示信息
            invoker.setOutputHandler(log::info);

            InvocationResult result = invoker.execute(request);

            // 判断是否执行成功
            if (result.getExitCode() == 0) {
                log.info("Maven invoke success");
                return;
            }
            if (result.getExecutionException() != null) {
                throw new MavenInvokeException(result.getExecutionException());
            }
        } catch (Exception exception) {
            throw new MavenInvokeException(exception);
        }
    }
}
