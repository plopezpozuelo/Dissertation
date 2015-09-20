/**
* <dl>
* <dt> File Name:
* <dd> Deck.java
*
* <dt> Description:
* <dd> This class represents a deck in a game of cards.
* </dl>
*
* @author Paula Lopez Pozuelo
*/
package cardGame;
import java.util.*;

public class Deck {

	// Arraylist to hold all the cards that are currently in the deck.
	private ArrayList <Card> cards = new ArrayList<Card>();

	// Arraylist to hold all the cards that have been dealt.
	private ArrayList <Card> dealtCards = new ArrayList<Card>();
  
	/*
	* Constructor of deck of three cards
	*/
	public Deck() {
		cards.add(new Card(Card.Rank.JACK, Card.Suit.SPADES));
		cards.add(new Card(Card.Rank.QUEEN, Card.Suit.SPADES));
		cards.add(new Card(Card.Rank.KING, Card.Suit.SPADES));
	}

	/*
	* Constructor of a full deck of cards
	*/
	public Deck(boolean b) {
		for (Card.Rank rank : Card.Rank.values()) {
			for (Card.Suit suit : Card.Suit.values()) {
				cards.add(new Card(rank, suit));       
			}
		}
	}

	/*
	* Method that adds all dealt cards back to deck
	* and shuffles it randomly. 
	*/
	public void shuffle() {
		cards.addAll(dealtCards);
		dealtCards.clear();
		Collections.shuffle(cards);
	}

	/*
	* Method that deals a card out of deck.
	*/
	public Card deal() throws EmptyDeckException {
		if (!cards.isEmpty()) {
			Card dealtCard = cards.get(0);
			dealtCards.add(dealtCard);
			cards.remove(0);
			return dealtCard;
		} else {
			throw new EmptyDeckException();
		}
	}

	/*
	* Method that returns a card to deck.
	*/
	public void add(Card card) {
		cards.add(new Card(card.getRank(), card.getSuit()));
	}
}

/*
* Exception for an empty Deck.
*/
class EmptyDeckException extends Exception {
	EmptyDeckException() {
		super();
	}
	EmptyDeckException(String s) {
		super(s);
	}
}
