package com.zxj.plugin;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.AdviceAdapter;


public class ClassAdapterVisitor1 extends ClassVisitor {


    public ClassAdapterVisitor1(ClassVisitor classVisitor) {
        super(Opcodes.ASM7, classVisitor);
    }

    @Override
    public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {
        System.out.println("====access:"+access+",name:"+name+",descriptor:"+descriptor+",signature:"+signature+",value:"+value);
        return super.visitField(access, name, descriptor, signature, value);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        System.out.println("fun:"+name+"，qianming:"+descriptor);

        MethodVisitor mv = super.visitMethod(access, name, descriptor, signature, exceptions);

        if("getUrl".equals(name) ){
            return new MethodAdapterVisitor(Opcodes.ASM7,mv,access,name,descriptor);
        }

        /*if("getNetworkType".equals(name) && "()Ljava/lang/String;".equals(descriptor)){
            System.out.println("123123123");
            return new MethodAdapterVisitor(Opcodes.ASM7,mv,access,name,descriptor);
        }*/
       /* if(("getNetworkType".equals(name) && "()Ljava/lang/String;".equals(descriptor)) || ("init".equals(name) && "()V".equals(descriptor))){
            return new MethodAdapterVisitor(Opcodes.ASM7,mv,access,name,descriptor);
        }*/
        /*if("reportDataToBi".equals(name)){
            return new MethodAdapterVisitor(Opcodes.ASM7,mv,access,name,descriptor);
        }*/
        return mv;
    }

    static class MethodAdapterVisitor extends AdviceAdapter {
        /**
         * Constructs a new {@link AdviceAdapter}.
         *
         * @param api           the ASM API version implemented by this visitor. Must be one of {@link
         *                      Opcodes#ASM4}, {@link Opcodes#ASM5}, {@link Opcodes#ASM6} or {@link Opcodes#ASM7}.
         * @param methodVisitor the method visitor to which this adapter delegates calls.
         * @param access        the method's access flags (see {@link Opcodes}).
         * @param name          the method's name.
         * @param descriptor    the method's descriptor (see {@link Type Type}).
         */
        protected MethodAdapterVisitor(int api, MethodVisitor methodVisitor, int access, String name, String descriptor) {
            super(api, methodVisitor, access, name, descriptor);
            System.out.println("MethodAdapterVisitor->MethodName:"+name);
        }

        @Override
        protected void onMethodEnter() {
            super.onMethodEnter();

            mv.visitVarInsn(ALOAD, 0);
            mv.visitFieldInsn(GETFIELD, "bi/com/tcl/bi/net/NetWorkManager", "mContext", "Landroid/content/Context;");
            Label label1 = new Label();
            mv.visitJumpInsn(IFNONNULL, label1);// IFNULL IFNONNULL
            Label label2 = new Label();
            mv.visitLabel(label2);
            mv.visitLdcInsn("zuojie");
            mv.visitLdcInsn("mContext is null");
            mv.visitMethodInsn(INVOKESTATIC, "android/util/Log", "e", "(Ljava/lang/String;Ljava/lang/String;)I", false);
            mv.visitInsn(POP);
            Label label3 = new Label();
            mv.visitLabel(label3);
            mv.visitLdcInsn("");
            mv.visitInsn(ARETURN);
            mv.visitLabel(label1);
            mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
        }

    }
}