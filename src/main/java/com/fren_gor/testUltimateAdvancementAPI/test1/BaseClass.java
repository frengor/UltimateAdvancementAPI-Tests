package com.fren_gor.testUltimateAdvancementAPI.test1;

import com.fren_gor.ultimateAdvancementAPI.advancement.Advancement;
import com.fren_gor.ultimateAdvancementAPI.advancement.BaseAdvancement;
import com.fren_gor.ultimateAdvancementAPI.advancement.display.AdvancementDisplay;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

public abstract class BaseClass extends BaseAdvancement {

    public BaseClass(@NotNull String key, @NotNull AdvancementDisplay display, @NotNull Advancement parent) {
        super(key, display, parent);
    }

    public BaseClass(@NotNull String key, @NotNull AdvancementDisplay display, @NotNull Advancement parent, @Range(from = 1L, to = 2147483647L) int maxCriteria) {
        super(key, display, parent, maxCriteria);
    }
}
