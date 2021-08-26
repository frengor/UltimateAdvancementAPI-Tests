package com.fren_gor.ultimateAdvancementAPITests;

import com.fren_gor.ultimateAdvancementAPI.AdvancementMain;
import com.fren_gor.ultimateAdvancementAPI.AdvancementPlugin;
import com.fren_gor.ultimateAdvancementAPI.AdvancementTab;
import com.fren_gor.ultimateAdvancementAPI.UltimateAdvancementAPI;
import com.fren_gor.ultimateAdvancementAPI.advancement.Advancement;
import com.fren_gor.ultimateAdvancementAPI.advancement.RootAdvancement;
import com.fren_gor.ultimateAdvancementAPI.advancement.display.AdvancementDisplay;
import com.fren_gor.ultimateAdvancementAPI.advancement.display.AdvancementFrameType;
import com.fren_gor.ultimateAdvancementAPI.advancement.display.FancyAdvancementDisplay;
import com.fren_gor.ultimateAdvancementAPI.database.CacheFreeingOption;
import com.fren_gor.ultimateAdvancementAPI.database.DatabaseManager;
import com.fren_gor.ultimateAdvancementAPI.database.TeamProgression;
import com.fren_gor.ultimateAdvancementAPI.events.PlayerLoadingCompletedEvent;
import com.fren_gor.ultimateAdvancementAPI.util.AdvancementUtils;
import com.fren_gor.ultimateAdvancementAPI.util.Versions;
import com.fren_gor.ultimateAdvancementAPITests.test1.MultiParent;
import com.fren_gor.ultimateAdvancementAPITests.test1.Test1Advancement;
import com.fren_gor.ultimateAdvancementAPITests.test1.Test1Root;
import com.fren_gor.ultimateAdvancementAPITests.test2.Test2MultiTask;
import com.fren_gor.ultimateAdvancementAPITests.test2.tasks.BreakTask;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.UUID;

public class UltimateAdvancementAPITests extends JavaPlugin implements Listener {

    @Getter
    private static UltimateAdvancementAPITests instance;
    @Getter
    private AdvancementTab test1Tab, test2Tab;
    private UltimateAdvancementAPI API;

    @Override
    public void onEnable() {
        instance = this;
        API = UltimateAdvancementAPI.getInstance(this);
        Bukkit.getPluginManager().registerEvents(this, this);
        test1Tab = API.createAdvancementTab("test1");

        Test1Root root = new Test1Root(test1Tab, "root", new AdvancementDisplay(Material.NETHER_STAR, "§eTest Root", AdvancementFrameType.TASK, true, true, 0, 2, "Hello!"), "textures/block/stone.png");

        Test1Advancement adv_1_1 = new Test1Advancement("1_1", new AdvancementDisplay(Material.GRASS_BLOCK, "(1, 1)", AdvancementFrameType.GOAL, true, true, 1, 1), root, 5);
        Test1Advancement adv_1_3 = new Test1Advancement("1_3", new AdvancementDisplay(Material.GRAVEL, "(1, 3)", AdvancementFrameType.TASK, true, false, 1, 3, "Row 1", "Row 2"), root, 5);
        Test1Advancement adv_2_2 = new Test1Advancement("2_2", new FancyAdvancementDisplay(Material.STICKY_PISTON, "(2, 2)", AdvancementFrameType.TASK, true, true, 2, 2, "Boh"), root, 7);
        Test1Advancement adv_2_1 = new Test1Advancement("2_1", new AdvancementDisplay(Material.STICKY_PISTON, "(2, 1)", AdvancementFrameType.TASK, true, true, 2, 1), adv_1_1, 7);

        MultiParent multi = new MultiParent("multi", new AdvancementDisplay(Material.OAK_SAPLING, "§lSaplings", AdvancementFrameType.CHALLENGE, true, true, 3, 2.5f, "§6Description:", "§7Chop trees and get 5 saplings.", "", "§6Rewards:", "§74 Oak saplings.", "§74 Birch saplings.", "§74 Spruce saplings.", "§74 Dark Oak saplings.", "§74 Jungle saplings."), 10, adv_2_2, adv_1_3);

        test1Tab.registerAdvancements(root, adv_1_1, adv_1_3, adv_2_2, adv_2_1, multi);

        test2Tab = API.createAdvancementTab("test2");

        RootAdvancement test2Root = new RootAdvancement(test2Tab, "root", new AdvancementDisplay(Material.OAK_SAPLING, "Root", AdvancementFrameType.TASK, false, false, 0, 0), "textures/block/stone.png");

        Test2MultiTask tasks = new Test2MultiTask("multi_tasks", new AdvancementDisplay(Material.STONE, "§6§lBreak blocks", AdvancementFrameType.GOAL, true, true, 1, 0, "", "Break blocks:", "-> 5 Oak planks", "-> 5 Spruce planks", "-> 5 Dark oak planks"), test2Root, 15);

        BreakTask oak = new BreakTask("oak", tasks, 5, Material.OAK_PLANKS);
        BreakTask spruce = new BreakTask("spruce", tasks, 5, Material.SPRUCE_PLANKS);
        BreakTask darkOak = new BreakTask("dark_oak", tasks, 5, Material.DARK_OAK_PLANKS);

        tasks.registerTasks(oak, spruce, darkOak);
        test2Tab.registerAdvancements(test2Root, tasks);
    }

    @EventHandler
    private void onPlayerLoading(PlayerLoadingCompletedEvent e) {
        test1Tab.showTab(e.getPlayer());
        test1Tab.grantRootAdvancement(e.getPlayer());
        test2Tab.showTab(e.getPlayer());
        test2Tab.grantRootAdvancement(e.getPlayer());
        System.out.println("Called");
        AdvancementUtils.displayToast(e.getPlayer(), new ItemStack(Material.GRASS_BLOCK), "Join", AdvancementFrameType.CHALLENGE);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!command.getName().equals("testadv")) {
            return false;
        }
        if (args.length == 0) {
            sender.sendMessage("§cIllegal syntax.");
            return false;
        }
        switch (args[0]) {
            case "version": {
                sender.sendMessage("API version: [" + String.join(", ", Versions.getApiVersion()) + ']');
                sender.sendMessage("Supported NMS versions: [" + String.join(", ", Versions.getSupportedNMSVersions()) + ']');
                sender.sendMessage("Supported versions: [" + String.join(", ", Versions.getSupportedVersions()) + ']');
                sender.sendMessage("NMS versions range: [" + String.join(", ", Versions.getNMSVersionsRange()) + ']');
                sender.sendMessage("NMS versions list: [" + String.join(", ", Versions.getNMSVersionsList()) + ']');
                break;
            }
            case "remove": {
                try {
                    AdvancementUtils.disableVanillaAdvancements();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
            case "toast": {
                if (sender instanceof Player) {
                    Player p = (Player) sender;
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            AdvancementUtils.displayToast(p, new ItemStack(Material.GRASS_BLOCK), "Test", AdvancementFrameType.CHALLENGE);
                        }
                    }.runTaskLater(this, 40);
                } else {
                    if (args.length != 2) {
                        sender.sendMessage("§cIllegal syntax.");
                        return false;
                    }
                    Player p = Bukkit.getPlayer(args[1]);
                    if (p == null) {
                        sender.sendMessage("§cThat player is not online.");
                        return false;
                    }
                    AdvancementUtils.displayToast(p, new ItemStack(Material.GRASS_BLOCK), "Test", AdvancementFrameType.CHALLENGE);
                }
                break;
            }
            case "load": {
                if (args.length == 1) {
                    sender.sendMessage("§cIllegal syntax.");
                    return false;
                }
                UUID uuid = UUID.fromString(args[1]);
                API.loadOfflinePlayer(uuid, CacheFreeingOption.AUTOMATIC(this, 20 * 20), r -> {
                    if (r.isExceptionOccurred()) {
                        r.printStackTrace();
                    } else {
                        sender.sendMessage("LoadOfflinePlayer succeeded: " + r.isSucceeded());
                        sender.sendMessage("LoadOfflinePlayer hasResult: " + r.hasResult());
                        if (r.getResult() != null)
                            sender.sendMessage("LoadOfflinePlayer teamId: " + r.getResult().getTeamId());
                    }
                });
                break;
            }
            case "loadm": { // Load with manual unloading
                if (args.length == 1) {
                    sender.sendMessage("§cIllegal syntax.");
                    return false;
                }
                UUID uuid = UUID.fromString(args[1]);
                API.loadOfflinePlayer(uuid, CacheFreeingOption.MANUAL(this), r -> {
                    if (r.isExceptionOccurred()) {
                        r.printStackTrace();
                    } else {
                        sender.sendMessage("LoadOfflinePlayer succeeded: " + r.isSucceeded());
                        sender.sendMessage("LoadOfflinePlayer hasResult: " + r.hasResult());
                        if (r.getResult() != null)
                            sender.sendMessage("LoadOfflinePlayer teamId: " + r.getResult().getTeamId());
                    }
                });
                break;
            }
            case "unload": {
                if (args.length == 1) {
                    sender.sendMessage("§cIllegal syntax.");
                    return false;
                }
                UUID uuid = UUID.fromString(args[1]);
                API.unloadOfflinePlayer(uuid);
                break;
            }
            case "dump": { // Dump database manager
                AdvancementMain main = AdvancementPlugin.getInstance().getMain();
                synchronized (main.getDatabaseManager()) {
                    System.out.println("ProgressionCache:");
                    try {
                        for (Entry<UUID, TeamProgression> e : getProgressionCache(main.getDatabaseManager()).entrySet()) {
                            StringJoiner j = new StringJoiner(", ");
                            e.getValue().forEachMember(u -> j.add(u.toString()));
                            System.out.println(e.getKey() + " -> " + e.getValue().toString() + '[' + j + ']');
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    System.out.println("--------------------------");
                    System.out.println("TempLoaded:");
                    try {
                        for (Entry<UUID, Object> e : getTempLoaded(main.getDatabaseManager()).entrySet()) {
                            System.out.print(e.getKey() + " -> TempUserMetadata:{");
                            System.out.print("\tIsOnline:" + isOnline(e.getValue()));
                            StringJoiner j = new StringJoiner(", ");
                            for (Entry<Plugin, Integer> i : getPluginRequests(e.getValue()).entrySet()) {
                                j.add(i.getKey().getName() + "=" + i.getValue());
                            }
                            System.out.println("\tRequests:[" + j + "]");
                            System.out.println("}");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    System.out.println("--------------------------");
                }

                break;
            }
            case "move": {
                if (args.length <= 2) {
                    sender.sendMessage("§cIllegal syntax.");
                    return false;
                }
                UUID uuid1 = UUID.fromString(args[1]);
                UUID uuid2 = UUID.fromString(args[2]);
                API.updatePlayerTeam(uuid1, uuid2, r -> {
                    if (r.isExceptionOccurred()) {
                        r.printStackTrace();
                    } else {
                        sender.sendMessage("UpdatePlayerTeam: " + r.isSucceeded());
                    }
                });
                break;
            }
            case "unregister": {
                if (args.length == 1) {
                    sender.sendMessage("§cIllegal syntax.");
                    return false;
                }
                UUID uuid = UUID.fromString(args[1]);
                API.unregisterOfflinePlayer(uuid, r -> {
                    if (r.isExceptionOccurred()) {
                        r.printStackTrace();
                    } else {
                        sender.sendMessage("UnregisterPlayer succeeded: " + r.isSucceeded());
                    }
                });
                break;
            }
            case "apart": {
                if (sender instanceof Player) {
                    API.movePlayerInNewTeam((Player) sender, r -> {
                        if (r.isExceptionOccurred()) {
                            r.printStackTrace();
                        } else {
                            sender.sendMessage("UnregisterPlayer succeeded: " + r.isSucceeded());
                            sender.sendMessage("UnregisterPlayer hasResult: " + r.hasResult());
                            sender.sendMessage("UnregisterPlayer teamId: " + r.getResult().getTeamId());
                        }
                    });
                } else {
                    if (args.length == 1) {
                        sender.sendMessage("§cIllegal syntax.");
                        return false;
                    }
                    UUID uuid = UUID.fromString(args[1]);
                    API.movePlayerInNewTeam(uuid, r -> {
                        if (r.isExceptionOccurred()) {
                            r.printStackTrace();
                        } else {
                            sender.sendMessage("UnregisterPlayer succeeded: " + r.isSucceeded());
                            sender.sendMessage("UnregisterPlayer hasResult: " + r.hasResult());
                            sender.sendMessage("UnregisterPlayer teamId: " + r.getResult().getTeamId());
                        }
                    });
                }
                break;
            }
            case "unredeemed": {
                if (args.length <= 2) {
                    sender.sendMessage("§cIllegal syntax.");
                    return false;
                }
                UUID uuid = UUID.fromString(args[1]);
                Advancement adv = Objects.requireNonNull(API.getAdvancement(args[2]), "Advancement doesn't exists.");
                API.setUnredeemed(adv, uuid, r -> {
                    if (r.isExceptionOccurred()) {
                        r.printStackTrace();
                    } else {
                        sender.sendMessage("SetUnredeemed: " + r.isSucceeded());
                    }
                });
                break;
            }
            default:
                sender.sendMessage("§cIllegal syntax.");
                return false;
        }
        return true;
    }

    private static Map<UUID, TeamProgression> getProgressionCache(DatabaseManager manager) throws Exception {
        Field tempLoaded = DatabaseManager.class.getDeclaredField("progressionCache");
        tempLoaded.setAccessible(true);

        return (Map<UUID, TeamProgression>) tempLoaded.get(manager);
    }

    private static boolean isOnline(Object temp) throws Exception {
        Field pR = temp.getClass().getDeclaredField("isOnline");
        pR.setAccessible(true);
        return pR.getBoolean(temp);
    }

    private static Map<Plugin, Integer> getPluginRequests(Object temp) throws Exception {
        Field pR = temp.getClass().getDeclaredField("pluginRequests");
        pR.setAccessible(true);
        return (Map<Plugin, Integer>) pR.get(temp);
    }

    private static Map<UUID, Object> getTempLoaded(DatabaseManager manager) throws Exception {
        Field tempLoaded = DatabaseManager.class.getDeclaredField("tempLoaded");
        tempLoaded.setAccessible(true);

        return (Map<UUID, Object>) tempLoaded.get(manager);
    }
}
