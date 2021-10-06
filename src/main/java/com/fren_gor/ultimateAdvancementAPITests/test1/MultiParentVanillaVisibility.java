package com.fren_gor.ultimateAdvancementAPITests.test1;

import com.fren_gor.ultimateAdvancementAPI.advancement.Advancement;
import com.fren_gor.ultimateAdvancementAPI.advancement.BaseAdvancement;
import com.fren_gor.ultimateAdvancementAPI.advancement.display.AdvancementDisplay;
import com.fren_gor.ultimateAdvancementAPI.database.TeamProgression;
import com.fren_gor.ultimateAdvancementAPI.visibilities.VanillaVisibility;
import org.jetbrains.annotations.NotNull;

public class MultiParentVanillaVisibility extends MultiParent implements VanillaVisibility {
    public MultiParentVanillaVisibility(@NotNull String key, @NotNull AdvancementDisplay display, int maxCriteria, @NotNull BaseAdvancement... parents) {
        super(key, display, maxCriteria, parents);
    }

    @Override
    public boolean isVisible(@NotNull Advancement advancement, @NotNull TeamProgression progression) {
        return VanillaVisibility.super.isVisible(advancement, progression);
    }
}
