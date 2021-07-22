package com.fren_gor.ultimateAdvancementAPITests.test1;

import com.fren_gor.ultimateAdvancementAPI.AdvancementDisplay;
import com.fren_gor.ultimateAdvancementAPI.advancement.BaseAdvancement;
import com.fren_gor.ultimateAdvancementAPI.advancement.multiParents.MultiParentsAdvancement;
import com.fren_gor.ultimateAdvancementAPI.visibilities.ParentGrantedVisibility;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class MultiParent extends MultiParentsAdvancement implements ParentGrantedVisibility {
    public MultiParent(@NotNull String key, @NotNull AdvancementDisplay display, int maxCriteria, @NotNull BaseAdvancement... parents) {
        super(key, display, maxCriteria, parents);
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
