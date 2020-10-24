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

package net.apolloclient.module;

import net.apolloclient.Apollo;
import net.apolloclient.event.Event;
import net.apolloclient.event.Priority;
import net.apolloclient.event.bus.EventContainer;
import net.apolloclient.event.bus.HandlerEventContainer;
import net.apolloclient.module.bus.Instance;
import net.apolloclient.module.bus.ModContainer;
import net.apolloclient.module.bus.Module;
import net.apolloclient.module.bus.ModuleFactory;
import net.apolloclient.module.bus.event.DisableEvent;
import net.apolloclient.module.bus.event.EnableEvent;
import net.apolloclient.module.bus.event.ModuleEvent;

import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Holds module information from any class annotated with {@link Module}
 *
 * @author Icovid | Icovid#3888
 * @see DraggableModuleContainer draggable module
 * @since 1.2.0-BETA
 */
public class ModuleContainer implements ModContainer {

    /** the instance of the module class for events to be called on */
    private final Object instance;
    /** the name of module */
    private final String name;
    /** the description of module usage */
    private final String description;
    /** the author of module */
    private final String author;
    /** the {@link Category} for module to be displayed in */
    private final Category category;
    /** the aliases or search terms of module */
    private final String[] aliases;
    /** the list of servers module is best compatible with */
    private final String[] recommendedServersIP;
    /** the list of servers module is not allowed on */
    private final String[] disallowedServersIP;
    /** the hashMap of {@link ModuleEvent}s with a list of {@link HandlerEventContainer}s */
    private final HashMap<Class<? extends ModuleEvent>, CopyOnWriteArrayList<HandlerEventContainer>> handlers = new HashMap<>();
    /** the hashMap of {@link Event}s with a list of {@link EventContainer}s */
    private final HashMap<Class<? extends Event>, CopyOnWriteArrayList<EventContainer>> events = new HashMap<>();
    /** the current priority of module */
    private int priority;
    /** the current enabled state of module */
    private boolean enabled;

    public ModuleContainer(Module moduleAnnotation, Object instance) {
        this(moduleAnnotation.name(), moduleAnnotation.description(), moduleAnnotation.author(),
             moduleAnnotation.category(), moduleAnnotation.aliases(), moduleAnnotation.recommendedServersIP(),
             moduleAnnotation.disallowedServersIP(), moduleAnnotation.priority(), moduleAnnotation.enabled(), instance);
    }

    /**
     * @param name                 the name of module
     * @param description          the description of module usage
     * @param author               the author of module
     * @param category             the {@link Category} for module to be displayed in
     * @param aliases              the aliases or search terms of module
     * @param recommendedServersIP the list of servers module is best compatible with
     * @param disallowedServersIP  the list of servers module is not allowed on
     * @param priority             the default priority of module
     * @param enabled              the default enabled state of module
     * @param instance             the instance of the module class for events to be called on
     */
    public ModuleContainer(String name, String description, String author, Category category, String[] aliases, String[] recommendedServersIP, String[] disallowedServersIP,
                           int priority, boolean enabled, Object instance) {
        this.name                 = name;
        this.description          = description;
        this.author               = author;
        this.category             = category;
        this.aliases              = aliases;
        this.recommendedServersIP = recommendedServersIP;
        this.disallowedServersIP  = disallowedServersIP;
        this.priority             = priority;
        this.enabled              = enabled;
        this.instance             = instance;

        if (this.enabled) Apollo.EVENT_BUS.register(this);
    }

    /** @return If module is currently enabled. */
    @Override
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Set module enabled state to certain value.
     */
    @Override
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        if (this.enabled) {
            Apollo.EVENT_BUS.register(this);
            post(new EnableEvent(this));
        } else {
            Apollo.EVENT_BUS.unRegister(this);
            post(new DisableEvent(this));
        }
    }

    /** @return the actual module class object */
    @Override
    public Object getInstance() {
        return instance;
    }

    /** @return the name of module to displayed in gui list */
    @Override
    public String getName() {
        return this.name;
    }

    /** @return the description of module displayed in gui list. */
    @Override
    public String getDescription() {
        return this.description;
    }

    /** @return the {@link Category} used to section modules. */
    @Override
    public Category getCategory() {
        return this.category;
    }

    /** @return the aliases or search terms people can type instead of module name to find the module. */
    @Override
    public String[] getAliases() {
        return this.aliases;
    }

    /** @return the {@link Priority} of modules events compared to other modules. */
    @Override
    public int getPriority() {
        return this.priority;
    }

    /**
     * Sets priority of current module and sort {@link ModuleFactory} again.
     */
    @Override
    public void setPriority(int priority) {
        this.priority = priority;
        Apollo.MODULE_FACTORY.sortModules();
    }

    /** @return the author of module to be displayed in credits. */
    @Override
    public String getAuthor() {
        return this.author;
    }

    /** @return the list of servers module is best compatible with. */
    @Override
    public String[] getRecommendedServersIPs() {
        return this.recommendedServersIP;
    }

    /** @return the list of servers module is not allowed on. */
    @Override
    public String[] getDisallowedServersIPs() {
        return this.disallowedServersIP;
    }

    /** @return the hashMap of {@link ModuleEvent}s with a list of {@link HandlerEventContainer} */
    @Override
    public HashMap<Class<? extends ModuleEvent>, CopyOnWriteArrayList<HandlerEventContainer>> getHandlers() {
        return handlers;
    }

    /** @return the hashMap of {@link Event}s with a list of {@link EventContainer}s */
    @Override
    public HashMap<Class<? extends Event>, CopyOnWriteArrayList<EventContainer>> getEvents() {
        return events;
    }
}
