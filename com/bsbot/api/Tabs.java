package com.bsbot.api;

import java.awt.Point;

import com.bsbot.input.Mouse;
import com.bsbot.launcher.BSLoader;

public class Tabs {

	public final int COMBATOPTIONS = 0;
	public final int STATS = 1;
	public final int QUESTS = 2;
	public final int INVENTORY = 3;
	public final int EQUIPMENT = 4;
	public final int PRAYER = 5;
	public final int MAGIC = 6;
	public final int MAGE = MAGIC;
	public final int CLANCHAT = 7;
	public final int FRIENDS = 8;
	public final int IGNORES = 9;
	public final int LOGOUT = 10;
	public final int SETTINGS = 11;
	public final int EMOTES = 12;
	public final int MUSIC = 13;
	public final Point MAGE_TAB_POINT = new Point(733, 182);
	public final Point INVENTORY_TAB_POINT = new Point(636, 187);
	Mouse m = new Mouse();
	
	public void switchTab(int id){
		switch(id){
		case INVENTORY:
		m.clickMouse(INVENTORY_TAB_POINT, true);
		break;
		case MAGIC:
		m.clickMouse(MAGE_TAB_POINT, true);
		default:
			break;
		}
	}
	
	public int getTab(){
		return BSLoader.getClient().getOpenTab();
	}

}
