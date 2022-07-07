package org.equinoxprojects.luxurystaff.support;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.equinoxprojects.luxurystaff.files.ConfigFile;
import org.equinoxprojects.luxurystaff.files.FileManager;
import org.equinoxprojects.luxurystaff.logger.LogType;
import org.equinoxprojects.luxurystaff.logger.Logger;
import org.equinoxprojects.luxurystaff.util.NBTEditor;
import org.equinoxprojects.luxurystaff.util.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class ReportHandler
{
    private final Logger logger = new Logger("LuxuryStaff");
    private final ConfigFile config = FileManager.getInstance().getReportsConfig();
    private final FileConfiguration fileConfig = config.getConfig();

    public static final String REPORT_INV_CODE = Utils.colorize("&0&f&2&5&e&r");

    public ArrayList<Report> getOpenReports()
    {
        ArrayList<Report> reports = new ArrayList<>();

        if(!fileConfig.contains("reports"))
            return null;

        for(String s : fileConfig.getConfigurationSection("reports").getKeys(false))
        {
            if(     !fileConfig.contains("reports." + s + ".target") ||
                    !fileConfig.contains("reports." + s + ".reported") ||
                    !fileConfig.contains("reports." + s + ".reason"))
            {
                logger.log(LogType.ERROR, "Failed to load reports. Plugin may not function as expected.");
                logger.debug("Loading reports failed, target, reported, or reason is null in config file.");
                return null;
            }
            UUID target = UUID.fromString(fileConfig.getString("reports." + s + ".target"));
            UUID reported = UUID.fromString(fileConfig.getString("reports." + s + ".reported"));
            String reason = fileConfig.getString("reports." + s + ".reason");
            String status = fileConfig.getString("reports." + s + ".status");

            Report report = new Report(target, reported, reason, Integer.parseInt(s));

            if(status.equalsIgnoreCase("OPEN")) reports.add(report);
        }
        return reports;
    }

    public void saveReport(Report report)
    {
        fileConfig.set("reports." + report.getId() + ".target", report.getTarget().toString());
        fileConfig.set("reports." + report.getId() + ".reported", report.getReported().toString());
        fileConfig.set("reports." + report.getId() + ".reason", report.getReason());
        fileConfig.set("reports." + report.getId() + ".status", report.getStatus().toString());

        config.save();
    }

    public void removeReport(Report report)
    {
        fileConfig.set("reports." + report.getId(), null);

        config.save();
    }

    public void removeReport(int id)
    {
        for(Report report : getOpenReports())
        {
            if(report.getId() == id)
                fileConfig.set("reports." + id, null);
        }

        config.save();
    }

    public Report getReport(int id)
    {
        for(Report report : getOpenReports())
        {
            if(report.getId() == id)
                return report;
        }

        return null;
    }

    public ArrayList<Report> getReports(UUID id)
    {
        ArrayList<Report> reports = new ArrayList<>();

        if(getOpenReports() == null)
            return reports;

        for(Report report : getOpenReports())
        {
            if(report.getTarget().equals(id))
                reports.add(report);
        }

        return reports;
    }

    public ArrayList<Report> getGivenReports(UUID id)
    {
        ArrayList<Report> reports = new ArrayList<>();

        if(getOpenReports() == null)
            return reports;

        for(Report report : getOpenReports())
        {
            if(report.getReported().equals(id))
                reports.add(report);
        }

        return reports;
    }

    public void addReport(UUID target, UUID reported, String reason)
    {
        Report report = new Report(target, reported, reason, getId());
        saveReport(report);
    }

    private int getId()
    {
        int i = 0;
        while(true)
        {
            if(!fileConfig.contains("reports." + i))
                return i;

            i++;
        }
    }

    public void getReportsInventory(int page, Inventory inv) // 0 is first page
    {
        inv.clear();
        fillInventory(inv);

        if(getOpenReports() == null)
            return;

        int startFrom = (page * 36);

        ArrayList<ItemStack> reportItems = getReportItems(startFrom);

        for(ItemStack i : reportItems)
            inv.addItem(i);

        boolean isNextPage = startFrom + 36 < getOpenReports().size();

        if(page != 0)
            inv.setItem(45, backButton(page)); // Page starts on page 0, so it's just page

        if(isNextPage)
            inv.setItem(53, nextButton(page+2)); // Page starts on page 0, so it's page+2

        ItemStack topLeft = inv.getItem(0);
        topLeft = NBTEditor.set(topLeft, page, "page");

        inv.setItem(0, topLeft);
    }

    public Inventory getReportsInventory(int page)
    {
        Inventory inv = Bukkit.createInventory(null, 54, REPORT_INV_CODE + "Reports");

        getReportsInventory(page, inv);

        return inv;
    }

    private ItemStack backButton(int prevPage)
    {
        ItemStack backButton = new ItemStack(Material.ARROW);
        ItemMeta meta = backButton.getItemMeta();

        meta.setDisplayName(Utils.colorize("&a&lGo Back"));
        meta.setLore(Collections.singletonList(Utils.colorize("&7&oClick to go back to page &b&o&n" + prevPage)));

        backButton.setItemMeta(meta);

        backButton = NBTEditor.set(backButton, "back", "button");
        backButton = NBTEditor.set(backButton, prevPage, "page");

        return backButton;
    }

    private ItemStack nextButton(int nextPage)
    {
        ItemStack nextButton = new ItemStack(Material.ARROW);
        ItemMeta meta = nextButton.getItemMeta();

        meta.setDisplayName(Utils.colorize("&a&lGo Forwards"));
        meta.setLore(Collections.singletonList(Utils.colorize("&7&oClick to go forward to page &b&o&n" + nextPage)));

        nextButton.setItemMeta(meta);

        nextButton = NBTEditor.set(nextButton, "forward", "button");
        nextButton = NBTEditor.set(nextButton, nextPage, "page");


        return nextButton;
    }


    private void fillInventory(Inventory inv)
    {
        ItemStack fillerPane = Utils.fillerPane(false);
        for (int i = 0; i < 9; i++) {
            inv.setItem(i, fillerPane);
            inv.setItem(i + 45, fillerPane);
        }
    }

    private ArrayList<ItemStack> getReportItems(int startFrom)
    {
        ArrayList<ItemStack> reportItems = new ArrayList<>();

        int start = 0;
        for(Report report : getOpenReports())
        {
            if(start != startFrom)
            {
                start++;
                continue;
            }

            if(reportItems.size() > 36)
                break;

            ItemStack item = new ItemStack(Material.PAPER);
            ItemMeta meta = item.getItemMeta();

            Player target = Bukkit.getPlayer(report.getTarget());
            Player reported = Bukkit.getPlayer(report.getReported());

            meta.setDisplayName(Utils.colorize("&a&l" + target.getName()));
            List<String> lore = new ArrayList<>();

            lore.add(Utils.colorize("&7Reported By: &b" + reported.getName()));
            lore.add(Utils.colorize("&7Reason: &b" + report.getReason()));
            lore.add(Utils.colorize("&7Report ID: &b" + report.getId()));
            lore.add(" ");
            lore.add(Utils.colorize("&7Left-Click this report to mark it as &aresolved"));
            lore.add(Utils.colorize("&7Right-Click this report to mark it as &cinvalid"));

            meta.setLore(lore);

            item.setItemMeta(meta);
            item = NBTEditor.set(item, report.getId(), "reportId");

            reportItems.add(item);
        }

        return reportItems;
    }

}
