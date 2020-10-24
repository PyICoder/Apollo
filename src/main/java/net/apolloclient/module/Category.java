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

/**
 * Container enums to categorize modules by what they do.
 *
 * <p>Descriptions or unused in client and are only here to stay organized
 * and provide a usage case for each category.</p>
 *
 * @author Icovid | Icovid#3888
 * @since 1.2.0-BETA
 */
public enum Category {

    VISUAL("Modules that offer a visual enhancement to the game."),
    UTIL("Modules that improves minecraft or the client itself or fixes a minor inconvenience"),
    PLAYER("Modules that offer playing enhancements to the player or players activities."),
    GAMEPLAY("Modules that improve gameplay on Hypixel or any other server.");

    /** description of category usage. */
    public final String description;

    /**
     * @param description of category usage.
     */
    Category(String description) { this.description = description; }
}
