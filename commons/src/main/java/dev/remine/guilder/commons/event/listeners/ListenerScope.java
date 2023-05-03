package dev.remine.guilder.commons.event.listeners;

import java.lang.annotation.*;

/**
 * Specifies the listener for a certain scope (e.g. A.B) or scope group (e.g. A.*)
 *
 * @see dev.remine.guilder.commons.event.scope.ScopeGroup
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ListenerScope {

    /**
     * @return the handler's scope or scope group
     */
    String value();
}