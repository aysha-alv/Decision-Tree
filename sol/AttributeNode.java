package sol;

import java.util.List;
import src.ITreeNode;
import src.Row;

/**
 * A class representing an inner node in the decision tree.
 */
// TODO: Uncomment this once you've implemented the methods in the ITreeNode interface!
public class AttributeNode implements ITreeNode {
    private String attribute;
    private String defVal;
    private List<ValueEdge> outgoingEdges;

    /**
     * First constructor for AttributeNode
     * @param attribute the attribute
     * @param defVal the default value
     * @param outgoingEdges the edges coming out of node
     *
     */
    public AttributeNode(String attribute, String defVal, List<ValueEdge> outgoingEdges) {
        this.attribute = attribute;
        this.defVal = defVal;
        this.outgoingEdges = outgoingEdges;
    }

    /**
     * Recursively traverses decision tree to return tree's decision for a row.
     *
     * @param forDatum the datum to lookup a decision for
     * @return the decision tree's decision
     */
    @Override
    public String getDecision(Row forDatum) {
       String decisionVal = forDatum.getAttributeValue(this.attribute);
       for (ValueEdge v : this.outgoingEdges) {
           if (decisionVal.equals(v.getVal())) {
               String decision = v.getChild().getDecision(forDatum);
               return decision;
           }
       }
       return this.defVal;
    }

}
