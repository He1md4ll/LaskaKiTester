package entities;

import java.util.UUID;

public class Player {
	private String parameters;
	private int score = 0;
	private String id;
	
	private int sv;
	private int gv;
	private int jsv;
	private int jjsv;
	private int mv;
	private int jv;
	private int dv;
	private boolean startedWithParas = false;
	
	public Player(String parameters){
		this.parameters = parameters;
		this.id = UUID.randomUUID().toString();
	}
	
	public Player(String parameters, int sv, int gv, int jsv, int jjsv, int mv, int jv, int dv){
		this.parameters = parameters;
		this.id = UUID.randomUUID().toString();
		this.sv = sv;
		this.gv = gv;
		this.jsv = jsv;
		this.jjsv = jjsv;
		this.mv = mv;
		this.jv = jv;
		this.dv = dv;
		this.startedWithParas = true;
	}
	
	public Player(){
		
	}
	
	public String getParameters(){
		return this.parameters;
	}
	
	public void setScore(int score){
		this.score = score;
	}
	
	public int getScore(){
		return this.score;
	}
	
	public void winScore(){
		score+=10;
	}
	
	public String getId(){
		return this.id;
	}

	public int getMv() {
		return mv;
	}

	public void setMv(int mv) {
		this.mv = mv;
	}

	public int getJv() {
		return jv;
	}

	public void setJv(int jv) {
		this.jv = jv;
	}

	public int getSv() {
		return sv;
	}

	public int getGv() {
		return gv;
	}

	public int getJsv() {
		return jsv;
	}

	public int getJjsv() {
		return jjsv;
	}

	public int getDv() {
		return dv;
	}

	public boolean isStartedWithParas() {
		return startedWithParas;
	}

	public void setStartedWithParas(boolean startedWithParas) {
		this.startedWithParas = startedWithParas;
	}
}
