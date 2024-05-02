package com.fren_gor.testUltimateAdvancementAPI.test1;

import com.fren_gor.ultimateAdvancementAPI.advancement.BaseAdvancement;
import com.fren_gor.ultimateAdvancementAPI.advancement.display.AdvancementDisplay;
import com.fren_gor.ultimateAdvancementAPI.visibilities.VanillaVisibility;
import org.jetbrains.annotations.NotNull;

public class MultiParentVanillaVisibility extends MultiParent implements VanillaVisibility {
    public MultiParentVanillaVisibility(@NotNull String key, @NotNull AdvancementDisplay display, int maxCriteria, @NotNull BaseAdvancement... parents) {
        super(key, display, maxCriteria, parents);
    }
}
