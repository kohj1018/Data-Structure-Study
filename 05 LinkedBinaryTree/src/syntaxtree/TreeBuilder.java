package syntaxtree;

import cse2010.trees.Position;
import cse2010.trees.binary.linked.LinkedBinaryTree;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class TreeBuilder {
    private static Map<String, Integer> operators = new HashMap<>();
    static {
        // We will consider only four binary operators: *, /, +, -
        operators.put("(", 4);
        operators.put("*", 3);
        operators.put("/", 3);
        operators.put("+", 2);
        operators.put("-", 2);
        operators.put(")", 1);

        operators.put("$", 0);
        // you may put other entry/entries if needed ...
    }

    // 여기 밑에 < >안에 SyntaxTree로 적혀있던거 Dobule로 바꿈
    private static Stack<SyntaxTree> operandStack = new Stack<>();
    private static Stack<String> operatorStack = new Stack<>();

    // postfix tree를 위한 stack
    private static Stack<SyntaxTree> postfixStack = new Stack<>();

    /**
     * Construct a syntax free from infix arithmetic expression.
     * @param expression an infix arithmetic expression
     * @return the syntax tree
     */
    public static SyntaxTree buildFromInfix(String expression) {
        initStacks();

        // token으로 parse
        String[] tokens = Utils.parse(expression);

        for (String token : tokens) {
            if (Utils.isNumeric(token) || Character.isLetter(token.charAt(0))) {
                SyntaxTree leaf = new SyntaxTree();
                leaf.addRoot(token);

                operandStack.push(leaf);
            } else {    // operator
                repeatOps(token);
            }
        }

        repeatOps("$");

        return operandStack.pop();
    }

    private static void repeatOps(String token) {
        if (operators.get(token) == 1) {    // token == ")"
            while (operators.get(operatorStack.peek()) != 4) {  // Stack의 top에 있는 operator가 "(" 아닐때까지 반복
                doOps();
            }
            operatorStack.pop();
        } else {
            while (operandStack.size() > 1 && operators.get(operatorStack.peek()) >= operators.get(token) && operators.get(operatorStack.peek()) != 4) {
                doOps();
            }
            if (token != "$") {
                operatorStack.push(token);
            }
        }
    }

    private static void doOps() {
        SyntaxTree subTree = new SyntaxTree();
        subTree.addRoot(operatorStack.pop());

        SyntaxTree rightSubTree = operandStack.pop();
        subTree.attach(subTree.root(), operandStack.pop(), rightSubTree);

        operandStack.push(subTree);
    }

    /**
     * Construct a syntax free from postfix arithmetic expression.
     * @param expression a postfix arithmetic expression
     * @return the syntax tree
     */
    public static SyntaxTree buildFromPostfix(String expression) {
        initStacks();

        // token으로 parse
        String[] tokens = Utils.parse(expression);

        for (String token : tokens) {
            SyntaxTree subTree = new SyntaxTree();
            subTree.addRoot(token);

            if (Utils.isNumeric(token) || Character.isLetter(token.charAt(0))) {
                postfixStack.push(subTree);
            } else {    // operator
                SyntaxTree rightSubTree = postfixStack.pop();
                subTree.attach(subTree.root(), postfixStack.pop(), rightSubTree);

                postfixStack.push(subTree);
            }
        }

        return postfixStack.pop();
    }

    /**
     * Reset stacks
     */
    private static void initStacks() {
        operandStack.clear();
        operatorStack.clear();
        postfixStack.clear();
    }
}
