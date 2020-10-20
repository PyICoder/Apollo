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

package net.apolloclient.mixins.client;

import net.apolloclient.Apollo;
import net.apolloclient.event.impl.client.GameLoopEvent;
import net.apolloclient.event.impl.client.input.KeyPressedEvent;
import net.apolloclient.event.impl.client.input.KeyReleasedEvent;
import net.apolloclient.event.impl.client.input.LeftClickEvent;
import net.apolloclient.event.impl.client.input.RightClickEvent;
import net.apolloclient.event.impl.hud.GuiSwitchEvent;
import net.apolloclient.event.impl.world.LoadWorldEvent;
import net.apolloclient.event.impl.world.SinglePlayerJoinEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.world.WorldSettings;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * {@link Mixin} target injections for events derived from {@link Minecraft}.class
 * and {@link Apollo} startup methods
 *
 * @author Icovid | Icovid#3888
 * @see Minecraft target
 * @since 1.2.0-BETA
 */
@Mixin(Minecraft.class)
public class MixinMinecraft {

    /**
     * Changes display window title to {@link Apollo#NAME} with {@link Apollo#VERSION}
     *
     * @param callbackInfo unused variable to cancel remaining method
     *
     * @see Minecraft#createDisplay() target
     */
    @Inject(method = "createDisplay", at = @At("RETURN"))
    public void createDisplay(CallbackInfo callbackInfo) {
        Display.setTitle(Apollo.NAME + " " + Apollo.VERSION);
    }

    /**
     * Initiates {@link Apollo#initialization()} when game is started
     *
     * @param callbackInfo unused variable to cancel remaining method
     *
     * @see Minecraft#startGame() target
     */
    @Inject(method = "startGame", at = @At("HEAD"))
    private void onGameStart(CallbackInfo callbackInfo) {
        Apollo.INSTANCE.initialization();
    }

    /**
     * Invokes {@link Apollo#postInitialization()} at the return statement of game start
     *
     * @param callbackInfo unused variable to cancel remaining method
     *
     * @see Minecraft#startGame() target
     */
    @Inject(method = "startGame", at = @At("RETURN"))
    private void onGameStartPost(CallbackInfo callbackInfo) {
        Apollo.INSTANCE.postInitialization();
    }

    /**
     * Invokes {@link Apollo#stopClient()} when game is shut down
     *
     * @param callbackInfo unused variable to cancel remaining method
     *
     * @see Minecraft#shutdown() target
     */
    @Inject(method = "shutdown", at = @At("HEAD"))
    private void shutdown(CallbackInfo callbackInfo) {
        Apollo.INSTANCE.stopClient();
    }

    /**
     * Post a new {@link GameLoopEvent} every game tick
     *
     * @param callbackInfo unused variable to cancel remaining method
     *
     * @author Nora Cos | #Nora#0001
     * @see Minecraft#runGameLoop() target
     */
    @Inject(method = "runGameLoop", at = @At(value = "FIELD", target = "Lnet/minecraft/client/Minecraft;" +
            "skipRenderWorld:Z", shift = At.Shift.AFTER))
    private void runGameLoop(CallbackInfo callbackInfo) {
        new GameLoopEvent().post();
    }

    /**
     * Post a new {@link KeyPressedEvent} or {@link KeyReleasedEvent} on
     * each key dispatch
     *
     * @param callbackInfo unused variable to cancel remaining method
     *
     * @see Minecraft#dispatchKeypresses() target
     */
    @Inject(method = "dispatchKeypresses", at = @At(value = "HEAD"))
    private void runTickKeyboard(CallbackInfo callbackInfo) {
        Apollo.EVENT_BUS.post(
                Keyboard.getEventKeyState()
                        ? new KeyPressedEvent(Keyboard.isRepeatEvent(), Keyboard.getEventKey())
                        : new KeyReleasedEvent(Keyboard.isRepeatEvent(), Keyboard.getEventKey()));
    }

    /**
     * Post a new {@link LeftClickEvent} each time left mouse button
     * is pressed
     *
     * @param callbackInfo unused variable to cancel remaining method
     *
     * @see Minecraft#clickMouse() target
     */
    @Inject(method = "clickMouse", at = @At("RETURN"))
    private void leftClickMouse(CallbackInfo callbackInfo) { new LeftClickEvent().post(); }

    /**
     * Post a new {@link RightClickEvent} each time right mouse button
     * is pressed
     *
     * @param callbackInfo unused variable to cancel remaining method
     *
     * @see Minecraft#rightClickMouse() target
     */
    @Inject(method = "rightClickMouse", at = @At("RETURN"))
    private void rightClickMouse(CallbackInfo callbackInfo) {
        new RightClickEvent().post();
    }

    /**
     * Post a new {@link SinglePlayerJoinEvent} when joining single player world.
     *
     * @param folderName      the name of folder world file is located in.
     * @param worldName       the name of the world
     * @param worldSettingsIn the settings of the world
     * @param callbackInfo    unused variable to cancel remaining method
     *
     * @see Minecraft#launchIntegratedServer(String, String, WorldSettings) target
     */
    @Inject(method = "launchIntegratedServer", at = @At("HEAD"))
    private void joinSinglePlayer(String folderName, String worldName, WorldSettings worldSettingsIn,
                                  CallbackInfo callbackInfo) {
        new SinglePlayerJoinEvent(folderName, worldName, worldSettingsIn).post();
    }

    /**
     * Post {@link LoadWorldEvent} when new world is loaded for player.
     *
     * @param worldClient  the world client on new world.
     * @param callbackInfo unused variable to cancel remaining method
     *
     * @see Minecraft#loadWorld(WorldClient)  target
     */
    @Inject(method = "loadWorld(Lnet/minecraft/client/multiplayer/WorldClient;)V", at = @At("HEAD"))
    private void loadWorld(WorldClient worldClient, CallbackInfo callbackInfo) {
        new LoadWorldEvent(worldClient).post();
    }

    /**
     * Post {@link GuiSwitchEvent} when current {@link GuiScreen} is changed.
     *
     * @param guiScreenIn  new {@link GuiScreen} displayed
     * @param callbackInfo unused variable to cancel remaining method
     *
     * @see Minecraft#displayGuiScreen(GuiScreen) target
     */
    @Inject(method = "displayGuiScreen", at = @At("HEAD"))
    private void displayGuiScreen(GuiScreen guiScreenIn, CallbackInfo callbackInfo) {
        new GuiSwitchEvent(guiScreenIn).post();
    }
}
