package com.simple.generator.mybatisplus;

import java.util.Properties;

import org.apache.velocity.app.VelocityEngine;

import com.baomidou.mybatisplus.generator.config.ConstVal;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;

/**
 * @author: Jfeng
 * @date: 2019-07-29
 */
public class SimpleVelocityTemplateEngine extends VelocityTemplateEngine {

    private VelocityEngine velocityEngine;

    @Override
    public VelocityTemplateEngine init(ConfigBuilder configBuilder) {
        super.init(configBuilder);
        if (null == this.velocityEngine) {
            Properties p = new Properties();
            p.setProperty("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
            p.setProperty("file.resource.loader.path", "");
            p.setProperty("UTF-8", ConstVal.UTF8);
            p.setProperty("input.encoding", ConstVal.UTF8);
            p.setProperty("file.resource.loader.unicode", "true");
            this.velocityEngine = new VelocityEngine(p);
        }

        return this;
    }

    // @Override
    // public void writer(Map<String, Object> objectMap, String templatePath, String outputFile) throws Exception {
    //     // super.writer(objectMap, templatePath, outputFile);
    //
    //     if (StringUtils.isEmpty(templatePath)) {
    //         return;
    //     }
    //
    //     Template template = this.velocityEngine.getTemplate(templatePath, ConstVal.UTF8);
    //     @Cleanup FileOutputStream fos = new FileOutputStream(outputFile);
    //     @Cleanup OutputStreamWriter ow = new OutputStreamWriter(fos, ConstVal.UTF8);
    //
    //     @Cleanup BufferedWriter writer = new BufferedWriter(ow);
    //
    //     VelocityContext context = new VelocityContext(objectMap);
    //
    //
    //     velocityEngine.evaluate(context, writer, "", template.replaceAll(
    //             "[ ]*(#if|#else|#elseif|#end|#set|#foreach)", "$1"));
    //
    //
    //     template.merge(context, writer);
    //
    //     logger.info("模板:" + templatePath + ";  文件:" + outputFile);
    // }
}
