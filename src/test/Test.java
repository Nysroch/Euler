package test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Test {
		  public static void main(String[] args) throws IOException {
		    // Read hands from file
			long start = System.nanoTime();
		    BufferedReader reader = new BufferedReader(new FileReader("poker.txt"));
		    String line;
		    int player1Wins = 0;
		    int linenum = 0;
		    while ((line = reader.readLine()) != null) {
		      String[] cards = line.split(" ");
		      linenum++;
		      int winner = getWinner(cards);
		      if (winner == 1) {
		        player1Wins++;
			    System.out.println(linenum);

		      }
		    }
		    reader.close();

		    // Print the number of hands won by Player 1
		    System.out.println(player1Wins);
		    
			long runtime = System.nanoTime() - start;
			System.out.println("Runtime: " + runtime / 1000000 + "ms (" + runtime
					+ "ns)");
		  }

		private static int getWinner(String[] cards) throws IOException {
			
		List<Card> player1hand = new ArrayList<>(); 
		List<Card> player2hand = new ArrayList<>(); 
		
		for(int i = 0; i < 5; i++) {
			player1hand.add(new Card(cards[i]));
			player2hand.add(new Card(cards[i+5]));
		}

		player1hand.sort((o1, o2) -> o1.compareTo(o2));

		player2hand.sort((o1, o2) -> o1.compareTo(o2));
		
		
		RANKING player1handRank = getRanking(player1hand);
		RANKING player2handRank = getRanking(player2hand);
		if(player1handRank.value > player2handRank.value) {
			return 1;
		}else if(player1handRank.value == player2handRank.value && player1handRank != RANKING.ROYALFLUSH) {
			if(compareHands(player1hand, player2hand, player1handRank)) {
				//System.out.println(String.valueOf(player1hand) + " - " + String.valueOf(player2hand) + " Player 1 wins");
				return 1;
			}
		}
		return 0;

		
		
		}

		private static boolean compareHands(List<Card> player1hand, List<Card> player2hand, RANKING Rank) {
			
			
			switch (Rank) {
			case STRAIGHT:
			case FLUSH:
			case HIGHCARD:
			case STRAIGHTFLUSH:
				
				for(int i = 4; i >= 0; i--) {
					if(player1hand.get(i).getValue() > player2hand.get(i).getValue()) {
						return true;
					}else if (player1hand.get(i).getValue() < player2hand.get(i).getValue()) {
						return false;
					}
				}
				break;
			case FULLHOUSE:
			case THREEOFAKIND:
			case FOUROFAKIND:
				if(player1hand.get(2).getValue() > player2hand.get(2).getValue()) {
					return true;
				}else if(player1hand.get(2).getValue() < player2hand.get(2).getValue()) {
					return false;
				}else {
					for(int i = 4; i >= 0; i--) {
						if(player1hand.get(i).getValue() > player2hand.get(i).getValue()) {
							return true;
						}else if (player1hand.get(i).getValue() < player2hand.get(i).getValue()) {
							return false;
						}
					}
				}
				break;
			case ONEPAIR:
			case TWOPAIR:
				if(getHigherPairValue(player1hand) > getHigherPairValue(player2hand)) {
					return true;
				}else if(getHigherPairValue(player1hand) < getHigherPairValue(player2hand)) {
					return false;
				}else if(getLowerPairValue(player1hand) > getLowerPairValue(player2hand)) {
					return true;
				}else if(getLowerPairValue(player1hand) < getLowerPairValue(player2hand)) {
					return false;
				}else {
					for(int i = 4; i >= 0; i--) {
						if(player1hand.get(i).getValue() > player2hand.get(i).getValue()) {
							return true;
						}else if (player1hand.get(i).getValue() < player2hand.get(i).getValue()) {
							return false;
						}
					}
				}
				
			default:
				return false;
			}
			return false;
		}

		private static int getHigherPairValue(List<Card> playerhand) {
			int highestpairvalue = 0;
			for(int i = 1; i < playerhand.size(); i++) {
				if(playerhand.get(i-1).getValue() == playerhand.get(i).getValue() && highestpairvalue < playerhand.get(i).getValue()) {
				highestpairvalue = playerhand.get(i).getValue();
				}
			}
			return highestpairvalue;
		}
		

		private static int getLowerPairValue(List<Card> playerhand) {
		
			int lowerPairValue = Integer.MAX_VALUE;
			for(int i = 1; i < playerhand.size(); i++) {
				if(playerhand.get(i-1).getValue() == playerhand.get(i).getValue() && lowerPairValue > playerhand.get(i).getValue()) {
					lowerPairValue = playerhand.get(i).getValue();
				}
			}
			return lowerPairValue;
		}

		private static RANKING getRanking(List<Card> playerhand) {
			boolean flush = playerhand.stream().allMatch(p -> p.getSuit().equals(playerhand.get(0).getSuit()));
			boolean straight = true;
			for(int i = 1; i < playerhand.size(); i++) {
				if(playerhand.get(i).getValue() != playerhand.get(i-1).getValue()+1) {
					straight = false;
				}
			}
			if(flush && straight && playerhand.get(0).getValue() == 10) {

				
				return RANKING.ROYALFLUSH;
			}
			
			if(flush && straight) {

				return RANKING.STRAIGHTFLUSH;
			}

			if(playerhand.get(0).getValue() == playerhand.get(3).getValue() || playerhand.get(1).getValue() == playerhand.get(4).getValue()){ 

				return RANKING.FOUROFAKIND;
			}
			
			if((playerhand.get(0).getValue() == playerhand.get(2).getValue() && playerhand.get(3).getValue() == playerhand.get(4).getValue())
					|| (playerhand.get(0).getValue() == playerhand.get(1).getValue() && playerhand.get(2).getValue() == playerhand.get(4).getValue())) {

				return RANKING.FULLHOUSE;
			}
			
			if(flush) {

				return RANKING.FLUSH;
			}
			
			if(straight) {

				return RANKING.STRAIGHT;
			}
			
			if(playerhand.get(0).getValue() == playerhand.get(2).getValue() || playerhand.get(1).getValue() == playerhand.get(3).getValue()
					|| playerhand.get(2).getValue() == playerhand.get(4).getValue()) {

				return RANKING.THREEOFAKIND;
			}
			if((playerhand.get(0).getValue() == playerhand.get(1).getValue() && playerhand.get(2).getValue() == playerhand.get(3).getValue())
					|| (playerhand.get(0).getValue() == playerhand.get(1).getValue() && playerhand.get(3).getValue() == playerhand.get(4).getValue())
					|| (playerhand.get(1).getValue() == playerhand.get(2).getValue() && playerhand.get(3).getValue() == playerhand.get(4).getValue())) {

				return RANKING.TWOPAIR;
			}

			if((playerhand.get(0).getValue() == playerhand.get(1).getValue())
					|| (playerhand.get(1).getValue() == playerhand.get(2).getValue())
					|| (playerhand.get(2).getValue() == playerhand.get(3).getValue())
					|| (playerhand.get(3).getValue() == playerhand.get(4).getValue())) {

				return RANKING.ONEPAIR;
			}
			
			return RANKING.HIGHCARD;
		
		}


	
}
