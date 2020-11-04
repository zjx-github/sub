package com.zjx.sub.starter;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;


import com.zjx.sub.model.*;
import com.zjx.sub.util.SubControl;
import com.zjx.sub.util.Subline;

public class Start {
	public static void main(String[] args) throws IOException {
		Subline s = new Subline("subway.txt");
		Scanner sc = new Scanner(System.in);
		while(true) {
			System.out.print("1.查找线路 2.查询两站之间的最短线路(quit代表退出): ");
			String choice = sc.nextLine();
			if(choice.equals("1")) {
				System.out.print("请输入想要搜索的线路: ");
				String linename = sc.nextLine();
				List<Station> stations=SubControl.getLineStation(linename);
				String temp="";
				if(stations==null)
					temp="该线路不存在";
				else {
				    for(int i=0;i<stations.size();i++) {
				    	if(i==stations.size()-1&&stations.get(i).getLinkStations().contains(stations.get(0)))
				    		temp=temp+stations.get(i).getstationName()+" --环线--";
				    	else
				    		temp=temp+stations.get(i).getstationName()+" ";
				    }
				}
				System.out.println(temp);
			}
			else if (choice.equals("2")) {
				System.out.print("请输入起点站: "); 
				String st = sc.nextLine();
				Station start= new Station(st);
				System.out.print("请输入终点站: "); 
				String ed = sc.nextLine();
				Station end = new Station(ed);
				for(List<Station> l:Subline.lineSet){
					for(int k=0;k<l.size();k++) {
						if(l.get(k).getstationName().equals(st)) {
							start=l.get(k);
			            }
			            if(l.get(k).getstationName().equals(ed)) {
			            	end=l.get(k);
			            }
					}
				}
				String context="";
				if(start==null||end==null)
					context="站点不存在";
				else {
					Result r=SubControl.shortestPath(start,end);
		            List<String> path=SubControl.getPath(r);
		            context="共经过"+(r.getDistance()+1)+"站\n";
		            for(String s1:path)
		            	context=context+s1+"\n";
				}
				System.out.println(context);
			}
			else if(choice.equals("quit")) {
				break;
			}
			else {
				System.out.println("ָ指令错误重新输入！或quit退出");
			}
		}
	}
}
