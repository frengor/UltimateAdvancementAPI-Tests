package com.fren_gor.ultimateAdvancementAPITests.test2;

import com.fren_gor.ultimateAdvancementAPI.AdvancementDisplay;
import com.fren_gor.ultimateAdvancementAPI.AdvancementTab;
import com.fren_gor.ultimateAdvancementAPI.advancement.Advancement;
import com.fren_gor.ultimateAdvancementAPI.advancement.MultiTasksAdvancement;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

public class Test2MultiTask extends MultiTasksAdvancement {

    public Test2MultiTask(@NotNull AdvancementTab advancementTab, @NotNull String key, @NotNull AdvancementDisplay display, @NotNull Advancement parent, @Range(from = 1L, to = 2147483647L) int maxCriteria) {
        super(advancementTab, key, display, parent, maxCriteria);
    }

    @Override
    public void giveReward(@NotNull Player player) {
        player.sendMessage("Tasks done");
        player.getInventory().addItem(display.getIcon());
    }
}
