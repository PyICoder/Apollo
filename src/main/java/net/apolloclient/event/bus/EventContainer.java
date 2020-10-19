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

import net.apolloclient.event.Priority;
import net.apolloclient.module.bus.EventHandler;

import java.lang.reflect.Method;

/**
 * Extension of {@link HandlerEventContainer} used to hold information gathered by
 * the {@link SubscribeEvent} annotation by the {@link EventBus} and to invoke methods
 * using an event parameter. Contains a {@link #cancelable} boolean to include the
 * {@link SubscribeEvent#cancelable()} functionality that the {@link EventHandler} lacks
 *
 * <p>To invoke an {@link EventContainer} use an instance of the objects {@link #invoke(Object...)}
 * method with a single event parameter.</p>
 *
 * @author Icovid | Icovid#3888
 * @since 1.2.0-BETA
 */
public class EventContainer extends HandlerEventContainer {

    /** If event method is still called if event is canceled */
    public final boolean cancelable;

    /**
     * Creates a new {@link EventContainer} instance with the given information.
     *
     * @param instance instance of the object to invoke methods on
     * @param method method to invoke with event parameter
     * @param priority priority of event method over other event methods
     * @param triggerCanceled If event method is still called if event is canceled.
     */
    public EventContainer(Object instance, Method method, Priority priority, boolean triggerCanceled) {
        super(instance, method, priority);
        this.cancelable = triggerCanceled;
    }
}
