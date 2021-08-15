package test;

import cse2010.bst.BinarySearchTree;
import cse2010.orderedmap.Entry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Test extends BinarySearchTree<Integer, Integer> {
    public static void main(String[] args) {
        BinarySearchTree<Integer, Integer> tree = new BinarySearchTree<>();
        List<Integer> elements = Arrays.asList(5, 3, 7, 2, 1, 4, 9, 10, 8, 22, 23, 21, 6);
        elements.forEach(i -> tree.put(i, i));
        System.out.println(countRange((Node<Entry<Integer, Integer>>) tree.root(), 1, 10, 0));
    }

    public static int countRange(Node<Entry<Integer, Integer>> node, int low, int high, int count) {
        if (node.getLeft() == null && node.getRight() == null) {    // External Node
            return count;
        } else if (node.getElement().getKey() >= low && node.getElement().getKey() <= high) {
            return countRange(node.getLeft(), low, high, count+1) + countRange(node.getRight(), low, high, count+1);
        } else if (node.getElement().getKey() < low) {
            return countRange(node.getRight(), low, high, count);
        } else if (node.getElement().getKey() > high) {
            return countRange(node.getLeft(), low, high, count);
        } else {
            return 0;
        }
    }
}