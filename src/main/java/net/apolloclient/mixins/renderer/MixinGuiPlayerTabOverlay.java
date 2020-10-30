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

package net.apolloclient.mixins.renderer;

import com.google.common.collect.Ordering;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.scoreboard.IScoreObjectiveCriteria;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.WorldSettings;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.awt.*;
import java.util.Iterator;
import java.util.List;

import static net.minecraft.client.gui.Gui.drawRect;

/**
 * {@link Mixin} target injections for events derived from {@link GuiPlayerTabOverlay}.class
 *
 * @author Befell/PyICoder | Befell#3788
 * @see GuiPlayerTabOverlay target
 * @since 1.2.0-BETA
 */
@Mixin(GuiPlayerTabOverlay.class)
public abstract class MixinGuiPlayerTabOverlay {
    @Final
    @Shadow
    private static Ordering<NetworkPlayerInfo> field_175252_a;
    @Final
    @Shadow
    private Minecraft mc;
    @Shadow
    private IChatComponent footer;
    @Shadow
    private IChatComponent header;

    @Shadow
    public abstract String getPlayerName(NetworkPlayerInfo networkPlayerInfoIn);

    /**
     * Overwrites GuiPlayerTabOverlay for {@link net.apolloclient.module.impl.render.CustomTablist}
     *
     * @author Befell/PyICoder| Befell#3788
     * @see GuiPlayerTabOverlay#renderPlayerlist(int, Scoreboard, ScoreObjective) target
     */
    @Overwrite
    public void renderPlayerlist(int width, Scoreboard scoreboardIn, ScoreObjective scoreObjectiveIn) {
        float scaleFactor = 0.90f;
        int x_down = 0;
        int y_down = 0;
        int headerColor = new Color(0, 178, 252, 100).getRGB();
        int bodyColor = new Color(0, 178, 252, 100).getRGB();
        int playersColor = new Color(0, 178, 252, 100).getRGB();
        int footerColor = new Color(0, 178, 252, 100).getRGB();
        // checks for an bossbar and moves the tab down to prevent overlapping (FUTURE FEATURE)
//        if (BossStatus.bossName != null && BossStatus.statusBarTime > 0) {
//            y_down = 16;
//        }
        GlStateManager.scale(scaleFactor, scaleFactor, 0);
        NetHandlerPlayClient nethandlerplayclient = this.mc.thePlayer.sendQueue;
        List<NetworkPlayerInfo> list = field_175252_a.sortedCopy(nethandlerplayclient.getPlayerInfoMap());
        int i = 0;
        int j = 0;
        int y = list.size();
        // removes the npcs from tab (FUTURE FEATURE)
//        list.removeIf(networkPlayerInfo -> networkPlayerInfo.getGameProfile().getId().version() == 2);
        for (NetworkPlayerInfo networkplayerinfo : list) {
            int k = this.mc.fontRendererObj.getStringWidth(this.getPlayerName(networkplayerinfo));
            i = Math.max(i, k);

            if (scoreObjectiveIn != null && scoreObjectiveIn.getRenderType() != IScoreObjectiveCriteria.EnumRenderType.HEARTS) {
                k = this.mc.fontRendererObj.getStringWidth(" " + scoreboardIn.getValueFromObjective(networkplayerinfo.getGameProfile().getName(), scoreObjectiveIn).getScorePoints());
                j = Math.max(j, k);
            }
        }

        list = list.subList(0, Math.min(list.size(), 80));
        int l3 = list.size();
        int i4 = l3;
        int j4;

        for (j4 = 1; i4 > 20; i4 = (l3 + j4 - 1) / j4) {
            ++j4;
        }

        boolean flag = this.mc.isIntegratedServerRunning() || this.mc.getNetHandler().getNetworkManager().getIsencrypted();
        int l;

        if (scoreObjectiveIn != null) {
            if (scoreObjectiveIn.getRenderType() == IScoreObjectiveCriteria.EnumRenderType.HEARTS) {
                l = 90;
            } else {
                l = j;
            }
        } else {
            l = 0;
        }

        int i1 = Math.min(j4 * ((flag ? 9 : 0) + i + l + 13), width - 50) / j4;
        int j1 = width / 2 - (i1 * j4 + (j4 - 1) * 5) / 2;
        int k1 = 10;
        int l1 = i1 * j4 + (j4 - 1) * 5;
        List<String> list1 = null;
        List<String> list2 = null;

        if (this.header != null) {
            list1 = this.mc.fontRendererObj.listFormattedStringToWidth(this.header.getFormattedText(), width - 50);

            for (String s : list1) {
                l1 = Math.max(l1, this.mc.fontRendererObj.getStringWidth(s));
            }
        }

        if (this.footer != null) {
            list2 = this.mc.fontRendererObj.listFormattedStringToWidth(this.footer.getFormattedText(), width - 50);

            for (String s2 : list2) {
                l1 = Math.max(l1, this.mc.fontRendererObj.getStringWidth(s2));
            }
        }
        if (list1 != null) {
            // header
            drawRect((int) ((width / 2 - l1 / 2 - 1) / scaleFactor) + x_down, (int) ((k1 - 1) / scaleFactor) + y_down, (int) ((width / 2 + l1 / 2 + 1) / scaleFactor) + x_down, (int) ((k1 + list1.size() * this.mc.fontRendererObj.FONT_HEIGHT) / scaleFactor) + y_down, headerColor);
            // header text
            for (String s3 : list1) {
                int i2 = this.mc.fontRendererObj.getStringWidth(s3);
                this.mc.fontRendererObj.drawStringWithShadow(s3, ((width / 2 - i2 / 2) / scaleFactor) + x_down, k1 / scaleFactor + y_down, -1);
                k1 += this.mc.fontRendererObj.FONT_HEIGHT;
            }

            ++k1;
        }
        // body
        drawRect((int) ((width / 2 - l1 / 2 - 1) / scaleFactor) + x_down, (int) ((k1 - 1) / scaleFactor) + y_down, (int) ((width / 2 + l1 / 2 + 1) / scaleFactor) + x_down, (int) ((k1 + i4 * 9) / scaleFactor) + y_down, bodyColor);
        for (int k4 = 0; k4 < l3; ++k4) {
            int l4 = k4 / i4;
            int i5 = k4 % i4;
            int j2 = (j1 + l4 * i1 + l4 * 5);
            int k2 = (k1 + i5 * 9);
            // player background
            drawRect((int) (j2 / scaleFactor) + x_down, (int) (k2 / scaleFactor) + y_down, (int) ((j2 + i1) / scaleFactor) + x_down, (int) ((k2 + 8) / scaleFactor) + y_down, playersColor);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.enableAlpha();
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);

            if (k4 < list.size()) {
                NetworkPlayerInfo networkplayerinfo1 = list.get(k4);
                String s1 = this.getPlayerName(networkplayerinfo1);
                GameProfile gameprofile = networkplayerinfo1.getGameProfile();

                if (flag) {
                    EntityPlayer entityplayer = this.mc.theWorld.getPlayerEntityByUUID(gameprofile.getId());
                    boolean flag1 = entityplayer != null && entityplayer.isWearing(EnumPlayerModelParts.CAPE) && (gameprofile.getName().equals("Dinnerbone") || gameprofile.getName().equals("Grumm"));
                    this.mc.getTextureManager().bindTexture(networkplayerinfo1.getLocationSkin());
                    int l2 = 8 + (flag1 ? 8 : 0);
                    int i3 = 8 * (flag1 ? -1 : 1);
                    // player head
                    Gui.drawScaledCustomSizeModalRect((int) (j2 / scaleFactor) + x_down, (int) (k2 / scaleFactor) + y_down, 8.0F, (float) l2, 8, i3, 8, 8, 64.0F, 64.0F);

                    if (entityplayer != null && entityplayer.isWearing(EnumPlayerModelParts.HAT)) {
                        int j3 = 8 + (flag1 ? 8 : 0);
                        int k3 = 8 * (flag1 ? -1 : 1);
                        // player head overlay
                        Gui.drawScaledCustomSizeModalRect((int) (j2 / scaleFactor) + x_down, (int) (k2 / scaleFactor) + y_down, 40.0F, (float) j3, 8, k3, 8, 8, 64.0F, 64.0F);
                    }

                    j2 += 9;
                }
                // spectator name
                if (networkplayerinfo1.getGameType() == WorldSettings.GameType.SPECTATOR) {
                    s1 = EnumChatFormatting.ITALIC + s1;
                    this.mc.fontRendererObj.drawStringWithShadow(s1, j2 / scaleFactor + x_down, k2 / scaleFactor + y_down, -1862270977);
                } else {
                    this.mc.fontRendererObj.drawStringWithShadow(s1, j2 / scaleFactor + x_down, k2 / scaleFactor + y_down, -1);
                }

                if (scoreObjectiveIn != null && networkplayerinfo1.getGameType() != WorldSettings.GameType.SPECTATOR) {
                    int k5 = j2 + i + 1;
                    int l5 = k5 + l;

                    if (l5 - k5 > 5) {
                        this.drawScoreboardValues(scoreObjectiveIn, (int) (k2 / scaleFactor) + y_down, gameprofile.getName(), (int) (k5 / scaleFactor) + x_down, l5, networkplayerinfo1);
                    }
                }
                // ping
                this.drawPing((int) (i1 / scaleFactor) + x_down, (int) ((j2 - (flag ? 9 : 0)) / scaleFactor) + x_down, (int) (k2 / scaleFactor) + y_down, networkplayerinfo1);
            }
        }

        if (list2 != null) {
            k1 = k1 + i4 * 9 + 1;
            // footer
            drawRect((int) ((width / 2 - l1 / 2 - 1) / scaleFactor) + x_down, (int) ((k1 - 1) / scaleFactor) + y_down, (int) ((width / 2 + l1 / 2 + 1) / scaleFactor) + x_down, (int) ((k1 + list2.size() * this.mc.fontRendererObj.FONT_HEIGHT) / scaleFactor) + y_down, footerColor);
            // footer text
            for (String s4 : list2) {
                int j5 = this.mc.fontRendererObj.getStringWidth(s4);
                this.mc.fontRendererObj.drawStringWithShadow(s4, (width / 2 - j5 / 2) / scaleFactor + x_down, k1 / scaleFactor + y_down, -1);
                k1 += this.mc.fontRendererObj.FONT_HEIGHT;
            }
        }
    }

    @Shadow
    protected abstract void drawScoreboardValues(ScoreObjective p_175247_1_, int p_175247_2_, String p_175247_3_, int p_175247_4_, int p_175247_5_, NetworkPlayerInfo p_175247_6_);

    @Shadow
    protected abstract void drawPing(int x, int x2, int y, NetworkPlayerInfo networkPlayerInfoIn);
}
