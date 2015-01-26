package com.wundero.HushNow;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Timer implements Runnable {

	private Hush hush;
	private Player player;
	private CommandSender sender;
	private boolean all;
	private int ID;
	
	public Timer(Hush h, Player p, CommandSender s, boolean b)
	{
		this.hush = h;
		this.player = p;
		this.sender = s;
		this.all = b;
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
		hush.stopTimer(player, sender, all, ID);
	}
	

}
