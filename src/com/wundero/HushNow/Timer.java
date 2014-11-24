package com.wundero.HushNow;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Timer extends BukkitRunnable {

	public int time = 120;
	private Hush hush;
	private Player player;
	private CommandSender sender;
	private boolean all;
	private int ID;
	
	public Timer(Hush h, Player p, CommandSender s, boolean b, int t)
	{
		this.hush = h;
		this.player = p;
		this.sender = s;
		this.all = b;
		this.time = t;
	}
	
	public void setID(int i)
	{
		this.ID = i;
	}
	
	public int getID()
	{
		return ID;
	}
	
	@Override
	public void run() {
		
		if(time==0)
		{
			hush.stopTimer(player, sender, all, ID);
		}
		
		time--;
	}
	

}
