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

package net.apolloclient.event.bus;

import net.apolloclient.Apollo;
import net.apolloclient.command.CommandBus;
import net.apolloclient.event.Event;
import net.apolloclient.event.EventCancelable;
import net.apolloclient.module.bus.ModContainer;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Main implementation of {@link SubscribeEvent}s generate a list of
 * {@link EventContainer}s used to sort and invoke all event methods. Also
 * registers event methods in {@link ModContainer}s and any other objects.<p></p>
 *
 * <p>{@link ModContainer}s are registered in the event bus by default and there events will
 * only be triggered if said container is enabled. However if you would like to register
 * and external object to receive {@link SubscribeEvent}s you can do so by calling
 * the {@link #register(Object)} method with an instance of the class as parameter</p><p></p>
 *
 * <p>Similar to the {@link CommandBus} the opposite can be done to remove an object from
 * invoking event methods by calling an instance of the {@link #unRegister(Object)} method. with
 * any object as parameter</p>
 *
 * @author Icovid | Icovid#3888
 * @since 1.2.0-BETA
 */
public class EventBus {

    /** List of {@link EventContainer}s used to sort and invoke all event methods */
    private final HashMap<Class<? extends Event>, CopyOnWriteArrayList<SubscribeEventContainer>> listeners = new HashMap<>();

    /**
     * Register mod container instance to receive {@link Event}s using
     * the {@link SubscribeEvent} annotation.
     *
     * <p>{@link ModContainer}s are handled differently than normal object
     * to provide support for module priority in future addon gui.</p>
     *
     * @param modContainer container to be registered in the EventBus
     */
    @SuppressWarnings("unchecked")
    public void register(ModContainer modContainer) {
        for (Method method : modContainer.getInstance().getClass().getDeclaredMethods()) {
            for (Annotation annotation : method.getAnnotationsByType(SubscribeEvent.class)) {
                // Check if method has 1 parameter of type Event
                if (method.getParameterTypes().length == 1 && Event.class.isAssignableFrom(method.getParameterTypes()[0])) {
                    // Set method accessible in case of private
                    method.setAccessible(true);
                    Class<? extends Event> event = (Class<? extends Event>) method.getParameterTypes()[0];
                    // Check if mod container hash contains current event
                    if (!modContainer.getEvents().containsKey(event))
                        modContainer.getEvents().put(event, new CopyOnWriteArrayList<>());
                    // Add event to container hash
                    modContainer.getEvents().get(event).add(new SubscribeEventContainer(modContainer.getInstance(), method, method.getAnnotation(SubscribeEvent.class).priority(), method.getAnnotation(SubscribeEvent.class).cancelable()));
                    // Log the event
                    Apollo.log("[" + modContainer.getName() + "] [EVENT] Registered method " + method.getName().toUpperCase() + " with " + method.getParameterTypes()[0].getCanonicalName() + " event.");
                    // Sort container hash by priority
                    modContainer.getEvents().get(event).sort(Comparator.comparingInt(listener -> listener.getPriority().id));
                } else {
                    // Log invalid parameter and ignore method
                    Apollo.error("[" + modContainer.getName() + "] [EVENT] Event method " + method.getName().toUpperCase() + " has invalid parameters!");
                }

            }
        }
    }

    /**
     * Clears a  {@link ModContainer#getEvents()} al all event methods
     *
     * <p>{@link ModContainer}s are handled differently than normal object
     * to provide support for module priority in future addon gui.</p>
     *
     * @param modContainer container to be unregistered from the EventBus
     */
    public void unRegister(ModContainer modContainer) { modContainer.getEvents().clear(); }

    /**
     * Registers an object to the {@link EventBus}. All methods annotated with the {@link
     * SubscribeEvent} annotation will be cached into a new {@link EventContainer} and
     * called when that Event is posted.
     *
     * @param any object you want to register
     */
    @SuppressWarnings("unchecked")
    public void register(Object any) {
        for (Method method : any.getClass().getDeclaredMethods()) {
            for (Annotation annotation : method.getAnnotationsByType(SubscribeEvent.class)) {
                // Check if method has 1 parameter of type Event
                if (method.getParameterTypes().length == 1 && Event.class.isAssignableFrom(method.getParameterTypes()[0])) {
                    // Set method accessible in case of private
                    method.setAccessible(true);
                    Class<? extends Event> event = (Class<? extends Event>) method.getParameterTypes()[0];
                    // Check if listener hash contains current event
                    if (!listeners.containsKey(event))
                        listeners.put(event, new CopyOnWriteArrayList<>());
                    // Add event to listener hash
                    listeners.get(event).add(new SubscribeEventContainer(any, method, method.getAnnotation(SubscribeEvent.class).priority(), method.getAnnotation(SubscribeEvent.class).cancelable()));
                    // Log the event
                    Apollo.log("[" + any.getClass().getSimpleName() + "] [EVENT] Registered method " + method.getName().toUpperCase() + " with " + method.getParameterTypes()[0].getCanonicalName() + " event.");
                    // Sort container hash by priority
                    listeners.get(event).sort(Comparator.comparingInt(listener -> listener.getPriority().id));
                } else {
                    // Log invalid parameter and ignore method
                    Apollo.error("[" + any.getClass().getSimpleName()+ "] [EVENT] Event method " + method.getName().toUpperCase() + " has invalid parameters!");
                }

            }
        }
    }

    /**
     * Unregisters an object from the {@link EventBus} and removes any of its
     * event methods from the {@link #listeners} hash.
     *
     * @param any object to be unregistered from the EventBus
     */
    public void unRegister(Object any) { listeners.values().forEach(it -> it.removeIf(listener -> listener.instance == any)); }

    /**
     * Posts an event to the {@link EventBus}. This calls every method that is
     * listening for the event in question using {@link SubscribeEvent}.
     *
     * @param event event to post
     */
    public void post(Event event) {
        boolean cancelable = event instanceof EventCancelable;
        // trigger event to all module containers by priority
        for (ModContainer container : Apollo.MODULE_FACTORY.modules) {
            for (SubscribeEventContainer eventContainer : container.getEvents().getOrDefault(event.getClass(), new CopyOnWriteArrayList<>())) {
                if (cancelable && ((EventCancelable) event).isCanceled() && eventContainer.cancelable) continue;
                eventContainer.invoke(event);
            }
        }
        // trigger event to all other classes
        for (SubscribeEventContainer container : this.listeners.getOrDefault(event.getClass(), new CopyOnWriteArrayList<>())) {
            if (cancelable && ((EventCancelable) event).isCanceled() && container.cancelable) continue;
            container.invoke(event);
        }
    }
}
