package syntaxtree;

import cse2010.trees.Position;
import cse2010.trees.binary.linked.LinkedBinaryTree;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Linked-based Arithmetic expression tree.
 */
public class SyntaxTree extends LinkedBinaryTree<String> {

    /**
     * Evaluate syntax tree.
     * @return evaluated arithmetic expression
     */
    public double evaluate() {
        return evaluate_help((Node<String>) this.root());
    }

    private double evaluate_help(Node<String> node) {
        if (node.getLeft() == null && node.getRight() == null) {
            return Double.parseDouble(node.getElement());
        }

        double x = evaluate_help(node.getLeft());
        double y = evaluate_help(node.getRight());
        String operator = node.getElement();

        return switch (operator) {
            case "+" -> x + y;
            case "-" -> x - y;
            case "*" -> x * y;
            case "/" -> x / y;
            default -> throw new IllegalArgumentException("Unknown operator");
        };
    }

    /**
     * Returns postfix expression corresponding to this syntax tree.
     * @return postfix representation of this syntax three
     */
    public String toPostFix() {
        return cvtToString(postOrder());
    }

    /**
     * Returns prefix expression corresponding to this syntax tree.
     * @return prefix representation of this syntax three
     */
    public String toPreFix() {
        return cvtToString(preOrder());
    }

    /**
     * Returns fully parenthesized infix expression corresponding to this syntax tree.
     * @return fully parenthesized prefix representation of this syntax tree
     */
    public String toInfix() {
        return toInfix(root());
    }

    /**
     * Returns fully parenthesized infix expression corresponding to this syntax subtree.
     * @return fully parenthesized infix representation of this syntax subtree
     */
    private String toInfix(Position<String> position) {
        Node<String> node = validate(position);
        StringBuilder builder = new StringBuilder();

        toInfix_help(node, builder);

        return builder.toString();
    }

    private void toInfix_help(Node<String> node, StringBuilder builder) {
        if (node.getLeft() != null || node.getRight() != null) {  //isOperator
            builder.append("(");
        }
        if (node.getLeft() != null) {
            toInfix_help(node.getLeft(), builder);
        }

        if (node.getLeft() != null || node.getRight() != null) {    // Operator라면 양쪽을 띄어쓰고 아니면 그냥 쓰기
            builder.append(" " + node.getElement() + " ");
        } else {
            builder.append(node.getElement());
        }



        if (node.getRight() != null) {
            toInfix_help(node.getRight(), builder);
        }
        if (node.getLeft() != null || node.getRight() != null) {  //isOperator
            builder.append(")");
        }
    }

    /**
     * Returns a formatted string representation of tree hierarchy.
     * The formatted string representation of the expression tree corresponsing
     * to {@code (a + b) * (c - d)} looks as follow:
     * *
     *   +
     *     a
     *     b
     *   -
     *     c
     *     d
     * @return a formatted string representation of tree hierarchy
     */
    public String showTree() {
        return indentTree(root(), 0);
    }

    /**
     * Returns a formatted string representation of the subtree hierarchy.
     * @param position a node position
     * @param level indentation level; 0 means no indentation; the unit of
     *              the indentation level is two spaces.
     * @return  a formatted string representation of the subtree hierarchy
     */
    private String indentTree(Position<String> position, int level) {
        Node<String> node = validate(position);
        StringBuilder builder = new StringBuilder();

        indentTree_help(node, level, builder);

        return builder.toString();
    }

    private void indentTree_help(Node<String> node, int level, StringBuilder builder) {

        builder.append(" ".repeat(level * 2) + node.getElement() + "\n");

        if (node.getLeft() != null) {
            indentTree_help(node.getLeft(), level + 1, builder);
        }
        if (node.getRight() != null) {
            indentTree_help(node.getRight(), level + 1, builder);
        }
    }

    /**
     * Convert list of Positions to a serialized string in which
     * each element of the position is delimited by the ' ' character.
     * @param positions a list of Position<String>s
     * @return a string serialized as a list of elements
     */
    private String cvtToString(List<Position<String>> positions) {
        return positions.stream().map(Position::getElement).collect(Collectors.joining(" "));
    }

    public static void main(String[] args) {
        SyntaxTree tree = TreeBuilder.buildFromInfix("((1 + 20) * (31 + 49))");

        System.out.println( tree.showTree() );

        System.out.println( tree.toPostFix() + "\n" );

        System.out.println( tree.toPreFix() + "\n" );

        System.out.println( tree.toInfix() + "\n" );

        System.out.println( tree.evaluate() + "\n" );
    }
}