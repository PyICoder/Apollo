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

package net.apolloclient.gui;

import net.apolloclient.utils.font.ApolloFont;
import net.apolloclient.utils.font.ApolloFontRenderer;
import net.minecraft.client.gui.GuiScreen;

import java.awt.*;

public class guitest extends GuiScreen {

    public static ApolloFontRenderer fontRenderer;

    @Override
    public void initGui() {
        fontRenderer = ApolloFontRenderer.create(ApolloFont.ARIAL_REGULAR, 20);
    }

    @Override
    public void drawScreen(int p_drawScreen_1_, int p_drawScreen_2_, float p_drawScreen_3_) {

        fontRenderer.drawString(
                "§!Class aptent §rtaciti sociosqu :hypixel_crush: ad litora torquent per" + '\n' +
                        "conubia nostra, §!§kApolloFont himenaeos. Nam nec ante. §r"  + '\n' +
                        "§!Sed :bacon: lacinia, urna non :a: tincidunt :yellow_circle: mattis, tortor neque"  + '\n' +
                        ":green_circle: adipiscing diam, a cursus ipsum ante quis turpis. Nulla facilisi."  + '\n' +
                        "Ut fringilla. :pepe_kek_pointing: Suspendisse potenti. :c: §lNunc feugiat mi a tellus consequat"  + '\n' +
                        "Proin quam. Etiam ultrices. Suspendisse in justo §meu magna luctus suscipit. :grinning_face:" , 100, 100, Color.WHITE, true
        );
    }
}
