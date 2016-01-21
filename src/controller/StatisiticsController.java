package controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.google.common.eventbus.Subscribe;

import entities.Player;
import entities.PlayerList;
import event.NewTimeoutEvent;
import event.NewWinEvent;

public class StatisiticsController {
	
	private HashMap<Integer, Integer> playerWinner = new LinkedHashMap<>();
	private HashMap<Integer, Integer> playerTimeout = new LinkedHashMap<>();
	
	public StatisiticsController() {
		GlobalEventBus.getEventBus().register(this);
	}
	
	@Subscribe
	public void onAiWin(NewWinEvent event) {
		final int playerId = event.getPlayerId();
		Integer result = playerWinner.get(playerId);
		if(result == null) {
			playerWinner.put(playerId, 1);
		} else {
			playerWinner.put(playerId, ++result);
		}
	}
	
	@Subscribe
	public void onAiTimeout(NewTimeoutEvent event) {
		final int playerId = event.getPlayerId();
		Integer result = playerTimeout.get(playerId);
		if(result == null) {
			playerTimeout.put(playerId, 1);
		} else {
			playerTimeout.put(playerId, result++);
		}
	}
	
	public void printStatistics() {
		System.out.println();
		for (Map.Entry<Integer,Player> a:PlayerList.getPlayerList().entrySet()){
			System.out.println("Parameter for Player " + a.getKey() + ":" 
					+ " SV: " + a.getValue().getSv()
					+ " GV: " + a.getValue().getGv()
					+ " JSV: " + a.getValue().getJsv()
					+ " JJSV: " + a.getValue().getJjsv()
					+ " MV: " + a.getValue().getMv()
					+ " JV: " + a.getValue().getJv()
					+ " DV: " + a.getValue().getDv());
			Integer wins = playerWinner.get(a.getKey());
			if(wins == null) {
				System.out.println("Player " + a.getKey() + " did not win.");
			} else {
				System.out.println("Player " + a.getKey() + " won " + wins + " times.");
			}
			Integer timeouts = playerTimeout.get(a.getKey());
			if(timeouts == null) {
				System.out.println("Player " + a.getKey() + " had no timeouts.");
			} else {
				System.out.println("Player " + a.getKey() + " had " + timeouts + " timeouts.");
			}
			System.out.println();
		}
	}
}