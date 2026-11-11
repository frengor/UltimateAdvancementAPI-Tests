package com.fren_gor.ultimateAdvancementAPITests.test1;

import com.fren_gor.ultimateAdvancementAPI.advancement.Advancement;
import com.fren_gor.ultimateAdvancementAPI.announcementMessage.IAnnouncementMessage;
import com.fren_gor.ultimateAdvancementAPI.chat.IComponent;
import com.fren_gor.ultimateAdvancementAPI.chat.bungeecord.BungeeCordComponent;
import com.fren_gor.ultimateAdvancementAPI.util.AdvancementUtils;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public interface TestAnnounceMessage extends IAnnouncementMessage {
    @Override
    @Nullable
    default Function<@NotNull Player, @Nullable IComponent> getAnnouncementMessage(@NotNull Advancement advancement, @NotNull Player advancementCompleter) {
        return player -> {
            if (player.equals(advancementCompleter)) {
                return null;
            }
            return BungeeCordComponent.of("[Custom announce message for player " + player.getName() + "] ")
                    .append(AdvancementUtils.getAnnouncementMessage(advancement, advancementCompleter));
        };
    }
}
