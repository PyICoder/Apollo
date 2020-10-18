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

import net.apolloclient.event.bus.EventBus;
import net.apolloclient.event.bus.SubscribeEvent;
import org.spongepowered.asm.mixin.Mixin;

/**
 * Implementation of {@link Event}, allocates a class to be excepted by the
 * {@link EventBus} and used in {@link SubscribeEvent} methods. A class of this
 * type can be called using the {@link #post()} method or by calling {@link EventBus#post(Event)}
 * method with this as the parameter.<p></p>
 *
 * <p>Events of this type can also be canceled by any {@link SubscribeEvent} method
 * however the canceled functionality will need to be implemented on a per event basis as this
 * class only provides the {@link #isCanceled()} boolean. If an event is canceled all other methods
 * set to be called by that event with a lower priority than the current method will be canceled as
 * well unless {@link SubscribeEvent#cancelable()} is set to false on the lower priority method.</p><p></p>
 *
 * <p>To setup an event class and trigger said event in {@link Mixin} ejections, extend this class and
 * populate the class with any get or set values.</p><p></p>
 *
 * <p>Once your event class is created it can be called from anywhere using the before mentioned methods.
 * If an event class has set values those will need to be handled where the event is implemented using a
 * temporary variable and then calling the event using the variable you created. Any changes done to event
 * by any {@link SubscribeEvent} methods will be represented in the variable you posted.</p>
 *
 * @see Event
 *
 * @author Icovid | Icovid#3888
 * @since 1.2.0-BETA
 */
public class EventCancelable extends Event {

    /** canceled state of current event. */
    private boolean canceled = false;

    /**
     * @return canceled state of current event.
     */
    public boolean isCanceled() { return canceled; }

    /**
     * Sets the state of this event, not all events are cancelable.
     * The functionality of setting the canceled state is defined on a per-event bases.
     *
     * @param canceled boolean to set canceled too
     */
    public void setCanceled(boolean canceled) { this.canceled = canceled; }
}
