package be.loeckerlang.compiler.intermediate.symbols;

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

}
