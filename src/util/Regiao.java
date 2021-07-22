package util;

import java.util.ArrayList;

public enum Regiao {
	
	REGIAO_FLORESTA,
	REGIAO_MANGUE,
	REGIAO_CALABOU�O;
	
	public static ArrayList<Regiao> allRegioes = new ArrayList<>();

	public static ArrayList<Regiao> addAll() {
		allRegioes.add(REGIAO_FLORESTA);
		allRegioes.add(REGIAO_CALABOU�O);
		
		return allRegioes;
	}
	
}
