package com.zxj.plugin;
import org.objectweb.asm.commons.AdviceAdapter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Label;

public class MethodAdapterVisitor extends AdviceAdapter {
    private String methodName;
    private String className;

    public MethodAdapterVisitor(int api, MethodVisitor methodVisitor, int access, String name, String descriptor,String className) {
        super(api, methodVisitor, access, name, descriptor);
        this.className = className;
        methodName = name;
        System.out.println("MethodAdapterVisitor->MethodName:"+name);
    }

    @Override
    protected void onMethodEnter() {
        super.onMethodEnter();
        //Log.e("zuojie","===mContext==="+mContext)
           /* mv.visitLdcInsn("zuojie");
            mv.visitTypeInsn(NEW, "java/lang/StringBuilder");
            mv.visitInsn(DUP);
            mv.visitMethodInsn(INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false);
            mv.visitLdcInsn("===mContext===");
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
//            mv.visitVarInsn(ALOAD, 0);
            mv.visitFieldInsn(GETSTATIC, "bi/com/tcl/bi/GetBaseDataInfo", "mContext", "Landroid/content/Context;");
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/Object;)Ljava/lang/StringBuilder;", false);
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);x
            mv.visitMethodInsn(INVOKESTATIC, "android/util/Log", "e", "(Ljava/lang/String;Ljava/lang/String;)I", false);
            mv.visitInsn(POP);*/

        /**
         *  mContext = Utils.getApp();
         *  Log.e("zuojie","mContext is "+mContext);
         */
        mv.visitVarInsn(ALOAD, 0);
        mv.visitMethodInsn(INVOKESTATIC, "com/blankj/utilcode/util/Utils", "getApp", "()Landroid/app/Application;", false);
        mv.visitFieldInsn(PUTFIELD, "bi/com/tcl/bi/GetBaseDataInfo", "mContext", "Landroid/content/Context;");
        Label label1 = new Label();
        mv.visitLabel(label1);
        mv.visitLdcInsn("zuojie");
        mv.visitTypeInsn(NEW, "java/lang/StringBuilder");
        mv.visitInsn(DUP);
        mv.visitMethodInsn(INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false);
        mv.visitLdcInsn("mContext is ");
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
        mv.visitVarInsn(ALOAD, 0);
        mv.visitFieldInsn(GETFIELD, "bi/com/tcl/bi/GetBaseDataInfo", "mContext", "Landroid/content/Context;");
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/Object;)Ljava/lang/StringBuilder;", false);
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);
        mv.visitMethodInsn(INVOKESTATIC, "android/util/Log", "e", "(Ljava/lang/String;Ljava/lang/String;)I", false);
        mv.visitInsn(POP);

        /**
         * if (this.mContext == null) {
         *     Log.e("zuojie", "mContext is null");
         *     return;
         * }
         */
       /* mv.visitVarInsn(ALOAD, 0);
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
        mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);*/
    }
}