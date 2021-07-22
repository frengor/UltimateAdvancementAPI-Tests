package com.fren_gor.ultimateAdvancementAPITests.test1;

import com.fren_gor.ultimateAdvancementAPI.AdvancementDisplay;
import com.fren_gor.ultimateAdvancementAPI.AdvancementTab;
import com.fren_gor.ultimateAdvancementAPI.advancement.Advancement;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class Test1Advancement extends BaseClass {

    public Test1Advancement(@NotNull String key, @NotNull AdvancementDisplay display, @NotNull Advancement parent) {
        this(key, display, parent, 1);
    }

    public Test1Advancement(@NotNull String key, @NotNull AdvancementDisplay display, @NotNull Advancement parent, int maxCriteria) {
        super(key, display, parent, maxCriteria);
        registerEvent(BlockBreakEvent.class, e -> {
            if (e.getBlock().getType() == display.getIcon().getType()) {
                incrementTeamCriteria(e.getPlayer());
            }
        });
    }

    @Override
    public void giveReward(@NotNull Player player) {
        player.getInventory().addItem(new ItemStack(display.getIcon()));
    }

}
