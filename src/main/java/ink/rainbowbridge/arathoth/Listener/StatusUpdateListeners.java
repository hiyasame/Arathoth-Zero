package ink.rainbowbridge.arathoth.Listener;

import ink.rainbowbridge.arathoth.Attributes.AttributeManager;
import ink.rainbowbridge.arathoth.Attributes.AttributesData;
import ink.rainbowbridge.arathoth.Attributes.sub.AdditionalHealth;
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
        AttributeManager.StatusUpdate((LivingEntity)player);
        new AdditionalHealth().Action(event);
    }
    @EventHandler
    void onPlayerSwapHandItemsEvent(PlayerSwapHandItemsEvent event){
        if (event.isCancelled()) {
            return;
        }
        Player player = event.getPlayer();
        AttributeManager.StatusUpdate((LivingEntity)player);
        new AdditionalHealth().Action(event);
    }
    @EventHandler
    void onInventoryCloseEvent(InventoryCloseEvent event){
        Player player = (Player) event.getPlayer();
        AttributeManager.StatusUpdate((LivingEntity)player);
        new AdditionalHealth().Action(event);
    }
    @EventHandler
    void onPlayerDropEvent(PlayerDropItemEvent event){
        if (event.isCancelled()) {
            return;
        }
        Player player = event.getPlayer();
        AttributeManager.StatusUpdate((LivingEntity)player);
        new AdditionalHealth().Action(event);
    }
    @EventHandler
    void onPlayerPickupItemEvent(PlayerPickupItemEvent event){
        if (event.isCancelled()) {
            return;
        }
        Player player = event.getPlayer();
        AttributeManager.StatusUpdate((LivingEntity)player);
        new AdditionalHealth().Action(event);
    }
    @EventHandler
    void onPlayerInteractEvent(PlayerInteractEvent event) {
        if (event.isCancelled()) {
            return;
        }
        Player player = event.getPlayer();
        AttributeManager.StatusUpdate((LivingEntity)player);
        new AdditionalHealth().Action(event);
    }
    @EventHandler
    void onPlayerJoinEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        AttributeManager.StatusUpdate((LivingEntity)player);
        new AdditionalHealth().Action(event);
    }
    @EventHandler
    void onPlayerQuitEvent(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        AttributesData.AttrData.remove(((LivingEntity)player).getUniqueId().toString());
    }
    @EventHandler
    void onEntitySpawnEvent(CreatureSpawnEvent event)  {
        LivingEntity e = event.getEntity();
        AttributeManager.StatusUpdate(e);
        new AdditionalHealth().Action(event);
    }
    @EventHandler
    void onEntityDeathEvent(EntityDeathEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            AttributesData.AttrData.remove((event.getEntity()).getUniqueId().toString());
        }
    }

}
