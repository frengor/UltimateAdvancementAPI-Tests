package com.fren_gor.ultimateAdvancementAPITests.test1;

import com.fren_gor.ultimateAdvancementAPI.advancement.display.AbstractPerPlayerAdvancementDisplay;
import com.fren_gor.ultimateAdvancementAPI.advancement.display.AdvancementDisplay;
import com.fren_gor.ultimateAdvancementAPI.advancement.display.AdvancementFrameType;
import com.fren_gor.ultimateAdvancementAPI.nms.wrappers.advancement.PreparedAdvancementDisplayWrapper;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

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
    public @NotNull ItemStack getIcon(@NotNull OfflinePlayer player) {
        return baseDisplay.getIcon();
    }

    @Override
    public @NotNull BaseComponent[] getTitleBaseComponent(@NotNull OfflinePlayer player) {
        return baseDisplay.getTitleBaseComponent();
    }

    @Override
    public @Unmodifiable List<BaseComponent[]> getDescriptionBaseComponent(@NotNull OfflinePlayer player) {
        return baseDisplay.getDescriptionBaseComponent();
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
    public @NotNull String getTitle(@NotNull Player player) {
        return "Your name is " + player.getName();
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
    public PreparedAdvancementDisplayWrapper getNMSWrapper(@NotNull Player player) {
        try {
            return PreparedAdvancementDisplayWrapper.craft(getIcon(), getTitle(player), baseDisplay.getCompactDescription(), getFrame().getNMSWrapper(), getX(), getY());
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }
}
