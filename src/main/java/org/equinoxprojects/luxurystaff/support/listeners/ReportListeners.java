package org.equinoxprojects.luxurystaff.support.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.equinoxprojects.luxurystaff.LuxuryStaff;
import org.equinoxprojects.luxurystaff.config.Messages;
import org.equinoxprojects.luxurystaff.logger.LogType;
import org.equinoxprojects.luxurystaff.logger.Logger;
import org.equinoxprojects.luxurystaff.support.Report;
import org.equinoxprojects.luxurystaff.support.ReportHandler;
import org.equinoxprojects.luxurystaff.support.ReportStatus;
import org.equinoxprojects.luxurystaff.util.NBTEditor;
import org.equinoxprojects.luxurystaff.util.Utils;

import java.util.HashMap;
import java.util.List;

public class ReportListeners implements Listener
{
    private final Logger logger = new Logger("LuxuryStaff");

    @EventHandler
    public void onClick(InventoryClickEvent e)
    {
        Player p = (Player) e.getWhoClicked();

        Inventory inv = e.getClickedInventory();

        if(!e.getView().getTitle().contains(ReportHandler.REPORT_INV_CODE))
            return;

        e.setCancelled(true);

        if(inv == null)
        {
            logger.log(LogType.ERROR, "Failed to process click in reports GUI...");
            logger.debug("inv is null");
            return;
        }

        ItemStack item = inv.getItem(e.getSlot());

        if(item == null || item.getType() == Material.AIR)
            return;

        ReportHandler handler = new ReportHandler();

        if(NBTEditor.contains(item, "button"))
        {
            int page = NBTEditor.getInt(item, "page");
            handler.getReportsInventory(page-1, inv);
            return;
        }

        int id = getId(item);

        if(id == -1)
            return;

        Report report = handler.getReport(id);

        if(e.isLeftClick())
            report.setStatus(ReportStatus.RESOLVED);
        else
            report.setStatus(ReportStatus.INVALID);

        handler.saveReport(report);

        if(!NBTEditor.contains(e.getInventory().getItem(0), "page"))
        {
            logger.log(LogType.ERROR, "Failed to read page number in inventory, unable to process click");
            return;
        }

        int page = NBTEditor.getInt(e.getInventory().getItem(0), "page"); // Item in top left will have page number nbt tag
        handler.getReportsInventory(page, inv);

        HashMap<String, String> placeholders = new HashMap<>();
        placeholders.put("%id%", Integer.toString(report.getId()));
        placeholders.put("%status%", report.getStatus().toString());
        p.sendMessage(Messages.REPORT_CLOSED.getMessage(placeholders));
    }

    private int getId(ItemStack item)
    {
        if(!NBTEditor.contains(item, "reportId"))
            return -1;

        return NBTEditor.getInt(item, "reportId");
    }


}
