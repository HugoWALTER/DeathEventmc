package fr.sayro.Sayro;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;


public class SayroListener implements Listener 
{

	private Sayro sayro;

	public SayroListener(Sayro sayro) {
		this.sayro = sayro;
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void qdJoueurSeConnecte(PlayerJoinEvent e) {
		
		Player p = e.getPlayer();
		
		
		if (!p.isOp()) {
			
		e.setJoinMessage(ChatColor.WHITE + "[" + ChatColor.GREEN + "+" + ChatColor.WHITE + "] " + ChatColor.YELLOW + e.getPlayer().getName());
		
		}
		
		if (p.isOp()) {
			
			if(e.getJoinMessage().contains("joined"));{
				
				e.setJoinMessage(ChatColor.YELLOW + "L'administrateur " + ChatColor.GREEN + e.getPlayer().getName() + ChatColor.YELLOW + " s'est connecté sur FightNations.");
			}
			}
			
		}
	/*public boolean joueurquijoue(PlayerJoinEvent e){
		Player p = e.getPlayer();
		if(p.hasPlayedBefore() == true){
			p.sendMessage(ChatColor.GREEN + "cc je me connecte !");
		}else
			return false;
		if(p.hasPlayedBefore() == false){
			p.sendMessage(ChatColor.GREEN + "Re-bonjour !");
			setupScoreboard(p);
		}
		return true;
	}*/
	
	public void setupScoreboard(Player p) {
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		Scoreboard board = manager.getNewScoreboard();
		 
		Objective objective = board.registerNewObjective("showhealth", "health");
		objective.setDisplaySlot(DisplaySlot.BELOW_NAME);
		objective.setDisplayName("" +ChatColor.RED + ChatColor.BOLD + "❤");
		 
		for(Player online : Bukkit.getOnlinePlayers()){
		  online.setScoreboard(board);
		  online.setHealth(online.getHealth()); //Update leur vie
		}
		
	}
	
    
	@EventHandler(priority = EventPriority.HIGHEST)
	public void qdJoueurSeDeconnecte(PlayerQuitEvent e) {
		
		Player p = e.getPlayer();
		
		if (!p.isOp()) {
		
		e.setQuitMessage(ChatColor.WHITE + "[" + ChatColor.DARK_RED + "-" + ChatColor.WHITE + "] " + ChatColor.YELLOW + e.getPlayer().getName());
		
		}
		
		if (p.isOp()) {
			
			if(e.getQuitMessage().contains("joined"));{
				
				e.setQuitMessage(ChatColor.YELLOW + "L'administrateur " + ChatColor.DARK_RED + e.getPlayer().getName() + ChatColor.YELLOW + " s'est déconnecté de FightNations.");
			}
			}
			
	}
	@EventHandler(priority = EventPriority.HIGHEST)   // event = d
	public void msg1(PlayerDeathEvent d) {

		Player p = d.getEntity().getPlayer();
		String deathMessage = d.getDeathMessage();
		String victim = d.getEntity().getName();
		EntityDamageEvent damageEvent = d.getEntity().getLastDamageCause();
		if(damageEvent instanceof EntityDamageByEntityEvent) {
			Entity damager = ((EntityDamageByEntityEvent)damageEvent).getDamager();
			//----------------------NORMAL KILL-------------------------
			if (deathMessage.contains("killed") || deathMessage.contains("slain") || deathMessage.contains("got finished")) {
				if (damager instanceof Player) {
					String itemname = ((Player)damager).getItemInHand().getItemMeta().getDisplayName();
						if (itemname==null) {
							d.setDeathMessage(ChatColor.GREEN + victim + ChatColor.YELLOW + " a été décapité par " + ChatColor.RED + ((Player)damager).getName());
							return;
						} else {
							d.setDeathMessage(ChatColor.GREEN + victim + ChatColor.YELLOW + " a été terassé par " + ChatColor.RED +((Player)damager).getName() + ChatColor.YELLOW + " en utilisant " + ChatColor.WHITE + ChatColor.AQUA + itemname);
							return;
						}
					
					}
				if (damager instanceof LivingEntity) {
					d.setDeathMessage(ChatColor.GREEN + victim + ChatColor.YELLOW + " est mort par " + ChatColor.RED + ((LivingEntity) damager).getName());
					return;
					}
				}
			//----------------------EXPLOSION-------------------------
			if (deathMessage.contains("blown up")) {
				if (damager instanceof Player) {
					d.setDeathMessage(ChatColor.GREEN + victim + ChatColor.YELLOW + " a été explosé par " + ChatColor.RED + ((Player)damager).getName());
					return;
					}
				if (damager instanceof LivingEntity) {
					d.setDeathMessage(ChatColor.GREEN + victim + ChatColor.YELLOW + " a explosé avec " + ChatColor.RED + ((LivingEntity) damager).getName());
					return;
					}
				}
			//----------------------WITHER/ENDER PEARL/SNOWBALL-------------------------
			if (deathMessage.contains("pummeled") || deathMessage.contains("withered")) {
				if (damager instanceof Player) {
					d.setDeathMessage(ChatColor.GREEN + victim + ChatColor.YELLOW + " a été victimisé par " + ChatColor.RED + ((Player)damager).getName());
					return;
					}
				if (damager instanceof LivingEntity) {
					d.setDeathMessage(ChatColor.GREEN + victim + ChatColor.YELLOW + " est mort par  " + ChatColor.RED + ((LivingEntity) damager).getName());
					return;
					}
				}
			//-----------------------ARROW---------------------------
			if (deathMessage.contains("shot") || deathMessage.contains("shooted")) {
				if (damager instanceof Projectile) {
					ProjectileSource shooter = ((Projectile)damager).getShooter();
					if (shooter instanceof Player) {
						String itemname = ((Player)shooter).getItemInHand().getItemMeta().getDisplayName();
						if (itemname==null) {
							d.setDeathMessage(ChatColor.GREEN + victim + ChatColor.YELLOW + " s'est fait tiré dessus par " + ChatColor.RED + ((Player)shooter).getName());
							return;
						} else {
							d.setDeathMessage(ChatColor.GREEN + victim + ChatColor.YELLOW + " s'est fait tiré dessus par " + ChatColor.RED + ((Player)shooter).getName() + ChatColor.YELLOW +  " utilisant " + ChatColor.WHITE + itemname);
							return;
						}
					}
					if (shooter instanceof LivingEntity) {
						d.setDeathMessage(ChatColor.GREEN + victim + ChatColor.YELLOW + " s'est fait tiré dessus par " + ChatColor.RED + ((LivingEntity) shooter).getName());
						return;
					}
				}
			}
			////-----------------------SPECIAL SNOWBALL LANCEE PAR UNE ENTITE---------------------------
			if (deathMessage.contains("pummeled")) {
				if (damager instanceof Projectile) {
					LivingEntity shooter = (LivingEntity) ((Projectile)damager).getShooter();
					if (shooter instanceof Player) {
						d.setDeathMessage(ChatColor.GREEN + victim + ChatColor.YELLOW + " s'est fait atomisé par une boule de neige de  " + ChatColor.RED + ((Player)shooter).getName());
						return;
						}
					if (shooter instanceof LivingEntity) {
						d.setDeathMessage(ChatColor.GREEN + victim + ChatColor.YELLOW + " s'est fait atomisé par une boule de neige de " + ChatColor.RED + ((LivingEntity) shooter).getName());
						return;
					}
				}
			}
			//-----------------------FIREBALL---------------------------
			if (deathMessage.contains("fireballed")) {
				if (damager instanceof Projectile) {
					LivingEntity shooter = (LivingEntity) ((Projectile)damager).getShooter();
					if (shooter instanceof Player) {
						d.setDeathMessage(ChatColor.GREEN + victim + ChatColor.YELLOW + " s'est fait atomisé par une boule de feu de  " + ChatColor.RED + ((Player)shooter).getName());
						return;
						}
					if (shooter instanceof LivingEntity) {
						d.setDeathMessage(ChatColor.GREEN + victim + ChatColor.YELLOW + " s'est fait atomisé par une boule de feu de " + ChatColor.RED + ((LivingEntity) shooter).getName());
						return;
					}
				}
			}
		}
		
		if (d.getDeathMessage().contains("from"))
			
		{
			d.setDeathMessage(ChatColor.GOLD + p.getName() + ChatColor.YELLOW + " s'est cassé la margoulette ... ");
			return;}
		if (d.getDeathMessage().contains("place"))
			
		{
			d.setDeathMessage(ChatColor.GOLD + p.getName() + ChatColor.YELLOW + " s'est cassé la margoulette ... ");
			return;}
		if(d.getDeathMessage().contains("pricked"))
		
		{
			d.setDeathMessage(ChatColor.GOLD + p.getName() + ChatColor.YELLOW + " a voulu faire un calin a un cactus...");
			return;}
		if(d.getDeathMessage().contains("hit"))
			
		{
			d.setDeathMessage(ChatColor.GOLD + p.getName() + ChatColor.YELLOW + " a voulu apprendre a voler...");
			return;}
		if(d.getDeathMessage().contains("too"))
			
		{
			d.setDeathMessage(ChatColor.GOLD + p.getName() + ChatColor.YELLOW + " a voulu apprendre a voler...");
			return;}
		if(d.getDeathMessage().contains("hard"))
			
		{
			d.setDeathMessage(ChatColor.GOLD + p.getName() + ChatColor.YELLOW + " a voulu apprendre a voler...");
			return;}
		if(d.getDeathMessage().contains("drowned"))
			
		{
			d.setDeathMessage(ChatColor.GOLD + p.getName() + ChatColor.YELLOW + " vient de boire la tasse...");
			return;}
		if(d.getDeathMessage().contains("starved"))
			
		{
			d.setDeathMessage(ChatColor.GOLD + p.getName() + ChatColor.YELLOW + " n'a pas trouvé a mangé...");
			return;}
		if(d.getDeathMessage().contains("lightning"))
			
		{
			d.setDeathMessage(ChatColor.GOLD + p.getName() + ChatColor.YELLOW + " s'est fait griller par l'orage...");
			return;}
		if(d.getDeathMessage().contains("lava"))
			
		{
			d.setDeathMessage(ChatColor.GOLD + p.getName() + ChatColor.YELLOW + " a rendu visite a Satan !");
			return;}
		if(d.getDeathMessage().contains("suffocated"))
			
		{
			d.setDeathMessage(ChatColor.GOLD + p.getName() + ChatColor.YELLOW + " a voulu respirer dans un mur ...");
			return;}
		if(d.getDeathMessage().contains("blew up"))
			
		{
			d.setDeathMessage(ChatColor.GOLD + p.getName() + ChatColor.YELLOW + " est monté au 7 ème ciel !");
			return;}
		if(d.getDeathMessage().contains("anvil"))
			
		{
			d.setDeathMessage(ChatColor.GOLD + p.getName() + ChatColor.YELLOW + " a été applati par une enclume !");
			return;}
		if(d.getDeathMessage().contains("magic"))
			
		{
			d.setDeathMessage(ChatColor.GOLD + p.getName() + ChatColor.YELLOW + " a été tué par le tout puissant magicien !");
			return;}
		if(d.getDeathMessage().contains("squashed"))
			
		{
			d.setDeathMessage(ChatColor.GOLD + p.getName() + ChatColor.YELLOW + " a été écrasé par une enclume !");
			return;}
		if(d.getDeathMessage().contains("burned"))
			
		{
			d.setDeathMessage(ChatColor.GOLD + p.getName() + ChatColor.YELLOW + " a cramé à petit feu ...");
			return;}
		if(d.getDeathMessage().contains("went up"))
			
		{
			d.setDeathMessage(ChatColor.GOLD + p.getName() + ChatColor.YELLOW + " a cramé à petit feu ...");
			return;}
		if(d.getDeathMessage().contains("crisp"))
			
		{
			d.setDeathMessage(ChatColor.GOLD + p.getName() + ChatColor.YELLOW + " a cramé à petit feu ...");
			return;}
		if(d.getDeathMessage().contains("flames"))
			
		{
			d.setDeathMessage(ChatColor.GOLD + p.getName() + ChatColor.YELLOW + " a cramé à petit feu ...");
			return;}
		if(d.getDeathMessage().contains("fire"))
			
		{
			d.setDeathMessage(ChatColor.GOLD + p.getName() + ChatColor.YELLOW + " a cramé à petit feu ...");
			return;}
		if(d.getDeathMessage().contains("fireballed"))
			
		{
			d.setDeathMessage(ChatColor.GOLD + p.getName() + ChatColor.YELLOW + " a été atomisé par une énorme boule de feu !");
			return;}
		if(d.getDeathMessage().contains("pummeled"))
			
		{
			d.setDeathMessage(ChatColor.GOLD + p.getName() + ChatColor.YELLOW + " a été atomisé par une petite boule de neige !");
			return;}
		if(d.getDeathMessage().contains("withered"))
			
		{
			d.setDeathMessage(ChatColor.GOLD + p.getName() + ChatColor.YELLOW + " a été atomisé par un WITHER !");
			return;}
		if(d.getDeathMessage().contains("world"))
			
		{
			d.setDeathMessage(ChatColor.GOLD + p.getName() + ChatColor.YELLOW + " a quitté notre beau monde ...");
			return;}
		if(d.getDeathMessage().contains("Killed"))
			
		{
			d.setDeathMessage(ChatColor.GOLD + p.getName() + ChatColor.YELLOW + " a mis fin à sa vie misérable ...");
			return;}
		if(d.getDeathMessage().contains("CustomGuardian"))
			
		{
			d.setDeathMessage(ChatColor.GOLD + p.getName() + ChatColor.YELLOW + " a voulu faire un bisous à un guardien...");
			return;}
		if(d.getDeathMessage().contains("Guardian"))
			
		{
			d.setDeathMessage(ChatColor.GOLD + p.getName() + ChatColor.YELLOW + " a voulu faire un bisous à un guardien...");
			return;}
		if (d.getDeathMessage().contains("fell"))
			
		{
			d.setDeathMessage(ChatColor.GOLD + p.getName() + ChatColor.YELLOW + " s'est cassé la margoulette ... ");
			return;}
		else
				
		if(d.getDeathMessage().contains("died"))
			
			{
				d.setDeathMessage(ChatColor.GOLD + p.getName() + ChatColor.YELLOW + " est mort pitoyablement...");
			return;}
		
		// continuer avec tous les messages de mort sur le wiki et ne pas oublie
		// de mettre des espaces apres 1er "
		return;}
	

	
	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerCommandpreprocess(PlayerCommandPreprocessEvent e) {

		Player p = e.getPlayer();
		String message = e.getMessage();

		String[] params = message.split(" ");

		if (params[0].equalsIgnoreCase("/heure")) {
			e.setCancelled(true);

			if (!p.isOp()) {
				p.sendMessage(ChatColor.RED + "NOPE !");
				return;
			}

			if (params.length == 2) {

				if (params[1].toLowerCase().startsWith("j")) {

					p.getWorld().setTime(0);
					p.sendMessage(ChatColor.GREEN
							+ "Il est l'heure de se lever !");

				} else if (params[1].toLowerCase().startsWith("n")) {

					p.getWorld().setTime(14000);
					p.sendMessage(ChatColor.GREEN + "Au dodo maintenant !");

				} else {
					p.sendMessage(ChatColor.RED + "/heure <jour|nuit>");

				}

			} else {
				p.sendMessage(ChatColor.RED + "/heure <jour|nuit>");

			}

		}

	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent e) {

		Player p = e.getPlayer();
		String message = e.getMessage();

		String[] params = message.split(" ");

		if (params[0].equalsIgnoreCase("/meteo")) {
			e.setCancelled(true);

			// p.sendMessage(ChatColor.AQUA + "Commande météo réalisée ! ");

			if (!p.isOp()) {
				p.sendMessage(ChatColor.RED + "Nope !");
				return;
			}

			if (params.length == 2) {

				if (params[1].toLowerCase().startsWith("p")) {

					p.getWorld().setThundering(true);
					p.getWorld().setStorm(true);
					p.sendMessage(ChatColor.GREEN
							+ "Mettez-vous à l'abris l'orage arrive ! ☔☔☔");

				} else if (params[1].toLowerCase().startsWith("s")) {

					p.getWorld().setThundering(false);
					p.getWorld().setStorm(false);
					p.sendMessage(ChatColor.GREEN
							+ "Oh, un rayon de soleil apparaît ... " + ChatColor.YELLOW + "☀☀☀");

				} else {
					p.sendMessage(ChatColor.RED + "/meteo <pluie|soleil>");

				}

			} else {
				p.sendMessage(ChatColor.RED + "/meteo <pluie|soleil>");

			}

		}
	}

}



// surement mettre priority en high