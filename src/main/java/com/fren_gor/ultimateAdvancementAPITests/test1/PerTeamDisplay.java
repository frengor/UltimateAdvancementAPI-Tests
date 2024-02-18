package com.fren_gor.ultimateAdvancementAPITests.test1;

import com.fren_gor.ultimateAdvancementAPI.advancement.display.AbstractPerTeamAdvancementDisplay;
import com.fren_gor.ultimateAdvancementAPI.advancement.display.AdvancementDisplay;
import com.fren_gor.ultimateAdvancementAPI.advancement.display.AdvancementFrameType;
import com.fren_gor.ultimateAdvancementAPI.database.TeamProgression;
import com.fren_gor.ultimateAdvancementAPI.nms.wrappers.advancement.PreparedAdvancementDisplayWrapper;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

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
        return baseDisplay.getTitleBaseComponent();
    }

    @Override
    public @Unmodifiable List<BaseComponent[]> getDescriptionBaseComponent(@NotNull TeamProgression progression) {
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
    public boolean doesShowToast() {
        return baseDisplay.doesShowToast();
    }

    @Override
    public boolean doesAnnounceToChat() {
        return baseDisplay.doesAnnounceToChat();
    }

    @Override
    public @NotNull ItemStack getIcon() {
        return baseDisplay.getIcon();
    }

    @Override
    public @NotNull String getTitle(@NotNull TeamProgression progression) {
        return "Your team id is " + progression.getTeamId();
    }

    @Override
    public @NotNull BaseComponent[] getTitleBaseComponent() {
        return baseDisplay.getTitleBaseComponent();
    }

    @Override
    public @Unmodifiable List<BaseComponent[]> getDescriptionBaseComponent() {
        return baseDisplay.getDescriptionBaseComponent();
    }

    @Override
    public @NotNull AdvancementFrameType getFrame() {
        return baseDisplay.getFrame();
    }

    @Override
    public float getX() {
        return baseDisplay.getX();
    }

    @Override
    public float getY() {
        return baseDisplay.getY();
    }

    @Override
    public @NotNull PreparedAdvancementDisplayWrapper getNMSWrapper() {
        return baseDisplay.getNMSWrapper();
    }

    @Override
    @NotNull
    public PreparedAdvancementDisplayWrapper getNMSWrapper(@NotNull TeamProgression team) {
        try {
            return PreparedAdvancementDisplayWrapper.craft(getIcon(), getTitle(team), baseDisplay.getCompactDescription(), getFrame().getNMSWrapper(), getX(), getY());
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }
}
