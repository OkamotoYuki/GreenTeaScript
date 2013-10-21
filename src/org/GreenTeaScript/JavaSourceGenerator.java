// ***************************************************************************
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

//ifdef JAVA
package org.GreenTeaScript;
import java.util.ArrayList;
//endif VAJA

public class JavaSourceGenerator extends GtSourceGenerator {

	public JavaSourceGenerator(String TargetCode, String OutputFile, int GeneratorFlag) {
		super("java", OutputFile, GeneratorFlag);
	}

	String HolderClassName(String NativeName) {
		return "Func" + NativeName;
	}
	
	@Override public void GenerateFunc(GtFunc Func, ArrayList<String> NameList, GtNode Body) {
		String MethodName = Func.GetNativeFuncName();
		GtSourceBuilder Builder = new GtSourceBuilder(this);
		Builder.Append("class");
		Builder.SpaceAppendSpace(HolderClassName(MethodName));
		Builder.AppendLine("{");
		Builder.Indent();
		Builder.IndentAndAppend("final static ");
		Builder.SpaceAppendSpace(MethodName);
		Builder.Append("(");

		Builder.Append(")");
		GtSourceBuilder PushedBuilder = this.VisitingBuilder;
		this.VisitingBuilder = Builder;
		this.VisitIndentBlock("{", Body, "}");
		this.VisitingBuilder = PushedBuilder;
		Builder.AppendLine("");
		Builder.UnIndent();
		Builder.AppendLine("}");
	}

	@Override public void OpenClassField(GtSyntaxTree ParsedTree, GtType ClassType, GtClassField ClassField) {
//		String ClassName = ClassType.GetNativeName();
//		String superClassName = ClassType.SuperType.GetNativeName();
//		JClassBuilder ClassBuilder = this.ClassGenerator.NewBuilder(ClassName, superClassName);
//		// generate field
//		for(GtFieldInfo field : ClassField.FieldList) {
//			if(field.FieldIndex >= ClassField.ThisClassIndex) {
//				String fieldName = field.NativeName;
//				Type fieldType = JLib.GetAsmType(field.Type);
//				FieldNode node = new FieldNode(ACC_PUBLIC, fieldName, fieldType.getDescriptor(), null, null);
//				ClassBuilder.AddField(node);
//			}
//		}
//		// generate default constructor (for jvm)
//		MethodNode constructor = new MethodNode(ACC_PUBLIC, "<init>", "(Lorg/GreenTeaScript/GtType;)V", null, null);
//		constructor.visitVarInsn(ALOAD, 0);
//		constructor.visitVarInsn(ALOAD, 1);
//		constructor.visitMethodInsn(INVOKESPECIAL, superClassName, "<init>", "(Lorg/GreenTeaScript/GtType;)V");
//		for(GtFieldInfo field : ClassField.FieldList) {
//			if(field.FieldIndex >= ClassField.ThisClassIndex && field.InitValue != null) {
//				String name = field.NativeName;
//				String desc = JLib.GetAsmType(field.Type).getDescriptor();
//				constructor.visitVarInsn(ALOAD, 0);
//				constructor.visitLdcInsn(field.InitValue);
//				constructor.visitFieldInsn(PUTFIELD, ClassName, name, desc);
//			}
//		}
//		constructor.visitInsn(RETURN);
//		ClassBuilder.AddMethod(constructor);
//		try {
//			ClassType.TypeBody = this.ClassGenerator.loadClass(ClassName);
//		}
//		catch (Exception e) {
//			LibGreenTea.VerboseException(e);
//		}
	}

	//-----------------------------------------------------

	@Override public void VisitConstNode(GtConstNode Node) {
//		Object constValue = Node.ConstValue;
//		LibGreenTea.Assert(Node.ConstValue != null);  // Added by kimio
//		this.VisitingBuilder.LoadConst(constValue);
	}

	@Override public void VisitNewNode(GtNewNode Node) {
//		Type type = JLib.GetAsmType(Node.Type);
//		String owner = type.getInternalName();
//		this.VisitingBuilder.MethodVisitor.visitTypeInsn(NEW, owner);
//		this.VisitingBuilder.MethodVisitor.visitInsn(DUP);
//		if(!Node.Type.IsNative()) {
//			this.VisitingBuilder.LoadConst(Node.Type);
//			this.VisitingBuilder.MethodVisitor.visitMethodInsn(INVOKESPECIAL, owner, "<init>", "(Lorg/GreenTeaScript/GtType;)V");
//		} else {
//			this.VisitingBuilder.MethodVisitor.visitMethodInsn(INVOKESPECIAL, owner, "<init>", "()V");
//		}
	}

	@Override public void VisitNullNode(GtNullNode Node) {
		this.VisitingBuilder.Append(this.NullLiteral);
	}

	@Override public void VisitLocalNode(GtLocalNode Node) {
		this.VisitingBuilder.Append(Node.NativeName);
	}

	@Override public void VisitConstructorNode(GtConstructorNode Node) {
//		if(Node.Type.TypeBody instanceof Class<?>) {
//			// native class
//			Class<?> klass = (Class<?>) Node.Type.TypeBody;
//			Type type = Type.getType(klass);
//			this.VisitingBuilder.MethodVisitor.visitTypeInsn(NEW, Type.getInternalName(klass));
//			this.VisitingBuilder.MethodVisitor.visitInsn(DUP);
//			for(int i = 0; i<Node.ParamList.size(); i++) {
//				GtNode ParamNode = Node.ParamList.get(i);
//				ParamNode.Evaluate(this);
//				this.VisitingBuilder.BoxIfUnboxed(ParamNode.Type, Node.Func.GetFuncParamType(i));
//			}
//			this.VisitingBuilder.Call((Constructor<?>) Node.Func.FuncBody);
//		} else {
//			LibGreenTea.TODO("TypeBody is not Class<?>");
//		}
	}

	@Override public void VisitGetterNode(GtGetterNode Node) {
		Node.RecvNode.Evaluate(this);
		this.VisitingBuilder.Append(".");
		this.VisitingBuilder.Append(Node.Func.FuncName);
	}
	
	@Override public void VisitSetterNode(GtSetterNode Node) {
		Node.RecvNode.Evaluate(this);
		this.VisitingBuilder.Append(".");
		this.VisitingBuilder.Append(Node.Func.FuncName);
		this.VisitingBuilder.Append("=");
		Node.ValueNode.Evaluate(this);
	}

	@Override public void VisitApplyNode(GtApplyNode Node) {
//		GtFunc Func = Node.Func;
//		for(int i = 1; i < Node.NodeList.size(); i++) {
//			GtNode ParamNode = Node.NodeList.get(i);
//			ParamNode.Evaluate(this);
//			this.VisitingBuilder.BoxIfUnboxed(ParamNode.Type, Func.GetFuncParamType(i - 1));
//		}
//		Method m = null;
//		if(Func.FuncBody instanceof Method) {
//			m = (Method) Func.FuncBody;
//		}
//		if(m != null) {
//			this.VisitingBuilder.InvokeMethodCall(Node.Type, m);
//		}
//		else {
//			String MethodName = Func.GetNativeFuncName(); 
//			String Owner = JLib.GetHolderClassName(Node.Type.Context, MethodName);
//			String MethodDescriptor = JLib.GetMethodDescriptor(Func);
//			this.VisitingBuilder.MethodVisitor.visitMethodInsn(INVOKESTATIC, Owner, MethodName, MethodDescriptor);
//			this.VisitingBuilder.UnboxIfUnboxed(Func.GetReturnType(), Node.Type);
//		}
	}

	@Override public void VisitStaticApplyNode(GtStaticApplyNode ApplyNode) {
//		GtFunc Func = ApplyNode.Func;
//		for(int i = 0; i < ApplyNode.ParamList.size(); i++) {
//			GtNode ParamNode = ApplyNode.ParamList.get(i);
//			ParamNode.Evaluate(this);
//			this.VisitingBuilder.BoxIfUnboxed(ParamNode.Type, Func.GetFuncParamType(i));
//		}
//		if(Func.FuncBody instanceof Method) {
//			this.VisitingBuilder.InvokeMethodCall(ApplyNode.Type, (Method) Func.FuncBody);
//		}
//		else {
//			String MethodName = Func.GetNativeFuncName(); 
//			String Owner = JLib.GetHolderClassName(ApplyNode.Type.Context, MethodName);
//			String MethodDescriptor = JLib.GetMethodDescriptor(Func);
//			this.VisitingBuilder.MethodVisitor.visitMethodInsn(INVOKESTATIC, Owner, MethodName, MethodDescriptor);
//			this.VisitingBuilder.UnboxIfUnboxed(Func.GetReturnType(), ApplyNode.Type);
//		}
	}

	@Override public void VisitBinaryNode(GtBinaryNode Node) {
		//if(Node.Func.FuncBody instanceof Method) {
		this.VisitingBuilder.Append("(");
		Node.LeftNode.Evaluate(this);
		this.VisitingBuilder.SpaceAppendSpace(Node.Token.ParsedText);
		Node.RightNode.Evaluate(this);
		this.VisitingBuilder.Append(")");
		//}
	}

	@Override public void VisitUnaryNode(GtUnaryNode Node) {
//		if(Node.Func.FuncBody instanceof Method) {
		this.VisitingBuilder.Append("(");
		this.VisitingBuilder.Append(Node.Token.ParsedText);
		Node.Expr.Evaluate(this);
		this.VisitingBuilder.Append(")");
//		}
	}

	@Override public void VisitIndexerNode(GtIndexerNode Node) {
//		ArrayList<GtNode> NodeList = Node.NodeList;
//		Node.Expr.Evaluate(this);
//		for(int i=0; i<NodeList.size(); i++) {
//			NodeList.get(i).Evaluate(this);
//		}
//		this.VisitingBuilder.InvokeMethodCall(Node.Type, (Method) Node.Func.FuncBody);
	}

	@Override public void VisitArrayNode(GtArrayNode Node) {
//		ArrayList<GtNode> NodeList = Node.NodeList;
//		this.VisitingBuilder.LoadConst(Node.Type);
//		this.VisitingBuilder.MethodVisitor.visitLdcInsn(NodeList.size());
//		this.VisitingBuilder.MethodVisitor.visitTypeInsn(ANEWARRAY, Type.getInternalName(Object.class));
//		for(int i=0; i<NodeList.size(); i++) {
//			this.VisitingBuilder.MethodVisitor.visitInsn(DUP);
//			this.VisitingBuilder.MethodVisitor.visitLdcInsn(i);
//			NodeList.get(i).Evaluate(this);
//			this.VisitingBuilder.BoxIfUnboxed(NodeList.get(i).Type, Node.Type.TypeParams[0]);
//			this.VisitingBuilder.MethodVisitor.visitInsn(AASTORE);
//		}
//		this.VisitingBuilder.InvokeMethodCall(Node.Type, JLib.NewArrayLiteral);
	}

	public void VisitNewArrayNode(GtNewArrayNode Node) {
//		this.VisitingBuilder.LoadConst(Node.Type);
//		this.VisitingBuilder.MethodVisitor.visitLdcInsn(Node.NodeList.size());
//		this.VisitingBuilder.MethodVisitor.visitTypeInsn(ANEWARRAY, Type.getInternalName(Object.class));
//		for(int i=0; i<Node.NodeList.size(); i++) {
//			this.VisitingBuilder.MethodVisitor.visitInsn(DUP);
//			this.VisitingBuilder.MethodVisitor.visitLdcInsn(i);
//			Node.NodeList.get(i).Evaluate(this);
//			this.VisitingBuilder.BoxIfUnboxed(Node.NodeList.get(i).Type, this.Context.AnyType);
//			this.VisitingBuilder.MethodVisitor.visitInsn(AASTORE);
//		}
//		this.VisitingBuilder.InvokeMethodCall(Node.Type, JLib.NewArray);
	}

	@Override public void VisitAndNode(GtAndNode Node) {
		this.VisitingBuilder.Append("(");
		Node.LeftNode.Evaluate(this);
		this.VisitingBuilder.Append(" && ");
		Node.RightNode.Evaluate(this);
		this.VisitingBuilder.Append(")");
	}

	@Override public void VisitOrNode(GtOrNode Node) {
		this.VisitingBuilder.Append("(");
		Node.LeftNode.Evaluate(this);
		this.VisitingBuilder.Append(" || ");
		Node.RightNode.Evaluate(this);
		this.VisitingBuilder.Append(")");
	}

	@Override public void VisitAssignNode(GtAssignNode Node) {
//		assert (Node.LeftNode instanceof GtLocalNode);
//		GtLocalNode Left = (GtLocalNode) Node.LeftNode;
//		JLocalVarStack local = this.VisitingBuilder.FindLocalVariable(Left.NativeName);
//		Node.RightNode.Evaluate(this);
//		this.VisitingBuilder.StoreLocal(local);
	}

	@Override public void VisitSelfAssignNode(GtSelfAssignNode Node) {
//		if(Node.LeftNode instanceof GtLocalNode) {
//			GtLocalNode Left = (GtLocalNode)Node.LeftNode;
//			JLocalVarStack local = this.VisitingBuilder.FindLocalVariable(Left.NativeName);
//			Node.LeftNode.Evaluate(this);
//			Node.RightNode.Evaluate(this);
//			this.VisitingBuilder.InvokeMethodCall((Method)Node.Func.FuncBody);
//			this.VisitingBuilder.StoreLocal(local);
//		}
//		else {
//			LibGreenTea.TODO("selfAssign");
//		}
	}

	@Override public void VisitVarNode(GtVarNode Node) {
//		JLocalVarStack local = this.VisitingBuilder.AddLocal(Node.Type, Node.NativeName);
//		Node.InitNode.Evaluate(this);
//		this.VisitingBuilder.StoreLocal(local);
//		this.VisitBlock(Node.BlockNode);
	}

	@Override public void VisitIfNode(GtIfNode Node) {
		this.VisitingBuilder.Append("if");
		this.VisitingBuilder.Append("(");
		Node.CondExpr.Evaluate(this);
		this.VisitingBuilder.Append(") ");
		this.VisitIndentBlock("{", Node.ThenNode, "}");
		if(Node.ElseNode != null) {
			this.VisitingBuilder.Append(" else ");			
			this.VisitIndentBlock("{", Node.ElseNode, "}");
		}
	}

	@Override public void VisitTrinaryNode(GtTrinaryNode Node) {
		this.VisitingBuilder.Append("(");
		Node.ConditionNode.Evaluate(this);
		this.VisitingBuilder.Append(" ? ");
		Node.ThenNode.Evaluate(this);
		this.VisitingBuilder.Append(" : ");
		Node.ElseNode.Evaluate(this);
		this.VisitingBuilder.Append(")");
	}

	@Override public void VisitSwitchNode(GtSwitchNode Node) {
//		int cases = Node.CaseList.size() / 2;
//		int[] keys = new int[cases];
//		Label[] caseLabels = new Label[cases];
//		Label defaultLabel = new Label();
//		Label breakLabel = new Label();
//		for(int i=0; i<cases; i++) {
//			keys[i] = ((Number)((GtConstNode)Node.CaseList.get(i*2)).ConstValue).intValue();
//			caseLabels[i] = new Label();
//		}
//		Node.MatchNode.Evaluate(this);
//		this.VisitingBuilder.MethodVisitor.visitInsn(L2I);
//		this.VisitingBuilder.MethodVisitor.visitLookupSwitchInsn(defaultLabel, keys, caseLabels);
//		for(int i=0; i<cases; i++) {
//			this.VisitingBuilder.BreakLabelStack.push(breakLabel);
//			this.VisitingBuilder.MethodVisitor.visitLabel(caseLabels[i]);
//			this.VisitBlock(Node.CaseList.get(i*2+1));
//			this.VisitingBuilder.BreakLabelStack.pop();
//		}
//		this.VisitingBuilder.MethodVisitor.visitLabel(defaultLabel);
//		this.VisitBlock(Node.DefaultBlock);
//		this.VisitingBuilder.MethodVisitor.visitLabel(breakLabel);
	}

	@Override public void VisitWhileNode(GtWhileNode Node) {
//		Label continueLabel = new Label();
//		Label breakLabel = new Label();
//		this.VisitingBuilder.BreakLabelStack.push(breakLabel);
//		this.VisitingBuilder.ContinueLabelStack.push(continueLabel);
//
//		this.VisitingBuilder.MethodVisitor.visitLabel(continueLabel);
//		Node.CondExpr.Evaluate(this);
//		this.VisitingBuilder.MethodVisitor.visitJumpInsn(IFEQ, breakLabel); // condition
//		this.VisitBlock(Node.LoopBody);
//		this.VisitingBuilder.MethodVisitor.visitJumpInsn(GOTO, continueLabel);
//		this.VisitingBuilder.MethodVisitor.visitLabel(breakLabel);
//
//		this.VisitingBuilder.BreakLabelStack.pop();
//		this.VisitingBuilder.ContinueLabelStack.pop();
	}

	@Override public void VisitDoWhileNode(GtDoWhileNode Node) {
//		Label headLabel = new Label();
//		Label continueLabel = new Label();
//		Label breakLabel = new Label();
//		this.VisitingBuilder.BreakLabelStack.push(breakLabel);
//		this.VisitingBuilder.ContinueLabelStack.push(continueLabel);
//
//		this.VisitingBuilder.MethodVisitor.visitLabel(headLabel);
//		this.VisitBlock(Node.LoopBody);
//		this.VisitingBuilder.MethodVisitor.visitLabel(continueLabel);
//		Node.CondExpr.Evaluate(this);
//		this.VisitingBuilder.MethodVisitor.visitJumpInsn(IFEQ, breakLabel); // condition
//		this.VisitingBuilder.MethodVisitor.visitJumpInsn(GOTO, headLabel);
//		this.VisitingBuilder.MethodVisitor.visitLabel(breakLabel);
//
//		this.VisitingBuilder.BreakLabelStack.pop();
//		this.VisitingBuilder.ContinueLabelStack.pop();
	}

	@Override public void VisitForNode(GtForNode Node) {
//		Label headLabel = new Label();
//		Label continueLabel = new Label();
//		Label breakLabel = new Label();
//		this.VisitingBuilder.BreakLabelStack.push(breakLabel);
//		this.VisitingBuilder.ContinueLabelStack.push(continueLabel);
//
//		this.VisitingBuilder.MethodVisitor.visitLabel(headLabel);
//		Node.CondExpr.Evaluate(this);
//		this.VisitingBuilder.MethodVisitor.visitJumpInsn(IFEQ, breakLabel); // condition
//		this.VisitBlock(Node.LoopBody);
//		this.VisitingBuilder.MethodVisitor.visitLabel(continueLabel);
//		Node.IterExpr.Evaluate(this);
//		this.VisitingBuilder.MethodVisitor.visitJumpInsn(GOTO, headLabel);
//		this.VisitingBuilder.MethodVisitor.visitLabel(breakLabel);
//
//		this.VisitingBuilder.BreakLabelStack.pop();
//		this.VisitingBuilder.ContinueLabelStack.pop();
	}

	@Override public void VisitForEachNode(GtForEachNode Node) {
		LibGreenTea.TODO("ForEach");
	}

	@Override public void VisitReturnNode(GtReturnNode Node) {
		this.VisitingBuilder.Append("return");
		if(Node.Expr != null) {
			this.VisitingBuilder.Append(" ");
			Node.Expr.Evaluate(this);
		}
	}

	@Override public void VisitBreakNode(GtBreakNode Node) {
		this.VisitingBuilder.Append("break");
	}

	@Override public void VisitContinueNode(GtContinueNode Node) {
		this.VisitingBuilder.Append("continue");
	}

	@Override public void VisitTryNode(GtTryNode Node) { //FIXME
//		int catchSize = Node.CatchBlock != null ? 1 : 0;
//		MethodVisitor mv = this.VisitingBuilder.MethodVisitor;
//		Label beginTryLabel = new Label();
//		Label endTryLabel = new Label();
//		Label finallyLabel = new Label();
//		Label catchLabel[] = new Label[catchSize];
//
//		// try block
//		mv.visitLabel(beginTryLabel);
//		this.VisitBlock(Node.TryBlock);
//		mv.visitLabel(endTryLabel);
//		mv.visitJumpInsn(GOTO, finallyLabel);
//
//		// prepare
//		for(int i = 0; i < catchSize; i++) { //TODO: add exception class name
//			catchLabel[i] = new Label();
//			String throwType = JLib.GetAsmType(Node.CatchExpr.Type).getInternalName();
//			mv.visitTryCatchBlock(beginTryLabel, endTryLabel, catchLabel[i], throwType);
//		}
//
//		// catch block
//		for(int i = 0; i < catchSize; i++) { //TODO: add exception class name
//			GtNode block = Node.CatchBlock;
//			mv.visitLabel(catchLabel[i]);
//			this.VisitBlock(block);
//			mv.visitJumpInsn(GOTO, finallyLabel);
//		}
//
//		// finally block
//		mv.visitLabel(finallyLabel);
//		this.VisitBlock(Node.FinallyBlock);
	}

	@Override public void VisitThrowNode(GtThrowNode Node) {
//		// use wrapper
//		String name = Type.getInternalName(GtThrowableWrapper.class);
//		this.VisitingBuilder.MethodVisitor.visitTypeInsn(NEW, name);
//		this.VisitingBuilder.MethodVisitor.visitInsn(DUP);
//		Node.Expr.Evaluate(this);
//		//this.box();
////		this.VisitingBuilder.typeStack.pop();
//		this.VisitingBuilder.MethodVisitor.visitMethodInsn(INVOKESPECIAL, name, "<init>", "(Ljava/lang/Object;)V");
//		this.VisitingBuilder.MethodVisitor.visitInsn(ATHROW);
	}

	@Override public void VisitInstanceOfNode(GtInstanceOfNode Node) {
//		Node.ExprNode.Evaluate(this);
//		this.VisitingBuilder.BoxIfUnboxed(Node.ExprNode.Type, this.Context.AnyType);
//		this.VisitingBuilder.LoadConst(Node.TypeInfo);
//		this.VisitingBuilder.InvokeMethodCall(JLib.GreenInstanceOfOperator);
	}

	@Override public void VisitCastNode(GtCastNode Node) {
//		this.VisitingBuilder.LoadConst(Node.CastType);
//		Node.Expr.Evaluate(this);
//		this.VisitingBuilder.BoxIfUnboxed(Node.Expr.Type, this.Context.AnyType);
//		this.VisitingBuilder.InvokeMethodCall(Node.CastType, JLib.GreenCastOperator);
	}

	@Override public void VisitFunctionNode(GtFunctionNode Node) {
		LibGreenTea.TODO("FunctionNode");
	}

	@Override public void VisitErrorNode(GtErrorNode Node) {
//		this.Builder.AsmMethodVisitor.visitLdcInsn("(ErrorNode)");
//		this.Builder.Call(this.methodMap.get("error_node"));
		LibGreenTea.Exit(1, "ErrorNode found in JavaByteCodeGenerator");
	}

	@Override public void VisitCommandNode(GtCommandNode Node) {
//		ArrayList<ArrayList<GtNode>> Args = new ArrayList<ArrayList<GtNode>>();
//		GtCommandNode node = Node;
//		while(node != null) {
//			Args.add(node.ArgumentList);
//			node = (GtCommandNode) node.PipedNextNode;
//		}
//		// new String[][n]
//		this.VisitingBuilder.MethodVisitor.visitLdcInsn(Args.size());
//		this.VisitingBuilder.MethodVisitor.visitTypeInsn(ANEWARRAY, Type.getInternalName(String[].class));
//		for(int i=0; i<Args.size(); i++) {
//			// new String[m];
//			ArrayList<GtNode> Arg = Args.get(i);
//			this.VisitingBuilder.MethodVisitor.visitInsn(DUP);
//			this.VisitingBuilder.MethodVisitor.visitLdcInsn(i);
//			this.VisitingBuilder.MethodVisitor.visitLdcInsn(Arg.size());
//			this.VisitingBuilder.MethodVisitor.visitTypeInsn(ANEWARRAY, Type.getInternalName(String.class));
//			for(int j=0; j<Arg.size(); j++) {
//				this.VisitingBuilder.MethodVisitor.visitInsn(DUP);
//				this.VisitingBuilder.MethodVisitor.visitLdcInsn(j);
//				Arg.get(j).Evaluate(this);
//				this.VisitingBuilder.MethodVisitor.visitInsn(AASTORE);
//			}
//			this.VisitingBuilder.MethodVisitor.visitInsn(AASTORE);
//		}
//		if(Node.Type.IsBooleanType()) {
//			this.VisitingBuilder.InvokeMethodCall(Node.Type, JLib.ExecCommandBool);
//		}
//		else if(Node.Type.IsStringType()) {
//			this.VisitingBuilder.InvokeMethodCall(Node.Type, JLib.ExecCommandString);
//		}
//		else {
//			this.VisitingBuilder.InvokeMethodCall(Node.Type, JLib.ExecCommandVoid);
//		}
	}

	@Override public void InvokeMainFunc(String MainFuncName) {
//		try {
//			Class<?> MainClass = Class.forName(JLib.GetHolderClassName(this.Context, MainFuncName), false, this.ClassGenerator);
//			Method m = MainClass.getMethod(MainFuncName);
//			if(m != null) {
//				m.invoke(null);
//			}
//		} catch(ClassNotFoundException e) {
//			LibGreenTea.VerboseException(e);
//		} catch(InvocationTargetException e) {
//			LibGreenTea.VerboseException(e);
//		} catch(IllegalAccessException e) {
//			LibGreenTea.VerboseException(e);
//		} catch(NoSuchMethodException e) {
//			LibGreenTea.VerboseException(e);
//		}
	}
}
