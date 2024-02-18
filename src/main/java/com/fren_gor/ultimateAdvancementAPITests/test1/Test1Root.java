package com.fren_gor.ultimateAdvancementAPITests.test1;

import com.fren_gor.ultimateAdvancementAPI.AdvancementTab;
import com.fren_gor.ultimateAdvancementAPI.advancement.RootAdvancement;
import com.fren_gor.ultimateAdvancementAPI.advancement.display.AdvancementDisplay;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class Test1Root extends RootAdvancement {

    public Test1Root(@NotNull AdvancementTab advancementTab, @NotNull String key, @NotNull AdvancementDisplay display) {
        super(advancementTab, key, display);
    }

    public Test1Root(@NotNull AdvancementTab advancementTab, @NotNull String key, @NotNull AdvancementDisplay display, int maxCriteria) {
        super(advancementTab, key, display, maxCriteria);
    }

    @Override
    public void giveReward(@NotNull Player player) {
        player.getInventory().addItem(new ItemStack(Material.NETHER_STAR));
    }
}
