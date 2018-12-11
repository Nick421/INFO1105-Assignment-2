# INFO1105-Assignment-2
Prefix and infix converter and simplifier using Linked Binary Tree. 

# Example of usage
* Main method is needed.
* prefix2tree(String expression) 
   * convert an arithmetic expression (in prefix notation), to a binary tree. 
   * @param expression - an arithmetic expression in prefix notation.
   
* tree2prefix(LinkedBinaryTree<String> tree) 
   * Given a tree, this method should output a string for the corresponding arithmetic expression in prefix notation, without (parenthesis) (also known as Polish notation)
   * @param tree - a tree representing an arithmetic expression
  
* tree2infix(LinkedBinaryTree<String> tree)
   * Given a tree, this method should output a string for the corresponding arithmetic expression in infix notation with parenthesis (i.e. the most commonly used notation).
   * @param tree - a tree representing an arithmetic expression
  
* simplify(LinkedBinaryTree<String> tree)
   * Given a tree, this method should simplify any subtrees which can be evaluated to a single integer value.
	 * @param tree - a tree representing an arithmetic expression
* simplifyFancy(LinkedBinaryTree<String> tree)
   * This should do everything the simplify method does AND also apply rules such as:
     *  (1 times 0) = 0 etc.
   * @param tree - a tree representing an arithmetic expression
* substitute(LinkedBinaryTree<String> tree, String variable, int value)
   * Given a tree, a variable label and a value, this should replace all instances of that variable in the tree with the given value.
   * @param tree - a tree representing an arithmetic expression
   * @param variable - a variable label that might exist in the tree
	 * @param value  - an integer value that the variable represents 
* substitute(LinkedBinaryTree<String> tree, HashMap<String, Integer> map)
   * Given a tree and a a map of variable labels to values, this should replace all instances of those variables in the tree with the corresponding given values
   * @param tree - a tree representing an arithmetic expression
	 * @param map - a map of variable labels to integer values
* isArithmeticExpression(LinkedBinaryTree<String> tree)
   * Given a tree, identify if that tree represents a valid arithmetic expression (possibly with variables)
   * @param tree - a tree representing an arithmetic expression

# DO NOT PLAGIARISE! UNIVERSITY OF SYDNEY TAKES PLAGIARISM SERIOUSLY!!
* INFO1105 does not exist anymore but COMP2123 is the replacement unit.
* In the case of assignments being reuse, please do not copy! you will learn nothing from copying!
