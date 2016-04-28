package event;

public class MatchEndedEvent {
	
	private int matchID;
	private int[] winner;
	public MatchEndedEvent(int matchID, int[] winner) {
		this.matchID = matchID;
		this.winner = winner;
	}
	public int getMatchID() {
		return matchID;
	}
	public int[] getWinner() {
		return winner;
	}
}