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

package net.apolloclient.utils.font;

import net.minecraft.client.gui.Gui;

/**
 * Hold information used to store and draw emojis in
 * {@link ApolloFontRenderer}
 *
 * @author Icovid | Icovid#3888
 * @since b0.2
 */
public class Emoji {

    public final int xOffset;
    public final int yOffset;
    public final int width;
    public final int height;

    /**
     * @param xOffset x atlas offset
     * @param yOffset y atlas offset
     * @param width texture width
     * @param height texture height
     */
    public Emoji(int xOffset, int yOffset, int width, int height) {
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.width = width;
        this.height = height;
    }

    /**
     * renders emoji in text line.
     *
     * @param xPosition x position of emoji
     * @param yPosition y position of emoji
     * @param scale scale for emoji
     */
    public void draw(int xPosition, int yPosition, int scale) {
        Gui.drawScaledCustomSizeModalRect(xPosition, yPosition, xOffset, yOffset,
                width, height, getWidth(scale), scale, ApolloFontRenderer.EMOJI_ATLAS_SIZE, ApolloFontRenderer.EMOJI_ATLAS_SIZE);
    }

    /**
     * @param scale emoji height
     * @return width of emoji relative to height and scale
     */
    public int getWidth(int scale) {
        return scale  * (width / height);
    }
}
