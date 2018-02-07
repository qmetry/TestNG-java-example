package com.qmetry;

public interface ICalculator {
	int sum(int a, int b);

	int subtraction(int a, int b);

	int multiplication(int a, int b);

	int divison(int a, int b) throws Exception;

	boolean equalIntegers(int a, int b);
}
