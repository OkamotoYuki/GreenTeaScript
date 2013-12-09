// ***************************************************************************
// Copyright (c) 2013, JST/CREST DEOS project authors. All rights reserved.
// Redistribution and use in source and binary forms, with or without
// modification, are permitted provided that the following conditions are met:
//
// *  Redistributions of source code must retain the above copyright notice,
//    this list of conditions and the following disclaimer.
// *  Redistributions in binary form must reproduce the above copyright
//    notice, this list of conditions and the following disclaimer in the
//    documentation and/or other materials provided with the distribution.
//
// THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
// "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
// TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
// PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR
// CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
// EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
// PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
// OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
// WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
// OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
// ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
// **************************************************************************

package org.GreenTeaScript;
import java.lang.reflect.Method;
import static org.objectweb.asm.Opcodes.*;
import org.GreenTeaScript.DShell.DShellAuthenticator;

public class JavaByteCodeGenerator4Test extends JavaByteCodeGenerator {

	public JavaByteCodeGenerator4Test(String TargetCode, String OutputFile,
			int GeneratorFlag) {
		super(TargetCode, OutputFile, GeneratorFlag);
		// TODO Auto-generated constructor stub
	}
	
	@Override public void VisitStaticApplyNode(GtStaticApplyNode Node) {
		GtFunc Func = Node.Func;
		this.VisitingBuilder.SetLineNumber(Node);
		for(int i = 0; i < Node.ParamList.size(); i++) {
			GtNode ParamNode = Node.ParamList.get(i);
			this.VisitingBuilder.PushEvaluatedNode(Func.GetFuncParamType(i), ParamNode);
//			ParamNode.Evaluate(this);
//			this.VisitingBuilder.CheckCast(Func.GetFuncParamType(i), ParamNode.Type);
		}
		if(Func.FuncBody instanceof Method) {
			this.VisitingBuilder.InvokeMethodCall(Node.Type, (Method) Func.FuncBody);
		}
		else {
			String MethodName = Func.GetNativeFuncName(); 
			String Owner = JLib.GetHolderClassName(this.Context, MethodName);
			String MethodDescriptor = JLib.GetMethodDescriptor(Func);
			this.VisitingBuilder.AsmVisitor.visitMethodInsn(INVOKESTATIC, Owner, MethodName, MethodDescriptor);
		}
		this.VisitingBuilder.CheckReturn(Node.Type, Func.GetReturnType());

		if(Node.Type.GetNativeType(false) == boolean.class
				&& Func.FuncName.startsWith("Test_")) {
			try {
				Method RecordTestResult = DShellAuthenticator.class.getMethod("RecordTestResult", boolean.class, GtFunc.class);
				this.VisitingBuilder.LoadConst(Func);
				this.VisitingBuilder.InvokeMethodCall(boolean.class, RecordTestResult);
			}
			catch(NoSuchMethodException e) {
				e.printStackTrace();
				LibGreenTea.Exit(1, "load error");
			}
		}
	}

}
