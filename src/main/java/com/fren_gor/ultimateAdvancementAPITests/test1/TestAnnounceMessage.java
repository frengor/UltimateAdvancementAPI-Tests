package com.fren_gor.ultimateAdvancementAPITests.test1;

import com.fren_gor.ultimateAdvancementAPI.advancement.Advancement;
import com.fren_gor.ultimateAdvancementAPI.announcementMessage.IAnnouncementMessage;
import com.fren_gor.ultimateAdvancementAPI.util.AdvancementUtils;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public interface TestAnnounceMessage extends IAnnouncementMessage {
    @Override
    @Nullable
    default Function<@NotNull Player, @Nullable BaseComponent> getAnnouncementMessage(@NotNull Advancement advancement, @NotNull Player advancementCompleter) {
        return player -> {
            if (player.equals(advancementCompleter)) {
                return null;
            }
            return AdvancementUtils.build(new ComponentBuilder("[Custom announce message for player " + player.getName() + "] ")
                    .append(AdvancementUtils.getAnnouncementMessage(advancement, advancementCompleter))
            );
        };
    }
}
