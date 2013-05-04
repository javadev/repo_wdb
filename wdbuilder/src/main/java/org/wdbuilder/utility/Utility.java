package org.wdbuilder.utility;

import static java.lang.Math.max;
import static java.lang.Math.min;

import java.util.Random;

public abstract class Utility {

	private static final Random random = new Random(System.currentTimeMillis());

	public static String getURLPartToAvoidCaching() {
		return "r=" + random.nextGaussian();
	}

	public static boolean isBetween(int m, int n1, int n2) {
		return m >= min(n1, n2) && m <= max(n1, n2);
	}

}
