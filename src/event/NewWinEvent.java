package event;

public class NewWinEvent {
	
	private int matchId;
	private int playerId;

	public NewWinEvent(int matchId, int playerId) {
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
