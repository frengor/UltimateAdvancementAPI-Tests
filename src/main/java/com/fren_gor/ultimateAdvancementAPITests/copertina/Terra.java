package com.fren_gor.ultimateAdvancementAPITests.copertina;

import com.fren_gor.ultimateAdvancementAPI.AdvancementTab;
import com.fren_gor.ultimateAdvancementAPI.advancement.RootAdvancement;
import com.fren_gor.ultimateAdvancementAPI.advancement.display.AdvancementDisplay;
import org.jetbrains.annotations.NotNull;

public class Terra extends RootAdvancement {
    public Terra(@NotNull AdvancementTab advancementTab, @NotNull String key, @NotNull AdvancementDisplay display, @NotNull String backgroundTexture) {
        super(advancementTab, key, display, backgroundTexture);
    }
}
