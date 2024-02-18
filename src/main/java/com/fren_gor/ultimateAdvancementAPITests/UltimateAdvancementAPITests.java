package com.fren_gor.ultimateAdvancementAPITests;

import com.fren_gor.ultimateAdvancementAPI.AdvancementTab;
import com.fren_gor.ultimateAdvancementAPI.UltimateAdvancementAPI;
import com.fren_gor.ultimateAdvancementAPI.advancement.Advancement;
import com.fren_gor.ultimateAdvancementAPI.advancement.RootAdvancement;
import com.fren_gor.ultimateAdvancementAPI.advancement.display.AdvancementDisplay;
import com.fren_gor.ultimateAdvancementAPI.advancement.display.AdvancementFrameType;
import com.fren_gor.ultimateAdvancementAPI.advancement.display.FancyAdvancementDisplay;
import com.fren_gor.ultimateAdvancementAPI.database.DatabaseManager;
import com.fren_gor.ultimateAdvancementAPI.database.TeamProgression;
import com.fren_gor.ultimateAdvancementAPI.events.PlayerLoadingCompletedEvent;
import com.fren_gor.ultimateAdvancementAPI.events.team.AsyncPlayerUnregisteredEvent;
import com.fren_gor.ultimateAdvancementAPI.events.team.AsyncTeamLoadEvent;
import com.fren_gor.ultimateAdvancementAPI.events.team.AsyncTeamUnloadEvent;
import com.fren_gor.ultimateAdvancementAPI.events.team.PlayerRegisteredEvent;
import com.fren_gor.ultimateAdvancementAPI.events.team.TeamUpdateEvent;
import com.fren_gor.ultimateAdvancementAPI.exceptions.IllegalOperationException;
import com.fren_gor.ultimateAdvancementAPI.util.AdvancementUtils;
import com.fren_gor.ultimateAdvancementAPI.util.Versions;
import com.fren_gor.ultimateAdvancementAPITests.test1.MultiParent;
import com.fren_gor.ultimateAdvancementAPITests.test1.MultiParentVanillaVisibility;
import com.fren_gor.ultimateAdvancementAPITests.test1.PerTeamDisplay;
import com.fren_gor.ultimateAdvancementAPITests.test1.Test1Advancement;
import com.fren_gor.ultimateAdvancementAPITests.test1.Test1AdvancementCustomAM;
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
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class UltimateAdvancementAPITests extends JavaPlugin implements Listener {

    @Getter
    private static UltimateAdvancementAPITests instance;
    @Getter
    private AdvancementTab test1Tab, test2Tab;
    private UltimateAdvancementAPI API;
    private final Map<Integer, TeamProgression> progressions = Collections.synchronizedMap(new HashMap<>());
    private final AtomicInteger i = new AtomicInteger(0);
    private final AtomicBoolean advancedDebugOutput = new AtomicBoolean(false);

    @Override
    public void onEnable() {
        instance = this;
        API = UltimateAdvancementAPI.getInstance(this);
        Bukkit.getPluginManager().registerEvents(this, this);
        test1Tab = API.createAdvancementTab("test1", "textures/block/stone.png");

        Test1Root root = new Test1Root(test1Tab, "root", new AdvancementDisplay.Builder(Material.NETHER_STAR, "§eTest Root").showToast().announceChat().taskFrame().description("Hello!").coords(0, 2).build());

        Test1Advancement adv_1_1 = new Test1Advancement("1_1", new PerTeamDisplay(new AdvancementDisplay.Builder(Material.GRASS_BLOCK, "(1, 1)").goalFrame().showToast().announceChat().coords(1, 1).build()), root, 5);
        Test1Advancement adv_1_3 = new Test1Advancement("1_3", new AdvancementDisplay.Builder(Material.GRAVEL, "(1, 3)").taskFrame().showToast().description("Row 1", "Row 2").coords(1, 3).build(), root, 5);
        Test1Advancement adv_2_2 = new Test1Advancement("2_2", new FancyAdvancementDisplay.Builder(Material.STICKY_PISTON, "(2, 2)").coords(2, 2).description("Boh").showToast().announceChat().taskFrame().build(), root, 7);
        Test1AdvancementCustomAM adv_2_1 = new Test1AdvancementCustomAM("2_1", new AdvancementDisplay.Builder(Material.STICKY_PISTON, "(2, 1)").taskFrame().coords(2, 1).showToast().announceChat().build(), adv_1_1, 7);

        MultiParent multi = new MultiParent("multi", new AdvancementDisplay.Builder(Material.OAK_SAPLING, "§lSaplings").challengeFrame().showToast().announceChat().coords(3, 2.5f).description("§6Description:", "§7Chop trees and get 5 saplings.", "", "§6Rewards:", "§74 Oak saplings.", "§74 Birch saplings.", "§74 Spruce saplings.", "§74 Dark Oak saplings.", "§74 Jungle saplings.").build(), 10, adv_2_2, adv_1_3);

        MultiParentVanillaVisibility multiVanilla = new MultiParentVanillaVisibility("multivanilla", new AdvancementDisplay.Builder(Material.ANVIL, "§7Anvils").challengeFrame().showToast().announceChat().coords(4, 2f).build(), 10, multi, adv_2_1, adv_2_2);

        test1Tab.registerAdvancements(root, adv_1_1, adv_1_3, adv_2_2, adv_2_1, multi, multiVanilla);

        test2Tab = API.createAdvancementTab("test2", "textures/block/stone.png");

        RootAdvancement test2Root = new RootAdvancement(test2Tab, "root", new AdvancementDisplay.Builder(Material.OAK_SAPLING, "Root").taskFrame().coords(0, 0).build());

        Test2MultiTask tasks = new Test2MultiTask("multi_tasks", new AdvancementDisplay.Builder(Material.STONE, "§6§lBreak blocks").goalFrame().showToast().announceChat().coords(1, 0).description("", "Break blocks:", "-> 5 Oak planks", "-> 5 Spruce planks", "-> 5 Dark oak planks").build(), test2Root, 15);

        BreakTask oak = new BreakTask("oak", tasks, 5, Material.OAK_PLANKS);
        BreakTask spruce = new BreakTask("spruce", tasks, 5, Material.SPRUCE_PLANKS);
        BreakTask darkOak = new BreakTask("dark_oak", tasks, 5, Material.DARK_OAK_PLANKS);

        tasks.registerTasks(oak, spruce, darkOak);
        test2Tab.registerAdvancements(test2Root, tasks);

        AdvancementTab ungrantable = API.createAdvancementTab("ungrantable", "textures/block/stone.png");
        ungrantable.registerAdvancements(new RootAdvancement(ungrantable, "ungrantable", new AdvancementDisplay.Builder(Material.BARRIER, "Ungrantable").build()));
        ungrantable.automaticallyShowToPlayers().automaticallyGrantRootAdvancement();
    }

    @EventHandler
    private void onRegister(PlayerRegisteredEvent e) {
        System.out.println("Player " + e.getPlayerUUID() + " has been registered in team " + e.getTeamProgression().getTeamId() + '.');
        if (advancedDebugOutput.get()) {
            StringJoiner j = new StringJoiner(", ", "[", "]");
            e.getTeamProgression().forEachMember(uuid -> {
                j.add(uuid.toString());
            });
            System.out.println("Team " + e.getTeamProgression().getTeamId() + " members: " + j.toString());
        }
    }

    @EventHandler
    private void onUnregister(AsyncPlayerUnregisteredEvent e) {
        System.out.println("Player " + e.getPlayerUUID() + " was unregistered.");
    }

    @EventHandler
    private void onTeamUpdate(TeamUpdateEvent e) {
        System.out.println("Player " + e.getPlayerUUID() + " left team " + e.getOldTeamProgression().getTeamId() + '.');
        if (advancedDebugOutput.get()) {
            StringJoiner j = new StringJoiner(", ", "[", "]");
            e.getOldTeamProgression().forEachMember(uuid -> {
                j.add(uuid.toString());
            });
            System.out.println("Team " + e.getOldTeamProgression().getTeamId() + " members: " + j.toString());
        }
        System.out.println("Player " + e.getPlayerUUID() + " joined team " + e.getNewTeamProgression().getTeamId() + '.');
        if (advancedDebugOutput.get()) {
            StringJoiner j = new StringJoiner(", ", "[", "]");
            e.getNewTeamProgression().forEachMember(uuid -> {
                j.add(uuid.toString());
            });
            System.out.println("Team " + e.getNewTeamProgression().getTeamId() + " members: " + j.toString());
        }
    }

    @EventHandler
    private void onTeamLoad(AsyncTeamLoadEvent e) {
        System.out.println("Loaded team " + i + " with id " + e.getTeamProgression().getTeamId() + '.');
        if (advancedDebugOutput.get()) {
            StringJoiner j = new StringJoiner(", ", "[", "]");
            e.getTeamProgression().forEachMember(uuid -> {
                j.add(uuid.toString());
            });
            System.out.println("Team " + e.getTeamProgression().getTeamId() + " members: " + j.toString());
        }
        progressions.put(i.getAndIncrement(), e.getTeamProgression());
    }

    @EventHandler
    private void onTeamUnload(AsyncTeamUnloadEvent e) {
        int id = -1;
        synchronized (progressions) {
            for (Entry<Integer, TeamProgression> en : progressions.entrySet()) {
                if (en.getValue() == e.getTeamProgression()) {
                    id = en.getKey();
                }
            }
        }
        if (id == -1)
            System.out.println("Unload team (couldn't find test-id) with id " + e.getTeamProgression().getTeamId() + '.');
        else
            System.out.println("Unload team " + id + " with id " + e.getTeamProgression().getTeamId() + '.');

        if (advancedDebugOutput.get()) {
            StringJoiner j = new StringJoiner(", ", "[", "]");
            e.getTeamProgression().forEachMember(uuid -> {
                j.add(uuid.toString());
            });
            System.out.println("Team " + e.getTeamProgression().getTeamId() + " members: " + j.toString());
        }
    }

    @EventHandler
    private void onPlayerLoading(PlayerLoadingCompletedEvent e) {
        test1Tab.showTab(e.getPlayer());
        test1Tab.grantRootAdvancement(e.getPlayer());
        test2Tab.showTab(e.getPlayer());
        test2Tab.grantRootAdvancement(e.getPlayer());
        System.out.println("Called PlayerLoadingCompletedEvent for player " + e.getPlayer().getName() + " (" + e.getPlayer().getUniqueId() + ").");

        if (advancedDebugOutput.get()) {
            StringJoiner j = new StringJoiner(", ", "[", "]");
            e.getTeamProgression().forEachMember(uuid -> {
                j.add(uuid.toString());
            });
            System.out.println("Team " + i + " members: " + j.toString());
        }

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
        switch (args[0].toLowerCase(Locale.ROOT)) {
            case "debug" -> {
                boolean toSet = !advancedDebugOutput.get();
                advancedDebugOutput.set(toSet);
                sender.sendMessage(toSet ? "AdvancedDebugOutput enabled" : "AdvancedDebugOutput disabled");
            }
            case "version" -> {
                sender.sendMessage("API version: [" + String.join(", ", Versions.getApiVersion()) + ']');
                sender.sendMessage("Supported NMS versions: [" + String.join(", ", Versions.getSupportedNMSVersions()) + ']');
                sender.sendMessage("Supported versions: [" + String.join(", ", Versions.getSupportedVersions()) + ']');
                sender.sendMessage("NMS versions range: [" + String.join(", ", Versions.getNMSVersionsRange()) + ']');
                sender.sendMessage("NMS versions list: [" + String.join(", ", Versions.getNMSVersionsList()) + ']');
            }
            case "remove" -> {
                try {
                    AdvancementUtils.disableVanillaAdvancements();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            case "toast" -> {
                if (sender instanceof Player p) {
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
            }
            case "load" -> {
                if (args.length == 1) {
                    sender.sendMessage("§cIllegal syntax.");
                    return false;
                }
                UUID uuid = UUID.fromString(args[1]);
                API.loadAndAddLoadingRequestToPlayer(uuid).handle((pro, err) -> {
                    if (err != null) {
                        err.printStackTrace();
                    } else {
                        sender.sendMessage("LoadOfflinePlayer teamId: " + pro.getTeamId());
                    }
                    return null;
                });
            }
            case "unload" -> {
                if (args.length == 1) {
                    sender.sendMessage("§cIllegal syntax.");
                    return false;
                }
                UUID uuid = UUID.fromString(args[1]);
                API.removeLoadingRequestToPlayer(uuid);
            }
            case "dump" -> { // Dump database manager
                final DatabaseManager manager = test1Tab.getDatabaseManager();
                synchronized (manager) {
                    System.out.println("LoadedTeams:");
                    try {
                        for (Entry<Integer, Object> e : getLoadedTeams(manager).entrySet()) {
                            StringJoiner j = new StringJoiner(", ");
                            TeamProgression pro = getTeamProgression(e.getValue());
                            pro.forEachMember(u -> j.add(u.toString()));
                            System.out.println(e.getKey() + " -> IR:" + getInternalRequests(e.getValue()) + ", PR:" + getPluginRequests(e.getValue()).size() + " [" + j + "] " + (pro.isValid() ? 'L' : 'U'));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    System.out.println("--------------------------");
                    System.out.println("LoadedPlayers:");
                    try {
                        for (Entry<UUID, Object> e : getLoadedPlayers(manager).entrySet()) {
                            Object loadedPlayer = e.getValue();
                            @Nullable TeamProgression pro = getPlayerTeam(loadedPlayer);
                            System.out.println(e.getKey() + " -> Team:" + (pro == null ? "null" : pro.getTeamId()) + ", IR:" + getInternalRequests(loadedPlayer) + ", PR:" + getPluginRequests(loadedPlayer).size() + (isOnline(loadedPlayer) ? " online" : " offline"));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    System.out.println("--------------------------");
                    System.out.println("Test-Stored TeamProgressions:");
                    for (Entry<Integer, TeamProgression> e : progressions.entrySet()) {
                        System.out.println(e.getKey() + " -> Id: " + e.getValue().getTeamId() + ", " + (e.getValue().isValid() ? 'L' : 'U'));
                    }
                    System.out.println("--------------------------");
                }

            }
            case "move" -> {
                if (args.length <= 2) {
                    sender.sendMessage("§cIllegal syntax.");
                    return false;
                }
                UUID uuid1 = UUID.fromString(args[1]);
                UUID uuid2 = UUID.fromString(args[2]);
                API.updatePlayerTeam(uuid1, uuid2).handle((v, err) -> {
                    if (err != null) {
                        err.printStackTrace();
                    } else {
                        sender.sendMessage("UpdatePlayerTeam!");
                    }
                    return null;
                });
            }
            case "unregister" -> {
                if (args.length == 1) {
                    sender.sendMessage("§cIllegal syntax.");
                    return false;
                }
                UUID uuid = UUID.fromString(args[1]);
                API.unregisterOfflinePlayer(uuid).handle((v, err) -> {
                    if (err != null) {
                        err.printStackTrace();
                    } else {
                        sender.sendMessage("UnregisterPlayer succeeded!");
                    }
                    return null;
                });
            }
            case "apart" -> {
                if (sender instanceof Player) {
                    API.movePlayerInNewTeam((Player) sender).handle((pro, err) -> {
                        if (err != null) {
                            err.printStackTrace();
                        } else {
                            sender.sendMessage("UnregisterPlayer teamId: " + pro.getTeamId());
                        }
                        return null;
                    });
                } else {
                    if (args.length == 1) {
                        sender.sendMessage("§cIllegal syntax.");
                        return false;
                    }
                    UUID uuid = UUID.fromString(args[1]);
                    API.movePlayerInNewTeam(uuid).handle((pro, err) -> {
                        if (err != null) {
                            err.printStackTrace();
                        } else {
                            sender.sendMessage("UnregisterPlayer teamId: " + pro.getTeamId());
                        }
                        return null;
                    });
                }
            }
            case "unredeemed" -> {
                if (args.length <= 2) {
                    sender.sendMessage("§cIllegal syntax.");
                    return false;
                }
                UUID uuid = UUID.fromString(args[1]);
                Advancement adv = Objects.requireNonNull(API.getAdvancement(args[2]), "Advancement doesn't exists.");
                API.setUnredeemed(adv, uuid).handle((v, err) -> {
                    if (err != null) {
                        err.printStackTrace();
                    } else {
                        sender.sendMessage("SetUnredeemed succeeded!");
                    }
                    return null;
                });
            }
            case "teamprogression" -> {
                try {
                    new TeamProgression(0);
                    System.out.println("No exception has been thrown.");
                } catch (IllegalOperationException e) {
                    e.printStackTrace();
                }
            }
            default -> {
                sender.sendMessage("§cIllegal syntax.");
                return false;
            }
        }
        return true;
    }

    private static Map<Integer, Object> getLoadedTeams(DatabaseManager manager) throws Exception {
        Field tempLoaded = DatabaseManager.class.getDeclaredField("teamsLoaded");
        tempLoaded.setAccessible(true);

        return (Map<Integer, Object>) tempLoaded.get(manager);
    }

    private static Map<UUID, Object> getLoadedPlayers(DatabaseManager manager) throws Exception {
        Field tempLoaded = DatabaseManager.class.getDeclaredField("playersLoaded");
        tempLoaded.setAccessible(true);

        return (Map<UUID, Object>) tempLoaded.get(manager);
    }

    private static boolean isOnline(Object loadedPlayer) throws Exception {
        Method isOnline = loadedPlayer.getClass().getDeclaredMethod("isOnline");
        isOnline.setAccessible(true);
        return (boolean) isOnline.invoke(loadedPlayer);
    }

    private static int getInternalRequests(Object cacheableEntry) throws Exception {
        Method getInternalRequests = cacheableEntry.getClass().getSuperclass().getDeclaredMethod("getInternalRequests");
        getInternalRequests.setAccessible(true);
        return (int) getInternalRequests.invoke(cacheableEntry);
    }

    private static Map<Plugin, Integer> getPluginRequests(Object cacheableEntry) throws Exception {
        Field pR = cacheableEntry.getClass().getSuperclass().getDeclaredField("pluginRequests");
        pR.setAccessible(true);
        return (Map<Plugin, Integer>) pR.get(cacheableEntry);
    }

    private static TeamProgression getTeamProgression(Object loadedTeam) throws Exception {
        Method getPro = loadedTeam.getClass().getDeclaredMethod("getTeamProgression");
        getPro.setAccessible(true);

        return (TeamProgression) getPro.invoke(loadedTeam);
    }

    @Nullable
    private static TeamProgression getPlayerTeam(Object loadedPlayer) throws Exception {
        Method playerTeam = loadedPlayer.getClass().getDeclaredMethod("getPlayerTeam");
        playerTeam.setAccessible(true);

        @Nullable Object loadedTeam = playerTeam.invoke(loadedPlayer);
        return loadedTeam == null ? null : getTeamProgression(loadedTeam);
    }
}
