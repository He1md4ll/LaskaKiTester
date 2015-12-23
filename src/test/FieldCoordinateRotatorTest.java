package test;

import static org.junit.Assert.*;

import org.junit.Test;

import staticUtils.FieldCoordinateRotator;

public class FieldCoordinateRotatorTest {
	
	@Test
	public void testRotateCoordinates() {
		assertEquals(FieldCoordinateRotator.rotateCoordinates("a1a1"), "g7g7");
		assertEquals(FieldCoordinateRotator.rotateCoordinates("a5c3"), "g3e5");
		assertEquals(FieldCoordinateRotator.rotateCoordinates("a5e5"), "g3c3");
	}

	@Test
	public void restRotateCoordinates3Values(){
		assertEquals(FieldCoordinateRotator.rotateCoordinates("a1a1a1"), "g7g7g7");
		assertEquals(FieldCoordinateRotator.rotateCoordinates("a1b2c3"), "g7f6e5");
		assertEquals(FieldCoordinateRotator.rotateCoordinates("c1d1e1"), "e7d7c7");
		assertEquals(FieldCoordinateRotator.rotateCoordinates("a5c3e5"), "g3e5c3");
	}
	
}
