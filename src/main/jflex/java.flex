package language.lexing.Lexer;

import language.java.lexing.*;import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

%%

%class Lexer
%char
%final
%public
%unicode
%line
%column
%type Token

%{
    public List<Token> lex() throws IOException {
        List<Token> result = new ArrayList<Token>();
        while (!yyatEOF()) {
            var token = yylex();
            if (token != null) {
                result.add(token);
            }
        }
        return result;
    }

    private Position pos() {
      return new Position(yyline, yycolumn);
    }
%}

LineTerminator = \r|\n|\r\n
WhiteSpace     = {LineTerminator} | [ \t\f]
Identifier     = [:jletter:] [:jletterdigit:]*
Number         = 0 | [1-9][0-9]*
// TODO identifier = "(?:\\b[_a-zA-Z]|\\B\\$)[_$a-zA-Z0-9]*+"

%%

<YYINITIAL> {
    ","  { return new CommaToken(pos()); }
    "{"  { return new CurlyOpenToken(pos()); }
    "}"  { return new CurlyCloseToken(pos()); }
    "("  { return new RoundOpenToken(pos()); }
    ")"  { return new RoundCloseToken(pos()); }
    ";"  { return new SemicolonToken(pos()); }
    "==" { return new EqualsToken(pos()); }
    "!=" { return new NotEqualsToken(pos()); }
    "="  { return new AssignmentToken(pos()); }
    "!"  { return new NotToken(pos()); }
    "+"  { return new PlusToken(pos()); }
    "-"  { return new MinusToken(pos()); }
    "*"  { return new TimesToken(pos()); }
    "/"  { return new DivideToken(pos()); }
    "%"  { return new ModuloToken(pos()); }
    "<=" { return new LessThanEqualsToken(pos()); }
    "<"  { return new LessThanToken(pos()); }
    ">=" { return new GreaterThanEqualsToken(pos()); }
    ">"  { return new GreaterThanToken(pos()); }
    "&&" { return new AndToken(pos()); }
    "||" { return new OrToken(pos()); }

    "class"    { return new ClassToken(pos());    }
    "static"   { return new StaticToken(pos());   }
    "int"      { return new IntToken(pos());      }
    "boolean"  { return new BooleanToken(pos());  }
    "void"     { return new VoidToken(pos());     }
    "if"       { return new IfToken(pos());       }
    "else"     { return new ElseToken(pos());     }
    "true"     { return new TrueToken(pos());     }
    "false"    { return new FalseToken(pos());    }
    "return"   { return new ReturnToken(pos());   }
    "while"    { return new WhileToken(pos());    }
    "break"    { return new BreakToken(pos());    }
    "continue" { return new ContinueToken(pos()); }

    {WhiteSpace} { }
    {Identifier} { return new IdentifierToken(yytext(), pos()); }
    {Number}     { return new NumberToken(Long.parseLong(yytext()), pos()); }
}
