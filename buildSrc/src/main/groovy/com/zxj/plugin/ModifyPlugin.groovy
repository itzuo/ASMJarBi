package com.zxj.plugin
import com.android.build.gradle.AppExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
/**
 * 用于注册自定义 Transform 的插件
 */
class ModifyPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        println "=====Hello TransformPlugin"
        def android = project.extensions.getByType(AppExtension)
        //注册 Transform，操作 class 文件
        android.registerTransform(new ModifyTransform())
    }
}