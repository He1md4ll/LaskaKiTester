package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.common.eventbus.Subscribe;

import entities.Player;
import entities.PlayerList;
import event.MatchEndedEvent;
import event.MatchErrorEvent;
import event.NewTimeoutEvent;
import event.NewWinEvent;

public class StatisiticsController {
	
	private HashMap<Integer, Integer> playerWinner = new LinkedHashMap<>();
	private HashMap<Integer, Integer> playerTimeout = new LinkedHashMap<>();
	
	private HashMap<Integer, int[]> matchWinner = new LinkedHashMap<>();
	private HashMap<Integer, Integer> matchError = new LinkedHashMap<>();
	private HashMap<Integer, List<Integer>> matchPlayerTimeout = new LinkedHashMap<>();
	
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
		final int matchId = event.getMatchID();
		final int playerId = event.getPlayerId();
		Integer result = playerTimeout.get(playerId);
		if(result == null) {
			playerTimeout.put(playerId, 1);
		} else {
			playerTimeout.put(playerId, result++);
		}
		List<Integer> result2 = matchPlayerTimeout.get(matchId);
		if(result2 == null) {
			List<Integer> tmpList = new ArrayList<>();
			tmpList.add(playerId);
			matchPlayerTimeout.put(matchId, tmpList);
		} else {
			result2.add(playerId);
			matchPlayerTimeout.put(matchId, result2);
		}
	}
	
	@Subscribe
	public void onMatchEnd(MatchEndedEvent event) {
		matchWinner.put(event.getMatchID(), event.getWinner());
	}
	
	@Subscribe
	public void onMatchError(MatchErrorEvent event) {
		matchError.put(event.getMatchID(), event.getPlayerId());
	}
	
	public void printStatistics() {
		printMatchStats();
		printPlayerStats();
	}
	
	private void printPlayerStats() {
		System.out.println();
		System.out.println("Player stats:");
		for (Map.Entry<Integer,Player> a:PlayerList.getPlayerList().entrySet()){
			System.out.println("[Player " + a.getKey() + "]");
			System.out.println("\tParameter:"
					+ " SV: " + a.getValue().getSv()
					+ " GV: " + a.getValue().getGv()
					+ " JSV: " + a.getValue().getJsv()
					+ " JJSV: " + a.getValue().getJjsv()
					+ " MV: " + a.getValue().getMv()
					+ " JV: " + a.getValue().getJv()
					+ " DV: " + a.getValue().getDv());
			final Integer wins = playerWinner.get(a.getKey());
			if(wins == null) {
				System.out.println("\tWon rounds: NONE ");
			} else {
				System.out.println("\tWon rounds: " + wins);
			}
			final Integer timeouts = playerTimeout.get(a.getKey());
			if(timeouts == null) {
				System.out.println("\tTimouts in rounds: NONE");
			} else {
				System.out.println("\tTimouts in rounds: " + timeouts);
			}
			System.out.println();
		}
	}
	
	private void printMatchStats() {
		System.out.println();
		System.out.println("Match stats:");
		for(Map.Entry<Integer,int[]> match : matchWinner.entrySet()){
			System.out.println("[Match " + match.getKey() + "]");
			int round = 1;
			for(int i : match.getValue()){
				if (i != -1) {
					System.out.println("\tPlayer " + i + " won round " + round++);
				}
			}
			for(Map.Entry<Integer,List<Integer>> matchTimeout : matchPlayerTimeout.entrySet()){
				if(matchTimeout.getKey() == match.getKey()) {
					for(Integer player : matchTimeout.getValue()){
						System.out.println("\tPlayer " + player + " had timeout.");
					}
				}
			}
			for(Map.Entry<Integer,Integer> matchError : matchError.entrySet()) {
				if(matchError.getKey() == match.getKey()) {
					System.out.println("\tEncountered error during match. (detected by player " + matchError.getValue() + "). Possible error in AI.");
				}
			}
			System.out.println();
		}
	}
}