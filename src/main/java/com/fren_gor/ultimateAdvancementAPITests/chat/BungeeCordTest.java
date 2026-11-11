package com.fren_gor.ultimateAdvancementAPITests.chat;

import com.fren_gor.ultimateAdvancementAPI.chat.IComponent;
import com.fren_gor.ultimateAdvancementAPI.chat.IDefaultStyle;
import com.fren_gor.ultimateAdvancementAPI.chat.JsonString;
import com.fren_gor.ultimateAdvancementAPI.chat.bungeecord.BungeeCordComponent;
import com.fren_gor.ultimateAdvancementAPI.chat.bungeecord.BungeeCordDefaultStyle;
import com.fren_gor.ultimateAdvancementAPI.chat.bungeecord.BungeeCordStyle;
import com.fren_gor.ultimateAdvancementAPI.nms.util.ReflectionUtil;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.awt.Color;
import java.util.List;

public class BungeeCordTest {

    public static final BungeeCordStyle EVERYTHING_NO_OBF = new BungeeCordStyle()
        .color(getRGBColorOr(ChatColor.GOLD))
        .shadowColor(new Color(143, 86, 86))
        .bold(true)
        .font("minecraft:uniform")
        .italic(true)
        .underlined(true)
        .strikethrough(true);

    public static void testBungeeCord(@NotNull Player player) {
        IComponent[] components = new IComponent[]{
            BungeeCordComponent.ofLegacy(ChatColor.RED + "Red"),
            BungeeCordComponent.of(new TextComponent(new ComponentBuilder("Gold").color(ChatColor.GOLD).create())),
            BungeeCordComponent.of(new JsonString("{\"text\":\"Raw Component\"}")),
            BungeeCordComponent.of("plain"),
            BungeeCordComponent.of("Hello world!")
                .applyDefaultStyle(BungeeCordDefaultStyle.of(EVERYTHING_NO_OBF)),
            complexComponent(),
            complexComponent()
                .applyDefaultStyle(BungeeCordDefaultStyle.of(EVERYTHING_NO_OBF)
                    .merge(BungeeCordDefaultStyle.of(
                        BungeeCordDefaultStyle.of(ChatColor.AQUA)
                            .toBungeeCordStyle()
                            .obfuscated(true)
                    ))
                ),
        };

        for (IComponent component : components) {
            component.sendTo(player);
            component.toPlain();
            component.toLegacy();
            component.toString();
            component.isEmptyOrStartsWithNewLine();
            if (!component.equals(BungeeCordComponent.of(component.toBungeeCordComponent()))) {
                player.sendMessage(ChatColor.RED + "Invalid toBungeeCordComponent() for previous component");
            }
        }

        if (!complexStyle().equals(complexStyle())) {
            player.sendMessage(ChatColor.RED + "Styles not equals!");
        }
        if (complexStyle().equals(BungeeCordDefaultStyle.of(EVERYTHING_NO_OBF))) {
            player.sendMessage(ChatColor.RED + "Styles equals!");
        }
        complexStyle().hashCode();
        complexStyle().toString();

        player.sendMessage("");
        player.sendMessage(ChatColor.DARK_GREEN + ChatColor.BOLD.toString() + "[CONVERSIONS JSON] The following components should be equals to the ones above:");

        for (IComponent component : components) {
            BungeeCordComponent.of(component.toJson()).sendTo(player);
        }
    }

    static IDefaultStyle complexStyle() {
        return BungeeCordDefaultStyle.of(EVERYTHING_NO_OBF.obfuscated(true));
    }

    static IComponent complexComponent() {
        return BungeeCordComponent.EMPTY
            .applyDefaultStyle(complexStyle())
            .setChildren(List.of(
                BungeeCordComponent.of("First"),
                BungeeCordComponent.SPACE
            ))
            .append(BungeeCordComponent.of("Second")
                .addHoverEvent(BungeeCordComponent.of("Hover")));
    }

    public static ChatColor getRGBColorOr(ChatColor defaultColor) {
        // Call ChatColor.of(...) only on 1.16+
        return ReflectionUtil.MAJOR_VERSION == 1 && ReflectionUtil.VERSION <= 15 ? defaultColor : ChatColor.of(new Color(143, 86, 86));
    }
}
