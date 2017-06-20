package fr.sayro.Sayro;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Grades extends JavaPlugin {

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		if (!(sender instanceof Player))
		{
			sender.sendMessage(ChatColor.RED + "Seulement les joueurs peuvent avoir un surnom !");
			return true;
		}
		Player p = (Player) sender;
		if (cmd.getName().equalsIgnoreCase("nick"))
		{
			if (args.length == 0)
			{
				p.sendMessage(ChatColor.RED + "Tu n'as pas choisi de surnom !");
				return true;
			}
			String nick = "";
			for (String arg : args)
			{
				nick += arg + " ";
			}
			nick = nick.substring(0, nick.length() - 1);
			nick = nick.replaceAll("&", "§");
			p.sendMessage(ChatColor.GREEN + "Tu as choisi ton nom pour " + nick);
			this.getConfig().set(p.getName(), nick);
			this.saveConfig();
		}
		return true;
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerChat(AsyncPlayerChatEvent e)
	{
		if (this.getConfig().getString(e.getPlayer().getName()) != null)
		{
			e.getPlayer().setDisplayName(this.getConfig().getString(e.getPlayer().getName()) + ChatColor.RESET);
		}
	}
}