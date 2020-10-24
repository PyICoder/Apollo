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

package net.apolloclient.module.bus;

import net.apolloclient.event.Event;
import net.apolloclient.event.bus.HandlerEventContainer;
import net.apolloclient.event.bus.SubscribeEvent;
import net.apolloclient.event.bus.EventContainer;
import net.apolloclient.module.Category;
import net.apolloclient.module.ModuleContainer;
import net.apolloclient.module.bus.event.ModuleEvent;
import net.apolloclient.module.DraggableModuleContainer;

import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Container class to hold basic module information and methods
 * used by the {@link ModuleFactory}. Has two child classes used in the
 * Module creation of type {@link ModuleContainer} and {@link DraggableModuleContainer}
 *
 * <p>Done this way so {@link ModuleContainer} and {@link DraggableModuleContainer} can
 * be handled differently within constructor and settings can be loaded correctly.</p>
 *
 * @author Icovid | Icovid#3888
 * @see ModuleContainer module
 * @see DraggableModuleContainer draggable module
 * @since 1.2.0-BETA
 */
public interface ModContainer {


    /**
     * Set module enabled state to certain value.
     */
    void setEnabled(boolean enabled);

    /**
     * Sets priority of current module and sort {@link ModuleFactory} again.
     *
     * @param priority to set module too.
     */
    void setPriority(int priority);


    /**
     * the name of module to displayed in gui list.
     * <p>Used to define settings in file / must be unique to module</p>
     */
    String getName();

    /**
     * the description of module usage
     */
    String getDescription();

    /**
     * the {@link Category} used to section modules.
     */
    Category getCategory();

    /**
     * the aliases or search terms people can type instead of module
     * name to find the module
     */
    String[] getAliases();

    /**
     * the priority of modules events compared to other modules
     */
    int getPriority();

    /**
     * the author of module to be displayed in credits.
     */
    String getAuthor();

    /**
     * if module is currently enabled.
     */
    boolean isEnabled();

    /**
     * the list of servers module is compatible
     * <p>Use this if module is dependant on certain aspects of a server such as chat formatting
     * or scoreboard information.</p>
     */
    String[] getRecommendedServersIPs();

    /**
     * the list of servers module is not allowed on
     * <p>Use this if module should always be disabled to follow server guidelines.</p>
     */
    String[] getDisallowedServersIPs();

    /**
     * @return the actual module class object
     */
    Object getInstance();

    /**
     * Toggle module enabled state
     */
    default void toggle() { this.setEnabled(!this.isEnabled()); };

    /**
     * Tracks all methods annotated with {@link EventHandler}
     *
     * @return HashMap of {@link ModuleEvent} with a list of {@link HandlerEventContainer}
     */
    HashMap<Class<? extends ModuleEvent>, CopyOnWriteArrayList<HandlerEventContainer>> getHandlers();

    /**
     * Tracks all methods annotated with {@link SubscribeEvent}
     *
     * @return HashMap of {@link Event} with a list of {@link HandlerEventContainer}
     */
    HashMap<Class<? extends Event>, CopyOnWriteArrayList<EventContainer>> getEvents();

    /**
     * Post an event to module class and any other module requesting its events
     *
     * @param moduleEvent event to be posted.
     */
    default void post(ModuleEvent moduleEvent) {
        for (HandlerEventContainer handlerEventContainer : getHandlers().getOrDefault(moduleEvent.getClass(), new CopyOnWriteArrayList<>()))
            handlerEventContainer.invoke(moduleEvent);
    };
}
