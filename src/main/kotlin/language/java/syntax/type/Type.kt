package language.java.syntax.type

sealed class Type

sealed class NonVoidType : Type()

class IntType : NonVoidType() {
    override fun toString(): String {
        return "IntType"
    }
}

class BooleanType : NonVoidType() {
    override fun toString(): String {
        return "BooleanType"
    }
}

class VoidType : Type() {
    override fun toString(): String {
        return "VoidType"
    }
}
