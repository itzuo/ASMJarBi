package com.zxj.plugin;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;


public class ClassAdapterVisitor extends ClassVisitor {
    //当前类的类名称
    //本例：com/zxj/plugin/demo/MainActivity
    private String className;

    //className类的父类名称
    //本例：androidx/appcompat/app/AppCompatActivity
    private String superName;

    public ClassAdapterVisitor(ClassVisitor classVisitor) {
        super(Opcodes.ASM7, classVisitor);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces);
        className = name;
        System.out.println("className:"+name+",superName:"+superName+",interfaces.length:"+interfaces.length);
    }

    @Override
    public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {
        System.out.println("====access:"+access+",name:"+name+",descriptor:"+descriptor+",signature:"+signature+",value:"+value);
        return super.visitField(access, name, descriptor, signature, value);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        System.out.println("methodName:"+name+",descriptor:"+descriptor+",signature:"+signature);

        MethodVisitor mv = super.visitMethod(access, name, descriptor, signature, exceptions);

        if("init".equals(name) && "()V".equals(descriptor)){
            return new MethodAdapterVisitor(Opcodes.ASM7,mv,access,name,descriptor,className);
        }

        /*if("getNetworkType".equals(name) && "()Ljava/lang/String;".equals(descriptor)){
            return new MethodAdapterVisitor(Opcodes.ASM7,mv,access,name,descriptor);
        }*/
        /*if(("getNetworkType".equals(name) && "()Ljava/lang/String;".equals(descriptor)) || ("init".equals(name) && "()V".equals(descriptor))){
            return new MethodAdapterVisitor(Opcodes.ASM7,mv,access,name,descriptor,className);
        }*/
        /*if("reportDataToBi".equals(name)){
            return new MethodAdapterVisitor(Opcodes.ASM7,mv,access,name,descriptor);
        }*/
        return mv;
    }

   /* static class MethodAdapterVisitor extends AdviceAdapter {
        private String methodName;
        private String className;

        protected MethodAdapterVisitor(int api, MethodVisitor methodVisitor, int access, String name, String descriptor,String className) {
            super(api, methodVisitor, access, name, descriptor);
            this.className = className;
            methodName = name;
            System.out.println("MethodAdapterVisitor->MethodName:"+name);
        }

        @Override
        protected void onMethodEnter() {
            super.onMethodEnter();
            System.out.println("wwwwwwwwwwwwwwww");

            //Log.e("zuojie","===mContext==="+mContext)
//            mv.visitLdcInsn("zuojie");
//            mv.visitTypeInsn(NEW, "java/lang/StringBuilder");
//            mv.visitInsn(DUP);
//            mv.visitMethodInsn(INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false);
//            mv.visitLdcInsn("===mContext===");
//            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
////            mv.visitVarInsn(ALOAD, 0);
//            mv.visitFieldInsn(GETSTATIC, "bi/com/tcl/bi/GetBaseDataInfo", "mContext", "Landroid/content/Context;");
//            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/Object;)Ljava/lang/StringBuilder;", false);
//            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);
//            mv.visitMethodInsn(INVOKESTATIC, "android/util/Log", "e", "(Ljava/lang/String;Ljava/lang/String;)I", false);
//            mv.visitInsn(POP);

            *//**
             * if (this.mContext == null) {
             *     Log.e("zuojie", "mContext is null");
             *     return;
             * }
             *//*
            mv.visitVarInsn(ALOAD, 0);
            mv.visitFieldInsn(GETFIELD, "bi/com/tcl/bi/GetBaseDataInfo", "mContext", "Landroid/content/Context;");
            Label label1 = new Label();
            mv.visitJumpInsn(IFNONNULL, label1);// IFNULL IFNONNULL
            Label label2 = new Label();
            mv.visitLabel(label2);
            mv.visitLdcInsn("zuojie");
            mv.visitLdcInsn(className+",mContext is null");
            mv.visitMethodInsn(INVOKESTATIC, "android/util/Log", "e", "(Ljava/lang/String;Ljava/lang/String;)I", false);
            mv.visitInsn(POP);
            Label label3 = new Label();
            mv.visitLabel(label3);
            mv.visitInsn(RETURN);
            mv.visitLabel(label1);
            mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
        }
    }*/
}