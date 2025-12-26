package com.fren_gor.ultimateAdvancementAPITests.test2;

import com.fren_gor.ultimateAdvancementAPI.advancement.Advancement;
import com.fren_gor.ultimateAdvancementAPI.advancement.display.AdvancementDisplay;
import com.fren_gor.ultimateAdvancementAPI.advancement.tasks.MultiTasksAdvancement;
import com.fren_gor.ultimateAdvancementAPI.announcementMessage.FancyAnnouncementMessage;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

public class Test2MultiTask extends MultiTasksAdvancement implements FancyAnnouncementMessage {

    public Test2MultiTask(@NotNull Advancement parent, @NotNull String key, @Range(from = 1L, to = 2147483647L) int maxCriteria, @NotNull AdvancementDisplay display) {
        super(parent, key, maxCriteria, display);
    }

    @Override
    public void giveReward(@NotNull Player player) {
        player.sendMessage("Tasks done");
        player.getInventory().addItem(display.dispatchGetIcon(player, advancementTab));
    }
}
