package seedu.address.logic.trie;

import static java.util.Objects.requireNonNull;

/**
 * Class for node object
 */
public class Node {

    private Node next;
    private Node child;

    private char key;

    public Node(char key , Node next, Node child) {
        requireNonNull(key);
        this.key = key;
        this.next = next;
        this.child = child;
    }

    public boolean hasNext() {
        return next != null;
    }

    public boolean hasChild() {
        return child != null;
    }

    public char getKey() {
        return key;
    }

    public void setKey(char key) {
        this.key = key;
    }

    public Node getChild() {
        return child;
    }

    public void setChild(Node child) {
        this.child = child;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }
}
