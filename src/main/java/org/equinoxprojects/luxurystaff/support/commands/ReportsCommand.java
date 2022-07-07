package org.equinoxprojects.luxurystaff.support.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.equinoxprojects.luxurystaff.commands.LuxuryCommand;
import org.equinoxprojects.luxurystaff.commands.exceptions.MustBePlayerException;
import org.equinoxprojects.luxurystaff.commands.exceptions.NoPermissionException;
import org.equinoxprojects.luxurystaff.commands.exceptions.NotEnoughArgumentsException;
import org.equinoxprojects.luxurystaff.commands.exceptions.UnknownCommandException;
import org.equinoxprojects.luxurystaff.permissions.Permissions;
import org.equinoxprojects.luxurystaff.support.Report;
import org.equinoxprojects.luxurystaff.support.ReportHandler;
import org.equinoxprojects.luxurystaff.util.Utils;

import java.util.ArrayList;
import java.util.List;

public class ReportsCommand extends LuxuryCommand
{

    public ReportsCommand()
    {
        super("reports");
    }


    @Override
    public void execute(CommandSender sender, Command cmd, String label, String[] args, JavaPlugin mainclass) throws UnknownCommandException, NotEnoughArgumentsException, NoPermissionException, MustBePlayerException
    {
        if(!(sender instanceof Player))
            throw new MustBePlayerException();

        if(!sender.hasPermission(Permissions.VIEW_REPORTS.getPermission()))
            throw new NoPermissionException();

        Player p = (Player) sender;

        ReportHandler handler = new ReportHandler();

        p.openInventory(handler.getReportsInventory(0)); // 0 is first page
    }

    @Override
    public String getUsage() {
        return "/reports";
    }

    @Override
    public List<String> getAliases() {
        return new ArrayList<>();
    }

    @Override
    public boolean noSubCommands() {
        return true;
    }
}
