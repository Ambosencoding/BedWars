package dev;

import com.google.common.collect.Lists;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class TeamSelector implements Listener {

    private ArrayList<Player> players = new ArrayList<>();

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (p.getItemInHand().getType() == Material.ENCHANTED_BOOK) {
                openInventory(p);
            }
        }
    }

    private void openInventory(Player p) {
        if (players.contains(p))
            players.remove(p);

        Inventory inv = Bukkit.createInventory(null, 9, "§7Team-Selector");

        if (IceWars.getType().getAmount() == 2) {

            ItemStack blue = new ItemStack(Material.STAINED_CLAY, 1, (short) 3);
            ItemMeta blueMeta = blue.getItemMeta();
            blueMeta.setDisplayName("§bBlue Team");
            List<String> blueLore = Lists.newArrayList();
            blueLore.add(" ");
            IceWars.getTeams().get(0).getPlayers().forEach(player -> blueLore.add("§7- §e" + player.getName()));
            blueMeta.setLore(blueLore);
            blue.setItemMeta(blueMeta);

            ItemStack green = new ItemStack(Material.STAINED_CLAY, 1, (short) 5);
            ItemMeta greenMeta = blue.getItemMeta();
            greenMeta.setDisplayName("§aGreen Team");
            List<String> greenLore = Lists.newArrayList();
            greenLore.add(" ");
            IceWars.getTeams().get(1).getPlayers().forEach(player -> greenLore.add("§7- §e" + player.getName()));
            greenMeta.setLore(greenLore);
            green.setItemMeta(greenMeta);

            inv.setItem(3, blue);
            inv.setItem(5, green);

        } else {

            ItemStack blue = new ItemStack(Material.STAINED_CLAY, 1, (short) 3);
            ItemMeta blueMeta = blue.getItemMeta();
            blueMeta.setDisplayName("§bBlue Team");
            List<String> blueLore = Lists.newArrayList();
            blueLore.add(" ");
            IceWars.getTeams().get(0).getPlayers().forEach(player -> blueLore.add("§7- §e" + player.getName()));
            blueMeta.setLore(blueLore);
            blue.setItemMeta(blueMeta);

            ItemStack green = new ItemStack(Material.STAINED_CLAY, 1, (short) 5);
            ItemMeta greenMeta = blue.getItemMeta();
            greenMeta.setDisplayName("§aGreen Team");
            List<String> greenLore = Lists.newArrayList();
            greenLore.add(" ");
            IceWars.getTeams().get(1).getPlayers().forEach(player -> greenLore.add("§7- §e" + player.getName()));
            greenMeta.setLore(greenLore);
            green.setItemMeta(greenMeta);

            ItemStack red = new ItemStack(Material.STAINED_CLAY, 1, (short) 6);
            ItemMeta redMeta = red.getItemMeta();
            redMeta.setDisplayName("§bRed Team");
            List<String> redLore = Lists.newArrayList();
            redLore.add(" ");
            IceWars.getTeams().get(2).getPlayers().forEach(player -> redLore.add("§7- §e" + player.getName()));
            redMeta.setLore(redLore);
            red.setItemMeta(redMeta);

            ItemStack yellow = new ItemStack(Material.STAINED_CLAY, 1, (short) 4);
            ItemMeta yellowMeta = blue.getItemMeta();
            yellowMeta.setDisplayName("§aYellow Team");
            List<String> yellowLore = Lists.newArrayList();
            yellowLore.add(" ");
            IceWars.getTeams().get(3).getPlayers().forEach(player -> yellowLore.add("§7- §e" + player.getName()));
            yellowMeta.setLore(yellowLore);
            yellow.setItemMeta(yellowMeta);

            inv.setItem(1, blue);
            inv.setItem(3, green);
            inv.setItem(5, red);
            inv.setItem(7, yellow);

        }

        p.openInventory(inv);
        players.add(p);
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (!(e.getWhoClicked() instanceof Player)) return;
        Player p = (Player) e.getWhoClicked();

        if (players.contains(p)) {
            if (e.getCurrentItem() != null) {
                e.setCancelled(true);
                p.updateInventory();

                if (e.getCurrentItem().getDurability() == 3) {
                    IceWars.getTeams().get(0).addPlayer(p);
                } else if (e.getCurrentItem().getDurability() == 5) {
                    IceWars.getTeams().get(1).addPlayer(p);
                } else if (e.getCurrentItem().getDurability() == 6) {
                    IceWars.getTeams().get(2).addPlayer(p);
                } else if (e.getCurrentItem().getDurability() == 4) {
                    IceWars.getTeams().get(3).addPlayer(p);
                }

                closeInventory(p);
            }
        }
    }

    @EventHandler
    public void onKick(PlayerQuitEvent e) {
        closeInventory(e.getPlayer());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        closeInventory(e.getPlayer());
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        Player p = (Player) e.getPlayer();
        if (players.contains(p)) {
            players.remove(p);
        }
    }

    private void closeInventory(Player p) {
        if (players.contains(p)) {
            players.remove(p);
            p.closeInventory();
        }
    }

}
