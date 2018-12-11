import java.util.*;

import textbook.LinkedBinaryTree;
import textbook.LinkedQueue;
import textbook.Position;

public class Assignment {

	/**
	 * Convert an arithmetic expression (in prefix notation), to a binary tree
	 * 
	 * Binary operators are +, -, * (i.e. addition, subtraction, multiplication)
	 * Anything else is assumed to be a variable or numeric value
	 * 
	 * Example: "+ 2 15" will be a tree with root "+", left child "2" and right
	 * child "15" i.e. + 2 15
	 * 
	 * Example: "+ 2 - 4 5" will be a tree with root "+", left child "2", right
	 * child a subtree representing "- 4 5" i.e. + 2 - 4 5
	 * 
	 * This method runs in O(n) time
	 * 
	 * @param expression
	 *            - an arithmetic expression in prefix notation
	 * @return BinaryTree representing an expression expressed in prefix
	 *         notation
	 * @throws IllegalArgumentException
	 *             if expression was not a valid expression
	 */
	public static LinkedBinaryTree<String> prefix2tree(String expression) throws IllegalArgumentException {
		if (expression == null) {
			throw new IllegalArgumentException("Expression string was null");
		}
		// break up the expression string using spaces, into a queue
		LinkedQueue<String> tokens = new LinkedQueue<String>();
		for (String token : expression.split(" ")) {
			tokens.enqueue(token);
		}
		// recursively build the tree
		return prefix2tree(tokens);
	}

	/**
	 * Recursive helper method to build an tree representing an arithmetic
	 * expression in prefix notation, where the expression has already been
	 * broken up into a queue of tokens
	 * 
	 * @param tokens
	 * @return
	 * @throws IllegalArgumentException
	 *             if expression was not a valid expression
	 */
	private static LinkedBinaryTree<String> prefix2tree(LinkedQueue<String> tokens) throws IllegalArgumentException {
		LinkedBinaryTree<String> tree = new LinkedBinaryTree<String>();

		// use the next element of the queue to build the root
		if (tokens.isEmpty()) {
			throw new IllegalArgumentException("String was not a valid arithmetic expression in prefix notation");
		}
		String element = tokens.dequeue();
		tree.addRoot(element);

		// if the element is a binary operation, we need to build the left and
		// right subtrees
		if (element.equals("+") || element.equals("-") || element.equals("*")) {
			LinkedBinaryTree<String> left = prefix2tree(tokens);
			LinkedBinaryTree<String> right = prefix2tree(tokens);
			tree.attach(tree.root(), left, right);
		}
		// otherwise, assume it's a variable or a value, so it's a leaf (i.e.
		// nothing more to do)

		return tree;
	}

	/**
	 * Test to see if two trees are identical (every position in the tree stores the same value)
	 * 
	 * e.g. two trees representing "+ 1 2" are identical to each other, but not to a tree representing "+ 2 1"
	 * @param a
	 * @param b
	 * @return true if the trees have the same structure and values, false otherwise
	 */
	public static boolean equals(LinkedBinaryTree<String> a, LinkedBinaryTree<String> b) {
		return equals(a, b, a.root(), b.root());
	}

	/**
	 * Recursive helper method to compare two trees
	 * @param aTree one of the trees to compare
	 * @param bTree the other tree to compare
	 * @param aRoot a position in the first tree
	 * @param bRoot a position in the second tree (corresponding to a position in the first)
	 * @return true if the subtrees rooted at the given positions are identical
	 */
	private static boolean equals(LinkedBinaryTree<String> aTree, LinkedBinaryTree<String> bTree, Position<String> aRoot, Position<String> bRoot) {
		//if either of the positions is null, then they are the same only if they are both null
		if(aRoot == null || bRoot == null) {
			return (aRoot == null) && (bRoot == null);
		}
		//first check that the elements stored in the current positions are the same
		String a = aRoot.getElement();
		String b = bRoot.getElement();
		if((a==null && b==null) || a.equals(b)) {
			//then recursively check if the left subtrees are the same...
			boolean left = equals(aTree, bTree, aTree.left(aRoot), bTree.left(bRoot));
			//...and if the right subtrees are the same
			boolean right = equals(aTree, bTree, aTree.right(aRoot), bTree.right(bRoot));
			//return true if they both matched
			return left && right;
		}
		else {
			return false;
		}
	}


	/**
	 * Given a tree, this method should output a string for the corresponding
	 * arithmetic expression in prefix notation, without (parenthesis) (also
	 * known as Polish notation)
	 * 
	 * Example: A tree with root "+", left child "2" and right child "15" would
	 * be "+ 2 15" Example: A tree with root "-", left child a subtree
	 * representing "(2+15)" and right child "4" would be "- + 2 15 4"
	 * 
	 * Ideally, this method should run in O(n) time
	 * 
	 * @param tree
	 *            - a tree representing an arithmetic expression
	 * @return prefix notation expression of the tree
	 * @throws IllegalArgumentException
	 *             if tree was not a valid expression
	 */

	public static String tree2prefix(LinkedBinaryTree<String> tree) throws IllegalArgumentException {
		
		if (tree == null){
			throw new IllegalArgumentException();
		}

		if (isArithmeticExpression(tree) == false){
			throw new IllegalArgumentException();
		}

		String solution = ""; // String to be return 

		solution = solution + prefixrecurse(tree,tree.root());

		return solution;
	}

	private static String prefixrecurse(LinkedBinaryTree<String> tree ,Position<String> p){
		//pre-order traversal
		if (p == null){
			return null;
		}
		// position has 0 children base case
		if (tree.numChildren(p) == 0){
			return p.getElement();
		}
		// position has 2 children recurse in pre-order and add to string
		if (tree.numChildren(p) == 2 ){
			return p.getElement() + " " +prefixrecurse(tree,tree.left(p)) + " " +prefixrecurse(tree,tree.right(p));
		}
		return "";
	}

	/**
	 * Given a tree, this method should output a string for the corresponding
	 * arithmetic expression in infix notation with parenthesis (i.e. the most
	 * commonly used notation).
	 * 
	 * Example: A tree with root "+", left child "2" and right child "15" would
	 * be "(2+15)"
	 * 
	 * Example: A tree with root "-", left child a subtree representing "(2+15)"
	 * and right child "4" would be "((2+15)-4)"
	 * 
	 * Optionally, you may leave out the outermost parenthesis, but it's fine to
	 * leave them on. (i.e. "2+15" and "(2+15)-4" would also be acceptable
	 * output for the examples above)
	 * 
	 * Ideally, this method should run in O(n) time
	 * 
	 * @param tree
	 *            - a tree representing an arithmetic expression
	 * @return infix notation expression of the tree
	 * @throws IllegalArgumentException
	 *             if tree was not a valid expression
	 */
	public static String tree2infix(LinkedBinaryTree<String> tree) throws IllegalArgumentException {
		
		if (tree == null){
			throw new IllegalArgumentException();
		}
		if (isArithmeticExpression(tree) == false){
			throw new IllegalArgumentException();
		}

		String solution = ""; // String to be return 

		solution = solution + infixrecurse(tree,tree.root());

		return solution;

	}
	private static String infixrecurse(LinkedBinaryTree<String> tree ,Position<String> p){
		//in-order traversal
		if (p == null){
			return null;
		}
		// position has 0 children base case
		if (tree.numChildren(p) == 0){
			return p.getElement();
		}
		// position has 2 children recurse in in-order and add to string
		if (tree.numChildren(p) == 2 ){
			return "("+infixrecurse(tree,tree.left(p))+p.getElement() +infixrecurse(tree,tree.right(p))+")";
		}
		return "";
	}

	/**
	 * Given a tree, this method should simplify any subtrees which can be
	 * evaluated to a single integer value.
	 * 
	 * Ideally, this method should run in O(n) time
	 * 
	 * @param tree
	 *            - a tree representing an arithmetic expression
	 * @return resulting binary tree after evaluating as many of the subtrees as
	 *         possible
	 * @throws IllegalArgumentException
	 *             if tree was not a valid expression
	 */
	public static LinkedBinaryTree<String> simplify(LinkedBinaryTree<String> tree) throws IllegalArgumentException {
		
		if (tree == null){
			throw new IllegalArgumentException();
		}
		if (isArithmeticExpression(tree) == false){
			throw new IllegalArgumentException();
		}
		
		if (tree.numChildren(tree.root()) == 0){
			return tree;
		}
		simplifyhelp(tree,tree.root());
		return tree;
	}
	
	// Parsing the string to integer
	private static Integer toInteger(String s){
		return Integer.parseInt(s);
	}
	// check if string is a number by using regex. Assumed case of O(1).
	private static boolean checkNum(String s){
		return s.matches("^-?\\d+$");
	}
	// Check if string is an operator. Assumed case of O(1). 
	private static boolean isOperator(String s){
		return (s.equals("+") || s.equals("-") || s.equals("*"));
	}
	
	private static boolean isVar(String s){
		if (checkNum(s) == false && isOperator(s) == false){
			return true;
		}
		return false;
	}
	
	// Sum the left and right node and simplify the sub tree
	private static void sumNode(LinkedBinaryTree<String> tree, Position<String> p){
		Integer left = toInteger(tree.left(p).getElement());
		Integer right = toInteger(tree.right(p).getElement());
		String sum = Integer.toString(left+right);
		tree.set(p, sum);
		tree.remove(tree.left(p));
		tree.remove(tree.right(p));
	}
	
	// Sum the left and right node and simplify the sub tree
	private static void timesNode(LinkedBinaryTree<String> tree, Position<String> p){
		Integer left = toInteger(tree.left(p).getElement());
		Integer right = toInteger(tree.right(p).getElement());
		String sum = Integer.toString(left*right);
		tree.set(p, sum);
		tree.remove(tree.left(p));
		tree.remove(tree.right(p));
	}
	
	// Subtract the left and right node and simplify the sub tree
	private static void subtractNode(LinkedBinaryTree<String> tree, Position<String> p){
		Integer left = toInteger(tree.left(p).getElement());
		Integer right = toInteger(tree.right(p).getElement());
		String sum = Integer.toString(left-right);
		tree.set(p, sum);
		tree.remove(tree.left(p));
		tree.remove(tree.right(p));
	}
	
	//simplify helper recursion method
	private static void simplifyhelp(LinkedBinaryTree<String> tree, Position<String> p){
		// should be post-order left right root
		if (p == null){
			return;
		}
		
		// if the position is operator 
		if (isOperator(p.getElement())){
			
			// if left node is not number recurse left
			if (!checkNum(tree.left(p).getElement())){
				simplifyhelp(tree,tree.left(p));
			}
			// if right node is not number recurse right
			if (!checkNum(tree.right(p).getElement())){
				simplifyhelp(tree,tree.right(p));
			}
			
			// if both left and right is number, perform operations.
			if (checkNum(tree.left(p).getElement()) && checkNum(tree.right(p).getElement())){

				if(p.getElement().equals("+")){

					sumNode(tree,p);
				}
				if(p.getElement().equals("-")){

					subtractNode(tree,p);
				}
				if(p.getElement().equals("*")){

					timesNode(tree,p);
				}
			}
		}
	}
	/**
	 * This should do everything the simplify method does AND also apply the following rules:
	 *  * 1 x == x  i.e.  (1*x)==x
	 *  * x 1 == x        (x*1)==x
	 *  * 0 x == 0        (0*x)==0
	 *  * x 0 == 0        (x*0)==0
	 *  + 0 x == x        (0+x)==x
	 *  + x 0 == 0        (x+0)==x
	 *  - x 0 == x        (x-0)==x
	 *  - x x == 0        (x-x)==0
	 *  
	 *  Example: - * 1 x x == 0, in infix notation: ((1*x)-x) = (x-x) = 0
	 *  
	 * Ideally, this method should run in O(n) time (harder to achieve than for other methods!)
	 * 
	 * @param tree
	 *            - a tree representing an arithmetic expression
	 * @return resulting binary tree after applying the simplifications
	 * @throws IllegalArgumentException
	 *             if tree was not a valid expression
	 */
	public static LinkedBinaryTree<String> simplifyFancy(LinkedBinaryTree<String> tree) throws IllegalArgumentException {
		
		if (tree == null){
			throw new IllegalArgumentException();
		}
		if (isArithmeticExpression(tree) == false){
			throw new IllegalArgumentException();
		}
		if (tree.numChildren(tree.root()) == 0){
			return tree;
		}
		simplifyFancyhelp(tree,tree.root());

		return tree;
	}
	
	// recurse the sub tree and remove nodes in post-order 
	private static void removeSubtree(LinkedBinaryTree<String> tree, Position<String> p){
		if (p == null){
			return;
		}

		removeSubtree(tree,tree.left(p));
		removeSubtree(tree,tree.right(p));
		tree.remove(p);
	}

	private static void simplifyFancyhelp(LinkedBinaryTree<String> tree, Position<String> p){
		// post-order left right root
		if (p == null){
			return;
		}

		if (isOperator(p.getElement())){
			
			if (isOperator(tree.left(p).getElement())){
				simplifyFancyhelp(tree,tree.left(p));
			}
			if (isOperator(tree.right(p).getElement())){
				simplifyFancyhelp(tree,tree.right(p));
			}


			// case for all multiplication as defined in the specification

			if(p.getElement().equals("*")){
				String left = tree.left(p).getElement();
				String right = tree.right(p).getElement();

				if (checkNum(left) && checkNum(right)){
					timesNode(tree,p);
				}
				else if (left.equals("1") && isOperator(right)){

					tree.remove(tree.left(p));
					tree.remove(p);
				}
				else if (right.equals("1") && isOperator(left)){

					tree.remove(tree.right(p));
					tree.remove(p);
				}
				// special case of O(n^2)
				else if (left.equals("0") && isOperator(right)){ 
					removeSubtree(tree,tree.right(p));
					tree.set(p, "0");
					tree.remove(tree.left(p));

				}
				// special case of O(n^2)
				else if (right.equals("0") && isOperator(left)){
					removeSubtree(tree,tree.left(p));
					tree.set(p, "0");
					tree.remove(tree.right(p));

				}
				else if (left.equals("1") && isVar(right)){

					tree.set(p, right);
					tree.remove(tree.left(p));
					tree.remove(tree.right(p));
				}
				else if (right.equals("1") && isVar(left)){

					tree.set(p, left);
					tree.remove(tree.left(p));
					tree.remove(tree.right(p));
				}
				else if (left.equals("0") && isVar(right)){

					tree.set(p, "0");
					tree.remove(tree.left(p));
					tree.remove(tree.right(p));
				}
				else if (right.equals("0") && isVar(left)){

					tree.set(p, "0");
					tree.remove(tree.left(p));
					tree.remove(tree.right(p));
				}

			}

			// case for all addition as defined in the specification

			else if (p.getElement().equals("+")){
				String left = tree.left(p).getElement();
				String right = tree.right(p).getElement();

				if (checkNum(left) && checkNum(right)){
					sumNode(tree,p);
				}
				else if (left.equals("0") && isVar(right)){

					tree.set(p, right);
					tree.remove(tree.left(p));
					tree.remove(tree.right(p));
				}

				else if (right.equals("0") && isVar(left)){

					tree.set(p, left);
					tree.remove(tree.left(p));
					tree.remove(tree.right(p));

				}
				else if (right.equals("0") && isOperator(left)){

					tree.remove(tree.right(p));
					tree.remove(p);

				}
				else if (left.equals("0") && isOperator(right)){

					tree.remove(tree.left(p));
					tree.remove(p);

				}

			}

			// case for all subtraction as defined in the specification

			else if (p.getElement().equals("-")){
				String left = tree.left(p).getElement();
				String right = tree.right(p).getElement();

				if (checkNum(left) && checkNum(right)){
					subtractNode(tree,p);
				}
				else if (right.equals("0") && isVar(left)){

					tree.set(p, left);
					tree.remove(tree.left(p));
					tree.remove(tree.right(p));
				}
				
				else if (right.equals(left) && (isVar(left) && isVar(right))){
					
					tree.set(p, "0");
					tree.remove(tree.left(p));
					tree.remove(tree.right(p));
					
				}
				// special case of O(n^2)
				else if (equals(tree, tree, tree.left(p), tree.right(p)) && (isOperator(left) && isOperator(right))){
					removeSubtree(tree,tree.left(p));
					removeSubtree(tree,tree.right(p));
					tree.set(p, "0");
				}
				else if (right.equals("0") && isOperator(left)){
					
					tree.remove(tree.right(p));
					tree.remove(p);

				}

			}
		}

	}

	/**
	 * Given a tree, a variable label and a value, this should replace all
	 * instances of that variable in the tree with the given value
	 * 
	 * Ideally, this method should run in O(n) time (quite hard! O(n^2) is easier.)
	 * 
	 * @param tree
	 *            - a tree representing an arithmetic expression
	 * @param variable
	 *            - a variable label that might exist in the tree
	 * @param value
	 *            - an integer value that the variable represents
	 * @return Tree after replacing all instances of the specified variable with
	 *         it's numeric value
	 * @throws IllegalArgumentException
	 *             if tree was not a valid expression, or either of the other
	 *             arguments are null
	 */
	public static LinkedBinaryTree<String> substitute(LinkedBinaryTree<String> tree, String variable, int value)
			throws IllegalArgumentException {
		
		if (tree == null){
			throw new IllegalArgumentException();
		}
		if (isArithmeticExpression(tree) == false){
			throw new IllegalArgumentException();
		}
		if (variable == null){
			throw new IllegalArgumentException();
		}
		if (tree.numChildren(tree.root()) == 0){
			return tree;
		}
		return substitutehelp(tree, variable, value,tree.root());
	}
	
	// helper method to recurse and find the variable that needs to be substitute
	// recurse in post order
	private static LinkedBinaryTree<String> substitutehelp(LinkedBinaryTree<String> tree, String variable, int value,Position<String> p){

		if (p.getElement().equals(variable)){

			tree.set(p, Integer.toString(value));
		}
		if (tree.numChildren(p) == 2 ){
			substitutehelp(tree,variable,value,tree.left(p));
			substitutehelp(tree,variable,value,tree.right(p));
			return tree;
		}
		if (tree.numChildren(p) == 0){
			return tree;
		}

		return tree;
	}
	/**
	 * Given a tree and a a map of variable labels to values, this should
	 * replace all instances of those variables in the tree with the
	 * corresponding given values
	 * 
	 * Ideally, this method should run in O(n) expected time
	 * 
	 * @param tree
	 *            - a tree representing an arithmetic expression
	 * @param map
	 *            - a map of variable labels to integer values
	 * @return Tree after replacing all instances of variables which are keys in
	 *         the map, with their numeric values
	 * @throws IllegalArgumentException
	 *             if tree was not a valid expression, or map is null, or tries
	 *             to substitute a null into the tree
	 */
	public static LinkedBinaryTree<String> substitute(LinkedBinaryTree<String> tree, HashMap<String, Integer> map)
			throws IllegalArgumentException {
		
		if (tree == null){
			throw new IllegalArgumentException();
		}
		if (map == null){
			throw new IllegalArgumentException();
		}
		if (isArithmeticExpression(tree) == false){
			throw new IllegalArgumentException();
		}
		if (map.isEmpty()){
			throw new IllegalArgumentException();
		}

		return substituteMaphelp(tree,tree.root(), map);
	}
	
	// helper method, recurse to find matching variable in the tree that match the keys in the map
	private static LinkedBinaryTree<String> substituteMaphelp(LinkedBinaryTree<String> tree, Position<String> p, HashMap<String, Integer> map ){
		if (p == null){
			throw new IllegalArgumentException();
		}
		if (isVar(p.getElement()) == true){
			if (map.containsKey(p.getElement())){
				if (map.get(p.getElement()) == null){
					throw new IllegalArgumentException();
				}
				Integer x = (map.get(p.getElement()));
				tree.set(p, Integer.toString(x));
			}
		}
		if (tree.numChildren(p) == 2){
			substituteMaphelp(tree,tree.left(p),map);
			substituteMaphelp(tree,tree.right(p),map);
			return tree;
		}
		if (tree.numChildren(p) == 0){
			return tree;
		}

		return tree;
	}
	/**
	 * Given a tree, identify if that tree represents a valid arithmetic
	 * expression (possibly with variables)
	 * 
	 * Ideally, this method should run in O(n) expected time
	 * 
	 * @param tree
	 *            - a tree representing an arithmetic expression
	 * @return true if the tree is not null and it obeys the structure of an
	 *              arithmetic expression. Otherwise, it returns false
	 */
	public static boolean isArithmeticExpression(LinkedBinaryTree<String> tree) {
		
		if (tree == null){
			return false;
		}
		if (tree.numChildren(tree.root()) == 1){
			return false;
		}

		return isArithHelp(tree,tree.root());
	}
	
	// traverse the tree to find a node that invalidate the tree
	private static boolean isArithHelp(LinkedBinaryTree<String> tree, Position<String> p){
		
		if (tree.numChildren(p) == 0 && isOperator(p.getElement()) == true){
			return false;
		}
		if (tree.numChildren(p) != 0 && (checkNum(p.getElement()) || isVar(p.getElement()))){
			return false;
		}
		if (tree.left(p) == null || tree.right(p) == null){
			return true;
		}
		
		return isArithHelp(tree,tree.left(p)) && isArithHelp(tree,tree.right(p));

	}

}