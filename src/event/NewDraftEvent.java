package event;

public class NewDraftEvent {
	
	private int matchId;
	private int playerId;
	private String aiAction;
	private int aiTime;
	
	public NewDraftEvent(int matchId, int playerId, String aiAction, int aiTime) {
		this.matchId = matchId;
		this.playerId = playerId;
		this.aiAction = aiAction;
		this.aiTime = aiTime;
	}

	public int getMatchID() {
		return matchId;
	}

	public String getAiAction() {
		return aiAction;
	}

	public int getAiTime() {
		return aiTime;
	}

	public int getPlayerId() {
		return playerId;
	}
}
