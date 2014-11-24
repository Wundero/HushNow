package com.wundero.HushNow;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Hush extends JavaPlugin implements Listener {
	
	private final String prefix = ChatColor.BLACK+"["+ChatColor.GREEN+"HushNow"+ChatColor.BLACK+"]";
	private int toggled;
	private String exPlayer;
	private Map<String, Integer> ptoggled = new HashMap<String, Integer>();
 	
	// command - /hush <[-e] player|all> [time]
	
	@Override
	public void onEnable()
	{
		getServer().getPluginManager().registerEvents(this, this);
		ptoggled.clear();
		toggled = 1;
		exPlayer = null;
		
		if(getServer().getOnlinePlayers()!=null)
		{
			for(Player p : getServer().getOnlinePlayers())
			{
				if(toggled<1)
					ptoggled.put(p.getName(), -1);
				else
				{
					ptoggled.put(p.getName(), 1);
				}
			}
		}
	}
	
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if(cmd.getName().equalsIgnoreCase("hush")) 
		{
			if(sender.hasPermission("hushnow.hush"))
			{
				if(args.length>0)
				{
					if(args[0].equalsIgnoreCase("all")){
						if(args.length>1)
						{
							int id;
							try
							{
								id = Integer.valueOf(args[1]);
							}
							catch(Exception e)
							{
								sender.sendMessage(prefix+" "+ChatColor.RED+"That is not a valid time!");
								e.printStackTrace();
								return true;
							}
							toggleAll(sender, null);
							Timer timer = new Timer(this, null, sender, true, id);
							timer.setID(Bukkit.getScheduler().scheduleSyncRepeatingTask(this, timer, 20l, 20l));
							
							if(args.length>2)
							{
								String s="";
								int i = 0;
								for(String f : args)
								{
									if(i>2)
									{
										s+=f;
										s+=" ";
									}
									i++;
								}
								for(Player p : Bukkit.getOnlinePlayers())
								{
									if(sender instanceof Player)
									{
										if((Player) sender!=p)
										{
											p.sendMessage(prefix+" "+ChatColor.RED+"You have been hushed for: "+s);
										}
									}
								}
							}
							
							return true;
						}
						else
						{
							if(args.length>1)
							{
								String s="";
								int i = 0;
								for(String f : args)
								{
									if(i>1)
									{
										s+=f;
										s+=" ";
									}
									i++;
								}
								for(Player p : Bukkit.getOnlinePlayers())
								{
									if(sender instanceof Player)
									{
										if((Player) sender!=p)
										{
											p.sendMessage(prefix+" "+ChatColor.RED+"You have been hushed for: "+s);
										}
									}
								}
							}
							toggleAll(sender, null);
							return true;
						}
					}
					else if(args[0].equalsIgnoreCase("-e"))
					{
						if(args.length>1)
						{
							for(Player p : Bukkit.getOnlinePlayers())
							{
								if(args[1].equalsIgnoreCase(p.getName()))
								{
									exPlayer = p.getName();
									getServer().getLogger().info(p.getName()+" has been set as the exempted player!");
								}
							}
							if(args.length>2)
							{
								int id;
								try
								{
									id = Integer.valueOf(args[1]);
								}
								catch(Exception e)
								{
									sender.sendMessage(prefix+" "+ChatColor.RED+"That is not a valid time!");
									e.printStackTrace();
									return true;
								}
								Timer timer = new Timer(this, null, sender, true, id);
								timer.setID(Bukkit.getScheduler().scheduleSyncRepeatingTask(this, timer, 20l, 20l));
								toggleAll(sender, Bukkit.getPlayer(exPlayer));
								if(args.length>3)
								{
									String s="";
									int i = 0;
									for(String f : args)
									{
										if(i>3)
										{
											s+=f;
											s+=" ";
										}
										i++;
									}
									for(Player p : Bukkit.getOnlinePlayers())
									{
										if(sender instanceof Player)
										{
											if((Player) sender!=p)
											{
												p.sendMessage(prefix+" "+ChatColor.RED+"You have been hushed for: "+s);
											}
										}
									}
								}
								
								return true;
							}
							else
							{
								if(args.length>2)
								{
									String s="";
									int i = 0;
									for(String f : args)
									{
										if(i>2)
										{
											s+=f;
											s+=" ";
										}
										i++;
									}
									for(Player p : Bukkit.getOnlinePlayers())
									{
										if(sender instanceof Player)
										{
											if((Player) sender!=p)
											{
												p.sendMessage(prefix+" "+ChatColor.RED+"You have been hushed for: "+s);
											}
										}
									}
								}
								toggleAll(sender, Bukkit.getPlayer(exPlayer));
								return true;
							}
						}
						else
						{
							sender.sendMessage(prefix+" "+ChatColor.RED+"You need to list a player!");
							return true;
						}
					}
					else
					{
						for(Player p : Bukkit.getOnlinePlayers())
						{
							if(args[0].equalsIgnoreCase(p.getName()))
							{
								if(!p.hasPermission("hushnow.exempt")){
									if(args.length>1)
									{
										int id;
										try
										{
											id = Integer.valueOf(args[1]);
										}
										catch(Exception e)
										{
											sender.sendMessage(prefix+" "+ChatColor.RED+"That is not a valid time!");
											e.printStackTrace();
											return true;
										}
										toggleHush(p, sender);
										Timer timer = new Timer(this, p, sender, true, id);
										timer.setID(Bukkit.getScheduler().scheduleSyncRepeatingTask(this, timer, 20l, 20l));
										if(args.length>2)
										{
											String s="";
											int i = 0;
											for(String f : args)
											{
												if(i>2)
												{
													s+=f;
													s+=" ";
												}
												i++;
											}
											for(Player pl : Bukkit.getOnlinePlayers())
											{
												if(sender instanceof Player)
												{
													if((Player) sender!=pl)
													{
														pl.sendMessage(prefix+" "+ChatColor.RED+"You have been hushed for: "+s);
													}
												}
											}
										}
										return true;
									}
									else
									{
										if(args.length>1)
										{
											String s="";
											int i = 0;
											for(String f : args)
											{
												if(i>1)
												{
													s+=f;
													s+=" ";
												}
												i++;
											}
											for(Player pl : Bukkit.getOnlinePlayers())
											{
												if(sender instanceof Player)
												{
													if((Player) sender!=pl)
													{
														pl.sendMessage(prefix+" "+ChatColor.RED+"You have been hushed for: "+s);
													}
												}
											}
										}
										toggleHush(p, sender);
										return true;
									}
								}
								else sender.sendMessage(prefix+" "+ChatColor.RED+"That player is unhushable!");
								return true;
							}
						}
						sender.sendMessage(prefix+" "+ChatColor.RED+"There is no player with that name!");
						return true;
					}
				}
				else
				{
					sender.sendMessage(prefix+" "+ChatColor.RED+"Type /hush <[-e] player|all> [time]");
					return true;
				}
			}
			sender.sendMessage(prefix+" "+ChatColor.RED+"You do not have permission for this command!");
			return true;
		}
		
		return true;
	}
	
	
	private void toggleHush(Player p, CommandSender s)
	{
		int h = ptoggled.get(p.getName());
		h*=-1;
		ptoggled.put(p.getName(), h);
		if(h>0)
		{
			s.sendMessage(prefix+" "+ChatColor.GREEN+p.getName()+" is no longer hushed!");
			p.sendMessage(prefix+" "+ChatColor.GREEN+"You can now speak!");
		}
		else
		{
			s.sendMessage(prefix+" "+ChatColor.GREEN+p.getName()+" is now hushed!");
			p.sendMessage(prefix+" "+ChatColor.RED+"You have been hushed!");
		}
			
	}
	
	private void toggleAll(CommandSender s, Player player)
	{
		toggled*=-1;
		if(player==null)
		{
			for(Player p : Bukkit.getOnlinePlayers())
			{
				ptoggled.put(p.getName(), toggled);
				if(toggled<1)
				{
					if(!p.hasPermission("hushnow.exempt"))
						p.sendMessage(prefix+" "+ChatColor.RED+"You have been hushed!");
				}
				else
				{
					if(!p.hasPermission("hushnow.exempt"))
						p.sendMessage(prefix+" "+ChatColor.GREEN+"You can now speak!");
				}
			}
			if(toggled>1)
			{
				s.sendMessage(prefix+" "+ChatColor.GREEN+"All players are no longer hushed!");
			}
			else if(toggled<1)
			{
				s.sendMessage(prefix+" "+ChatColor.GREEN+"All players are now hushed!");
			}
			else
			{
				s.sendMessage(prefix+" "+ChatColor.GREEN+"All players are no longer hushed!");
			}
		}
		else
		{
			for(Player p : Bukkit.getOnlinePlayers())
			{
				if(player!=p)
				{
					ptoggled.put(p.getName(), toggled);
					if(toggled<1)
					{
						if(!p.hasPermission("hushnow.exempt"))
							p.sendMessage(prefix+" "+ChatColor.RED+"You have been hushed!");
					}
					else
					{
						if(!p.hasPermission("hushnow.exempt"))
							p.sendMessage(prefix+" "+ChatColor.GREEN+"You can now speak!");
					}
				}
				
			}
			if(toggled>1)
			{
				s.sendMessage(prefix+" "+ChatColor.GREEN+"All players except "+player.getName()+" are no longer hushed!");
			}
			else if(toggled<1)
			{
				s.sendMessage(prefix+" "+ChatColor.GREEN+"All players except "+player.getName()+" are now hushed!");
			}
			else
			{
				s.sendMessage(prefix+" "+ChatColor.GREEN+"All players except "+player.getName()+" are no longer hushed!");
			}
			exPlayer = null;
		}
	}
	
	public void stopTimer(Player p, CommandSender s, boolean b, int id)
	{
		Bukkit.getScheduler().cancelTask(id);
		if(b) toggleHush(p, s); else toggleAll(s, exPlayer==null ? null : Bukkit.getPlayer(exPlayer));
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e)
	{
		if(!ptoggled.containsKey(e.getPlayer().getName()))
		{
			if(toggled<1)
				ptoggled.put(e.getPlayer().getName(), -1);
			else
			{
				ptoggled.put(e.getPlayer().getName(), 1);
			}
		}
	}
	
	@EventHandler
	public void onPlayerPreprocessCommand(PlayerCommandPreprocessEvent e)//TODO blacklist/whitelist of commands
	{
		if(ptoggled.get(e.getPlayer().getName())<0&&!(e.getPlayer().hasPermission("hushnow.exempt")))
		{
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onAsynchPlayerChat(AsyncPlayerChatEvent e)
	{
		if(ptoggled.get(e.getPlayer().getName())<0&&!(e.getPlayer().hasPermission("hushnow.exempt")))
		{
			e.setCancelled(true);
		}
	}

}
