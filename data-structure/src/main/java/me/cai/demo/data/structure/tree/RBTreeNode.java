package me.cai.demo.data.structure.tree;

import lombok.Getter;
import lombok.Setter;

/**
 * me.cai.demo.data.structure.tree
 *
 * @author caiguangzheng
 * date: 2019/9/4
 * mail: caiguangzheng@terminus.io
 * description:
 */
@Getter
@Setter
public class RBTreeNode <T extends Comparable<T>> {

    private T value;

    private RBTreeNode<T> left;

    private RBTreeNode<T> right;

    private RBTreeNode<T> parent;

    private boolean red;

    public RBTreeNode() {
    }

    public RBTreeNode(T value) {
        this.value = value;
    }

    public RBTreeNode(T value, boolean isRed) {
        this.value = value;
        this.red = isRed;
    }

    public boolean isBlack(){
        return !red;
    }

    public boolean isLeaf() {
        return left == null && right == null;
    }

    public void makeRed() {
        this.red = true;
    }

    public void makeBlack() {
        this.red = false;
    }

    @Override
    public String toString(){
        return value.toString();
    }

}
