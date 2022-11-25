package com.mycom.myapp.util.market;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import com.mycom.myapp.info.dto.MarketDto;




public class MarketSAXHandler extends DefaultHandler {
	private List<MarketDto> marketList = new ArrayList<>();
	
	private String temp;
	private MarketDto marketDto;
	
	public void startElement(String uri, String localName, String qName, Attributes attr) {
		if(qName.equals("row")) {
			marketDto = new MarketDto();
		}
	}
	
	public void endElement(String uri, String localName, String qName) {
		if(qName.equals("GUNAME")) {
			marketDto.setGuName(temp);
			
		} else if(qName.equals("M_NAME")) {
			marketDto.setmName(temp);		
			
		} else if(qName.equals("M_ADDR")) {
			marketDto.setmAddr(temp);			
			
		} else if(qName.equals("LAT")) {
			marketDto.setLat(temp);			
			
		} else if(qName.equals("LNG")) {
			marketDto.setLng(temp);			
			
		} else if(qName.equals("row")) {
			marketList.add(marketDto);
		} 
	}
	
	
	public void characters(char[] ch, int start, int length) {
		temp = new String(ch, start, length);		
	}
	
	public List<MarketDto> getMarketList(){
		return marketList;
	}
}
