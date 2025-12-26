package com.fren_gor.ultimateAdvancementAPITests.test1;

import com.fren_gor.ultimateAdvancementAPI.advancement.display.AbstractPerTeamAdvancementDisplay;
import com.fren_gor.ultimateAdvancementAPI.advancement.display.AdvancementDisplay;
import com.fren_gor.ultimateAdvancementAPI.advancement.display.AdvancementFrameType;
import com.fren_gor.ultimateAdvancementAPI.database.TeamProgression;
import com.fren_gor.ultimateAdvancementAPI.util.AdvancementUtils;
import com.fren_gor.ultimateAdvancementAPI.util.display.DefaultStyle;
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
    @NotNull
    public ItemStack getIcon(@NotNull TeamProgression progression) {
        return baseDisplay.getIcon();
    }

    @Override
    @NotNull
    public BaseComponent getTitle(@NotNull TeamProgression progression) {
        return AdvancementUtils.build(new ComponentBuilder("Your team id is ")
                .append(String.valueOf(progression.getTeamId()))
        );
    }

    @Override
    @NotNull
    public List<BaseComponent> getDescription(@NotNull TeamProgression progression) {
        return baseDisplay.getDescription();
    }

    @Override
    @NotNull
    public AdvancementFrameType getFrame(@NotNull TeamProgression progression) {
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
    public DefaultStyle getDefaultTitleStyle(@NotNull TeamProgression progression) {
        return baseDisplay.getDefaultTitleStyle();
    }

    @Override
    @NotNull
    public DefaultStyle getAnnouncementMessageDefaultTitleStyle(@NotNull TeamProgression progression) {
        return baseDisplay.getAnnouncementMessageDefaultTitleStyle();
    }

    @Override
    @NotNull
    public DefaultStyle getDefaultDescriptionStyle(@NotNull TeamProgression progression) {
        return baseDisplay.getDefaultDescriptionStyle();
    }

    @Override
    @NotNull
    public DefaultStyle getAnnouncementMessageDefaultDescriptionStyle(@NotNull TeamProgression progression) {
        return baseDisplay.getAnnouncementMessageDefaultDescriptionStyle();
    }
}
