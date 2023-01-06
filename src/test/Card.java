package test;

import java.io.IOException;

public class Card implements Comparable<Card> {

	private final int value;
	private final SUIT suit;
	
	public Card(String input)  throws IOException{
		super();
		
		switch(input.charAt(0)) {
		  case 'T':
			  this.value = 10;
		    break;
		  case 'J':
			  this.value = 11;
		    break;
		  case 'Q':
			  this.value = 12;
		    break;
		  case 'K':
			  this.value = 13;
		    break;
		  case 'A':
			  this.value = 14;
		    break;
		  default:
			  this.value = input.charAt(0) - '0';
		}
		
		switch(input.charAt(1)) {
		  case 'D':
				this.suit = SUIT.DIAMONDS;
		    break;
		  case 'C':
				this.suit = SUIT.CLUBS;
		    break;
		  case 'S':
				this.suit = SUIT.SPADES;
		    break;
		  case 'H':
				this.suit = SUIT.HEARTS;
		    break;
		  default:
				throw new IOException("Given suit is not among the four defined.");
		}
	}

	public int getValue() {
		return value;
	}

	public SUIT getSuit() {
		return suit;
	}
	
	@Override
	public String toString() {
		Integer.toString(value);
		
		return Integer.toString(value) + suit;
	}

	@Override
	public int compareTo(Card o) {
		if(this.value == o.getValue())
			return 0;
		return this.value > o.getValue()? 1 :-1;
	}
	
	
	
}
