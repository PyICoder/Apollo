/*⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼
  Copyright (C) 2020-2021 developed by Icovid and Apollo Development Team

  This program is free software: you can redistribute it and/or modify
  it under the terms of the GNU Affero General Public License as published
  by the Free Software Foundation, either version 3 of the License, or
  (at your option) any later version.
  This program is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU Affero General Public License for more details.
  You should have received a copy of the GNU Affero General Public License
  along with this program.  If not, see https://www.gnu.org/licenses/.

  Contact: Icovid#3888 @ https://discord.com
 ⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼⎼*/

package net.apolloclient.command;

import net.apolloclient.event.Priority;

import java.lang.reflect.Method;

/**
 * Container object used to hold needed information used in the @{@link Command} annotation by the {@link CommandBus}
 * and to invoke methods using a string parameter.
 *
 * <p>To invoke a {@link CommandContainer} use an instance of the objects {@link #invoke(Object...)} method
 * with a single string parameter.</p>
 *
 * @author Icovid | Icovid#3888
 * @see Command anotation this contains
 * @since 1.2.0-BETA
 */
public class CommandContainer {

    /** the instance of the object to invoke methods on. */
    public final Object instance;
    /** the method to invoke with string parameter. */
    public final Method method;
    /** the collection of all strings method can be invoke on. */
    public final String[] alias;
    /** the string description of command usage */
    public final String description;
    /** if command chat packet is canceled. */
    public final boolean deleteMessage;
    /** if {@link CommandBus} triggers at incorrect case. */
    public final boolean ignoreCase;
    /** the priority of command method over other command methods. */
    public final Priority priority;

    /**
     * Creates a new {@link CommandContainer} instance with the given information.
     *
     * @param instance      the instance of the object to invoke methods on.
     * @param method        the method to invoke with string parameter.
     * @param alias         the collection of all strings method can be invoke on.
     * @param description   the string description of command usage.
     * @param deleteMessage if command chat packet is canceled.
     * @param ignoreCase    if {@link CommandBus} triggers at incorrect case.
     * @param priority      the priority of command method over other command methods.
     */
    public CommandContainer(Object instance, Method method, String[] alias, String description, boolean deleteMessage, boolean ignoreCase, Priority priority) {
        this.instance      = instance;
        this.method        = method;
        this.alias         = alias;
        this.description   = description;
        this.deleteMessage = deleteMessage;
        this.ignoreCase    = ignoreCase;
        this.priority      = priority;
    }

    /**
     * Invoke method using instance.
     *
     * @param args the arguments for invoking method.
     */
    public void invoke(Object... args) {
        try {
            this.method.invoke(instance, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
