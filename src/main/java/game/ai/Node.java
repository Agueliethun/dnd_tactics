package game.ai;

import java.util.ArrayList;
import java.util.List;

public class Node<T> {
    private T val;
    private List<Node<T>> children;

    public Node(T val) {
        this.val = val;
        children = new ArrayList<>();
    }

    public T getVal() {
        return val;
    }

    public void setVal(T val) {
        this.val = val;
    }

    public void addChild(T child) {
        children.add(new Node<>(child));
    }

    public List<Node<T>> getChildren() {
        return children;
    }
}
