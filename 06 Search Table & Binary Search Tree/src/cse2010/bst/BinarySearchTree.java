package cse2010.bst;

import cse2010.orderedmap.Entry;
import cse2010.orderedmap.OrderedMap;
import cse2010.trees.Position;
import cse2010.trees.binary.linked.LinkedBinaryTree;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Link-based implementation of binary search tree
 *
 * @param <K> the key type
 * @param <V> the value type
 */
public class BinarySearchTree<K extends Comparable<K>, V> extends LinkedBinaryTree<Entry<K, V>>
        implements OrderedMap<K, V> {


    // search method
    private Node<Entry<K, V>> treeSearch(K key, Node<Entry<K, V>> node) {
        if (node.getLeft() == null && node.getRight() == null) {
            return node;
        }
        if (key.compareTo(node.getElement().getKey()) < 0) {
            return treeSearch(key, node.getLeft());
        } else if (key.compareTo(node.getElement().getKey()) == 0) {
            return node;
        } else {
            return treeSearch(key, node.getRight());
        }
    }

    /**
     * Removes the entry having key "key" (if any) and returns its associated value.
     *
     * @param key search key
     */
    @Override
    public V remove(K key) {
        if (root == null) {
            return null;
        }
        Node<Entry<K, V>> resultNode = treeSearch(key, root);
        if (resultNode.getLeft() == null && resultNode.getRight() == null) {
            return null;
        } else {
            V oldValue = resultNode.getElement().getValue();
            if (resultNode.getLeft().getLeft() == null && resultNode.getLeft().getRight() == null) {    // resultNode의 left child가 external node인 경우
                if (resultNode.getParent() == null) {   // root 노드인 경우
                    resultNode.getRight().setParent(null);
                    resultNode.setElement(null);
                    root = resultNode.getRight();
                } else {
                    if (resultNode.getParent().getLeft() == resultNode) {   // resultNode가 부모 노드의 left child 노드인지 확인
                        resultNode.getParent().setLeft(resultNode.getRight());
                    } else {    // resultNode가 부모 노드의 right child 노드인 경우
                        resultNode.getParent().setRight(resultNode.getRight());
                    }
                    resultNode.getRight().setParent(resultNode.getParent());
                    resultNode.setParent(null);
                    resultNode.setRight(null);
                }
            } else if (resultNode.getRight().getLeft() == null && resultNode.getRight().getRight() == null) {   // resultNode의 right child가 external node인 경우
                if (resultNode.getParent() == null) {   // root 노드인 경우
                    resultNode.getLeft().setParent(null);
                    resultNode.setElement(null);
                    root = resultNode.getLeft();
                } else {
                    if (resultNode.getParent().getLeft() == resultNode) {   // resultNode가 부모 노드의 left child 노드인지 확인
                        resultNode.getParent().setLeft(resultNode.getLeft());
                    } else {    // resultNode가 부모 노드의 right child 노드인 경우
                        resultNode.getParent().setRight(resultNode.getLeft());
                    }
                    resultNode.getLeft().setParent(resultNode.getParent());
                    resultNode.setParent(null);
                    resultNode.setLeft(null);
                }
            } else {    // resultNode의 두 child가 모두 internal node인 경우
                Node<Entry<K, V>> nextInorderNode = resultNode.getRight(); // inorder traverse시의 다음 순서의 노드를 찾음
                while (nextInorderNode.getLeft().getLeft() != null && nextInorderNode.getLeft().getRight() != null) {
                    nextInorderNode = nextInorderNode.getLeft();
                }
                // swap
                Entry<K, V> temp = resultNode.getElement();
                resultNode.setElement(nextInorderNode.getElement());
                nextInorderNode.setElement(temp);

                if (resultNode.getRight().getLeft().getElement() == null) {
                    resultNode.getRight().getRight().setParent(resultNode);
                    resultNode.getRight().setParent(null);
                    resultNode.setRight(resultNode.getRight().getRight());
                } else {
                    nextInorderNode.getParent().setLeft(nextInorderNode.getRight());
                    nextInorderNode.getRight().setParent(nextInorderNode.getParent());
                    nextInorderNode.setParent(null);
                    nextInorderNode.setRight(null);
                }
            }
            size--;
            return oldValue;
        }
    }

    /**
     * Returns the set of keys in the map sorted in ascending order.
     * 
     * @return the set of keys in the map sorted in ascending order
     */
    @Override
    public Set<K> keys() {

        return inOrder().stream().filter(p -> p.getElement() != null).map(p -> p.getElement().getKey())
                .collect(Collectors.toSet());
    }

    /**
     * Returns the collection of values in the map.
     * 
     * @return the collection of values in the map
     */
    @Override
    public Collection<V> values() {
        return inOrder().stream().filter(p -> p.getElement() != null).map(p -> p.getElement().getValue())
                .collect(Collectors.toList());
    }

    /**
     * Returns the collection of entries in the map.
     * 
     * @return the collection of entries in the map
     */
    @Override
    public Collection<Entry<K, V>> entries() {
        return inOrder().stream().filter(p -> p.getElement() != null).map(Position::getElement)
                .collect(Collectors.toList());
    }

    /**
     * Returns the entry with smallest key value (or null, if the tree is empty).
     *
     * @return the entry with smallest key value (or null, if the tree is empty)
     */
    @Override
    public Entry<K, V> firstEntry() {
        if (size == 0) {
            return null;
        }
        Node<Entry<K, V>> cursor = root;
        while (cursor.getLeft().getLeft() != null || cursor.getLeft().getRight() != null) {
            cursor = cursor.getLeft();
        }
        return cursor.getElement();
    }

    /**
     * Returns the entry with largest key value(or null if the tree is empty)
     *
     * @return the entry with largest key value (or null, if the tree is empty)
     */
    @Override
    public Entry<K, V> lastEntry() {
        if (size == 0) {
            return null;
        }
        Node<Entry<K, V>> cursor = root;
        while (cursor.getRight().getLeft() != null || cursor.getRight().getRight() != null) {
            cursor = cursor.getRight();
        }
        return cursor.getElement();
    }

    /**
     * Returns the entry with the largest key value less than or equal to "key" (or
     * null if no such entry exists).
     *
     * @param key search key
     * @return the entry with the largest key value less than or equal to "key" (or
     *         null, if no such entry exists)
     */
    @Override
    public Entry<K, V> floorEntry(K key) {
        if (size == 0) {
            return null;
        }
        Node<Entry<K, V>> resultNode = treeSearch(key, root);
        if (resultNode.getLeft() == null && resultNode.getRight() == null) {
            if (resultNode.getParent().getLeft() == resultNode && resultNode.getParent().getElement().getKey().compareTo(firstEntry().getKey()) <= 0) {   // no such entry exists.
                return null;
            } else if (resultNode.getParent().getRight() != resultNode) {
                while (resultNode.getParent().getParent() != null && resultNode.getParent().getRight() != resultNode) {
                    resultNode = resultNode.getParent();
                }
            }
            return resultNode.getParent().getElement();
        } else {
            return resultNode.getElement();
        }
    }

    /**
     * Returns the entry with the largest key value strictly less than "key" (or
     * null if no such entry exists).
     *
     * @param key search key
     * @return the entry with the largest key value strictly less than k (or null,
     *         if no such entry exists).
     */
    @Override
    public Entry<K, V> lowerEntry(K key) {
        if (size == 0) {
            return null;
        }
        Node<Entry<K, V>> resultNode = treeSearch(key, root);
        if (resultNode.getParent() == null) {   // root 노드인 경우
            if (resultNode.getLeft().getElement() == null) {
                return null;
            } else {
                resultNode = resultNode.getLeft();
                while (resultNode.getRight().getLeft() != null || resultNode.getRight().getRight() != null) {
                    resultNode = resultNode.getRight();
                }
                return resultNode.getElement();
            }
        }
        if (resultNode.getLeft() == null && resultNode.getRight() == null) {
            if (resultNode.getParent().getLeft() == resultNode && resultNode.getParent().getElement().getKey().compareTo(firstEntry().getKey()) <= 0) {   // no such entry exists.
                return null;
            } else if (resultNode.getParent().getRight() != resultNode) {
                while (resultNode.getParent().getParent() != null && resultNode.getParent().getRight() != resultNode) {
                    resultNode = resultNode.getParent();
                }
            }
            return resultNode.getParent().getElement();
        } else {
            if (resultNode.getElement().getKey().compareTo(firstEntry().getKey()) == 0) {
                return null;
            } else if (resultNode.getLeft().getElement() == null) {
                while (resultNode.getParent().getParent() != null && resultNode.getParent().getRight() != resultNode) {
                    resultNode = resultNode.getParent();
                }
                return resultNode.getParent().getElement();
            } else {
                resultNode = resultNode.getLeft();
                while (resultNode.getRight().getLeft() != null || resultNode.getRight().getRight() != null) {
                    resultNode = resultNode.getRight();
                }
                return resultNode.getElement();
            }
        }
    }

    /**
     * Returns the entry with the least key value greater than or equal to "key"
     * (or null if no such entry exists).
     *
     * @param key search key
     * @return the entry with the least key value greater than or equal to k (or
     *         null, if no such entry exists)
     */
    @Override
    public Entry<K, V> ceilingEntry(K key) {
        if (size == 0) {
            return null;
        }
        Node<Entry<K, V>> resultNode = treeSearch(key, root);
        if (resultNode.getLeft() == null && resultNode.getRight() == null) {
            if (resultNode.getParent().getRight() == resultNode && resultNode.getParent().getElement().getKey().compareTo(lastEntry().getKey()) >= 0) {   // no such entry exists.
                return null;
            } else if (resultNode.getParent().getLeft() != resultNode) {
                while (resultNode.getParent().getParent() != null && resultNode.getParent().getLeft() != resultNode) {
                    resultNode = resultNode.getParent();
                }
            }
            return resultNode.getParent().getElement();
        } else {
            return resultNode.getElement();
        }
    }

    /**
     * Returns the entry with the least key value strictly greater than "key" (or
     * null if no such entry exists).
     *
     * @param key search key
     * @return the entry with the least key value strictly greater than "key" (or
     *         null if no such entry exists)
     */
    @Override
    public Entry<K, V> higherEntry(K key) {
        if (size == 0) {
            return null;
        }
        Node<Entry<K, V>> resultNode = treeSearch(key, root);
        if (resultNode.getParent() == null) {   // root 노드인 경우
            if (resultNode.getRight().getElement() == null) {
                return null;
            } else {
                resultNode = resultNode.getRight();
                while (resultNode.getLeft().getLeft() != null || resultNode.getLeft().getRight() != null) {
                    resultNode = resultNode.getLeft();
                }
                return resultNode.getElement();
            }
        }
        if (resultNode.getLeft() == null && resultNode.getRight() == null) {
            if (resultNode.getParent().getRight() == resultNode && resultNode.getParent().getElement().getKey().compareTo(lastEntry().getKey()) >= 0) {   // no such entry exists.
                return null;
            } else if (resultNode.getParent().getLeft() != resultNode) {
                while (resultNode.getParent().getParent() != null && resultNode.getParent().getLeft() != resultNode) {
                    resultNode = resultNode.getParent();
                }
            }
            return resultNode.getParent().getElement();
        } else {
            if (resultNode.getElement().getKey().compareTo(lastEntry().getKey()) == 0) {
                return null;
            } else if (resultNode.getRight().getElement() == null) {
                while (resultNode.getParent().getParent() != null && resultNode.getParent().getLeft() != resultNode) {
                    resultNode = resultNode.getParent();
                }
                return resultNode.getParent().getElement();
            } else {
                resultNode = resultNode.getRight();
                while (resultNode.getLeft().getLeft() != null || resultNode.getLeft().getRight() != null) {
                    resultNode = resultNode.getLeft();
                }
                return resultNode.getElement();
            }
        }
    }

    /**
     * Returns the value associated with the specified key (or else null).
     *
     * @param key search key
     * @return the value associated with the specified key (or else null)
     */
    @Override
    public V get(K key) {
        if (root == null) {
            return null;
        }
        Node<Entry<K, V>> resultNode = treeSearch(key, root);
        if (resultNode.getLeft() == null && resultNode.getRight() == null) {
            return null;
        } else {
            return resultNode.getElement().getValue();
        }
    }

    /**
     * Associates the given value with the given key, returning any overridden
     * value.
     * 
     * @param key   search key
     * @param value value of entry
     * @return old value associated with the key, if already exists, or null
     *         otherwise
     */
    @Override
    public V put(K key, V value) {
        if (root == null) {
            root = new Node<>(new Entry<>(key, value));
            root.setLeft(new Node<>(null, root, null, null));
            root.setRight(new Node<>(null, root, null, null));
            size++;
            return null;
        }
        Node<Entry<K, V>> resultNode = treeSearch(key, root);
        if (resultNode.getLeft() == null && resultNode.getRight() == null) {
            resultNode.setElement(new Entry<>(key, value));
            resultNode.setLeft(new Node<>(null, resultNode, null, null));
            resultNode.setRight(new Node<>(null, resultNode, null, null));
            size++;
            return null;
        } else {
            V oldValue = resultNode.getElement().getValue();
            resultNode.setElement(new Entry<>(key, value));
            return oldValue;
        }
    }

}
