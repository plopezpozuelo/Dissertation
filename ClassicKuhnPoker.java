/**
* <dl>
* <dt> File Name:
* <dd> ClassicKuhnPoker.java
*
* <dt> Description:
* <dd> This program consists of a classic Kuhn Poker game for two players.
* </dl>
*
* @author Paula Lopez Pozuelo
*/

import cardGame.*;
import geneticAlgo.*;

import java.util.Random;
import java.util.Scanner;

public class ClassicKuhnPoker {

	// Variable to hold the pot in game.
    public static int pot;

	/*
	* Returns the winning player by comparing their hands.
	*/
    public static Player checkCards(Player p1, Player p2) {
        int r1 = p1.popCard().getRank().ordinal();
        int r2 = p2.popCard().getRank().ordinal();
        if ( r1 > r2 ) {
            return p1;
        } else {
            return p2;
        }
    }

	/*
	* Play a game given two chromosomes, and return the amount won by
	* each player.
	*/
    public static int[] chromoGamePlay(Chromosome ch1, Chromosome ch2) {

        // Players initialized
        Player p1 = new Player();
        Player p2 = new Player();

        // Ante (each player puts 1 in pot)
        pot = 2;
        p1.bet(1);
        p2.bet(1);

        // Deck is shuffled and one card is dealt to each player
        Deck deck = new Deck();
        deck.shuffle();

        // Deal cards (will return exception if deck is empty)
        try {
            p1.addCard(deck.deal());
            p2.addCard(deck.deal());
        } catch (Exception e) {
            System.out.println(e);
        }

        // Play hand and give pot to winning player.
        Player winner = chromoHandPlay(p1, p2, ch1, ch2);

        // Print out result and give pot to winner.
        winner.win(pot);
        pot = 0;

        // Store wins in an array and return it.
        int[] wins = new int[2];
        wins[0] = p1.getStack();
        wins[1] = p2.getStack();
        
        return wins;
    }

	/*
	* Play 6 games between two chromosomes, one for each possible
	* combination of cards, and return the average winnings of all of
	* them.
	*/
    public static int[] chromoFairPlay(Chromosome ch1, Chromosome ch2) {

        // Deck is shuffled and one card is dealt to each player
        Deck deck = new Deck();
        deck.shuffle();

        Card card1 = new Card(Card.Rank.JACK, Card.Suit.SPADES);
        Card card2 = new Card(Card.Rank.QUEEN, Card.Suit.SPADES);
        Card card3 = new Card(Card.Rank.QUEEN, Card.Suit.SPADES);

        // Store wins in an array.
        int[] wins1 = gamePlay(ch1, card1, ch2, card2);
        int[] wins2 = gamePlay(ch1, card1, ch2, card3);
        int[] wins3 = gamePlay(ch1, card2, ch2, card1);
        int[] wins4 = gamePlay(ch1, card2, ch2, card3);
        int[] wins5 = gamePlay(ch1, card3, ch2, card1);
        int[] wins6 = gamePlay(ch1, card3, ch2, card2);

        int[] wins = new int[2];
        wins[0] = (wins1[0] + wins2[0] + wins3[0] + wins4[0] + wins5[0] + wins6[0])/6;
        wins[1] = (wins1[1] + wins2[1] + wins3[1] + wins4[1] + wins5[1] + wins6[1])/6;

        return wins;
    }

	/*
	* Return wins from a game between two strategies with the given cards.
	*/
    public static int[] gamePlay(Chromosome ch1, Card c1, Chromosome ch2, Card c2) {
        // Players initialized
        Player p1 = new Player();
        Player p2 = new Player();

        // Deal cards passed in function
        p1.addCard(c1);
        p2.addCard(c2);

        // Ante (each player puts 1 in pot)
        pot = 2;
        p1.bet(1);
        p2.bet(1);

        // Play hand and give pot to winning player.
        Player winner = chromoHandPlay(p1, p2, ch1, ch2);

        // Print out result and give pot to winner.
        winner.win(pot);
        pot = 0;

        // Store wins in an array.
        int[] wins = new int[2];
        wins[0] = p1.getStack();
        wins[1] = p2.getStack();

        return wins;
    }

	/*
	* Returns whether a player checks or bets from the chromosome passed 
	* to it.
	*/
    public static char checkOrBet(Player p, Chromosome ch) {
        // Get ordinal of player's card J = 9, Q = 10, K = 11, A=12
        int card = p.showCard(0).getRank().ordinal();

        // Get index of strategy corresponding to card within chromosome
        int indStrategy = card - 9;

        // Generate random double between 0 and 1
        Random rand = new Random();
        double n = rand.nextDouble();

        // Determine strategy from corresponding parameter
        if (n <= ch.getGene(indStrategy)) {
            return 'b';
        } else {
            return 'c';
        }
    }

	public static char checkOrBet(Player p) {
		Scanner in = new Scanner(System.in);
		System.out.println(p.getName() + ", check or bet 1? [C/B]");
		char input = in.next(".").charAt(0);
		
		if ( input == 'c' || input == 'C' ) {
			System.out.println(p.getName() + " checks");
			return 'c';
		} else if ( input == 'b' || input == 'B' ) {
			System.out.println(p.getName() + " bets 1");
			return 'b';
		} else {
			System.out.println("Wrong input, try again");
			return checkOrBet(p);
		}
	}

	public static char callOrFold(Player p) {
		Scanner in = new Scanner(System.in);
		System.out.println(p.getName() + ", call or fold? [C/F]");
		char input = in.next(".").charAt(0);
		
		if ( input == 'c' || input == 'C' ) {
			System.out.println(p.getName() + " calls");
			return 'c';
		} else if ( input == 'f' || input == 'F' ) {
			System.out.println(p.getName() + " folds");
			return 'f';
		} else {
			System.out.println("Wrong input, try again");
			return callOrFold(p);
		}
	}
    
    
	/*
	* Returns whether a player calls or folds from the chromosome passed 
	* to it.
	*/
    public static char callOrFold(Player p, Chromosome ch) {
        // Get ordinal of player's card J = 9, Q = 10, K = 11
        int card = p.showCard(0).getRank().ordinal();

        // Get index of strategy corresponding to card within chromosome
        int indStrategy = card - 6;

        // Generate random double between 0 and 1.
        Random rand = new Random();
        double n = rand.nextDouble();

        // Determine strategy from corresponding parameter
        if (n <= ch.getGene(indStrategy)) {
            return 'c';
        } else {
            return 'f';
        }
    }

	/*
	* Plays a hand between two players using the given strategies and
	* returns the winning Player.
	*/
    public static Player chromoHandPlay(Player p1, Player p2, Chromosome ch1, Chromosome ch2) {
    	
        // P1 calls/folds
        char action = checkOrBet(p1, ch1);

        if ( action == 'c' ) {
        	// P2 checks/bets
            action = checkOrBet(p2, ch2);
            if ( action == 'b' ) {
                // P2 bets $1 and gets added to pot
                p2.bet(1);
                pot += 1;
                // P1 calls/folds
                action = callOrFold(p1, ch1);
                if ( action == 'c') {
                	// P1 bets $1 and gets added to pot
                    p1.bet(1);
                    pot += 1;
                    // Showdown: check cards and return winner
                    return checkCards(p1, p2);
                } else {
                    // P1 has folded so P2 wins
                    return p2;
                }
            } else {
            	// Showdown: check cards and return winner
                return checkCards(p1, p2);
            }
        } else {
        	// P1 bets $1 and gets added to pot
            p1.bet(1);
            pot += 1;
            // P2 calls/folds
            action = callOrFold(p2, ch2);
            if ( action == 'c' ) {
            	// P2 bets $1 and gets added to pot
                p2.bet(1);
                pot += 1;
                // Showdown: check cards and return winner
                return checkCards(p1, p2);
            } else {
                // P2 has folded so P1 wins
                return p1;
            }
        }
    }   
}
