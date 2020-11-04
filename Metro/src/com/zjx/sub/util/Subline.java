package com.zjx.sub.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import com.zjx.sub.model.*;

public class Subline {
	public static LinkedHashSet<List<Station>> lineSet = new LinkedHashSet<List<Station>>(); //线路集合
	public Subline(String path) throws IOException{
        File filename = new File(path); 
        InputStreamReader reader = new InputStreamReader( new FileInputStream(filename)); 
        BufferedReader br = new BufferedReader(reader); 
        String content="";
        content=br.readLine();  //读第一行获取总的线路和
        int linenum=Integer.parseInt(content);
        for(int i=0;i<linenum;i++) {  //构造集合增加线路
        	content=br.readLine();
        	List<Station> line=new ArrayList<Station>();
        	String[] linearr=content.split(" "); 
        	String linename=linearr[0];
        	for(int j=1;j<linearr.length;j++) {  //向线路中添加站点
        		//System.out.println();
        		int flag=0;
        		for(List<Station> l:lineSet) {  //处理换乘
        			for(int k=0;k<l.size();k++) {
        				if(l.get(k).getstationName().equals(linearr[j])) {  
        					//System.out.println(l.get(k).getLine());
        					List<String> newline=l.get(k).getLine();
        					newline.add(linename);
        					l.get(k).setLine(newline);
        					line.add(l.get(k));
        					flag=1;
        					break;
        				}
        			}
        			if(flag==1)
        				break;
        		}
        		if(j==linearr.length-1&&linearr[j].equals(linearr[1])) {  //环线
        			line.get(0).getLinkStations().add(line.get(line.size()-1));
        			line.get(line.size()-1).getLinkStations().add(line.get(0));
        			flag=1;
        		}
        		if(flag==0)
        			line.add(new Station(linearr[j],linename));
        	}
        	for(int j=0;j<line.size();j++) {  //处理车站相邻车站
        		List<Station> newlinkStations=line.get(j).getLinkStations();
        		if(j==0) {
        			newlinkStations.add(line.get(j+1));
        			line.get(j).setLinkStations(newlinkStations);
        		}
        		else if(j==line.size()-1) {
        			newlinkStations.add(line.get(j-1));
        			line.get(j).setLinkStations(newlinkStations);
        		}
        		else {
        			newlinkStations.add(line.get(j+1));
        			newlinkStations.add(line.get(j-1));
        			line.get(j).setLinkStations(newlinkStations);
        		}
        	}
        	lineSet.add(line);
        	//System.out.println(lineSet.size());
        }
        br.close();
	}
}
