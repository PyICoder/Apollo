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

package net.apolloclient.event.impl.client;

import net.apolloclient.event.EventCancelable;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.util.IChatComponent;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Implementation of {@link EventCancelable} posted when a chat packet is received by the player that is not an
 * instance of {@link S02PacketChat#type} 2.<p></p>
 *
 * <p>Canceling this event will result in the {@link IChatComponent} not rendering. </p>
 *
 * @author Nora Cos | Nora#0001
 * @see net.apolloclient.mixins.network.MixinNetHandlerPlayClient#onChatPacket(S02PacketChat, CallbackInfo) injection
 * @since 1.2.0-BETA
 */
public class ChatReceivedEvent extends EventCancelable {

    /** the {@link IChatComponent} collected from current {@link S02PacketChat} */
    public final IChatComponent chatComponent;

    /**
     * @param chatComponent the {@link IChatComponent} collected from current {@link S02PacketChat}
     */
    public ChatReceivedEvent(IChatComponent chatComponent) {
        this.chatComponent = chatComponent;
    }
}
