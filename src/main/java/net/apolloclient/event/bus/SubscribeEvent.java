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

import net.apolloclient.event.*;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Allocates a method for the {@link EventBus}. Any method with this annotation
 * must have a single parameter of type {@link Event} and  will receive all posted events.<p></p>
 *
 * <p>Any method using this annotation can also use its event parameter to change any set values and
 * get values of the {@link Event} object set to the parameter.</p><p></p>
 *
 * <p>The following settings also can be changed to adjust how the event method behaves such as
 * when the method is called and if its called at all in case of
 * {@link EventCancelable#setCanceled(boolean)}</p>
 *
 * <ul>
 *  <li>{@link #priority} : priority of event methods over other event methods</li>
 *  <li>{@link #cancelable()} : boolean if event method is still called if event is canceled</li>
 * </ul>
 *
 * @author Icovid | Icovid#3888
 * @since 1.2.0-BETA
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.METHOD)
public @interface SubscribeEvent {

    /**
     * @return priority of event methods over other event methods.
     */
    Priority priority() default Priority.NORMAL;

    /**
     * @return weather event method is still called if event is canceled.
     */
    boolean cancelable() default true;
}
