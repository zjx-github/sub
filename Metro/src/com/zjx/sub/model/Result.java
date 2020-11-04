package com.zjx.sub.model;

public class Result {
	private Station startStation;  //起始站
    private Station endStation;   //终点站
    private int distance;  //距离
    private Station lastStations;  //所在上一站
    private String line;   //所在线路
    private int linechange;  //是否换乘站
    
	public Result() {

    }

	public Station getStartStation() {
		return startStation;
	}

	public void setStartStation(Station startStation) {
		this.startStation = startStation;
	}

	public Station getEndStation() {
		return endStation;
	}

	public void setEndStation(Station endStation) {
		this.endStation = endStation;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public Station getLastStations() {
		return lastStations;
	}

	public void setLastStations(Station lastStations) {
		this.lastStations = lastStations;
	}

	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
	}

	public int getLinechange() {
		return linechange;
	}

	public void setLinechange(int linechange) {
		this.linechange = linechange;
	}  
}
