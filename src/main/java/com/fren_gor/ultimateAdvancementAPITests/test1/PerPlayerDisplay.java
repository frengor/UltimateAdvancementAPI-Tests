package com.fren_gor.ultimateAdvancementAPITests.test1;

import com.fren_gor.ultimateAdvancementAPI.advancement.display.AbstractPerPlayerAdvancementDisplay;
import com.fren_gor.ultimateAdvancementAPI.advancement.display.AdvancementDisplay;
import com.fren_gor.ultimateAdvancementAPI.advancement.display.AdvancementFrameType;
import com.fren_gor.ultimateAdvancementAPI.chat.IComponent;
import com.fren_gor.ultimateAdvancementAPI.chat.IDefaultStyle;
import com.fren_gor.ultimateAdvancementAPI.chat.bungeecord.BungeeCordComponent;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PerPlayerDisplay extends AbstractPerPlayerAdvancementDisplay {

    private final AdvancementDisplay baseDisplay;

    public PerPlayerDisplay(@NotNull AdvancementDisplay baseDisplay) {
        this.baseDisplay = baseDisplay;
    }

    @Override
    public boolean doesShowToast(@NotNull OfflinePlayer player) {
        return baseDisplay.doesShowToast();
    }

    @Override
    public boolean doesAnnounceToChat(@NotNull OfflinePlayer player) {
        return baseDisplay.doesAnnounceToChat();
    }

    @Override
    @NotNull
    public ItemStack getIcon(@NotNull OfflinePlayer player) {
        return baseDisplay.getIcon();
    }

    @Override
    @NotNull
    public IComponent getTitle(@NotNull OfflinePlayer player) {
        return BungeeCordComponent.of("Your name is ").append(BungeeCordComponent.of(player.getName()));
    }

    @Override
    public @NotNull List<IComponent> getDescription(@NotNull OfflinePlayer player) {
        return baseDisplay.getDescription();
    }

    @Override
    public @NotNull AdvancementFrameType getFrame(@NotNull OfflinePlayer player) {
        return baseDisplay.getFrame();
    }

    @Override
    public float getX(@NotNull OfflinePlayer player) {
        return baseDisplay.getX();
    }

    @Override
    public float getY(@NotNull OfflinePlayer player) {
        return baseDisplay.getY();
    }

    @Override
    @NotNull
    public IDefaultStyle getDefaultTitleStyle(@NotNull OfflinePlayer player) {
        return baseDisplay.getDefaultTitleStyle();
    }

    @Override
    @NotNull
    public IDefaultStyle getAnnouncementMessageDefaultTitleStyle(@NotNull OfflinePlayer player) {
        return baseDisplay.getAnnouncementMessageDefaultTitleStyle();
    }

    @Override
    @NotNull
    public IDefaultStyle getDefaultDescriptionStyle(@NotNull OfflinePlayer player) {
        return baseDisplay.getDefaultDescriptionStyle();
    }

    @Override
    @NotNull
    public IDefaultStyle getAnnouncementMessageDefaultDescriptionStyle(@NotNull OfflinePlayer player) {
        return baseDisplay.getAnnouncementMessageDefaultDescriptionStyle();
    }
}
