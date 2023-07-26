package be.loeckerlang.compiler.intermediate.symbols;

import be.loeckerlang.compiler.intermediate.Symbol;
import be.loeckerlang.compiler.intermediate.tables.SymbolTable;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a group of method signatures.
 *
 * @author   Jelle De Loecker   <jelle@elevenways.be>
 * @since    0.1.0
 */
public class MethodSignatureGroup extends Symbol {

    private Map<MethodSignature, Method> methods = new HashMap<>();

    @Override
    public String getName() {
        return null;
    }

    @Override
    public SymbolTable getParentScope() {
        return null;
    }

}
