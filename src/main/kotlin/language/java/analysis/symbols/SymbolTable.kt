package language.java.analysis.symbols

import language.java.syntax.Identifier

class SymbolTable(private var symbols: MutableMap<Identifier, SymbolTableEntry>) {

    companion object {
        fun of(): SymbolTable {
            return SymbolTable(HashMap())
        }
    }

    fun insert(identifier: Identifier, entry: SymbolTableEntry) {
        symbols[identifier] = entry
    }

    fun lookup(identifier: Identifier): SymbolTableEntry? {
        return symbols[identifier]
    }
}
