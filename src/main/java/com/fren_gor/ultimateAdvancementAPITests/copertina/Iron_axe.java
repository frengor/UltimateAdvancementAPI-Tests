package com.fren_gor.ultimateAdvancementAPITests.copertina;

import com.fren_gor.ultimateAdvancementAPI.advancement.BaseAdvancement;
import com.fren_gor.ultimateAdvancementAPI.advancement.display.AdvancementDisplay;
import com.fren_gor.ultimateAdvancementAPI.advancement.multiParents.MultiParentsAdvancement;
import org.jetbrains.annotations.NotNull;

public class Iron_axe extends MultiParentsAdvancement {

    public Iron_axe(@NotNull String key, @NotNull AdvancementDisplay display, @NotNull BaseAdvancement... parents) {
        super(key, display, parents);
    }
}
