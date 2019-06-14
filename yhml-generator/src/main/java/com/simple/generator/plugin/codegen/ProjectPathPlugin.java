// package com.simple.generator.plugin.codegen;
//
// import java.io.File;
// import java.util.List;
//
// import org.mybatis.generator.api.PluginAdapter;
// import org.springframework.util.FileSystemUtils;
//
// import lombok.extern.slf4j.Slf4j;
//
// /**
//  * 项目路径插件
//  */
// @Slf4j
// public class ProjectPathPlugin extends PluginAdapter {
//
//
//     @Override
//     public boolean validate(List<String> warnings) {
//         initPath();
//         return true;
//     }
//
//     private void initPath() {
//         String directory = gc.getOutputDir();
//
//         File file = new File(directory);
//         FileSystemUtils.deleteRecursively(file);
//         file.mkdirs();
//
//         // context.getJavaModelGeneratorConfiguration().setTargetPackage(pc.getEntity());
//         // context.getJavaClientGeneratorConfiguration().setTargetPackage(pc.getMapper());
//         // context.getSqlMapGeneratorConfiguration().setTargetPackage(pc.getXml());
//
//         // log.info("delete dir            ===== {}", directory);
//         log.info("output dir            ===== {}", directory);
//         log.info("package               ===== {}", pc.getPackageName());
//         log.info("entityPackage         ===== {}", pc.getEntity());
//         log.info("mapperPackage         ===== {}", pc.getMapper());
//         log.info("servicePackage        ===== {}", pc.getService());
//         log.info("controllerPackage     ===== {}", pc.getController());
//         log.info("xmlPackage            ===== {}", pc.getXml());
//     }
//
// }
