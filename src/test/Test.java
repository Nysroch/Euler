package test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Test {
		  public static void main(String[] args) throws IOException {
		    // Read hands from file
		    BufferedReader reader = new BufferedReader(new FileReader("poker.txt"));
		    String line;
		    int player1Wins = 0;
		    while ((line = reader.readLine()) != null) {
		      String[] cards = line.split(" ");
		      int winner = getWinner(cards);
		      if (winner == 1) {
		        player1Wins++;
		      }
		    }
		    reader.close();

		    // Print the number of hands won by Player 1
		    System.out.println(player1Wins);
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
		
		System.out.println(player1hand.toString() + ' '+ player2hand.toString());
		
		if(getRanking(player1hand) > getRanking(player2hand))
		{
			System.out.println("Winner player 1");
			return 1;
		}

		System.out.println("Winner player 2");
		return 0;
		
		
		}

		private static int getRanking(List<Card> playerhand) {
			if(playerhand.stream().allMatch(p -> p.getSuit().equals(playerhand.get(0).getSuit())) 
					&& playerhand.get(0).getValue()+4 == playerhand.get(playerhand.size()-1).getValue() 
					&& playerhand.get(playerhand.size()-1).getValue() == 14) {

				System.out.println("ROYALFLUSH");
				return RANKING.ROYALFLUSH.value;
			}
			
			if(playerhand.stream().allMatch(p -> p.getSuit().equals(playerhand.get(0).getSuit())) 
					&& playerhand.get(0).getValue()+4 == playerhand.get(playerhand.size()-1).getValue()) {
				System.out.println("STRAIGHTFLUSH");

				return RANKING.STRAIGHTFLUSH.value;
			}

			if(playerhand.get(0).getValue() == playerhand.get(3).getValue() || playerhand.get(1).getValue() == playerhand.get(4).getValue()){ 
				System.out.println("FOUROFAKIND");

				return RANKING.FOUROFAKIND.value;
			}
			
			if((playerhand.get(0).getValue() == playerhand.get(2).getValue() && playerhand.get(3).getValue() == playerhand.get(4).getValue())
					|| (playerhand.get(0).getValue() == playerhand.get(1).getValue() && playerhand.get(2).getValue() == playerhand.get(4).getValue())) {
				System.out.println("FULLHOUSE");

				return RANKING.FULLHOUSE.value;
			}
			
			if(playerhand.stream().allMatch(p -> p.getSuit().equals(playerhand.get(0).getSuit()))) {
				System.out.println("FLUSH");

				return RANKING.FLUSH.value;
			}
			
			if(playerhand.get(0).getValue()+4 == playerhand.get(4).getValue()) {
				
			}
			
			if(playerhand.get(0).getValue() == playerhand.get(2).getValue() || playerhand.get(1).getValue() == playerhand.get(3).getValue()
					|| playerhand.get(2).getValue() == playerhand.get(4).getValue()) {
				System.out.println("THREEOFAKIND");

				return RANKING.THREEOFAKIND.value;
			}
			if((playerhand.get(0).getValue() == playerhand.get(1).getValue() && playerhand.get(2).getValue() == playerhand.get(3).getValue())
					|| (playerhand.get(0).getValue() == playerhand.get(1).getValue() && playerhand.get(3).getValue() == playerhand.get(4).getValue())
					|| (playerhand.get(1).getValue() == playerhand.get(2).getValue() && playerhand.get(3).getValue() == playerhand.get(4).getValue())) {
				System.out.println("TWOPAIR");

				return RANKING.TWOPAIR.value;
			}

			if((playerhand.get(0).getValue() == playerhand.get(1).getValue())
					|| (playerhand.get(1).getValue() == playerhand.get(2).getValue())
					|| (playerhand.get(2).getValue() == playerhand.get(3).getValue())
					|| (playerhand.get(3).getValue() == playerhand.get(4).getValue())) {
				System.out.println("onepair");
				return RANKING.ONEPAIR.value;
			}
			int res = playerhand.stream().max((o1, o2) -> o1.getValue() > o2.getValue()? 1 : -1).get().getValue();
			System.out.println("HIGH CARD");
			return res;
		
		}


	
}
