package rs.ac.bg.etf.pp1;

import java_cup.runtime.Symbol;

%%

%{

	// ukljucivanje informacije o poziciji tokena
	private Symbol new_symbol(int type) {
		return new Symbol(type, yyline+1, yycolumn);
	}
	
	// ukljucivanje informacije o poziciji tokena
	private Symbol new_symbol(int type, Object value) {
		return new Symbol(type, yyline+1, yycolumn, value);
	}

%}

%cup

%line
%column

%xstate COMMENT

%eofval{
	return new_symbol(sym.EOF);
%eofval}

%%

" " 	{ }
"\b" 	{ }
"\t" 	{ }
"\r\n" 	{ }
"\f" 	{ }

"program"   { return new_symbol(sym.PROG, yytext()); }
"break"   { return new_symbol(sym.BREAK, yytext()); }
"class"   { return new_symbol(sym.CLASS, yytext()); }
"else"   { return new_symbol(sym.ELSE, yytext()); }
"const"   { return new_symbol(sym.CONST, yytext()); }
"if"   { return new_symbol(sym.IF, yytext()); }
"while"   { return new_symbol(sym.WHILE, yytext()); }
"new"   { return new_symbol(sym.NEW, yytext()); }
"print"   { return new_symbol(sym.PRINT, yytext()); }
"read"   { return new_symbol(sym.READ, yytext()); }
"return"   { return new_symbol(sym.RETURN, yytext()); }
"void"   { return new_symbol(sym.VOID, yytext()); }
"extends"   { return new_symbol(sym.EXTENDS, yytext()); }
"continue"   { return new_symbol(sym.CONTINUE, yytext()); }
"foreach"   { return new_symbol(sym.FOREACH, yytext()); }

"+"   { return new_symbol(sym.PLUS, yytext()); }
"-"   { return new_symbol(sym.MINUS, yytext()); }
"/"   { return new_symbol(sym.DIV, yytext()); }
"*"   { return new_symbol(sym.MUL, yytext()); }
"%"   { return new_symbol(sym.PERCENT, yytext()); }
"=="   { return new_symbol(sym.EQUALS, yytext()); }
"!="   { return new_symbol(sym.NOTEQUALS, yytext()); }
">"   { return new_symbol(sym.MORE, yytext()); }
">="   { return new_symbol(sym.NOTLESS, yytext()); }
"<"   { return new_symbol(sym.LESS, yytext()); }
"<="   { return new_symbol(sym.NOTMORE, yytext()); }
"&&"   { return new_symbol(sym.ANDAND, yytext()); }
"||"   { return new_symbol(sym.OROR, yytext()); }
"="   { return new_symbol(sym.IS, yytext()); }
"++"   { return new_symbol(sym.INCREMENT, yytext()); }
"--"   { return new_symbol(sym.DECREMENT, yytext()); }
";"   { return new_symbol(sym.SEMICOLON, yytext()); }
":"   { return new_symbol(sym.DOUBLEDOTS, yytext()); }
","   { return new_symbol(sym.COMA, yytext()); }
"."   { return new_symbol(sym.FULLSTOP, yytext()); }
"("   { return new_symbol(sym.OPENBRACKET, yytext()); }
")"   { return new_symbol(sym.CLOSEBRACKET, yytext()); }
"["   { return new_symbol(sym.OPENANGLE, yytext()); }
"]"   { return new_symbol(sym.CLOSEANGLE, yytext()); }
"{"   { return new_symbol(sym.OPENPAR, yytext()); }
"}"   { return new_symbol(sym.CLOSEPAR, yytext()); }
"=>"   { return new_symbol(sym.ARROW, yytext()); }

"//" 		     { yybegin(COMMENT); }
<COMMENT> .      { yybegin(COMMENT); }
<COMMENT> "\r\n" { yybegin(YYINITIAL); }

(true|false)  { return new_symbol(sym.BOOL, new Boolean (yytext())); }
[0-9]+  { return new_symbol(sym.NUMBER, new Integer (yytext())); }
([a-z]|[A-Z])[a-z|A-Z|0-9|_]* 	{return new_symbol (sym.IDENT, yytext()); }
\".\" { return new_symbol(sym.CHARCONST, yytext().charAt(1)); }


. { System.err.println("Lexer error ("+yytext()+") on line "+(yyline+1)); }









