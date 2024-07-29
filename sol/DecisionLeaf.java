package sol;

import src.ITreeNode;
import src.Row;

/**
 * A class representing a leaf in the decision tree.
 */
public class DecisionLeaf implements ITreeNode {
    public String value;

    /**
     * constructor for the leaf
     *
     * @param s  the value of the leaf
     */

    public DecisionLeaf(String s) {
        this.value = s;
    }

    /**
     * Recursively traverses decision tree to return tree's decision for a row.
     *
     * @param forDatum the datum to lookup a decision for
     * @return the decision tree's decision
     */
    @Override
    public String getDecision(Row forDatum) {
        return this.value;
    }

}
