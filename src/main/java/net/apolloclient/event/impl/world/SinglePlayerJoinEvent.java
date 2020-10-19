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

package net.apolloclient.event.impl.world;

import net.apolloclient.event.Event;
import net.minecraft.client.Minecraft;
import net.minecraft.world.WorldSettings;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Implementation of {@link Event} posted when {@link net.minecraft.client.Minecraft#thePlayer()} joins a single
 * player world using {@link Minecraft#launchIntegratedServer(String, String, WorldSettings)}.
 *
 * @author Icovid | Icovid#3888
 * @see net.apolloclient.mixins.client.MixinMinecraft#joinSinglePlayer(String, String, WorldSettings, CallbackInfo)
 * injection
 * @since 1.2.0-BETA
 */
public class SinglePlayerJoinEvent extends Event {

    /** the name of folder world file is located in */
    public final String folderName;
    /** the name of world */
    public final String worldName;
    /** the {@link WorldSettings} of the world */
    public final WorldSettings worldSettings;

    /**
     * @param folderName    the name of folder world file is located in
     * @param worldName     the name of world
     * @param worldSettings the {@link WorldSettings} of the world
     */
    public SinglePlayerJoinEvent(String folderName, String worldName, WorldSettings worldSettings) {
        this.folderName    = folderName;
        this.worldName     = worldName;
        this.worldSettings = worldSettings;
    }
}
