package controller;

import java.util.ArrayList;
import java.util.Map;

import com.google.common.eventbus.Subscribe;

import entities.Player;
import entities.PlayerList;
import event.MatchEndedEvent;

/**
 * Class to play a full tournament with round robin
 * AIs are to be added with addAi()
 * Tournament is started with startTournament()
 */
public class TournamentController {
	
	private StatisiticsController stats;
	private final ArrayList<MatchController> matches = new ArrayList<>();
	private int matchCounter = 0;
	private int currentlyPlaying = 0;
	
	public TournamentController() {
		stats = new StatisiticsController();
	}
	
	public void addAi(Player ai){
		PlayerList.addAi(ai);
	}
	
	public void startTournament(){
		GlobalEventBus.getEventBus().register(this);
		for (Map.Entry<Integer,Player> a:PlayerList.getPlayerList().entrySet()){
			for (Map.Entry<Integer,Player> b:PlayerList.getPlayerList().entrySet()){
				if (!a.getKey().equals(b.getKey()) && !PlayerList.hasPlayerDoneAllMatches(b.getKey())){
					MatchController mc = new MatchController(matches.size(), a.getValue(), b.getValue());
					matches.add(mc);
				}
			}
			PlayerList.playerDoneAllMachtes(a.getKey());	
		}
		startFirstMatches();
	}
	
	private void startFirstMatches() {
		System.out.println("Tournament consists of " + matches.size() + " matches.");
		for(; matchCounter < LaskaKITester.MAX_MATCHES; matchCounter++) {
			if(matches.size() > matchCounter) {
				matches.get(matchCounter).play();
				currentlyPlaying++;
			}
		}
	}

	@Subscribe
	public void onMatchEnd(MatchEndedEvent event) {
		currentlyPlaying--;
		if(matches.size() > matchCounter) {
			matches.get(matchCounter++).play();
			currentlyPlaying++;
		} else if (currentlyPlaying == 0) {
			stats.printStatistics();
			System.out.println("Tournament over.");
		} else {
			System.out.println("Waiting for " + currentlyPlaying + " matches to finish..."); 
		}
	}
}