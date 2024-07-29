package sol;

import src.ITreeGenerator;
import src.ITreeNode;
import src.Row;

import java.util.ArrayList;
import java.util.List;

/**
 * A class that implements the ITreeGenerator interface used to generate a decision tree
 */
public class TreeGenerator implements ITreeGenerator<Dataset> {
    private ITreeNode root;

    private String targetAttribute;

    /**
     * Generates the decision tree for a given training dataset.
     *
     * @param trainingData    the dataset to train on
     * @param targetAttribute the attribute to predict
     */
    @Override
    public void generateTree(Dataset trainingData, String targetAttribute) {
        this.root = this.generateTreeHelper(trainingData, targetAttribute);
        this.targetAttribute = targetAttribute;
    }

    /**
     * A helper for generate tree doing all the calling of other mehtods
     *
     * @param trainingData    the dataset to build on
     * @param targetAttribute the target attribute that will be decided on
     */
    public ITreeNode generateTreeHelper(Dataset trainingData, String targetAttribute) {
        if (trainingData.getDataObjects().size() == 0) {
            throw new RuntimeException("Empty Dataset");
            //check which exception to use
        }

        String defaultValue = trainingData.getDefault(trainingData.getDataObjects(), targetAttribute);
        if (trainingData.getDataObjects().size() == 1) {
            return new DecisionLeaf(defaultValue);
            //check which exception to use
        }
        trainingData.filterList(trainingData.getAttributeList(), targetAttribute);

        if (this.isLeaf(trainingData, targetAttribute)) {
            //return new DecisionLeaf(defaultValue);
            return new DecisionLeaf(trainingData.getDataObjects().get(0).getAttributeValue(targetAttribute));
        }

        String selfAttribute = trainingData.getAttributeToSplitOn();

        List<Dataset> partitionData = trainingData.partition(selfAttribute);
        List<ValueEdge> valEdge = new ArrayList<ValueEdge>();

        for (Dataset d : partitionData) {
            String nextAttribute = d.getDataObjects().get(0).getAttributeValue(selfAttribute);
            ValueEdge valueEdge = new ValueEdge(nextAttribute, this.generateTreeHelper(d, targetAttribute));
            valEdge.add(valueEdge);
        }
        return new AttributeNode(selfAttribute, defaultValue, valEdge);
    }


    /**
     * Looks up the decision for a datum in the decision tree.
     *
     * @param datum the datum to lookup a decision for
     * @return the decision of the row
     */
    @Override
    public String getDecision(Row datum) {
        return this.root.getDecision(datum);
    }

    /**
     * Recognizes if something is a leaf when generating tree
     *
     * @param dataset the dataset
     * @param targetAttribute the attribute to predict
     * @return boolean indicating leaf
     */
    public boolean isLeaf(Dataset dataset, String targetAttribute) {
        return (dataset.sameValues(targetAttribute) || dataset.getDataObjects().size() == 0);
    }

}
