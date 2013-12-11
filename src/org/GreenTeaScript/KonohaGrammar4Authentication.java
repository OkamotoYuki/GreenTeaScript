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
import org.GreenTeaScript.DShell.DShellAuthenticator;
//endif VAJA

public class KonohaGrammar4Authentication extends GtGrammar {
	
	public static GtNode TypeFuncCall(GtTypeEnv Gamma, GtSyntaxTree ParsedTree, GtNode FuncNode, GtType ContextType) {
		if(FuncNode.IsConstNode()) {
			/*local*/Object Func = FuncNode.ToConstValue(Gamma.Context, false);
			if(Func instanceof GtType) {  // constructor;
				return KonohaGrammar.TypeNewNode(Gamma, ParsedTree, FuncNode.Token, (/*cast*/GtType)Func, ContextType);
			}
			else if(Func instanceof GtFunc) {
				if(!DShellAuthenticator.AuthenticateFunction(((GtFunc)Func).FuncName)) {
					LibGreenTea.Exit(1, "not authenticated function: "+((GtFunc)Func).FuncName);
				}
				return KonohaGrammar.TypePolyFuncCall(Gamma, ParsedTree, new GtPolyFunc(null).Append(Gamma.Context, (/*cast*/GtFunc)Func, null));
			}
			else if(Func instanceof GtPolyFunc) {
				return KonohaGrammar.TypePolyFuncCall(Gamma, ParsedTree, (/*cast*/GtPolyFunc)Func);
			}
		}
		if(FuncNode.Type.IsFuncType()) {
			return KonohaGrammar.TypeFuncObject(Gamma, ParsedTree, FuncNode);
		}
		return KonohaGrammar.TypeMethodNameCall(Gamma, ParsedTree, FuncNode, "()", ContextType);
	}
	
	public static GtSyntaxTree ParseApply(GtNameSpace NameSpace, GtTokenContext TokenContext, GtSyntaxTree LeftTree, GtSyntaxPattern Pattern) {
		/*local*/int ParseFlag = TokenContext.SetSkipIndent(true);
		/*local*/GtSyntaxTree FuncTree = TokenContext.CreateSyntaxTree(NameSpace, Pattern, null);
		FuncTree.SetMatchedTokenAt(KeyTokenIndex, NameSpace, TokenContext, "(", Required);
		FuncTree.AppendParsedTree2(LeftTree);
		if(!TokenContext.MatchToken(")")) {
			while(!FuncTree.IsMismatchedOrError()) {
				FuncTree.AppendMatchedPattern(NameSpace, TokenContext, "$Expression$", Required);
				if(TokenContext.MatchToken(")")) {
					break;
				}
				FuncTree.SetMatchedTokenAt(NoWhere, NameSpace, TokenContext, ",", Required);
			}
		}
		TokenContext.SetRememberFlag(ParseFlag);
		return FuncTree;
	}
	
	public static GtNode TypeApply(GtTypeEnv Gamma, GtSyntaxTree ParsedTree, GtType ContextType) {
		/*local*/GtNode FuncNode = ParsedTree.TypeCheckAt(0, Gamma, GtStaticTable.FuncType, NoCheckPolicy);
		if(FuncNode.IsErrorNode()) {
			return FuncNode;
		}
		return KonohaGrammar4Authentication.TypeFuncCall(Gamma, ParsedTree, FuncNode, ContextType);
	}
	
	@Override public void LoadTo(GtNameSpace NameSpace) {
		// Define Constants
		/*local*/GtParserContext Context = NameSpace.Context;

		NameSpace.AppendExtendedSyntax("(", 0, GtGrammar.LoadParseFunc(Context, this, "ParseApply"), GtGrammar.LoadTypeFunc(Context, this, "TypeApply"));
	}
	
}
