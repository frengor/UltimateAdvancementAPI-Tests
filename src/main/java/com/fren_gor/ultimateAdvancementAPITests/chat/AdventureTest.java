package com.fren_gor.ultimateAdvancementAPITests.chat;

import com.fren_gor.ultimateAdvancementAPI.chat.IComponent;
import com.fren_gor.ultimateAdvancementAPI.chat.IDefaultStyle;
import com.fren_gor.ultimateAdvancementAPI.chat.JsonString;
import com.fren_gor.ultimateAdvancementAPI.chat.adventure.AdventureComponent;
import com.fren_gor.ultimateAdvancementAPI.chat.adventure.AdventureDefaultStyle;
import com.fren_gor.ultimateAdvancementAPI.chat.bungeecord.BungeeCordComponent;
import com.fren_gor.ultimateAdvancementAPI.chat.bungeecord.BungeeCordDefaultStyle;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextDecoration;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AdventureTest {

    public static void testAdventure(@NotNull Player player) {
        IComponent[] components = new IComponent[]{
            AdventureComponent.ofLegacy(ChatColor.RED + "Red"),
            AdventureComponent.of(Component.text("Gold").color(NamedTextColor.GOLD)),
            AdventureComponent.of(new JsonString("{\"text\":\"Raw Component\"}")),
            AdventureComponent.of("plain"),
            AdventureComponent.of("Hello world!")
                .applyDefaultStyle(AdventureDefaultStyle.of(complexStyleNoObf())),
            complexComponent(),
            complexComponent()
                .applyDefaultStyle(AdventureDefaultStyle.of(complexStyleNoObf())
                .merge(AdventureDefaultStyle.of(
                    AdventureDefaultStyle.of(NamedTextColor.AQUA)
                        .toAdventureStyle()
                        .decorate(TextDecoration.OBFUSCATED)
                ))
            ),
        };

        for (IComponent component : components) {
            component.sendTo(player);
            component.toPlain();
            component.toLegacy();
            component.toString();
            component.isEmptyOrStartsWithNewLine();
            if (!component.equals(AdventureComponent.of(component.toAdventureComponent()))) {
                player.sendMessage(ChatColor.RED + "Invalid toAdventureComponent() for previous component");
            }
        }

        if (!complexStyle().equals(complexStyle())) {
            player.sendMessage(ChatColor.RED + "Styles not equals!");
        }
        if (complexStyle().equals(AdventureDefaultStyle.of(complexStyleNoObf()))) {
            player.sendMessage(ChatColor.RED + "Styles equals!");
        }
        complexStyle().hashCode();
        complexStyle().toString();

        player.sendMessage("");
        player.sendMessage(ChatColor.DARK_GREEN + ChatColor.BOLD.toString() + "[CONVERSIONS A->B] The following components should be equals to the ones above:");

        for (IComponent component : components) {
            player.spigot().sendMessage(component.toBungeeCordComponent());
        }

        if (!complexStyle().toBungeeCordStyle().equals(complexStyle().toBungeeCordStyle())) {
            player.sendMessage(ChatColor.RED + "Styles not equals 2!");
        }
        if (complexStyle().toBungeeCordStyle().equals(AdventureDefaultStyle.of(complexStyleNoObf()).toBungeeCordStyle())) {
            player.sendMessage(ChatColor.RED + "Styles equals 2!");
        }

        player.sendMessage("");
        player.sendMessage(ChatColor.DARK_GREEN + ChatColor.BOLD.toString() + "[CONVERSIONS B->A] The following components should be equals to the ones above:");

        for (IComponent component : components) {
            AdventureComponent
                .of(BungeeCordComponent.of(component.toBungeeCordComponent()).toAdventureComponent())
                .sendTo(player);
        }

        if (!roundTripStyle(complexStyle()).equals(roundTripStyle(complexStyle()))) {
            player.sendMessage(ChatColor.RED + "Styles not equals 3!");
        }
        if (roundTripStyle(complexStyle()).equals(roundTripStyle(AdventureDefaultStyle.of(complexStyleNoObf())))) {
            player.sendMessage(ChatColor.RED + "Styles equals 3!");
        }

        player.sendMessage("");
        player.sendMessage(ChatColor.DARK_GREEN + ChatColor.BOLD.toString() + "[CONVERSIONS JSON] The following components should be equals to the ones above:");

        for (IComponent component : components) {
            AdventureComponent.of(component.toJson()).sendTo(player);
        }
    }

    private static Style complexStyleNoObf() {
       return BungeeCordDefaultStyle.of(BungeeCordTest.EVERYTHING_NO_OBF).toAdventureStyle();
    }

    private static IDefaultStyle complexStyle() {
        return AdventureDefaultStyle.of(complexStyleNoObf().decorate(TextDecoration.OBFUSCATED));
    }

    private static IComponent complexComponent() {
        return AdventureComponent.EMPTY
            .applyDefaultStyle(complexStyle())
            .setChildren(List.of(
                AdventureComponent.of("First"),
                AdventureComponent.SPACE
            ))
            .append(AdventureComponent.of("Second")
                .addHoverEvent(AdventureComponent.of("Hover")));
    }

    private static IDefaultStyle roundTripStyle(@NotNull IDefaultStyle style) {
        return AdventureDefaultStyle.of(
            BungeeCordDefaultStyle.of(style.toBungeeCordStyle()).toAdventureStyle()
        );
    }
}
