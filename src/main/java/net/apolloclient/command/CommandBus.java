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

import net.apolloclient.Apollo;
import net.apolloclient.event.Priority;
import net.apolloclient.event.bus.EventBus;
import net.apolloclient.event.bus.SubscribeEvent;
import net.apolloclient.event.impl.player.PlayerChatEvent;
import net.apolloclient.module.bus.Module;
import net.apolloclient.module.bus.event.InitializationEvent;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Comparator;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Implementation of @{@link Command}. Generates a list of {@link CommandContainer}s
 * used to sort and invoke all command messages. Any instance of this class must be registered
 * in the {@link EventBus} to receive {@link PlayerChatEvent}s and invoke needed methods from
 * the {@link #commands} CopyOnWriteArrayList.<p></p>
 *
 * <p>To setup on object to invoke command methods you must first register the object.
 * You can do this by calling an instance of the {@link #register(Object)} method.
 * This is usually done in a {@link Module} {@link InitializationEvent} or in an object constructor.</p><p></p>
 *
 * <p>The opposite can be done to remove an object from invoking command methods by
 * calling an instance of the {@link #unRegister(Object)} method.</p>
 *
 * @author Icovid | Icovid#3888
 * @since 1.2.0-BETA
 */
public class CommandBus {

    /** Chat @{@link Command} prefix used in {@link CommandBus}*/
    public static String PREFIX = ".";
    /** A list of all {@link CommandContainer}s currently set to be invoked in {@link CommandBus}. */
    private final CopyOnWriteArrayList<CommandContainer> commands = new CopyOnWriteArrayList<>();

    /**
     * Registers an object in the {@link CommandBus}. Any method annotated with @{@link Command} will be cached into
     * a new {@link CommandContainer} object and added to the {@link #commands} CopyOnWriteArrayList.
     *
     * @param any the object to be registered
     */
    public void register(Object any) {
        for (Method method : any.getClass().getDeclaredMethods()) {
            for (Annotation annotation : method.getAnnotationsByType(Command.class)) {
                // Check if methods as 1 parameter of type string.
                if (method.getParameterTypes().length == 1 && String.class.isAssignableFrom(method.getParameterTypes()[0])) {
                    // Set method accessible in case its private.
                    method.setAccessible(true);
                    Command command = method.getAnnotation(Command.class);
                    // add the discovered method to commands cache.
                    commands.add(new CommandContainer(any, method, command.alias(), command.description(), command.deleteMessage(), command.ignoreCase(), command.priority()));
                    // Log command container creation
                    Apollo.log("[COMMAND-BUS] Registered method " + method.getName().toUpperCase() + " at " + any.getClass().getCanonicalName());

                } else {
                    // Log error if method does not have 1 parameter of type string
                    Apollo.error("[COMMAND-BUS] Method " + method.getName().toUpperCase() + " has invalid parameters!");
                }
            }
        }
        // Sort the newly added methods with all methods inside the commands cache.
        commands.sort(Comparator.comparingInt(listener -> listener.priority.id));
    }

    /**
     * Unregisters an object from the {@link CommandBus}. Any method annotated with @{@link Command} will be removed
     * from the current {@link #commands} method cache.
     *
     * @param any the object to be unregistered from the {@link CommandBus}
     */
    public void unRegister(Object any) {
        commands.removeIf(listener -> listener.instance == any);
    }

    /**
     * Test chat event against all cached {@link CommandContainer}s and invoke container method on appropriate
     * alias.
     *
     * @param event the chat event that's cancelable
     */
    @SubscribeEvent(priority = Priority.LOW)
    public void onChat(PlayerChatEvent event) {
        if (event.message.startsWith(CommandBus.PREFIX)) {
            // Set message canceled by default.
            event.setCanceled(true);
            // Split command and string after command.
            String[] args = event.message.split(" ", 2);
            for (CommandContainer container : this.commands) {
                if (Arrays.stream(container.alias).anyMatch(container.ignoreCase ?
                                                                    args[0].replaceFirst(".", "")::equalsIgnoreCase :
                                                                    args[0].replaceFirst(".", "")::equals)) {
                    // Invoke method in remaining string length is greater than 1
                    if (args.length > 1) container.invoke(args[1]);
                    event.setCanceled(container.deleteMessage);
                }
            }
        }
    }

}
