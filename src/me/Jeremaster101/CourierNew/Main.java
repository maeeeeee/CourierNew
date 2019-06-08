package me.Jeremaster101.CourierNew;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Villager;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class Main extends JavaPlugin {

    public static Main plugin;

    private final Permission letter = new Permission("couriernew.letter");
    private final Permission post = new Permission("couriernew.post");
    private final Permission courier = new Permission("couriernew.courier");
    private final Permission shred = new Permission("couriernew.shred");
    private final Permission shredall = new Permission("couriernew.shredall");
    private final Permission unread = new Permission("couriernew.unread");
    private final Permission reload = new Permission("couriernew.reload");

    public void onEnable() {
        plugin = this;

        PluginManager pm = Bukkit.getPluginManager();

        pm.registerEvents(new PostLetter(), this);

        pm.addPermission(letter);
        pm.addPermission(post);
        pm.addPermission(courier);
        pm.addPermission(shred);
        pm.addPermission(shredall);
        pm.addPermission(unread);
        pm.addPermission(reload);

        getCommand("letter").setExecutor(new CommandExec());
        getCommand("post").setExecutor(new CommandExec());
        getCommand("postman").setExecutor(new CommandExec());
        getCommand("courier").setExecutor(new CommandExec());
        getCommand("shred").setExecutor(new CommandExec());
        getCommand("shredall").setExecutor(new CommandExec());
        getCommand("unread").setExecutor(new CommandExec());
        getCommand("courierreload").setExecutor(new CommandExec());


        new BukkitRunnable() {
            @Override
            public void run() {
                int count = 0;
                Message msg = new Message();
                Bukkit.getLogger().info(msg.CLEANING);
                for (World world : Bukkit.getWorlds()) {
                    for (Entity entity : world.getEntities()) {
                        if (entity instanceof Villager) {
                            if (entity.getCustomName() != null) {
                                if (entity.getCustomName().equals(msg.POSTMAN_NAME_RECEIVED) ||
                                        entity.getCustomName().contains("Postman")) {
                                    entity.remove();
                                    count++;
                                }
                            }
                        }
                    }
                }
                Bukkit.getLogger().info(msg.DONE_CLEANING.replace("$COUNT$", Integer.toString(count)));
            }
        }.runTaskLater(plugin, 2);

        getConfig().options().copyDefaults(true);
        saveConfig();

    }

    public void onDisable() {
        plugin = null;
    }
}
