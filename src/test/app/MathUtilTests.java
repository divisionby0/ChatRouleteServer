package test.app;

import org.junit.Test;

import dev.div0.util.MathUtil;

public class MathUtilTests {
	
	@Test
	public void testMathUtil(){
		int randomInt = MathUtil.randInt(0, 900000);
		System.out.println("random = "+randomInt);
	}
}
