package com.qmetry.testng_demo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.qmetry.Calculator;
import com.qmetry.ICalculator;


public class CalculatorTest {
	private static ICalculator calculator;

	@BeforeClass
	public static void initCalculator() {
		calculator = new Calculator();
	}

	@BeforeTest
	public void beforeEachTest() {
		System.out.println("This is executed before each Test");
	}

	@AfterTest
	public void afterEachTest() {
		System.out.println("This is exceuted after each Test");
	}

	@Test
	public void testSum() {
		int result = calculator.sum(3, 4);

		assertEquals(7, result);
	}

	@Test
	public void testDivison() {
		try {
			int result = calculator.divison(10, 2);

			assertEquals(5, result);
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
	}

	@Test(expectedExceptions = Exception.class)
	public void testDivisionException() throws Exception {
		calculator.divison(10, 0);
	}

	@Test
	public void testEqual() {
		boolean result = calculator.equalIntegers(20, 20);
		assertTrue(result);
	}

	@Test
	public void testSubstraction() {
		int result = 10 - 3;

		assertTrue(result == 7);
	}
}
