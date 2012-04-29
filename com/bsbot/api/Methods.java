package com.bsbot.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bsbot.hooks.Npc;
import com.bsbot.input.Keyboard;
import com.bsbot.input.Mouse;
import com.bsbot.launcher.BSLoader;
import com.bsbot.wrappers.RSInterface;
import com.bsbot.wrappers.RSNPC;
import com.bsbot.launcher.BSLoader;
import com.bsbot.wrappers.RSPlayer;

public class Methods {

	// A cache of all the interfaces. Only as big as the maximum size of the
	// client's cache.
	Calculations calc = new Calculations();
	public Objects objects = new Objects(this);
	public Inventory inventory = new Inventory(this);
	public Interfaces interfaces = new Interfaces(this);
	public Bank banking = new Bank(this);
	public Mouse n = new Mouse();
	public Keyboard keyboard = new Keyboard();
	public Camera camera = new Camera(this);
	private RSInterface[] mainCache = new RSInterface[0];
	public Skills skills = new Skills();
	// If it doesn't fit in the above cache.
	private Map<Integer, RSInterface> sparseMap = new HashMap<Integer, RSInterface>();

	public static void sleep(int time) {
		try {
			Thread.sleep(time);
		} catch (Exception e) {
		}
	}
	
	public boolean isLoggedIn(){
		return BSLoader.getClient().isLoggedIn();
	}

	public static RSPlayer getMyPlayer() {
		return new RSPlayer(BSLoader.getClient().getMyPlayer());
	}

	public synchronized RSInterface[] getAll() {
		final com.bsbot.hooks.GameInterface[][] inters = BSLoader.getClient()
				.getInterfaceCache();
		if (inters == null) {
			return new RSInterface[0];
		}
		final List<RSInterface> out = new ArrayList<RSInterface>();
		for (int i = 0; i < inters.length; i++) {
			if (inters[i] != null) {
				final RSInterface in = get(i);
				out.add(in);
			}
		}
		return out.toArray(new RSInterface[out.size()]);
	}

	public synchronized RSInterface get(final int index) {
		RSInterface inter;
		final int cacheLen = mainCache.length;
		if (index < cacheLen) {
			inter = mainCache[index];
			if (inter == null) {
				inter = new RSInterface(index);
				mainCache[index] = inter;
			}
		} else {
			inter = sparseMap.get(index);
			if (inter == null) {
				if (index < cacheLen) {
					inter = mainCache[index];
					if (inter == null) {
						inter = new RSInterface(index);
						mainCache[index] = inter;
					}
				} else {
					inter = new RSInterface(index);
					sparseMap.put(index, inter);
				}
			}
		}
		return inter;
	}

	public RSNPC getNearestNpc(String name) {
		double dist = -1;
		RSNPC fin;
		RSNPC cur = null;
		int curX = getMyPlayer().getLocation().getX();
		int curY = getMyPlayer().getLocation().getY();
		// //// System.out.println("curX: " + curX + " curY: " +curY);
		for (int i = 0; i < BSLoader.getClient().getNpcs().length; i++) {
			Npc a = BSLoader.getClient().getNpcs()[i];
			if (a != null && a.getDefinition() != null
					&& a.getDefinition().getName() != null)
				if (a.getDefinition().getName().equalsIgnoreCase(name)) {
					int x, y;
					/*
					 * x = a.getX(); y = a.getY();
					 */
					RSNPC th = new RSNPC(a, i);
					x = th.getLocation().getX();
					y = th.getLocation().getY();
					double distance = Math.sqrt((curX - x) * (curX - x)
							+ (curY - y) * (curY - y));
					// /////////System.out.println("Distance: " + distance);
					if (distance < dist || dist == -1) {
						dist = distance;
						cur = new RSNPC(a, i);
					}
				}
		}
		return cur;

	}

	public RSNPC getNearestNpc(int id) {
		double dist = -1;
		RSNPC cur = null;
		int curX = getMyPlayer().getLocation().getX();
		int curY = getMyPlayer().getLocation().getY();
		// //// System.out.println("curX: " + curX + " curY: " +curY);
		for (int i = 0; i < BSLoader.getClient().getNpcs().length; i++) {
			if (i == id) {
				Npc a = BSLoader.getClient().getNpcs()[i];
				if (a != null && a.getDefinition() != null
						&& a.getDefinition().getName() != null) {
					int x, y;
					/*
					 * x = a.getX(); y = a.getY();
					 */
					RSNPC th = new RSNPC(a, i);
					x = th.getLocation().getX();
					y = th.getLocation().getY();
					double distance = Math.sqrt((curX - x) * (curX - x)
							+ (curY - y) * (curY - y));
					// /////////System.out.println("Distance: " + distance);
					if (distance < dist || dist == -1) {
						dist = distance;
						cur = new RSNPC(a, i);
					}
					return cur;

				}

			}
		}
		return cur;
	}
}
