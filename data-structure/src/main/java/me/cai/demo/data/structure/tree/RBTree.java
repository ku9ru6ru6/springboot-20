package me.cai.demo.data.structure.tree;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * me.cai.demo.data.structure.tree
 *
 * @author caiguangzheng
 * date: 2019/9/3
 * mail: caiguangzheng@terminus.io
 * description:
 */
@Getter
public class RBTree<T extends Comparable<T>> {

    private final RBTreeNode<T> root;

    private AtomicInteger size = new AtomicInteger(0);

    //in overwrite mode,all node's value can not  has same    value
    //in non-overwrite mode,node can have same value, suggest don't use non-overwrite mode.
    @Setter
    private volatile boolean overrideMode = true;

    public RBTree() {
        this.root = new RBTreeNode<>();
    }

    public RBTree(boolean overrideMode) {
        this();
        this.overrideMode = overrideMode;
    }

    /**
     * get the root node
     *
     * @return
     */
    private RBTreeNode<T> getRoot() {
        return root.getLeft();
    }

    /**
     * add value to a new node,if this value exist in this tree,
     * if value exist,it will return the exist value.otherwise return null
     * if override mode is true,if value exist in the tree,
     * it will override the old value in the tree
     *
     * @param value
     * @return
     */
    public T addNode(T value) {
        RBTreeNode<T> t = new RBTreeNode<T>(value);
        return addNode(t);
    }

    private T addNode(RBTreeNode<T> node) {
        node.setLeft(null);
        node.setRight(null);
        node.setRed(true);
        this.setParent(node, null);
        if (root.getLeft() == null) {
            root.setLeft(node);
            node.setRed(false);
            size.incrementAndGet();
        } else {
            RBTreeNode<T> x = this.findParentNode(node);
            int cmp = x.getValue().compareTo(node.getValue());

            if (overrideMode && cmp == 0) {
                T v = x.getValue();
                x.setValue(node.getValue());
                return v;
            } else if (cmp == 0) {
                return x.getValue();
            }

            this.setParent(node, x);
            if (cmp > 0) {
                x.setLeft(node);
            } else {
                x.setRight(node);
            }
            this.fixInsert(node);
            size.incrementAndGet();
        }
        return null;
    }

    /**
     * red black tree insert fix.
     *
     * @param x
     */
    private void fixInsert(RBTreeNode<T> x) {
        RBTreeNode<T> parent = x.getParent();

        while (parent != null && parent.isRed()) {
            RBTreeNode<T> uncle = this.getUncle(x);
            if (uncle == null) {
                // 叔节点不为空，需要旋转
                RBTreeNode<T> ancestor = parent.getParent();
                if (parent == ancestor.getLeft()) {
                    boolean isRight = x == parent.getRight();
                    if (isRight) {
                        rotateLeft(parent);
                    }
                    rotateRight(ancestor);

                    if (isRight) {
                        x.setRed(false);
                        parent = null;
                    } else {
                        parent.setRed(false);
                    }
                    ancestor.setRed(true);
                } else {
                    boolean isLeft = x == parent.getLeft();
                    if (isLeft) {
                        rotateRight(parent);
                    }
                    rotateLeft(ancestor);

                    if (isLeft) {
                        x.setRed(false);
                        parent = null;
                    } else {
                        parent.setRed(false);
                    }
                    ancestor.setRed(true);
                }
            } else {
                // uncle is red
                parent.setRed(false);
                uncle.setRed(false);
                parent.getParent().setRed(true);
                x = parent.getParent();
                parent = x.getParent();
            }
        }
        this.getRoot().makeBlack();
        this.getRoot().setParent(null);
    }

    /**
     * get uncle node
     *
     * @param node
     * @return
     */
    private RBTreeNode<T> getUncle(RBTreeNode<T> node) {
        RBTreeNode<T> parent = node.getParent();
        RBTreeNode<T> ancestor = parent.getParent();
        if (ancestor == null) {
            return null;
        }
        if (parent == ancestor.getLeft()) {
            return ancestor.getRight();
        } else {
            return ancestor.getLeft();
        }
    }

    /**
     * find the parent node to hold node x,if parent value equals x.value return parent.
     *
     * @param x
     * @return
     */
    private RBTreeNode<T> findParentNode(RBTreeNode<T> x) {
        RBTreeNode<T> dataRoot = this.getRoot();
        RBTreeNode<T> child = dataRoot;

        while (child != null) {
            int cmp = child.getValue().compareTo(x.getValue());
            if (cmp == 0) {
                return child;
            }
            if (cmp > 0) {
                dataRoot = child;
                child = child.getLeft();
            } else {
                dataRoot = child;
                child = child.getRight();
            }
        }
        return dataRoot;
    }


    /**
     * find the value by give value(include key,key used for search,
     * other field is not used,@see compare method).if this value not exist return null
     *
     * @param value
     * @return
     */
    public T find(T value) {
        RBTreeNode<T> dataRoot = this.getRoot();
        while (dataRoot != null) {
            int cmp = dataRoot.getValue().compareTo(value);
            if (cmp < 0) {
                dataRoot = dataRoot.getRight();
            } else if (cmp == 0) {
                return dataRoot.getValue();
            } else {
                dataRoot = dataRoot.getLeft();
            }
        }
        return null;
    }


    /**
     * remove the node by give value,if this value not exists in tree return null
     *
     * @param value include search key
     * @return the value contain in the removed node
     */
    public T remove(T value) {
        RBTreeNode<T> dataRoot = this.getRoot();
        RBTreeNode<T> parent = root;

        while (dataRoot != null) {
            int cmp = dataRoot.getValue().compareTo(value);
            if (cmp < 0) {
                parent = dataRoot;
                dataRoot = dataRoot.getRight();
            } else if (cmp > 0) {
                parent = dataRoot;
                dataRoot = parent.getLeft();
            } else {
                if (dataRoot.getRight() != null) {
                    RBTreeNode<T> min = this.removeMin(dataRoot.getRight());
                    //  x used for fix color balance
                    RBTreeNode<T> x = min.getRight() == null ? min.getParent() : min.getRight();
                    boolean isParent = min.getRight() == null;

                    min.setLeft(dataRoot.getLeft());
                    this.setParent(dataRoot.getLeft(), min);
                    if (parent.getLeft() == dataRoot) {
                        parent.setLeft(min);
                    } else {
                        parent.setRight(min);
                    }
                    this.setParent(min, parent);

                    boolean curMinIsBlack = min.isBlack();
                    min.setRed(dataRoot.isRed());

                    if (min != dataRoot.getRight()) {
                        min.setRight(dataRoot.getRight());
                        this.setParent(dataRoot.getRight(), min);
                    }

                    if (curMinIsBlack) {
                        if (min != dataRoot.getRight()) {
                            fixRemove(x, isParent);
                        } else if (min.getRight() != null) {
                            fixRemove(min.getRight(), false);
                        } else {
                            fixRemove(min, true);
                        }
                    }
                } else {
                    this.setParent(dataRoot.getLeft(), parent);
                    if (parent.getLeft() == dataRoot) {
                        parent.setLeft(dataRoot.getLeft());
                    } else {
                        parent.setRight(dataRoot.getLeft());
                    }
                    if (dataRoot.isBlack() && !(root.getLeft() == null)) {
                        RBTreeNode<T> x = dataRoot.getLeft() == null ? parent : dataRoot.getLeft();
                        boolean isParent = dataRoot.getLeft() == null;
                        fixRemove(x, isParent);
                    }
                }
                this.setParent(dataRoot, null);
                dataRoot.setLeft(null);
                dataRoot.setRight(null);
                if (this.getRoot() != null) {
                    getRoot().setRed(false);
                    getRoot().setParent(null);
                }
                size.decrementAndGet();
                return dataRoot.getValue();
            }
        }
        return null;
    }

    /**
     * find the successor node
     *
     * @param node current node's right node
     * @return
     */
    private RBTreeNode<T> removeMin(@NonNull RBTreeNode<T> node) {
        RBTreeNode<T> parent = node;
        while (node != null && node.getLeft() != null) {
            parent = node;
            node = node.getLeft();
        }

        if (parent == node) {
            return node;
        }

        parent.setLeft(node.getRight());
        this.setParent(node.getRight(), parent);

        return node;
    }

    /**
     * fix remove action
     *
     * @param node
     * @param isParent
     */
    private void fixRemove(RBTreeNode<T> node, boolean isParent) {
        RBTreeNode<T> cur = isParent ? null : node;
        boolean isRed = !isParent && node.isRed();
        RBTreeNode<T> parent = isParent ? node : node.getParent();

        while (!isRed && !this.isRoot(cur)) {
            // 为啥 兄弟节点一定不为空？
            RBTreeNode<T> sibling = this.getSibling(cur, parent);

            boolean isLeft = (parent.getRight() == sibling);
            if (sibling.isRed() && !isLeft) {
                // case 1 当前节点在右边，对兄弟节点做右旋
                parent.makeRed();
                sibling.makeBlack();
                rotateRight(parent);
            } else if (sibling.isRed() && isLeft) {
                parent.makeRed();
                sibling.makeBlack();
                rotateLeft(parent);
            } else if ((isBlack(sibling.getLeft())) && this.isBlack(sibling.getRight())) {
                // case 2 待删除的节点的兄弟节点是黑色的节点，且兄弟节点的子节点都是黑色的
                sibling.makeBlack();
                cur = parent;
                isRed = cur.isRed();
                parent = parent.getParent();
            } else if (isLeft && !this.isBlack(sibling.getLeft()) && this.isBlack(sibling.getRight())) {
                // case 3待调整的节点的兄弟节点是黑色的节点，且兄弟节点的左子节点是红色的，右节点是黑色的(兄弟节点在右边)
                sibling.makeRed();
                sibling.getLeft().makeBlack();
                rotateRight(sibling);
            } else if (!isLeft && !this.isBlack(sibling.getRight()) && this.isBlack(sibling.getLeft())) {
                sibling.makeRed();
                sibling.getRight().makeBlack();
                rotateLeft(sibling);
            } else if (isLeft && !this.isBlack(sibling.getRight())) {
                // case 4 待调整的节点的兄弟节点是黑色的节点，且右子节点是是红色的(兄弟节点在右边)
                sibling.setRed(parent.isRed());
                parent.makeBlack();
                sibling.getRight().makeBlack();
                rotateLeft(parent);
                cur = getRoot();
            } else if (!isLeft && !this.isBlack(sibling.getLeft())) {
                sibling.setRed(parent.isRed());
                parent.makeBlack();
                sibling.getLeft().makeBlack();
                rotateRight(parent);
                cur = getRoot();
            }
        }

        if (isRed) {
            cur.makeBlack();
        }

        if (getRoot() != null) {
            getRoot().setRed(false);
            getRoot().setParent(null);
        }
    }

    private boolean isBlack(RBTreeNode<T> node) {
        return node == null || node.isBlack();
    }

    private boolean isRoot(RBTreeNode<T> node) {
        return root.getLeft() == node && node.getParent() == null;
    }

    private RBTreeNode<T> getSibling(RBTreeNode<T> node, RBTreeNode<T> parent) {
        parent = node == null ? parent : node.getParent();

        if (node == null) {
            return parent.getLeft() == null ? parent.getRight() : parent.getLeft();
        }

        if (node == parent.getLeft()) {
            return parent.getRight();
        } else {
            return parent.getLeft();
        }
    }

    /**
     * 左旋，右子节点上升到父节点
     *
     * @param node
     */
    private void rotateLeft(RBTreeNode<T> node) {
        RBTreeNode<T> right = node.getRight();
        if (right == null) {
            throw new IllegalStateException("right node is null");
        }

        RBTreeNode<T> parent = node.getParent();
        node.setRight(right.getLeft());
        this.setParent(right.getLeft(), node);

        right.setLeft(node);
        setParent(node, right);

        if (parent == null) {
            root.setLeft(right);
            setParent(right, null);
        } else {
            if (parent.getLeft() == node) {
                parent.setLeft(right);
            } else {
                parent.setRight(right);
            }
            setParent(right, parent);
        }
    }

    private void rotateRight(RBTreeNode<T> node) {
        RBTreeNode<T> left = node.getLeft();
        if (left == null) {
            throw new IllegalStateException("left node is null");
        }

        RBTreeNode<T> parent = node.getParent();
        node.setLeft(left.getRight());

        left.setRight(node);
        this.setParent(node, left);

        if (parent == null) {
            root.setLeft(left);
            this.setParent(left, null);
        } else {
            if (parent.getLeft() == node) {
                parent.setLeft(left);
            } else {
                parent.setRight(left);
            }
            this.setParent(left, parent);
        }
    }

    private void setParent(RBTreeNode<T> node, RBTreeNode<T> parent) {
        if (node != null) {
            node.setParent(parent);
            if (parent == root) {
                node.setParent(null);
            }
        }
    }


    /**
     * debug method,it used print the given node and its children nodes,
     * every layer output in one line
     *
     * @param root
     */
    public void printTree(RBTreeNode<T> root) {
        java.util.LinkedList<RBTreeNode<T>> queue = new java.util.LinkedList<>();
        java.util.LinkedList<RBTreeNode<T>> queue2 = new java.util.LinkedList<>();
        if (root == null) {
            return;
        }
        queue.add(root);
        boolean firstQueue = true;

        while (!queue.isEmpty() || !queue2.isEmpty()) {
            java.util.LinkedList<RBTreeNode<T>> q = firstQueue ? queue : queue2;
            RBTreeNode<T> n = q.poll();

            if (n != null) {
                String pos = n.getParent() == null ? "" : (n == n.getParent().getLeft()
                        ? " LE" : " RI");
                String pstr = n.getParent() == null ? "" : n.getParent().toString();
                String cstr = n.isRed() ? "R" : "B";
                cstr = n.getParent() == null ? cstr : cstr + " ";
                System.out.print(n + "(" + (cstr) + pstr + (pos) + ")" + "\t");
                if (n.getLeft() != null) {
                    (firstQueue ? queue2 : queue).add(n.getLeft());
                }
                if (n.getRight() != null) {
                    (firstQueue ? queue2 : queue).add(n.getRight());
                }
            } else {
                System.out.println();
                firstQueue = !firstQueue;
            }
        }
    }

    public static void main(String[] args) {
        RBTree<String> bst = new RBTree<>();
        bst.addNode("d");
        bst.addNode("d");
        bst.addNode("c");
        bst.addNode("c");
        bst.addNode("b");
        bst.addNode("f");

        bst.addNode("a");
        bst.addNode("e");

        bst.addNode("g");
        bst.addNode("h");


        bst.remove("c");

        bst.printTree(bst.getRoot());
    }


}
