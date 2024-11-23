package com.fren_gor.ultimateAdvancementAPITests.test1;

import com.fren_gor.ultimateAdvancementAPI.advancement.display.AbstractPerTeamAdvancementDisplay;
import com.fren_gor.ultimateAdvancementAPI.advancement.display.AdvancementDisplay;
import com.fren_gor.ultimateAdvancementAPI.advancement.display.AdvancementFrameType;
import com.fren_gor.ultimateAdvancementAPI.database.TeamProgression;
import com.fren_gor.ultimateAdvancementAPI.util.AdvancementUtils;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PerTeamDisplay extends AbstractPerTeamAdvancementDisplay {

    private final AdvancementDisplay baseDisplay;

    public PerTeamDisplay(@NotNull AdvancementDisplay baseDisplay) {
        this.baseDisplay = baseDisplay;
    }

    @Override
    public boolean doesShowToast(@NotNull TeamProgression progression) {
        return baseDisplay.doesShowToast();
    }

    @Override
    public boolean doesAnnounceToChat(@NotNull TeamProgression progression) {
        return baseDisplay.doesAnnounceToChat();
    }

    @Override
    public @NotNull ItemStack getIcon(@NotNull TeamProgression progression) {
        return baseDisplay.getIcon();
    }

    @Override
    public @NotNull BaseComponent getTitle(@NotNull TeamProgression progression) {
        return AdvancementUtils.build(new ComponentBuilder("Your team id is ")
                .append(String.valueOf(progression.getTeamId()))
        );
    }

    @Override
    public @NotNull List<BaseComponent> getDescription(@NotNull TeamProgression progression) {
        return baseDisplay.getDescription();
    }

    @Override
    public @NotNull AdvancementFrameType getFrame(@NotNull TeamProgression progression) {
        return baseDisplay.getFrame();
    }

    @Override
    public float getX(@NotNull TeamProgression progression) {
        return baseDisplay.getX();
    }

    @Override
    public float getY(@NotNull TeamProgression progression) {
        return baseDisplay.getY();
    }
}
