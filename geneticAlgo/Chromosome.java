/**
* <dl>
* <dt> File Name:
* <dd> Chromosome.java
*
* <dt> Description:
* <dd> Class that represents a chromosome in a Genetic Algorithm.
* </dl>
*
* @author Paula Lopez Pozuelo
*/
package geneticAlgo;
import java.util.Random;
import java.util.Arrays;

public class Chromosome implements Comparable<Chromosome> {

	// Fitness rate for chromosome, array holding genes
	// (which are random doubles between 0 and 1) and length
	private double fitness;
	private double[] genes;
	private static int length;
	
	// Random variable
	private static Random rand = new Random();

	/*
	 * Chromosome constructor
	 */
	public Chromosome(int l) {
		length = l;
		fitness = 0;
		genes = new double[length];
	}

	/*
	 * Chromosome clone constructor
	 */
	public Chromosome(Chromosome clone) {
		this.length = clone.getLength();
		this.fitness = clone.fitness;
		genes = new double[length];
		for (int i = 0; i < length; i++) {
			this.setGene(i, clone.getGene(i));
		}
	}

	/*
	 * Method that randomizes gene values in chromosome.
	 */
	public void randomize() {
		for (int i=0; i<length; i++) {
			genes[i] = (double) rand.nextDouble();
		}
	}

	/*
	 * Getter method for gene value
	 */
	public double getGene(int ind) {
		return genes[ind];
	}
	
	/*
	 * Setter method for gene value
	 */
	public void setGene(int ind, double value) {
		genes[ind] = value;
	}
  
	/*
	 * Getter method for chromosome length
	 */
	public int getLength() {
		return length;
	}

	/*
	 * Getter method for fitness value
	 */
	public double getFitness() {
		return fitness;
	}

	/*
	 * Setter method for fitness value
	 */
	public void setFitness(double f) {
		fitness = f;
	}

	/*
	 * Method to reset fitness value to zero
	 */
	public void resetFitness() {
		fitness = 0;
	}

	/*
	 * Method that returns true if the chromosome passe to it
	 * has the same gene values.
	 */
	public boolean sameGenes(Chromosome compareChromo) {
		return (Arrays.equals(this.genes, compareChromo.genes));
	}

	/*
	 * Method that returns offspring produced from One-Point crossover of the 
	 * two chromosomes passed to it.
	 */
	public static Chromosome[] onePointCrossover(Chromosome ch1, Chromosome ch2) {	
		// Random number between 1 and length (both included, not 0 because it
		// would result in no crossover)
		int ind = rand.nextInt(length-1) + 1;
		
		// Create a two chromosome array to hold the children produced
		// from crossover.
		Chromosome[] newChromo = new Chromosome[2];
		newChromo[0] = new Chromosome(ch1);
		newChromo[1] = new Chromosome(ch2);
		
		for (int i = ind; i < length; i++) {
			newChromo[0].setGene(i, ch2.getGene(i));
			newChromo[1].setGene(i, ch1.getGene(i));
		}
		
		return newChromo;
	}

	/*
	 * Method that returns offspring produced from Uniform crossover of the 
	 * two chromosomes passed to it.
	 */
	public static Chromosome[] uniformCrossover(Chromosome ch1, Chromosome ch2) {
		// Variable that determines similarity to each parent
		double n = rand.nextDouble();
		n = 0.5;
		
		// Make copies of parents
		Chromosome[] newChromo = new Chromosome[2];
		newChromo[0] = new Chromosome(ch1);
		newChromo[1] = new Chromosome(ch2);
		
		for (int i = 0; i < length; i++) {
			double r = rand.nextDouble();
			if (r < n) {
				newChromo[0].setGene(i, ch2.getGene(i));
				newChromo[1].setGene(i, ch1.getGene(i));
			} 
		}
		
		return newChromo;
	}

	/*
	 * Method that returns mutation of chromosome.
	 */
	public Chromosome mutate() {
		int ind = rand.nextInt(length);
		Chromosome mutatedChromo = new Chromosome(this);
		mutatedChromo.setGene(ind, rand.nextDouble());
		return mutatedChromo;
	}

	/*
	 * Method that returns String with gene values of chromosome.
	 */	
	public String toString() {
		String chromo = "[";
		for (int i=0; i<(length-3); i=i+3) {
			chromo += "(" + String.format("%.2g", getGene(i));
			chromo += ", " + String.format("%.2g", getGene(i+1));
			chromo += ", " + String.format("%.2g", getGene(i+2)) + "),\n";
		}
		chromo += "(" + String.format("%.2g", getGene(length-3));
		chromo += ", " + String.format("%.2g", getGene(length-2));
		chromo += ", " + String.format("%.2g", getGene(length-1)) + ")]";
		return chromo;
	}

/*	public String toString() {
		String chromo = "[";
		for (int i=0; i<(length-1); i++) {
			chromo += String.format("%.2g", getGene(i));
			chromo += ", ";
		}
		chromo += String.format("%.2g", getGene(length-1));
		chromo += "]";
		return chromo;
	}*/

	@Override
	public int compareTo(Chromosome compareChromo) {
		double compareFitness = compareChromo.getFitness();
		if (this.getFitness() > compareFitness) {
			return -1;
		} else if (this.getFitness() == compareFitness) {
			return 0;
		} else {
			return 1;
		}
	}

}