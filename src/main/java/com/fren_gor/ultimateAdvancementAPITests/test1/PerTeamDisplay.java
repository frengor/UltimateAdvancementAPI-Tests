package com.fren_gor.ultimateAdvancementAPITests.test1;

import com.fren_gor.ultimateAdvancementAPI.advancement.display.AbstractPerTeamAdvancementDisplay;
import com.fren_gor.ultimateAdvancementAPI.advancement.display.AdvancementDisplay;
import com.fren_gor.ultimateAdvancementAPI.advancement.display.AdvancementFrameType;
import com.fren_gor.ultimateAdvancementAPI.database.TeamProgression;
import com.fren_gor.ultimateAdvancementAPI.nms.wrappers.advancement.PreparedAdvancementDisplayWrapper;
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
    public @NotNull BaseComponent[] getTitleBaseComponent(@NotNull TeamProgression progression) {
        return new ComponentBuilder("Your team id is ")
                .append(String.valueOf(progression.getTeamId()))
                .create();
    }

    @Override
    public @NotNull List<BaseComponent[]> getDescriptionBaseComponent(@NotNull TeamProgression progression) {
        return baseDisplay.getDescriptionBaseComponent();
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

    @Override
    @NotNull
    public PreparedAdvancementDisplayWrapper getNMSWrapper(@NotNull TeamProgression team) {
        try {
            return PreparedAdvancementDisplayWrapper.craft(getIcon(team), getTitle(team), baseDisplay.getCompactDescription(), getFrame(team).getNMSWrapper(), getX(team), getY(team));
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }
}
