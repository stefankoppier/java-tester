package language.java.lexing

import java.util.*

open class Token(val position: Position) {
    override fun toString(): String {
        return "${javaClass.name.split('.').last()}{$position}"
    }

    override fun equals(other: Any?): Boolean {
        return if (other == null) false else other::class == this::class
    }

    override fun hashCode(): Int {
        return Objects.hashCode(this::class)
    }
}

class IdentifierToken(val value: String, position: Position = Position.unknown()) : Token(position) {
    override fun toString(): String {
        return "IdentifierToken{$value $position}"
    }
}

class NumberToken(val value: Long, position: Position = Position.unknown()) : Token(position) {
    override fun toString(): String {
        return "NumberToken{$value $position}"
    }
}

class CommaToken(position: Position = Position.unknown()) : Token(position)

class CurlyOpenToken(position: Position = Position.unknown()) : Token(position)

class CurlyCloseToken(position: Position = Position.unknown()) : Token(position)

class RoundOpenToken(position: Position = Position.unknown()) : Token(position)

class RoundCloseToken(position: Position = Position.unknown()) : Token(position)

class SemicolonToken(position: Position = Position.unknown()) : Token(position)

class EqualsToken(position: Position = Position.unknown()) : Token(position)

class NotEqualsToken(position: Position = Position.unknown()) : Token(position)

class LessThanToken(position: Position = Position.unknown()) : Token(position)

class LessThanEqualsToken(position: Position = Position.unknown()) : Token(position)

class GreaterThanToken(position: Position = Position.unknown()) : Token(position)

class GreaterThanEqualsToken(position: Position = Position.unknown()) : Token(position)

class AndToken(position: Position = Position.unknown()) : Token(position)

class OrToken(position: Position = Position.unknown()) : Token(position)

class AssignmentToken(position: Position = Position.unknown()) : Token(position)

class NotToken(position: Position = Position.unknown()) : Token(position)

class PlusToken(position: Position = Position.unknown()) : Token(position)

class MinusToken(position: Position = Position.unknown()) : Token(position)

class TimesToken(position: Position = Position.unknown()) : Token(position)

class DivideToken(position: Position = Position.unknown()) : Token(position)

class ModuloToken(position: Position = Position.unknown()) : Token(position)

class ClassToken(position: Position = Position.unknown()) : Token(position)

class StaticToken(position: Position = Position.unknown()) : Token(position)

class IntToken(position: Position = Position.unknown()) : Token(position)

class BooleanToken(position: Position = Position.unknown()) : Token(position)

class VoidToken(position: Position = Position.unknown()) : Token(position)

class ReturnToken(position: Position = Position.unknown()) : Token(position)

class IfToken(position: Position = Position.unknown()) : Token(position)

class ElseToken(position: Position = Position.unknown()) : Token(position)

class WhileToken(position: Position = Position.unknown()) : Token(position)

class TrueToken(position: Position = Position.unknown()) : Token(position)

class BreakToken(position: Position = Position.unknown()) : Token(position)

class ContinueToken(position: Position = Position.unknown()) : Token(position)

class FalseToken(position: Position = Position.unknown()) : Token(position)
