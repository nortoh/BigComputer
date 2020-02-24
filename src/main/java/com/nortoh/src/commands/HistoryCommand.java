package com.nortoh.src.commands;

import java.util.ArrayList;
import java.util.Map;

import com.nortoh.src.BigComputer;
import com.nortoh.src.input.Parameter;
import com.nortoh.src.states.State;

public class HistoryCommand extends Command {
	
	private int page;
	private int totalPages;
	private int itemsPerPage = 5;

	public HistoryCommand(String commandName, BigComputer computer) {
		super(commandName, computer);
	}


	public void execCommand(Map<String, String> params) {

		
		/* 
		if(params.containsKey("param0")) {
			if(params.get("param0").equalsIgnoreCase("next")) {
				showPage(page++);
				return;
			} else if(params.get("param0").equalsIgnoreCase("prev")) {
				if(page != 0) {
					showPage(page--);
				} else {
					System.out.println("You're already on page 0!");
				}
				return;
			} else if(params.get("param0").equalsIgnoreCase("front")) {
				if(page != 0) {
					showPage(0);
				} else {
					System.out.println("You're already at the front!");
				}
				return;
			} else if(params.get("param0").equalsIgnoreCase("clear")) {
				getComputer().getInputHandler().getHistory().clear();
				System.out.println("Cleared out history");
				return;
			}
		}
		*/ 
		
		showPage(page);
	}

	private void showPage(int page) {
		System.out.println("[ === Page " + page + " === ]");
//		ArrayList<String> history = getComputer().getInputHandler().getHistory();
		ArrayList<String> history = null;
		int remainder = totalPages % itemsPerPage;
		if(history.isEmpty()) {
			System.out.print("There is no history to show!");
		}
		
		if(totalPages % itemsPerPage != 0) {
			System.out.println("We need to index the last by " + (totalPages - (totalPages % itemsPerPage)));
		}
		
		System.out.println("Page:" + page);
		System.out.println("Total Pages: " + totalPages);
		System.out.println("Total Pages: " + totalPages);
		System.out.println("Remainder Pages % itemsPerPage: " + remainder);
		totalPages = history.size() / itemsPerPage + (history.size() / itemsPerPage);
	}

	@Override
	public void execCommand(ArrayList<Parameter> params) {
//		totalPages = getComputer().getInputHandler().getHistory().size() / itemsPerPage;
		page = 0;
		
		// set the computer into history mode
		if(getComputer().getState().getType() == State.Type.HISTORY) {
			printConsole("You are in history mode");
			return;
		}
		
		System.out.println("[ === Now in History Mode === ]");
		getComputer().getState().setType(State.Type.HISTORY);
		
		String direction = (String) params.get(0).value();
		System.out.println(direction);
		
	}
	
}
