package com.microstream.data.generator;

import java.util.Random;

class GBMPriceGenerator implements PriceGenerator {

  private double currentPrice;
  private final double scaledMu;
  private final double scaledSigma;
  private final double deltaT;
  private final Random random;

  /**
   * Constructor for StockPriceGenerator.
   *
   * @param initialPrice Initial stock price.
   * @param mu           Drift (expected return, annualized).
   * @param sigma        Volatility (annualized standard deviation).
   */
  public GBMPriceGenerator(double initialPrice, double mu, double sigma) {
    this.currentPrice = initialPrice;
    double annualSeconds = 365.0 * 24 * 60 * 60; // Total seconds in a year
    this.scaledMu = mu / annualSeconds;
    this.scaledSigma = sigma / Math.sqrt(annualSeconds);
    this.deltaT = 1.0; // Time step is 1 second
    this.random = new Random();
  }

  /**
   * Generates the next stock price based on the Geometric Brownian Motion model.
   *
   * @return The next stock price.
   */
  @Override
  public double generate() {
    double Z = random.nextGaussian(); // Generate a standard normal random variable (Gaussian)
    currentPrice = currentPrice * Math.exp((scaledMu - 0.5 * scaledSigma * scaledSigma) * deltaT + scaledSigma * Math.sqrt(deltaT) * Z);
    return currentPrice;
  }
}
