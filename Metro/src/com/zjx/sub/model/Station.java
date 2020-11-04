package com.zjx.sub.model;

import java.util.ArrayList;
import java.util.List;

public class Station {
	private String stationName;  //站点名
	private List<String> line=new ArrayList<String>();  //所在线路名
	private List<Station> linkStations = new ArrayList<Station>();  //相邻站点

	public String getstationName() {
	    return stationName;
	}
	public void setstationName(String stationName) {
	    this.stationName = stationName;
	}
	public List<String> getLine() {
		return line;
	}
	public void setLine(List<String> line) {
		this.line = line;
	}
	public List<Station> getLinkStations() {
	    return linkStations;
	}
	public void setLinkStations(List<Station> linkStations) {
	    this.linkStations = linkStations;
	}


	public Station(String stationName, String line) {
	    this.stationName = stationName;
	    this.line.add(line);
	}

	public Station(String stationName) {
	    this.stationName = stationName;
	}

	public Station (){ 

	}
}

