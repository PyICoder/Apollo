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
import net.apolloclient.event.bus.SubscribeEvent;
import net.apolloclient.module.bus.Module;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Allocates a method for the {@link CommandBus}, must have a single parameter
 * of type {@link String}. Any method found with this annotation will be invoked if
 * one of its {@link #alias()} match the string value found after {@link CommandBus#PREFIX}<p></p>
 *
 * <p> Currently command methods will only be invoked if no {@link Module} instance cancels
 * the chat packet using @{@link SubscribeEvent} since all modules have a higher priority
 * than command annotations.</p><p></p>
 *
 * <p><b>Parameter</b> - the chat message excluding the command portion. For example if a chat message equals
 * {@code .hello icovid}; any command using the {@code .hello} alias will be invoke with the
 * {@code icovid} string value.</p><p></p>
 *
 * @author Icovid | Icovid#3888
 * @since 1.2.0-BETA
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Command {

    /**
     * @return collection of all strings method can be invoke on.
     */
    String[] alias();

    /**
     * @return string description of command usage.
     */
    String description();

    /**
     * @return boolean for if command chat packet is canceled.
     */
    boolean deleteMessage() default true;

    /**
     * @return boolean for if {@link CommandBus} triggers at incorrect case
     */
    boolean ignoreCase() default true;

    /**
     * @return priority of command method over other command methods.
     */
    Priority priority() default Priority.NORMAL;
}
