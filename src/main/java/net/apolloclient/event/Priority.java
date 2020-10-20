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

package net.apolloclient.event;

import net.apolloclient.command.Command;
import net.apolloclient.command.CommandBus;
import net.apolloclient.event.bus.EventBus;
import net.apolloclient.event.bus.SubscribeEvent;
import net.apolloclient.module.bus.EventHandler;
import net.apolloclient.module.bus.Module;
import net.apolloclient.module.bus.ModuleFactory;

import java.util.Comparator;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Public enum for storing {@link SubscribeEvent}, {@link EventHandler} and {@link Command} priority
 * data used by the {@link EventBus}, {@link ModuleFactory} and {@link CommandBus} to invoke methods
 * in order of {@link #HIGH}, {@link #NORMAL}, and then {@link #LOW}.<p></p>
 *
 * <p>Events are sorted based on the {@link #id} integer using {@link CopyOnWriteArrayList#sort(Comparator)}
 * and is defaulted to {@code 1}. Event Priority is different then {@link Module#priority()} as modules can
 * have any priority integer but events are one of 0-2.</p>
 *
 * @author Icovid | Icovid#3888
 * @since 1.2.0-BETA
 */
public enum Priority {

    HIGH(0),
    NORMAL(1),
    LOW(2);

    /** number used for event sorting */
    public final int id;

    /**
     * @param id number used for event sorting
     */
    Priority(int id) {
        this.id = id;
    }
}
