package com.nortoh.src.commands;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import com.nortoh.src.BigComputer;
import com.nortoh.src.input.Parameter;
import com.nortoh.src.input.InputHandler.InputType;

public class PingCommand extends Command {

	public PingCommand(String commandName, BigComputer computer) {
		super(commandName, computer);
		setUsage("ping <site>");
	}

	@Override
	public void execCommand(ArrayList<Parameter> params) {
		if(params.get(0).type() == InputType.WEBSITE) {
			String website = formatted((String) params.get(0).value());
			ping(website);
			return;
		}
		
		printError("Input must be a website");
	}
	
	/**
	 * Formats the string into a URL
	 * 
	 * @param website
	 * @return
	 */
	private String formatted(String website) {
		StringBuilder sb = new StringBuilder();
		
		if(website.startsWith("www")) {
			sb.append("http://");
			sb.append(website);
		}
		
		if(!website.startsWith("http://")) {
			sb.append("http://");
			System.out.println("Riight???");
			sb.append(website);			
		}
		return sb.toString();
	}
	
	/**
	 * Pings the specified website
	 * @param website
	 */
	private void ping(String website) {
		URLConnection pingConnection;
		try {
			pingConnection = new URL(website).openConnection();
			pingConnection.connect();
			printConsole(website + " is alive!");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			printError(website + " seems to be down.");
		}
	}
}
