package controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
	
	public static String FOLDER_PATH = "";
	
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
		createFolder();
		startFirstMatches();
	}
	
	private void createFolder() {
		final String dateString = new SimpleDateFormat("yyyy-MM-dd_HH_mm_ss").format(new Date());
		FOLDER_PATH = dateString + " Tournament (" + matches.size() + " matches)/";
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