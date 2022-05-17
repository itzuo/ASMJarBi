package com.zxj.plugin

import com.android.build.api.transform.DirectoryInput
import com.android.build.api.transform.Format
import com.android.build.api.transform.JarInput
import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.Transform
import com.android.build.api.transform.TransformException
import com.android.build.api.transform.TransformInput
import com.android.build.api.transform.TransformInvocation
import com.android.build.api.transform.TransformOutputProvider
import com.android.build.gradle.internal.pipeline.TransformManager
import com.android.utils.FileUtils
import org.apache.commons.codec.digest.DigestUtils
import org.apache.commons.io.IOUtils
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassWriter

import java.util.jar.JarEntry
import java.util.jar.JarFile
import java.util.jar.JarOutputStream
import java.util.zip.ZipEntry

/**
 * 自定义的 Transform 类
 */
class ModifyTransform extends Transform {

    //自定义 Task 名称
    @Override
    String getName() {
        return this.getClass().name
    }

    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }

    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    // 当前Transform是否支持增量编译
    @Override
    boolean isIncremental() {
        return false
    }

    @Override
    void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        super.transform(transformInvocation)
        TransformOutputProvider outputProvider = transformInvocation.getOutputProvider()
        //清理文件
        outputProvider.deleteAll();

        transformInvocation.inputs.each {
            TransformInput input ->

                //这里存放着开发者手写的类
                input.directoryInputs.each {
                    DirectoryInput directoryInput ->
                        File dir = directoryInput.file
                        println("dir===" + dir)
                        /* dir.eachFileRecurse {
                             File file ->
                                 def name = file.name
                                 if(name.endsWith(".class") && !name.startsWith("R\$") &&
                                         !"R.class".equalsIgnoreCase(name) && !"BuildConfig.class".equalsIgnoreCase(name)){
                                     println("name==="+name)
                                     //class阅读器
                                     ClassReader cr = new ClassReader(file.bytes);
                                     //写出器
                                     ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
                                     //分析，处理结果写入cw
                                     cr.accept(new ClassAdapterVisitor(cw),ClassReader.EXPAND_FRAMES)
                                     byte[] newClassBytes = cw.toByteArray();
                                     FileOutputStream fos = new FileOutputStream(file.parentFile.absolutePath+File.separator+name)
                                     fos.write(newClassBytes)
                                     fos.close()
                                 }
                        }*/

                        //获取output目录，处理完输入文件之后，要把输出给下一个任务
                        def dest = outputProvider.getContentLocation(directoryInput.name, directoryInput.contentTypes,
                                directoryInput.scopes, Format.DIRECTORY)
                        //将input的目录复制到output指定目录
                        FileUtils.copyDirectory(directoryInput.file, dest)
                }

                //遍历jar文件
                input.jarInputs.each {
                    JarInput jarInput ->
                        def src = jarInput.file
                        String jarName = src.name
                        String absolutePath = src.absolutePath
                        if (jarName.contains("bi-9.1-runtime")) {
                            println("jarName = ${jarName}")
                            println("jar = ${absolutePath}")
                        }
                        String md5name = DigestUtils.md5Hex(src.getAbsolutePath());
                        if (absolutePath.endsWith(".jar")) {
                            //...对jar进行插入字节码
                            jarName = jarName.substring(0, jarName.length() - 4)
                        }

                        // bi-9.1-runtime.jar
                        File dest = outputProvider.getContentLocation(jarName + md5name, jarInput.contentTypes, jarInput.scopes, Format.JAR)
                        if (jarName.contains("bi-9.1-runtime")) {
                            JarOutputStream jarOutputStream = new JarOutputStream(new FileOutputStream(dest))
                            JarFile jarFile = new JarFile(src)
                            Enumeration<JarEntry> entries = jarFile.entries()
                            while (entries.hasMoreElements()) {
                                JarEntry jarEntry = entries.nextElement()
                                def jarEntryName = jarEntry.name
                                println "====jarEntryName:$jarEntryName"
                                ZipEntry zipEntry = new ZipEntry(jarEntryName)

                                if(zipEntry.isDirectory()) continue

                                jarOutputStream.putNextEntry(zipEntry);

                                //读取class的字节数据
                                 InputStream is = jarFile.getInputStream(jarEntry)

                                ByteArrayOutputStream bos = new ByteArrayOutputStream()
                                IOUtils.copy(is, bos)
                                byte[] sourceClassBytes = bos.toByteArray()
                                is.close()
                                bos.close()

                                 if ("bi/com/tcl/bi/GetBaseDataInfo.class" == jarEntryName) {
                                    println "55555"
                                     //class阅读器
                                     ClassReader cr = new ClassReader(bos.toByteArray());
                                     //写出器
                                     ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
                                     //分析，处理结果写入cw
                                     cr.accept(new ClassAdapterVisitor(cw),ClassReader.EXPAND_FRAMES);
                                     byte[] newClassBytes = cw.toByteArray();
                                     jarOutputStream.write(newClassBytes)
                                }else if("bi/com/tcl/bi/net/NetWorkManager.class" == jarEntryName) {
                                     println "6666"
                                     //class阅读器
                                     ClassReader cr = new ClassReader(bos.toByteArray());
                                     //写出器
                                     ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
                                     //分析，处理结果写入cw
                                     cr.accept(new ClassAdapterVisitor1(cw),ClassReader.EXPAND_FRAMES);
                                     byte[] newClassBytes = cw.toByteArray();
                                     jarOutputStream.write(newClassBytes)
                                 } else {
                                    jarOutputStream.write(sourceClassBytes)
                                }
                            }
                            jarOutputStream.close()
                        } else {
                            FileUtils.copyFile(src, dest)
                        }
                }
        }
    }
}