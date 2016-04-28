package event;

public class NewTimeoutEvent {
	
	private int matchId;
	private int playerId;

	public NewTimeoutEvent(int matchId, int playerId) {
		this.matchId = matchId;
		this.playerId = playerId;
	}

	public int getMatchID() {
		return matchId;
	}
	
	public int getPlayerId() {
		return playerId;
	}
}
