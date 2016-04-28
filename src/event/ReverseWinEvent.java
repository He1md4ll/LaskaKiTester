package event;

public class ReverseWinEvent {
	
	private int matchId;
	private int playerId;

	public ReverseWinEvent(int matchId, int playerId) {
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
