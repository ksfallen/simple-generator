package com.simple.generator.util;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.MethodDoc;
import com.sun.javadoc.RootDoc;

import lombok.Getter;

/**
 * @author: Jfeng
 * @date: 2019-07-26
 */
public class JavaDocReader {
    @Getter
    private static RootDoc root;

    // 显示DocRoot中的基本信息
    public static void show() {
        ClassDoc[] classes = root.classes();
        for (int i = 0; i < classes.length; ++i) {
            System.out.println(classes[i]);
            System.out.println(classes[i].commentText());
            for (MethodDoc method : classes[i].methods()) {
                System.out.printf("\t%s\n", method.commentText());
            }
        }
    }

    public static Map<String, ClassDoc> getClassDoc(String packageName) {
        init(packageName);
        Stream<ClassDoc> stream = Arrays.stream(root.classes());
        Map<String, ClassDoc> map = stream.collect(Collectors.toMap(v -> v.toString(), v -> v, (v1, v2) -> v1));
        return map;
    }

    public static void main(String[] args) {
        getClassDoc("com.simple");
    }


    /**
     * javadoc的调用参数，参见 参考资料1
     * -doclet 指定自己的docLet类名
     * -classpath 参数指定 源码文件及依赖库的class位置，不提供也可以执行，但无法获取到完整的注释信息(比如annotation)
     * -encoding 指定源码文件的编码格式
     *
     * @param pacgeName
     * @return
     * @throws Exception
     */
    private static void init(String pacgeName) {
        String sourcepath = System.getProperty("user.dir") + "/src/main/java";

        // @formatter:off
        com.sun.tools.javadoc.Main.execute(new String[]{
                    "-doclet", Doclet.class.getName(),
                    // "-docletpath", Doclet.class.getResource("/").getPath(),
                    "-encoding", StandardCharsets.UTF_8.name(),
                    "-sourcepath", sourcepath, pacgeName}
                );
        // @formatter:on
    }


    // 一个简单Doclet,收到 RootDoc对象保存起来供后续使用
    public static class Doclet {

        public Doclet() {
        }

        public static boolean start(RootDoc root) {
            JavaDocReader.root = root;
            return true;
        }
    }


}
