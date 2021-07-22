package util;

import java.util.ArrayList;

public enum Mapa {
	
	MAPA_FLORESTA,
	MAPA_CALABOUÇO;
	
	public static ArrayList<Mapa> allMapas = new ArrayList<>();

	public static ArrayList<Mapa> addAll() {
		allMapas.add(MAPA_FLORESTA);
		allMapas.add(MAPA_CALABOUÇO);
		
		return allMapas;
	}
}
