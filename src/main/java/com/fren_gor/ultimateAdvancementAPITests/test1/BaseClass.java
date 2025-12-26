package com.fren_gor.ultimateAdvancementAPITests.test1;

import com.fren_gor.ultimateAdvancementAPI.advancement.Advancement;
import com.fren_gor.ultimateAdvancementAPI.advancement.BaseAdvancement;
import com.fren_gor.ultimateAdvancementAPI.advancement.display.AbstractAdvancementDisplay;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

public abstract class BaseClass extends BaseAdvancement {

    public BaseClass(@NotNull Advancement parent, @NotNull String key, @NotNull AbstractAdvancementDisplay display) {
        super(parent, key, display);
    }

    public BaseClass(@NotNull Advancement parent, @NotNull String key, @Range(from = 1L, to = 2147483647L) int maxCriteria, @NotNull AbstractAdvancementDisplay display) {
        super(parent, key, maxCriteria, display);
    }
}
