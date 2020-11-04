package com.zjx.sub.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import com.zjx.sub.model.*;

public class SubControl {
	private static List<Station> visited = new ArrayList<>(); //以访问过站点
	private static HashMap<Station, Result> resultset = new HashMap<>();//结果集
	private static Station nextStation() {//获取下一个站点
		int min=999999;
        Station rs = null;
        Set<Station> stations = resultset.keySet();
        for (Station station : stations) {
            if (visited.contains(station)) {
                continue;
            }
            Result result = resultset.get(station);
            if (result.getDistance() < min) {
                min = result.getDistance();
                rs = result.getEndStation();
            }
        }
        return rs;
	}
	
	private static List<String> getSameLine(List<String> line1,List<String> line2) {  //获取两条线路的相同
		List<String> sameline=new ArrayList<String>();
		for(String la:line1) {
			for(String lb:line2) {
				if(la.equals(lb))
					sameline.add(la);
			}
		}
		return sameline;
	}
	
	public static Result shortestPath(Station start, Station end) {//dijkstra算法处理最短路径
		for (List<Station> l:Subline.lineSet) {//构造结果集
			for(int i = 0 ; i < l.size(); i++) {
				Result re = new Result();
				re.setStartStation(start);
				re.setEndStation(l.get(i));
				re.setDistance(9999);
				re.setLinechange(0);
				resultset.put(l.get(i), re);
			}
			
		}
		for(Station s:start.getLinkStations()) {  //初始化
			resultset.get(s).setDistance(1);
			resultset.get(s).setLastStations(start);
			List<String> samelines=getSameLine(start.getLine(),s.getLine());
			resultset.get(s).setLine(samelines.get(0));
		}
		resultset.get(start).setDistance(0);
		visited.add(start);
		Station ns = nextStation();//获取下一站点
		while(ns!=null) {  
        	for(Station s:ns.getLinkStations()) {
        		if(resultset.get(ns).getDistance()+1<resultset.get(s).getDistance()) {  
        			resultset.get(s).setDistance(resultset.get(ns).getDistance()+1);
        			resultset.get(s).setLastStations(ns);
        			List<String> samelines=getSameLine(ns.getLine(),s.getLine());
        			if(!samelines.contains(resultset.get(ns).getLine())) {  
        				resultset.get(s).setLine(samelines.get(0));
        				resultset.get(s).setLinechange(1);
        			}
        			else {
        				resultset.get(s).setLine(resultset.get(ns).getLine());
        			}
        		}
        	}
        	visited.add(ns); 
        	ns = nextStation();
        }    
        return resultset.get(end);
	}
	
	public static List<Station> getLineStation(String linename){  //获取该线路的所有站点
		int flag=1;
		List<Station> temp = new ArrayList<Station>();
		for (List<Station> l:Subline.lineSet) {
			flag=1;
			for(Station s :l) {
				if(!s.getLine().contains(linename)) {
					flag=0;
					//System.out.println(s.getstationName());
				}
				else {
					temp.add(s);
				}
			}
			if(flag==1)
				return l;
		}	
		return temp;
	}
	
	public static List<String> getPath(Result r){  //生成推荐路线
		List<String> path=new ArrayList<String>();
		Stack<Station> tmp=new Stack<Station>();
		Station s=r.getLastStations();
		while(!s.equals(r.getStartStation())) {
			tmp.push(s);
			s=resultset.get(s).getLastStations();
		}
		path.add(r.getStartStation().getstationName());
		while(!tmp.empty()) {
			if(resultset.get(tmp.peek()).getLinechange()==1) {
				path.add("->换乘"+resultset.get(tmp.peek()).getLine());
				path.add(tmp.pop().getstationName());
			}
			else
				path.add(tmp.pop().getstationName());
		}
		if(r.getLinechange()==1) {
			path.add("->换乘"+r.getLine());
			path.add(r.getEndStation().getstationName());
		}
		else
		    path.add(r.getEndStation().getstationName());
		return path;
	}
	
}
