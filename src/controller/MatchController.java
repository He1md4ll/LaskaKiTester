package controller;

import java.util.Random;

import com.google.common.eventbus.Subscribe;

import entities.Player;
import event.MatchEndedEvent;
import event.MatchErrorEvent;
import event.NewDraftEvent;
import event.NewTimeoutEvent;
import event.NewWinEvent;
import event.ReverseWinEvent;

/**
 * Class to one match between two players.
 */
public class MatchController {
	public static String[] COLOR;
	
	private int matchId;
	private Player ai1;
	private Player ai2;
	private MoveController mc1;
	private MoveController mc2;
	
	private int round = -1;
	private int[] winner = new int[]{-1,-1,-1};
	
	public MatchController(int matchId, Player ai1, Player ai2) {
		GlobalEventBus.getEventBus().register(this);
		this.matchId = matchId;
		this.ai1 = ai1;
		this.ai2 = ai2;
		if (new Random().nextInt(1) == 0) {
			COLOR = new String[]{"black", "white"};
		} else {
			COLOR = new String[]{"white", "black"};
		}
	}
	
	@Subscribe
	public void onAiWin(NewWinEvent event) {
		final int matchId = event.getMatchID();
		final int playerId = event.getPlayerId();
		if (this.matchId == matchId) {
			System.out.println("[Match " + matchId + ", Player " + playerId + "] Win.");
			roundEnded(playerId);
		}
	}
	
	@Subscribe
	public void onAiTimeout(NewTimeoutEvent event) {
		final int matchId = event.getMatchID();
		final int playerId = event.getPlayerId();
		if (this.matchId == matchId) {
			System.out.println("[Match " + matchId + ", Player " + playerId + "] Lose due to timeout.");
			if (playerId == ai1.getId()) {
				GlobalEventBus.getEventBus().post(new NewWinEvent(matchId, ai2.getId()));
			} else {
				GlobalEventBus.getEventBus().post(new NewWinEvent(matchId, ai1.getId()));
			}
		}
	}
	
	@Subscribe
	public void onAiDraft(NewDraftEvent event) {
		final int matchId = event.getMatchID();
		final int playerId = event.getPlayerId();
		if (this.matchId == matchId) {
			final String aiAction = event.getAiAction();
			System.out.println("[Match " + matchId + ", Player " + playerId + "] Move to " + aiAction + " in " + event.getAiTime() + "ms.");
			if (playerId == ai1.getId()) {
				mc2.move(aiAction);
			} else {
				mc1.move(aiAction);
			}
		}
	}
	
	@Subscribe
	public void onMatchError(MatchErrorEvent event) {
		final int matchId = event.getMatchID();
		final int playerId = event.getPlayerId();
		if (this.matchId == matchId) {
			System.out.println("[Match " + matchId + ", Player " + playerId + "] Error during game.");
			stopGame();
			reverseWins(matchId);
			round = 2;
			playNextRound();
		}
	}
	
	private void reverseWins(int matchId) {
		for(int playerId : winner) {
			if(playerId != -1) {
				GlobalEventBus.getEventBus().post(new ReverseWinEvent(matchId, playerId));
			}
		}
		winner = new int[]{-1,-1,-1};
	}
	
	private void roundEnded(int aiId) {
		winner[round] = aiId;
		stopGame();
		playNextRound();
	}
	
	private void stopGame(){
		mc1.stopGame();
		mc2.stopGame();
	}
	
	private void playNextRound() {
		switch(round) {
			case 0 :
				play();
				break;
			case 1:
				/*if(winner[0] != winner[1]) {
					play();
					break;
				}*/
			case 2: 
				GlobalEventBus.getEventBus().post(new MatchEndedEvent(matchId, winner));
				break;
		}
	}
	
	public void play(){
		round++;
		startNewMatch();
	}
	
	private void startNewMatch() {
		switch(round) {
			case 0:
			case 2:
				mc1 = new MoveController(matchId, ai1, COLOR[0]);
				mc2 = new MoveController(matchId, ai2, COLOR[1]);
				break;
			case 1:
				mc1 = new MoveController(matchId, ai1, COLOR[1]);
				mc2 = new MoveController(matchId, ai2, COLOR[0]);
		}
	}
}