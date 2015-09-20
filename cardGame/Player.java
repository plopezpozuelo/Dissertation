/**
* <dl>
* <dt> File Name:
* <dd> Player.java
*
* <dt> Description:
* <dd> This class represents a player in a game of cards.
* </dl>
*
* @author Paula Lopez Pozuelo
*/
package cardGame;
import java.util.ArrayList;

public class Player {

	// Player's money
    private int stack;
    
    // Player's hand
    private ArrayList <Card> hand;
    
    // Player's name
    private String name;

	/*
	 * Default constructor (players have 0 money by default)
	 */
	public Player() {
		stack = 0;
		hand = new ArrayList<Card>();
		name = "John Doe";
	}

	/*
	 * Constructor for creating a player with an initial amount of money.
	 */
	public Player(int st) {
		stack = st;
		hand = new ArrayList<Card>();
	}

	/*
	 * Getter method that returns the value of the player's stack.
	 */
	public int getStack() {
		return stack;
	}

	/*
	 * Setter method to modify the value of the player's stack.
	 */
	public void setStack(int s) {
		stack = s;
	}

	/*
	 * Getter method that returns the player's name.
	 */
	public String getName() {
		return name;
	}

	/*
	 * Setter method to modify the player's name.
	 */
	public void setName(String n) {
		name = n;
	}	
	
	/*
	 * Method to add a card to the player's hand.
	 */    
	public void addCard(Card newCard) {
		hand.add(newCard);
	}

	/*
	 * Method to show a specific card from a player's hand.
	 */
	public Card showCard(int ind) {
		return hand.get(ind);
	}

	/*
	* Method that takes away the first card in the player's hand.
	*/
	public Card popCard() {
		Card tempCard = hand.get(0);
		hand.remove(0);
		return tempCard;
	}

	/*
	 * Method that takes away a specific card from the player's hand.
	 */
	public Card popCard(Card oldCard) {
		Card tempCard = oldCard;
		hand.remove(oldCard);
		return tempCard;
	}
	
	/*
	* Method that takes away all the player's cards.
	*/
	public void emptyHand() {
		hand.clear();
	}

	/*
	 * Method that decreases stack by the value passed to it.
	 */
	public void bet(int n) {
		stack -= n;
	}

	/*
	 * Method that increases stack by the value passed to it.
	 */   
	public void win(int n) {
		stack += n;
	}

	/*
	 * Method that returns a String with the player's current hand.
	 */
	public String handToString() {
		String handString = "[";
		for (Card card : hand) {
			//handString += card.toString() + ", ";
			handString += card.toString();
		}
		handString += "]";
		return handString;
	} 
}
