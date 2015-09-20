/**
* <dl>
* <dt> File Name:
* <dd> Card.java
*
* <dt> Description:
* <dd> This class represents a card in a game.
* </dl>
*
* @author Paula Lopez Pozuelo
*/
package cardGame;

public class Card {

	public enum Suit {
		SPADES, HEARTS, DIAMONDS, CLUBS;
	}
	
	public enum Rank {
	TWO, THREE, FOUR, FIVE,
	SIX, SEVEN, EIGHT, NINE,
	TEN, JACK, QUEEN, KING, ACE
	}
	
	private Suit suit;
	private Rank rank;

	/*
	 * Constructor that takes rank and suit of the card to initialise.
	 */
	public Card(Rank rank, Suit suit) {
		this.rank = rank;
		this.suit = suit;
	}

	
	/*
	 * Getter method that returns the card's suit.
	 */
	public Suit getSuit() {
	    return suit;
	}
	
	/*
	 * Setter method to modify the card's suit.
	 */
	public void setSuit(Suit s) {
	    this.suit = s;
	}

	/*
	 * Getter method that returns the card's suit.
	 */
	public Rank getRank() {
	    return rank;
	}

	/*
	 * Setter method to modify the card's rank.
	 */
	public void setRank(Rank r) {
	    this.rank = r;
	}

	/*
	 * Method that returns true if the card passed to it has the same rank
	 */
	public boolean sameRank(Card otherCard) {
	    return (rank == otherCard.getRank());
	}

	/*
	 * Method that returns true if the card passed to it has the same suit
	 */
	public boolean sameSuit(Card otherCard) {
		return (this.suit == otherCard.getSuit());
	}

	/*
	 * Method that returns a string to represent the current card.
	 */
	public String toString() {
	    String cardString = "";
	    switch (rank) {
	      case ACE:
	        cardString += "Ace";
	        break;
	      case TWO:
	        cardString += "Two";
	        break;
	      case THREE:
	        cardString += "Three";
	        break;
	      case FOUR:
	        cardString += "Four";
	        break;
	      case FIVE:
	        cardString += "Five";
	        break;
	      case SIX:
	        cardString += "Six";
	        break;
	      case SEVEN:
	        cardString += "Seven";
	        break;
	      case EIGHT:
	        cardString += "Eight";
	        break;
	      case NINE:
	        cardString += "Nince";
	        break;
	      case TEN:
	        cardString += "Ten";
	        break;
	      case JACK:
	        cardString += "Jack";
	        break;
	      case QUEEN:
	        cardString += "Queen";
	        break;
	      case KING:
	        cardString += "King";
	        break;
	    }    
	    cardString += " of ";
	    switch (suit) {
	      case SPADES:
	        cardString += "Spades";
	        break;
	      case HEARTS:
	        cardString += "Hearts";
	        break;
	      case DIAMONDS:
	        cardString += "Diamonds";
	        break;
	      case CLUBS:
	        cardString += "Clubs";
	        break;
	    }
	    return cardString;
	}	
}
