/**
* <dl>
* <dt> File Name:
* <dd> BreadthKuhnPoker.java
*
* <dt> Description:
* <dd> This program consists on a Kuhn Poker game for two players.
* </dl>
*
* @author Paula Lopez Pozuelo
*/

import cardGame.*;
import geneticAlgo.*;

import java.util.Random;

public class BreadthKuhnPoker {

	// Variable to hold the pot in the game
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

        // Store wins in an array.
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

        // Deal cards (will return exception if deck is empty)
        try {
            card1 = deck.deal();
            card2 = deck.deal();
        } catch (Exception e) {
            System.out.println(e);
        }

        // Store wins in an array.
        int[] wins1 = gamePlay(ch1, card1, ch2, card2);
        int[] wins2 = gamePlay(ch1, card2, ch2, card1);

        int[] wins = new int[2];
        wins[0] = (wins1[0] + wins2[0])/2;
        wins[1] = (wins1[1] + wins2[1])/2;

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
        // Get ordinal of player's card 2 = 0, ... J = 9, Q = 10, K = 11, A=12
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

	/*
	* Returns whether a player bets $1 or more from the chromosome passed 
	* to it.
	*/
    public static char bet1orMore(Player p, Chromosome ch) {
        // Get ordinal of player's card 2 = 0, ... J = 9, Q = 10, K = 11, A=12
        int card = p.showCard(0).getRank().ordinal();

        // Get index of strategy corresponding to card within chromosome
        int indStrategy = card - 6;

        // Generate random double between 0 and 1
        Random rand = new Random();
        double n = rand.nextDouble();

        // Determine strategy from corresponding parameter
        if (n <= ch.getGene(indStrategy)) {
            return '2';
        } else {
            return '1';
        }
    }

	/*
	* Returns whether a player bets $2 or $10 from the chromosome passed 
	* to it.
	*/
    public static char bet2or10(Player p, Chromosome ch) {
        // Get ordinal of player's card 2 = 0, ... J = 9, Q = 10, K = 11, A=12
        int card = p.showCard(0).getRank().ordinal();

        // Get index of strategy corresponding to card within chromosome
        int indStrategy = card - 3;

        // Generate random double between 0 and 1
        Random rand = new Random();
        double n = rand.nextDouble();

        // Determine strategy from corresponding parameter
        if (n <= ch.getGene(indStrategy)) {
            return 'x';
        } else {
            return '2';
        }
    }

	/*
	* Returns whether a player calls or folds $1 from the chromosome passed 
	* to it.
	*/
    public static char callOrFold1(Player p, Chromosome ch) {
        // Get ordinal of player's card J = 11, Q = 12, K = 13
        int card = p.showCard(0).getRank().ordinal();

        // Get index of strategy corresponding to card within chromosome
        int indStrategy = card;

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
	* Returns whether a player calls or folds $2 from the chromosome passed 
	* to it.
	*/
    public static char callOrFold2(Player p, Chromosome ch) {
        // Get ordinal of player's card J = 11, Q = 12, K = 13
        int card = p.showCard(0).getRank().ordinal();

        // Get index of strategy corresponding to card within chromosome
        int indStrategy = card + 3;

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
	* Returns whether a player calls or folds $10 from the chromosome passed 
	* to it.
	*/
    public static char callOrFold10(Player p, Chromosome ch) {
        // Get ordinal of player's card J = 11, Q = 12, K = 13
        int card = p.showCard(0).getRank().ordinal();

        // Get index of strategy corresponding to card within chromosome
        int indStrategy = card + 6;

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
        
    	// P1 checks/bets
        char action = checkOrBet(p1, ch1);

        if ( action == 'c' ) {
        	// P2 checks/bets
            action = checkOrBet(p2, ch2);
            if ( action == 'b' ) {
            	// P2 bets $1 or more
                action = bet1orMore(p2, ch2);
                if (action == '1') {
                	// P2 bets $1 and gets added to pot
                    p2.bet(1);
                    pot += 1;
                    // P1 calls/folds $1
                    action = callOrFold1(p1, ch1);
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
                	// P2 bets $2 or $10
                    action = bet2or10(p2, ch2);
                    if (action == '2') {
                    	// P2 bets $2 and gets added to pot
                        p2.bet(2);
                        pot += 2;
                        // P1 calls/folds $2
                        action = callOrFold2(p1, ch1);
                        if ( action == 'c') {
                        	// P1 bets $2 and gets added to pot
                            p1.bet(2);
                            pot += 2;
                            // Showdown: check cards and return winner
                            return checkCards(p1, p2);
                        } else {
                        	// P2 has folded so P1 wins
                            return p2;
                        }
                    } else {
                    	// P2 bets $10 and gets added to pot
                        p2.bet(10);
                        pot += 10;
                        // P1 calls/folds $10
                        action = callOrFold10(p1, ch1);
                        if ( action == 'c') {
                        	// P1 bets $10 and gets added to pot
                            p1.bet(10);
                            pot += 10;
                            // Showdown: check cards and return winner
                            return checkCards(p1, p2);
                        } else {
                        	// P1 has folded so P2 wins
                            return p2;
                        }
                    }
                }
            } else {
            	// Showdown: check cards and return winner
                return checkCards(p1, p2);
            }
        } else {
        	// P1 bets $1 or more
            action = bet1orMore(p1, ch1);
            if (action == '1') {
            	// P1 bets $1 and gets added to pot
                p1.bet(1);
                pot += 1;
                // P2 calls/folds $1
                action = callOrFold1(p2, ch2);
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
            } else {
            	// P1 bets $2 or $10
                action = bet2or10(p1, ch1);
                if (action == '2') {
                    p1.bet(2);
                    pot += 2;
                    // P2 calls/folds $2
                    action = callOrFold2(p2, ch2);
                    if ( action == 'c' ) {
                    	// P2 bets $2 and gets added to pot
                        p2.bet(2);
                        pot += 2;
                        // Showdown: check cards and return winner
                        return checkCards(p1, p2);
                    } else {
                    	// P2 has folded so P1 wins
                        return p1;
                    }
                } else {
                	// P1 bets $10 and gets added to pot
                    p1.bet(10);
                    pot += 10;
                    // P2 calls/folds $2
                    action = callOrFold2(p2, ch2);
                    if ( action == 'c' ) {
                    	// P2 bets $2 and gets added to pot
                        p2.bet(10);
                        pot += 10;
                        // Showdown: check cards and return winner
                        return checkCards(p1, p2);
                    } else {
                    	// P2 has folded so P1 wins
                        return p1;
                    } 
                }
            }
        }
    }
}
