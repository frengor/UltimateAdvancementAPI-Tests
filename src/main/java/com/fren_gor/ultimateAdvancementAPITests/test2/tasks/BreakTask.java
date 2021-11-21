package com.fren_gor.ultimateAdvancementAPITests.test2.tasks;

import com.fren_gor.ultimateAdvancementAPI.advancement.display.AdvancementDisplay;
import com.fren_gor.ultimateAdvancementAPI.advancement.display.AdvancementFrameType;
import com.fren_gor.ultimateAdvancementAPI.advancement.tasks.AbstractMultiTasksAdvancement;
import com.fren_gor.ultimateAdvancementAPI.advancement.tasks.TaskAdvancement;
import org.apache.commons.lang.Validate;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

public class BreakTask extends TaskAdvancement {

    public BreakTask(@NotNull String key, @NotNull AbstractMultiTasksAdvancement parent, @Range(from = 1L, to = Integer.MAX_VALUE) int maxCriteria, @NotNull Material block) {
        super(key, new AdvancementDisplay(block, "Break " + maxCriteria + " blocks of " + block, AdvancementFrameType.TASK, false, false, 0, 0), parent, maxCriteria);
        Validate.isTrue(block.isBlock(), "Material " + block + " is not a block.");
        registerEvent(BlockBreakEvent.class, e -> {
            if (e.getBlock().getType() == block) {
                incrementProgression(e.getPlayer());
            }
        });
    }

    @Override
    public void giveReward(@NotNull Player player) {
        player.sendMessage("Done task " + display.getTitle());
    }
}
