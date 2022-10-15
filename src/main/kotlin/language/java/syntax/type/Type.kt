package language.java.syntax.type

import language.java.lexing.Position

sealed class Type(val position: Position)

sealed class NonVoidType(position: Position) : Type(position)

class IntType(position: Position) : NonVoidType(position) {
    override fun toString(): String {
        return "IntType"
    }
}

class BooleanType(position: Position) : NonVoidType(position) {
    override fun toString(): String {
        return "BooleanType"
    }
}

class VoidType(position: Position) : Type(position) {
    override fun toString(): String {
        return "VoidType"
    }
}
