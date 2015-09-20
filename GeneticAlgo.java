/**
* <dl>
* <dt> File Name:
* <dd> GeneticAlgo.java
*
* <dt> Description:
* <dd> This file consists on a genetic algorithm that can be used 
* to solve optimization problems. In this cases, it attempts to find 
* strategies for a Kuhn Poker game. 
* By default, CHROMO_LENGTH is set to 6 such that a Classic Kuhn Poker 
* game will be used. Can be set to 18 to add another betting round, 
* to 12 to add additional betting choices ($1, $2, $10) and, finally,
* to 36 to add both an additional round and extra betting options.
* </dl>
*
* @author Paula Lopez Pozuelo
*/

import geneticAlgo.*;

public class GeneticAlgo {
	public final static int GENERATIONS = 100;
	public final static int SIZE = 1010;
	public final static double CROSS_RATE = 0.7;
	public final static double MUTATE_RATE = 0.01;
	
	public final static int ELITE_COUNT = 0;
	
	// CHANGE VALUES DEPENDING ON VARIANT OF KUHN POKER TO BE USED
	// Set chromo length for classic Kuhn Poker
	public final static int CHROMO_LENGTH = 6;
	
	// Set chromo length for added breadth Kuhn Poker
	//public final static int CHROMO_LENGTH = 18;
	
	// Set chromo length for added depth Kuhn Poker
	//public final static int CHROMO_LENGTH = 12;
	
	// Set chromo length for added breadth and depth Kuhn Poker
	//public final static int CHROMO_LENGTH = 36;
  
	/*
	* Evaluate fitness of two populations through co-evolution.
	*/
	public static void evalFitness(Population p1, Population p2) {
	    
		// Set all fitness values to zero in both populations
		for (int i = 0; i < SIZE; i++) {
			p1.getChromo(i).resetFitness();
			p2.getChromo(i).resetFitness();
		}
		
		// Play every chromosome in every population against each other,
		// adding wins of gameplays to their fitnesses
		double[][] wins = new double[2][SIZE];
		int[] gameResult = new int[2];
		
		// Loop through chromosomes of population 1
		for (int i = 0; i < SIZE; i++) {
			// Loop through chromosomes of population 2
			for (int j = 0; j < SIZE; j++) {
				switch(CHROMO_LENGTH) {
					case 6:
						gameResult = ClassicKuhnPoker.chromoGamePlay(p1.getChromo(i), p2.getChromo(j));
						break;
					case 18:
						gameResult = BreadthKuhnPoker.chromoGamePlay(p1.getChromo(i), p2.getChromo(j));
						break;
					case 12:
						gameResult = DepthKuhnPoker.chromoGamePlay(p1.getChromo(i), p2.getChromo(j));
						break;
					case 36:
						gameResult = ComplexKuhnPoker.chromoGamePlay(p1.getChromo(i), p2.getChromo(j));
						break;
				}
				
				wins[0][i] += gameResult[0];
				wins[1][j] += gameResult[1];
				
				// Mixed approach (with rational player)
				// gameResult = KuhnPoker.chromoGamePlay(p1.getChromo(i), rational);	
				// wins[0][i] += gameResult[0];
				// wins[1][j] += gameResult[1];
				// wins[0][i] /= 2;
				// wins[1][j] /= 2;
			}
		}
		
		// Divide into number of games to get average win
		for (int i = 0; i < SIZE; i++) {
			p1.getChromo(i).setFitness(Math.pow(wins[0][i]/(SIZE), 1));
			p2.getChromo(i).setFitness(Math.pow(wins[1][i]/(SIZE), 1));
		}
	}
	
	/*
	* Evaluate fitness of population against strategy as a first player.
	*/
	public static void evalFitness(Population p1, Chromosome strategy) {
	    
		// Set all fitness values to zero in both populations
		for (int i = 0; i < SIZE; i++) {
			p1.getChromo(i).resetFitness();
		}
		
		// Play every chromosome in every population against each other,
		// adding wins of gameplays to their fitnesses
		double[] wins = new double[SIZE];
		int[] gameResult = new int[2];
		
		// Loop through chromosomes of population 1
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				switch(CHROMO_LENGTH) {
					case 6:
						gameResult = ClassicKuhnPoker.chromoGamePlay(p1.getChromo(i), strategy);
						break;
					case 18:
						gameResult = BreadthKuhnPoker.chromoGamePlay(p1.getChromo(i), strategy);
						break;
					case 12:
						gameResult = DepthKuhnPoker.chromoGamePlay(p1.getChromo(i), strategy);
						break;
					case 36:
						gameResult = ComplexKuhnPoker.chromoGamePlay(p1.getChromo(i), strategy);
						break;
				}
				wins[i] += gameResult[0];
			}
		}
		
		// Divide into number of games to get average win
		for (int i = 0; i < SIZE; i++) {
			p1.getChromo(i).setFitness(Math.pow(wins[i]/(SIZE), 1));
		}
	}
	
	/*
	* Evaluate fitness of population against strategy as a second player.
	*/
	public static void evalFitness(Chromosome strategy, Population p2) {
	    
		// Set all fitness values to zero in both populations
		for (int i = 0; i < SIZE; i++) {
			p2.getChromo(i).resetFitness();
		}
		
		// Play every chromosome in every population against each other,
		// adding wins of gameplays to their fitnesses
		double[] wins = new double[SIZE];
		int[] gameResult = new int[2];
		
		// Play each chromosome against the given strategy
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				switch(CHROMO_LENGTH) {
					case 6:
						gameResult = ClassicKuhnPoker.chromoGamePlay(strategy, p2.getChromo(i));
						break;
					case 18:
						gameResult = BreadthKuhnPoker.chromoGamePlay(strategy, p2.getChromo(i));
						break;
					case 12:
						gameResult = DepthKuhnPoker.chromoGamePlay(strategy, p2.getChromo(i));
						break;
					case 36:
						gameResult = ComplexKuhnPoker.chromoGamePlay(strategy, p2.getChromo(i));
						break;
				}
				wins[i] += gameResult[0];
			}
		}
		
		// Divide into number of games to get average win
		for (int i = 0; i < SIZE; i++) {
			p2.getChromo(i).setFitness(Math.pow(wins[i]/(SIZE), 1));
		}
	}

  
	/*
	* Breed and return new generation from an old one.
	*/
	public static Population breedGeneration(Population oldPop) {
		// Create new population
		Population newPop = new Population(SIZE, CHROMO_LENGTH, CROSS_RATE, MUTATE_RATE);
		
		// Generation counter
		int count = 0;
		
		// Sort population when there is elitism bigger than zero
		if (ELITE_COUNT > 0) {
			oldPop.sort();
		}
		
		while ( count < SIZE ) {
			Chromosome[] chromo = new Chromosome[2];
			
			// Take best chromosomes and reinsert them unaltered.
			if (count < ELITE_COUNT) {
				  chromo[0] = new Chromosome(oldPop.getChromo(count));
				  chromo[1] = new Chromosome(oldPop.getChromo(count+1));
				// For the rest, take chromosomes using Roulete wheel selection and
				// then crossover/mutate them.
			} else {
				// Select two chromosomes with tournament selection 
				chromo[0] = new Chromosome(oldPop.selectTournament(2));
				chromo[1] = new Chromosome(oldPop.selectTournament(2));
				
				// Select two chromosomes with roulette wheel
				//chromo[0] = new Chromosome(oldPop.selectRouletteWheel());
				//chromo[1] = new Chromosome(oldPop.selectRouletteWheel());
				
				// Select two chromosomes with rank wheel
				//chromo[0] = new Chromosome(oldPop.selectRankWheel());
				//chromo[1] = new Chromosome(oldPop.selectRankWheel());
				
				// Crossover chromosomes with Uniform crossover
				chromo = Population.uniformCrossover(chromo);
				
				// Crossover chromosomes with One-Point crossover
				//chromo = Population.onePointCrossover(chromo);
				
				// Mutate chromosomes
				chromo[0] = new Chromosome(Population.mutate(chromo[0]));
				chromo[1] = new Chromosome(Population.mutate(chromo[1]));
			}
			
			// Add resulting chromosomes to new population, making sure that they are
			// not clones of other members of population
			if (!newPop.contains(chromo[0], count)) {
				newPop.setChromo(count, chromo[0]);
				count += 1;
			} 
			if (count < SIZE && !newPop.contains(chromo[1], count)) {
				newPop.setChromo(count, chromo[1]);
				count += 1;
			}
		}
		
		return newPop;
	}
  
    public static void main(String[] args) {
    	
    	// Folder player strategy
        Chromosome folder = new Chromosome(CHROMO_LENGTH);
    	folder.setGene(0, 0);
    	folder.setGene(1, 0);
    	folder.setGene(2, 0);
    	folder.setGene(3, 0);
    	folder.setGene(4, 0);
    	folder.setGene(5, 0);

    	// Shy player strategy
        Chromosome shy = new Chromosome(CHROMO_LENGTH);
        shy.setGene(0, 0);
        shy.setGene(1, 0);
        shy.setGene(2, 0);
        shy.setGene(3, 0);
        shy.setGene(4, 0);
        shy.setGene(5, 1);

        // Safe player strategy
        Chromosome safe = new Chromosome(CHROMO_LENGTH);
        safe.setGene(0, 0);
        safe.setGene(1, 0);
        safe.setGene(2, 1);
        safe.setGene(3, 0);
        safe.setGene(4, 0);
        safe.setGene(5, 1);

        // Caller player strategy
        Chromosome caller = new Chromosome(CHROMO_LENGTH);
        caller.setGene(0, 1);
        caller.setGene(1, 1);
        caller.setGene(2, 1);
        caller.setGene(3, 1);
        caller.setGene(4, 1);
        caller.setGene(5, 1);

        // Rational player strategy
        Chromosome rational = new Chromosome(CHROMO_LENGTH);
        rational.setGene(0, 0);
        rational.setGene(1, 0.5);
        rational.setGene(2, 1);
        rational.setGene(3, 0);
        rational.setGene(4, 0.5);
        rational.setGene(5, 1);

        // Bluffer player strategy
        Chromosome bluffer = new Chromosome(CHROMO_LENGTH);
        bluffer.setGene(0, 0.5);
        bluffer.setGene(1, 0.7);
        bluffer.setGene(2, 1);
        bluffer.setGene(3, 0);
        bluffer.setGene(4, 0.7);
        bluffer.setGene(5, 1);
    	
	    // Print the parameters used
	    System.out.println("Generations: " + GENERATIONS);
	    System.out.println("Population size: " + SIZE);
	    System.out.println("Crossover rate: " + CROSS_RATE);
	    System.out.println("Mutation rate: " + MUTATE_RATE);
	    System.out.println("Elitism: " + ELITE_COUNT);
	
	    String csvFile = "";
	
	    // Array to hold fittest chromosomes in populations.
	    Chromosome[] fittestChromo = new Chromosome[2];
	    fittestChromo[0] = new Chromosome(CHROMO_LENGTH);
	    fittestChromo[1] = new Chromosome(CHROMO_LENGTH);
	
	    // Create two initial populations (p1 and p2).
	    Population[] oldPopulation = new Population[2];
	    oldPopulation[0] = new Population(SIZE, CHROMO_LENGTH, CROSS_RATE, MUTATE_RATE);
	    oldPopulation[1] = new Population(SIZE, CHROMO_LENGTH, CROSS_RATE, MUTATE_RATE);
	    
	    // Randomize uniformly initial population
	    oldPopulation[0].randomizeUniformly();
	    oldPopulation[1].randomizeUniformly();
	    
	    // Randomize uniformly initial population
	    //oldPopulation[0].randomize();
	    //oldPopulation[1].randomize();
	
	    // Evaluate fitness rates of chromosomes in initial population
	    //evalFitness(oldPopulation[0], oldPopulation[1]);
		evalFitness(oldPopulation[0], rational);
		//evalFitness(rational, oldPopulation[1]);
	
	    // Print outcome
	    System.out.println("First generation created. Fittest chromosomes: " + oldPopulation[0].getFittest().getFitness() + " and " + oldPopulation[1].getFittest().getFitness());
	    System.out.println("Average fitness: " + oldPopulation[0].avgFitness() + " and " + oldPopulation[1].avgFitness());
	    System.out.println(" > Player 1 chromosome: \n" + oldPopulation[0].getFittest().toString());
	    System.out.println(" > Player 2 chromosome: \n" + oldPopulation[1].getFittest().toString()); 
	
	    // Create array to hold two new populations
	    Population[] newPopulation = new Population[2];
	    newPopulation[0] = new Population(SIZE, CHROMO_LENGTH, CROSS_RATE, MUTATE_RATE);
	    newPopulation[1] = new Population(SIZE, CHROMO_LENGTH, CROSS_RATE, MUTATE_RATE);
	
	    for (int i = 0; i < GENERATIONS; i++) {
	
			// Breed new generations from previous ones.
			newPopulation[0] = new Population(breedGeneration(oldPopulation[0]));
			newPopulation[1] = new Population(breedGeneration(oldPopulation[1]));
			
			// Evaluate the fitness of the populations through co-evolution
			//evalFitness(newPopulation[0], newPopulation[1]);
			
			// Evaluate the fitness population 1 against rational player
			evalFitness(newPopulation[0], rational);
			
			// Evaluate the fitness of population 2 against rational player
			//evalFitness(rational, newPopulation[1]);
			
			// Print outcome of generation
			System.out.println("Generation " + (i+1) + " created. Fittest chromosomes: " + newPopulation[0].getFittest().getFitness() + " and " + newPopulation[1].getFittest().getFitness());
			System.out.println("Average fitness: " + newPopulation[0].avgFitness() + " and " + newPopulation[1].avgFitness());
			System.out.println(" > Player 1 chromosome: \n" + newPopulation[0].getFittest().toString());
			System.out.println(" > Player 2 chromosome: \n" + newPopulation[1].getFittest().toString());
			
			// Save outcome to string to generate a csv in the end
			csvFile += (i+1) + ", " + (newPopulation[0].avgFitness()) + ", " + (newPopulation[0].getFittest().getFitness()) + "\n";
	
			// Keep track of fittest chromosome in Population 1
			if (fittestChromo[0].getFitness() < newPopulation[0].getFittest().getFitness()){
				fittestChromo[0] = new Chromosome(newPopulation[0].getFittest());
			}
	
			// Keep track of fittest chromosome in Population 2
			if (fittestChromo[1].getFitness() < newPopulation[1].getFittest().getFitness()){
				fittestChromo[1] = new Chromosome(newPopulation[1].getFittest());
			}
		      
			// New population becomes old population to be used for next generation breeding
			oldPopulation[0] = new Population(newPopulation[0]);
			oldPopulation[1] = new Population(newPopulation[1]);
	    }
	
	    // Generate csv file with results
	    GenerateCsv.generateCsvFile("test.csv", csvFile);
	    
	    // Print out results
	    System.out.println("Pop1 fittest chromosome accross all generations has fitness: " + fittestChromo[0].getFitness());
	    System.out.println(fittestChromo[0].toString());
	    System.out.println("Pop2 fittest chromosome accross all generations has fitness: " + fittestChromo[1].getFitness());
	    System.out.println(fittestChromo[1].toString());
    }
}
