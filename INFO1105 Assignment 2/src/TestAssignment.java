import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import textbook.LinkedBinaryTree;

public class TestAssignment {
	
	// Set up JUnit to be able to check for expected exceptions
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	// Some simple testing of prefix2tree
	@Test(timeout = 100)
	public void testPrefix2tree() {
		
		LinkedBinaryTree<String> tree;

		tree = Assignment.prefix2tree("hi");
		assertEquals(1, tree.size());
		assertEquals("hi", tree.root().getElement());

		tree = Assignment.prefix2tree("+ 5 10");
		assertEquals(3, tree.size());
		assertEquals("+", tree.root().getElement());
		assertEquals("5", tree.left(tree.root()).getElement());
		assertEquals("10", tree.right(tree.root()).getElement());
		
		tree = Assignment.prefix2tree("- 5 10");
		assertEquals(3, tree.size());
		assertEquals("-", tree.root().getElement());
		assertEquals("5", tree.left(tree.root()).getElement());
		assertEquals("10", tree.right(tree.root()).getElement());
		
		tree = Assignment.prefix2tree("* 5 10");
		assertEquals(3, tree.size());
		assertEquals("*", tree.root().getElement());
		assertEquals("5", tree.left(tree.root()).getElement());
		assertEquals("10", tree.right(tree.root()).getElement());
				
		tree = Assignment.prefix2tree("+ 5 - 4 3");
		assertEquals(5, tree.size());
		assertEquals("+", tree.root().getElement());
		assertEquals("5", tree.left(tree.root()).getElement());
		assertEquals("-", tree.right(tree.root()).getElement());
		assertEquals("4", tree.left(tree.right(tree.root())).getElement());
		assertEquals("3", tree.right(tree.right(tree.root())).getElement());
		
		thrown.expect(IllegalArgumentException.class);
		tree = Assignment.prefix2tree("+ 5 - 4");
	}
	
	// example of using the Assignment.equals method to check that "- x + 1 2" simplifies to "- x 3"
	@Test(timeout = 100)
	public void testSimplify1() {
		LinkedBinaryTree<String> tree = Assignment.prefix2tree("- x + 1 2");
		tree = Assignment.simplify(tree);
		LinkedBinaryTree<String> expected = Assignment.prefix2tree("- x 3");
		assertTrue(Assignment.equals(tree, expected));
	}
	
	@Test(timeout = 100)
	public void testTree2Prefix() {
		LinkedBinaryTree<String> tree1 = Assignment.prefix2tree("1");
		LinkedBinaryTree<String> tree2 = Assignment.prefix2tree("X");
		LinkedBinaryTree<String> tree3 = Assignment.prefix2tree("+ 1 2");
		LinkedBinaryTree<String> tree4 = Assignment.prefix2tree("+ 1 - 2 3");
		LinkedBinaryTree<String> tree5 = Assignment.prefix2tree("* - 1 + b 3 d");
		
		assertEquals("1",Assignment.tree2prefix(tree1));
		assertEquals("X",Assignment.tree2prefix(tree2));
		assertEquals("+ 1 2",Assignment.tree2prefix(tree3));
		assertEquals("+ 1 - 2 3",Assignment.tree2prefix(tree4));
		assertEquals("* - 1 + b 3 d",Assignment.tree2prefix(tree5));
		
	}
	@Test(timeout = 100)
	public void tree2prefixinvalid(){
		thrown.expect(IllegalArgumentException.class);
		LinkedBinaryTree<String> tree = Assignment.prefix2tree("+ 1 +");
		LinkedBinaryTree<String> tree2 = Assignment.prefix2tree("- 1");
		Assignment.tree2prefix(tree);
		Assignment.tree2prefix(tree2);
		
	}
	@Test(timeout = 100)
	public void tree2infixinvalid(){
		thrown.expect(IllegalArgumentException.class);
		LinkedBinaryTree<String> tree = Assignment.prefix2tree("+ 1 +");
		LinkedBinaryTree<String> tree2 = Assignment.prefix2tree("- 1");
		Assignment.tree2infix(tree);
		Assignment.tree2infix(tree2);
		
	}
	@Test(timeout = 100)
	public void testTree2infix() {
		LinkedBinaryTree<String> tree1 = Assignment.prefix2tree("1");
		LinkedBinaryTree<String> tree2 = Assignment.prefix2tree("X");
		LinkedBinaryTree<String> tree3 = Assignment.prefix2tree("+ 1 2");
		LinkedBinaryTree<String> tree4 = Assignment.prefix2tree("+ 1 - 2 3");
		LinkedBinaryTree<String> tree5 = Assignment.prefix2tree("* - 1 + b 3 d");
		LinkedBinaryTree<String> tree6 = Assignment.prefix2tree("- + a b + a b");
		
		assertEquals("1",Assignment.tree2infix(tree1));
		assertEquals("X",Assignment.tree2infix(tree2));
		assertEquals("(1+2)",Assignment.tree2infix(tree3));
		assertEquals("(1+(2-3))",Assignment.tree2infix(tree4));
		assertEquals("((1-(b+3))*d)",Assignment.tree2infix(tree5));
		assertEquals("((a+b)-(a+b))",Assignment.tree2infix(tree6));
		
	}
	@Test(timeout = 100)
	public void substitueExamples() {
		LinkedBinaryTree<String> tree1 = Assignment.prefix2tree("- 1 c");
		Assignment.substitute(tree1, "c", 5);
		assertEquals("- 1 5",Assignment.tree2prefix(tree1));
		
		LinkedBinaryTree<String> tree2 = Assignment.prefix2tree("- 1 b");
		Assignment.substitute(tree2, "c", 5);
		assertEquals("- 1 b",Assignment.tree2prefix(tree2));
		
		LinkedBinaryTree<String> tree3 = Assignment.prefix2tree("+ c - c c");
		Assignment.substitute(tree3, "c", 5);
		assertEquals("+ 5 - 5 5",Assignment.tree2prefix(tree3));
		
	}
	@Test(timeout = 100)
	public void substitueMapExamples() {
		LinkedBinaryTree<String> tree = Assignment.prefix2tree("+ c - a b");
		HashMap<String, Integer> map = new HashMap<String,Integer>();
		map.put("a", 1);
		map.put("b", 5);
		map.put("c", 3);
		Assignment.substitute(tree, map);
		assertEquals("+ 3 - 1 5",Assignment.tree2prefix(tree));
		
		LinkedBinaryTree<String> tree2 = Assignment.prefix2tree("+ c - a b");
		HashMap<String, Integer> map2 = new HashMap<String,Integer>();
		map2.put("a", -1);
		map2.put("b", -5);
		map2.put("c", -3);
		map2.put("d", 1);
		Assignment.substitute(tree2, map2);
		assertEquals("+ -3 - -1 -5",Assignment.tree2prefix(tree2));
		
	}
	@Test(timeout = 100)
	public void substitueMapInvalid() {
		
		thrown.expect(IllegalArgumentException.class);
		LinkedBinaryTree<String> tree = Assignment.prefix2tree("+ c - a b");
		HashMap<String, Integer> map = new HashMap<String,Integer>();
		map.put("a", null);
		Assignment.substitute(tree, map);
		
	}
	@Test(timeout = 100)
	public void simplifyExamples() {
		LinkedBinaryTree<String> tree = Assignment.prefix2tree("- + 2 15 4");
		tree = Assignment.simplify(tree);
		LinkedBinaryTree<String> expected = Assignment.prefix2tree("13");
		assertTrue(Assignment.equals(tree, expected));
		
		LinkedBinaryTree<String> tree2 = Assignment.prefix2tree("- + 2 15 c");
		tree2 = Assignment.simplify(tree2);
		LinkedBinaryTree<String> expected2 = Assignment.prefix2tree("- 17 c");
		assertTrue(Assignment.equals(tree2, expected2));
		
		LinkedBinaryTree<String> tree3 = Assignment.prefix2tree("- - 2 2 c");
		tree3 = Assignment.simplify(tree3);
		LinkedBinaryTree<String> expected3 = Assignment.prefix2tree("- 0 c");
		assertTrue(Assignment.equals(tree3, expected3));
		
		LinkedBinaryTree<String> tree4 = Assignment.prefix2tree("- 2 3");
		tree4 = Assignment.simplify(tree4);
		LinkedBinaryTree<String> expected4 = Assignment.prefix2tree("-1");
		assertTrue(Assignment.equals(tree4, expected4));
		
		LinkedBinaryTree<String> tree5 = Assignment.prefix2tree("+ - 2 3 1");
		tree5 = Assignment.simplify(tree5);
		LinkedBinaryTree<String> expected5 = Assignment.prefix2tree("0");
		assertTrue(Assignment.equals(tree5, expected5));
		
		LinkedBinaryTree<String> tree6 = Assignment.prefix2tree("- 0 1");
		tree6 = Assignment.simplify(tree6);
		LinkedBinaryTree<String> expected6 = Assignment.prefix2tree("-1");
		assertTrue(Assignment.equals(tree6, expected6));
		
		LinkedBinaryTree<String> tree7 = Assignment.prefix2tree("- 2 3");
		tree7 = Assignment.simplify(tree7);
		LinkedBinaryTree<String> expected7 = Assignment.prefix2tree("-1");
		assertTrue(Assignment.equals(tree7, expected7));
		
	}
	
	
	@Test(timeout = 100)
	public void simplifyFancyExamples() {
		LinkedBinaryTree<String> tree = Assignment.prefix2tree("* 1 a");
		tree = Assignment.simplifyFancy(tree);
		LinkedBinaryTree<String> expected = Assignment.prefix2tree("a");
		assertTrue(Assignment.equals(tree, expected));
		
		LinkedBinaryTree<String> tree2 = Assignment.prefix2tree("+ - 2 2 c");
		tree2 = Assignment.simplifyFancy(tree2);
		LinkedBinaryTree<String> expected2 = Assignment.prefix2tree("c");
		assertTrue(Assignment.equals(tree2, expected2));
		
		LinkedBinaryTree<String> tree3 = Assignment.prefix2tree("+ - + 1 1 2 c");
		tree3 = Assignment.simplifyFancy(tree3);
		LinkedBinaryTree<String> expected3 = Assignment.prefix2tree("c");
		assertTrue(Assignment.equals(tree3, expected3));
		
		LinkedBinaryTree<String> tree4 = Assignment.prefix2tree("- * 1 c + c 0");
		tree4 = Assignment.simplifyFancy(tree4);
		LinkedBinaryTree<String> expected4 = Assignment.prefix2tree("0");
		assertTrue(Assignment.equals(tree4, expected4));
		
		LinkedBinaryTree<String> tree5 = Assignment.prefix2tree("- * 1 c + c 1");
		tree5 = Assignment.simplifyFancy(tree5);
		LinkedBinaryTree<String> expected5 = Assignment.prefix2tree("- c + c 1");
		assertTrue(Assignment.equals(tree5, expected5));
		
	}
	@Test(timeout = 100)
	public void testisArithemathicExpression(){
		LinkedBinaryTree<String> tree1 = Assignment.prefix2tree("1");
		LinkedBinaryTree<String> tree2 = Assignment.prefix2tree("X");
		LinkedBinaryTree<String> tree3 = Assignment.prefix2tree("+ 1 2");
		LinkedBinaryTree<String> tree4 = Assignment.prefix2tree("+ 1 - 2 3");
		LinkedBinaryTree<String> tree5 = Assignment.prefix2tree("* - 1 + b 3 d");
		
		LinkedBinaryTree<String> tree6 = new LinkedBinaryTree<String>();
		tree6.addRoot("+");
		tree6.addLeft(tree6.root(), "1");
		tree6.addRight(tree6.root(), "+");
		
		LinkedBinaryTree<String> tree7 = new LinkedBinaryTree<String>();
		tree7.addRoot("-");
		tree7.addLeft(tree7.root(), "1");
		LinkedBinaryTree<String> tree8 = Assignment.prefix2tree("-1");
		
		assertTrue(Assignment.isArithmeticExpression(tree1));
		assertTrue(Assignment.isArithmeticExpression(tree2));
		assertTrue(Assignment.isArithmeticExpression(tree3));
		assertTrue(Assignment.isArithmeticExpression(tree4));
		assertTrue(Assignment.isArithmeticExpression(tree5));
		assertFalse(Assignment.isArithmeticExpression(tree7));
		assertTrue(Assignment.isArithmeticExpression(tree8));
		
	}
	@Test(timeout = 100)
	public void testEqualsSubtreeSimplifyFancy(){
		LinkedBinaryTree<String> tree = Assignment.prefix2tree("- x x");
		tree = Assignment.simplifyFancy(tree);
		LinkedBinaryTree<String> expected = Assignment.prefix2tree("0");
		assertTrue(Assignment.equals(tree, expected));
		
		LinkedBinaryTree<String> tree1 = Assignment.prefix2tree("- - x x - x x");
		tree1 = Assignment.simplifyFancy(tree1);
		LinkedBinaryTree<String> expected1 = Assignment.prefix2tree("0");
		assertTrue(Assignment.equals(tree1, expected1));
		
		LinkedBinaryTree<String> tree6 = Assignment.prefix2tree("- + 4 - 6 5 + 4 - 6 5");
		tree6 = Assignment.simplifyFancy(tree6);
		assertEquals("0", Assignment.tree2prefix(tree6));

		LinkedBinaryTree<String> tree8 = Assignment.prefix2tree("- + a b + a b");
		tree8 = Assignment.simplifyFancy(tree8);
		assertEquals("0", Assignment.tree2prefix(tree8));
		
		LinkedBinaryTree<String> tree9 = Assignment.prefix2tree("- * 1 b * b 1");
		tree9 = Assignment.simplifyFancy(tree9);
		assertEquals("0", Assignment.tree2prefix(tree9));
		
		LinkedBinaryTree<String> tree12 = Assignment.prefix2tree("- + x 1 + x 1");
		tree12 = Assignment.simplifyFancy(tree12);
		assertEquals("0", Assignment.tree2prefix(tree12));
		
		LinkedBinaryTree<String> tree13 = Assignment.prefix2tree("- + x 1 + y 1");
		tree13 = Assignment.simplifyFancy(tree13);
		assertEquals("- + x 1 + y 1", Assignment.tree2prefix(tree13));
		
	}
	@Test(timeout = 100)
	public void testSimplifyFancyWeird(){
		LinkedBinaryTree<String> tree = Assignment.prefix2tree("* * 0 1 * 1 0");
		tree = Assignment.simplifyFancy(tree);
		assertEquals("0", Assignment.tree2prefix(tree));
		
		LinkedBinaryTree<String> tree2 = Assignment.prefix2tree("* 1 + a b");
		tree2 = Assignment.simplifyFancy(tree2);
		assertEquals("+ a b", Assignment.tree2prefix(tree2));
		
		LinkedBinaryTree<String> tree3 = Assignment.prefix2tree("* 0 + a b");
		tree3 = Assignment.simplifyFancy(tree3);
		assertEquals("0", Assignment.tree2prefix(tree3));
		
		LinkedBinaryTree<String> tree4 = Assignment.prefix2tree("+ * 0 b - 3 2");
		tree4 = Assignment.simplifyFancy(tree4);
		assertEquals("1", Assignment.tree2prefix(tree4));
		
		LinkedBinaryTree<String> tree5 = Assignment.prefix2tree("+ 1 * x 0");
		tree5 = Assignment.simplifyFancy(tree5);
		assertEquals("1", Assignment.tree2prefix(tree5));
		
		LinkedBinaryTree<String> tree7 = Assignment.prefix2tree("+ 7 * + a b 1");
		tree7 = Assignment.simplifyFancy(tree7);
		assertEquals("+ 7 + a b", Assignment.tree2prefix(tree7));
		
		LinkedBinaryTree<String> tree11 = Assignment.prefix2tree("* 1 + x y");
		tree11 = Assignment.simplifyFancy(tree11);
		assertEquals("+ x y", Assignment.tree2prefix(tree11));
		
		LinkedBinaryTree<String> tree10 = Assignment.prefix2tree("* 1 + - 2 x 0");
		tree10 = Assignment.simplifyFancy(tree10);
		assertEquals("- 2 x", Assignment.tree2prefix(tree10));
		
		LinkedBinaryTree<String> tree12 = Assignment.prefix2tree("* + 0 - 4 c 1");
		tree12 = Assignment.simplifyFancy(tree12);
		assertEquals("- 4 c", Assignment.tree2prefix(tree12));
		
		LinkedBinaryTree<String> tree13 = Assignment.prefix2tree("* 1 - - 4 c 0");
		tree13 = Assignment.simplifyFancy(tree13);
		assertEquals("- 4 c", Assignment.tree2prefix(tree13));
		
	}

}
