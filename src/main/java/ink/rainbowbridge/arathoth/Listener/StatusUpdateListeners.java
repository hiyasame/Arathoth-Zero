package ink.rainbowbridge.arathoth.Listener;

import ink.rainbowbridge.arathoth.API.ArathothAPI;
import ink.rainbowbridge.arathoth.Attributes.AttributeLoader;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.*;

public class StatusUpdateListeners implements Listener {
    @EventHandler
    void onPlayerItemHeldEvent(PlayerItemHeldEvent event){
        if (event.isCancelled()) {
            return;
        }
        Player player = event.getPlayer();
        AttributeLoader.StatusUpdate(player);
    }
    @EventHandler
    void onPlayerSwapHandItemsEvent(PlayerSwapHandItemsEvent event){
        if (event.isCancelled()) {
            return;
        }
        Player player = event.getPlayer();
        AttributeLoader.StatusUpdate(player);
    }
    @EventHandler
    void onInventoryCloseEvent(InventoryCloseEvent event){
        Player player = (Player) event.getPlayer();
        AttributeLoader.StatusUpdate(player);
    }
    @EventHandler
    void onPlayerDropEvent(PlayerDropItemEvent event){
        if (event.isCancelled()) {
            return;
        }
        Player player = event.getPlayer();
        AttributeLoader.StatusUpdate(player);
    }
    @EventHandler
    void onPlayerPickupItemEvent(PlayerPickupItemEvent event){
        if (event.isCancelled()) {
            return;
        }
        Player player = event.getPlayer();
        AttributeLoader.StatusUpdate(player);
    }
    @EventHandler
    void onPlayerInteractEvent(PlayerInteractEvent event) {
        if (event.isCancelled()) {
            return;
        }
        Player player = event.getPlayer();
        AttributeLoader.StatusUpdate(player);
    }
    @EventHandler
    void onPlayerJoinEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        AttributeLoader.StatusUpdate(player);
    }
    @EventHandler
    void onPlayerQuitEvent(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        ArathothAPI.UnregisterEntityData(player);
    }
    @EventHandler
    void onEntitySpawnEvent(CreatureSpawnEvent event)  {
        LivingEntity e = event.getEntity();
        AttributeLoader.StatusUpdate(e);
    }
    @EventHandler
    void onEntityDeathEvent(EntityDeathEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            ArathothAPI.UnregisterEntityData(event.getEntity());
        }
    }

}
