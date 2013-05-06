package org.wdbuilder.utility;

import java.util.Random;

public abstract class Utility {

	private static final Random random = new Random(System.currentTimeMillis());

	public static String getURLPartToAvoidCaching() {
		return "r=" + random.nextGaussian();
	}

}
