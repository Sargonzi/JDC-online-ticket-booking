package com.solt.jdc.boot.services;

import java.util.List;

import com.solt.jdc.boot.domains.Station;

public interface StationService {
	void addStation(Station station);

	List<Station> getAllStations();

	Station findById(int stationId);

	void deleteStation(int stationId);

	void updateStation(Station station);
	
	List<Station> findStationByCity(String city);
	
	List<Station> findStationByName(String stationName);
	
	List<Station> findStationByFilter(String stationName,String cityName);
}
