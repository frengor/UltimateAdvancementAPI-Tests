package com.fren_gor.ultimateAdvancementAPITests.test1;

import com.fren_gor.ultimateAdvancementAPI.advancement.Advancement;
import com.fren_gor.ultimateAdvancementAPI.announceMessage.IAnnounceMessage;
import com.fren_gor.ultimateAdvancementAPI.util.AdvancementUtils;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface TestAnnounceMessage extends IAnnounceMessage {
    @Override
    @Nullable
    default BaseComponent[] getAnnounceMessage(@NotNull Advancement advancement, @NotNull Player advancementCompleter) {
        return new ComponentBuilder("[Custom announce message] ")
                .append(AdvancementUtils.getAnnounceMessage(advancement, advancementCompleter))
                .create();
    }
}
