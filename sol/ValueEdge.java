package sol;

import src.ITreeNode;

/**
 * A class that represents the edge of an attribute node in the decision tree
 */
public class ValueEdge {
    private String value;
    private ITreeNode child;

    /**
     * Constructor for the value edge
     *
     * @param s    the value of the edge
     * @param child the child coming out of the edge (node/leaf)
     */
    public ValueEdge(String s, ITreeNode child) {
        this.value = s;
        this.child = child;
    }


    /**
     * Method to get the child of the value edge
     *
     * @return the child of the current value edge
     */
    public ITreeNode getChild() {
        return this.child;
    }

    /**
     * Gets the value of a value edge
     *
     * @return string of the value edge value
     */
    public String getVal() {
        return this.value;
    }
}
