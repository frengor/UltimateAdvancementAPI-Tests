package com.fren_gor.ultimateAdvancementAPITests.test1;

import com.fren_gor.ultimateAdvancementAPI.advancement.Advancement;
import com.fren_gor.ultimateAdvancementAPI.advancement.display.AdvancementDisplay;
import org.jetbrains.annotations.NotNull;

public class Test1AdvancementCustomAM extends Test1Advancement implements TestAnnounceMessage {

    public Test1AdvancementCustomAM(@NotNull String key, @NotNull AdvancementDisplay display, @NotNull Advancement parent) {
        super(key, display, parent);
    }

    public Test1AdvancementCustomAM(@NotNull String key, @NotNull AdvancementDisplay display, @NotNull Advancement parent, int maxCriteria) {
        super(key, display, parent, maxCriteria);
    }
}
