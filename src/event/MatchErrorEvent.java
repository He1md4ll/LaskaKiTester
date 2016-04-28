package event;

public class MatchErrorEvent {
	
	private int matchId;
	private int playerId;

	public MatchErrorEvent(int matchId, int playerId) {
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