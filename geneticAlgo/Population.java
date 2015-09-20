/**
* <dl>
* <dt> File Name:
* <dd> Population.java
*
* <dt> Description:
* <dd> Class that represents a Population in a Genetic Algorithm.
* </dl>
*
* @author Paula Lopez Pozuelo
*/

package geneticAlgo;
import java.util.Random;
import java.util.Arrays;
import java.util.*;

public class Population {

	// Population parameters
	private int size;
	private Chromosome[] chromos;
	private double fitness;
	private int chromoLength;
	
	// Rates for population
	private static double crossRate;
	private static double mutateRate;

	/*
	* Population constructor
	*/
	public Population(int s, int cL, double c, double m) {
		size = s;
		chromoLength = cL;
		crossRate = c;
		mutateRate = m;
		chromos = new Chromosome[size];
	}

	/*
	 * Population clone constructor.
	 */
	public Population(Population clone) {
		this.size = clone.size;
		this.chromos = clone.chromos;
		this.chromos = new Chromosome[size];
		for (int i = 0; i < size; i++) {
			this.setChromo(i, new Chromosome(clone.getChromo(i)));
		}
		this.fitness = clone.fitness;
		this.crossRate = clone.crossRate;
		this.mutateRate = clone.mutateRate;
	}

	/*
	 * Generates a population of distinct random chromosomes
	 */
	public void randomize() {
		for (int i = 0 ; i < size; i++) {
			Chromosome randomChromo = new Chromosome(chromoLength);
			randomChromo.randomize();
			while (this.contains(randomChromo, i)) {
				randomChromo.randomize();
			}
			chromos[i] = new Chromosome(randomChromo);
		}
	}
	
	/*
	 * Generates a population of distinct chromosomes with uniformly 
	 * distributed values.
	 */
	public void randomizeUniformly() {
		ArrayList<Integer> values = new ArrayList<Integer>();
		for (int i = 0; i < size; i++) {
			values.add(i);
		}
		for (int i = 0; i < size; i++) {
			chromos[i] = new Chromosome(chromoLength);
		}
		for (int i = 0 ; i < chromoLength; i++) {
			Collections.shuffle(values);
			for (int j=0; j < size; j++) {
				chromos[j].setGene(i, (double) (values.get(j)%101)/100);
			}
		}
	}

	/*
	 * Returns true if population contains given chromosome.
	 */
	public boolean contains(Chromosome chromo, int topLimit) {
		for (int i=0; i<topLimit; i++) {
			if (chromo.sameGenes(chromos[i])) {
				return true;
			}
		}
		return false;
	}

	/*
	 * Evaluate total fitness of population.
	 */
	public void evalTotalFitness() {
		fitness = 0;
		for (int i = 0 ; i < size; i++) {
			fitness += chromos[i].getFitness();
		}
	}

	/*
	 * Returns average fitness of population.
	 */
	public double avgFitness() {
		evalTotalFitness();
		return fitness/size;
	}
 
	/*
	 * Select and return chromosome using Roulette-Wheel selection.
	 */
	public Chromosome selectRouletteWheel() {
		evalTotalFitness();
		Random rand = new Random();
		double randFitness = rand.nextDouble() * (fitness);
		int i;
		for (i = 0; (randFitness > 0) && (i < size); i++) {
			randFitness -= (chromos[i].getFitness());
		}
		return chromos[i-1];
	}

	/*
	 * Select and return chromosome using Rank selection.
	 */
	public Chromosome selectRankWheel() {
		int rankTotal = 0;
		for (int i = 0; i < size; i++) {
			rankTotal += i;
		}
		this.sort();
		Random rand = new Random();
		double randRank = rand.nextDouble() * rankTotal;
		int i;
		for (i = 0; (randRank > 0) && (i < size); i++) {
			randRank -= i;
		}
		return chromos[size-(i-1)];
	}

	/*
	 * Select and return chromosome using Tournament selection.
	 */
	public Chromosome selectTournament(int tourSize) {
		Random rand = new Random();
		double bestFitness = -1;
		int bestInd = 0;
		for (int i = 0; i < tourSize; i++) {
			int randInd = rand.nextInt(size);
			if (chromos[randInd].getFitness() > bestFitness) {
				bestFitness = chromos[randInd].getFitness();
				bestInd = randInd;
			}
		}
		return chromos[bestInd];
	}

	/*
	 * Find and return fittest chromosome.
	 */
	public Chromosome getFittest() {
		Chromosome fittest = chromos[0];
		for (int i = 1 ; i < size; i++) {
			if (fittest.getFitness() < chromos[i].getFitness()) {
				fittest = chromos[i];
			}
		}
		return fittest;
	}

	/*
	 * Sort population.
	 */
	public void sort() {
		Arrays.sort(chromos);
	}

	/*
	 * Return specified chromosome from population.
	 */
	public Chromosome getChromo(int ind) {
		return chromos[ind];
	}

	/*
	 * Set chromosome in population.
	 */
	public void setChromo(int ind, Chromosome ch) {
		chromos[ind] = ch;
	}

	/*
	 * Returns mutated copy of chromosome (subject to mutation rate)
	 */
	public static Chromosome mutate(Chromosome ch) {
		Random rand = new Random();
		double prob = rand.nextDouble();
		if ( prob < mutateRate ) {
			return ch.mutate();
		} else {
			return ch;
		}
	}

	/*
	 * Method that returns offspring produced from Uniform crossover,
	 * subject to the crossover rate
	 */
	public static Chromosome[] uniformCrossover(Chromosome[] ch) {
		Random rand = new Random();
		double prob = rand.nextDouble();
		if (prob < crossRate) {
			return Chromosome.uniformCrossover(ch[0], ch[1]);
		} else {
			return ch;
		}
	}
	
	/*
	 * Method that returns offspring produced from One-Point crossover,
	 * subject to the crossover rate
	 */
	public static Chromosome[] onePointCrossover(Chromosome[] ch) {
		Random rand = new Random();
		double prob = rand.nextDouble();
		if (prob < crossRate) {
			return Chromosome.onePointCrossover(ch[0], ch[1]);
		} else {
			return ch;
		}
	}
}
