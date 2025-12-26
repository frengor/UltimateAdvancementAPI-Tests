package com.fren_gor.ultimateAdvancementAPITests.test2.tasks;

import com.fren_gor.ultimateAdvancementAPI.advancement.display.AdvancementDisplayBuilder;
import com.fren_gor.ultimateAdvancementAPI.advancement.tasks.AbstractMultiTasksAdvancement;
import com.fren_gor.ultimateAdvancementAPI.advancement.tasks.TaskAdvancement;
import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

public class BreakTask extends TaskAdvancement {

    public BreakTask(@NotNull String key, @NotNull AbstractMultiTasksAdvancement multiTask, @Range(from = 1L, to = Integer.MAX_VALUE) int maxCriteria, @NotNull Material block) {
        super(multiTask, key, maxCriteria, new AdvancementDisplayBuilder(block, "Break " + maxCriteria + " blocks of " + block).taskFrame().x(0).y(0).build());
        Preconditions.checkArgument(block.isBlock(), "Material " + block + " is not a block.");
        registerEvent(BlockBreakEvent.class, e -> {
            if (e.getBlock().getType() == block) {
                incrementProgression(e.getPlayer());
            }
        });
    }

    @Override
    public void giveReward(@NotNull Player player) {
        player.sendMessage("Done task " + display.dispatchGetLegacyTitle(player, advancementTab));
    }
}
