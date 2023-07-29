package be.loeckerlang.compiler.intermediate;

import be.loeckerlang.compiler.ast.nodes.ModifiersNode;

public interface HasModifiers {

    ModifiersNode getModifiersNode();

    default boolean isPublic() {
        return getModifiersNode().isPublic();
    }

    default boolean isPrivate() {
        return getModifiersNode().isPrivate();
    }

    default boolean isProtected() {
        return getModifiersNode().isProtected();
    }

    default boolean isAbstract() {
        return getModifiersNode().isAbstract();
    }

    default boolean isFinal() {
        return getModifiersNode().isFinal();
    }

    default boolean isStatic() {
        return getModifiersNode().isStatic();
    }

    default boolean isBuiltin() {
        return getModifiersNode().isBuiltin();
    }

    default boolean isImmutable() {
        return getModifiersNode().isImmutable();
    }

    default boolean isMemoized() {
        return getModifiersNode().isMemoized();
    }
}
