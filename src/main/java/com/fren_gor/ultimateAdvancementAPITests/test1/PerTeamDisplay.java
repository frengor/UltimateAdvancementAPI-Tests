package com.fren_gor.ultimateAdvancementAPITests.test1;

import com.fren_gor.ultimateAdvancementAPI.advancement.display.AbstractPerTeamAdvancementDisplay;
import com.fren_gor.ultimateAdvancementAPI.advancement.display.AdvancementDisplay;
import com.fren_gor.ultimateAdvancementAPI.advancement.display.AdvancementFrameType;
import com.fren_gor.ultimateAdvancementAPI.chat.IComponent;
import com.fren_gor.ultimateAdvancementAPI.chat.IDefaultStyle;
import com.fren_gor.ultimateAdvancementAPI.chat.bungeecord.BungeeCordComponent;
import com.fren_gor.ultimateAdvancementAPI.database.TeamProgression;
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
    public IComponent getTitle(@NotNull TeamProgression progression) {
        return BungeeCordComponent.ofLegacy("Your team id is ")
                .append(BungeeCordComponent.ofLegacy(String.valueOf(progression.getTeamId())))
                .append(BungeeCordComponent.ofLegacy(" (" + progression.getSize() + " members)"));
    }

    @Override
    @NotNull
    public List<IComponent> getDescription(@NotNull TeamProgression progression) {
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
    public IDefaultStyle getDefaultTitleStyle(@NotNull TeamProgression progression) {
        return baseDisplay.getDefaultTitleStyle();
    }

    @Override
    @NotNull
    public IDefaultStyle getAnnouncementMessageDefaultTitleStyle(@NotNull TeamProgression progression) {
        return baseDisplay.getAnnouncementMessageDefaultTitleStyle();
    }

    @Override
    @NotNull
    public IDefaultStyle getDefaultDescriptionStyle(@NotNull TeamProgression progression) {
        return baseDisplay.getDefaultDescriptionStyle();
    }

    @Override
    @NotNull
    public IDefaultStyle getAnnouncementMessageDefaultDescriptionStyle(@NotNull TeamProgression progression) {
        return baseDisplay.getAnnouncementMessageDefaultDescriptionStyle();
    }
}
