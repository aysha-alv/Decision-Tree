package sol;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import src.AttributeSelection;
import src.IDataset;
import src.Row;

import javax.xml.crypto.Data;
import java.util.stream.Collectors;

/**
 * A class representing a training dataset for the decision tree
 */
public class Dataset implements IDataset {
    private List<String> attributeList;

    private List<Row> dataObjects;

    private AttributeSelection selectionType;

    /**
     * Constructor for a Dataset object
     *
     * @param attributeList      - a list of attributes
     * @param dataObjects        -  a list of rows
     * @param attributeSelection - an enum for which way to select attributes
     */
    public Dataset(List<String> attributeList, List<Row> dataObjects, AttributeSelection attributeSelection) {
        this.dataObjects = new ArrayList<Row>(dataObjects);
        this.attributeList = new ArrayList<String>(attributeList);
        this.selectionType = attributeSelection;
    }

    /**
     * Recursively traverses decision tree to return tree's decision for a row.
     * @return split the tree based on a specific type of order
     */
    public String getAttributeToSplitOn() {
        switch (this.selectionType) {
            case ASCENDING_ALPHABETICAL -> {
                return this.attributeList.stream().sorted().toList().get(0);
            }
            case DESCENDING_ALPHABETICAL -> {
                return this.attributeList.stream().sorted().toList().get(this.attributeList.size() - 1);
            }
            case RANDOM -> {
                Random random = new Random();
                return this.attributeList.get(random.nextInt(this.attributeList.size()));
            }
        }
        throw new RuntimeException("Non-Exhaustive Switch Case");
    }

    /**
     * Gets list of attributes in the dataset
     *
     * @return a list of strings
     */
    @Override
    public List<String> getAttributeList() {
        return this.attributeList;
    }

    /**
     * Gets list of data objects (row) in the dataset
     *
     * @return a list of Rows
     */
    @Override
    public List<Row> getDataObjects() {
        return this.dataObjects;
    }

    /**
     * Returns the attribute selection type (alphabetical, reverse alphabetical, random) for this Dataset
     *
     * @return the attribute selection type
     */
    @Override
    public AttributeSelection getSelectionType() {
        return this.selectionType;
    }

    /**
     * finds the size of the dataset (number of rows)
     *
     * @return the number of rows in the dataset
     */
    @Override
    public int size() {
        return this.dataObjects.size();
    }

    /**
     * creates distinct list of attribute values
     *
     * @param attribute the attirbute to add values of to new list of unique ones
     * @return the decision tree's decision
     */
    public List<String> attValues(String attribute) {
        List<String> vals = new ArrayList<>();
        for (Row r : this.dataObjects) {
            vals.add(r.getAttributeValue(attribute));
        }
        return vals.stream().distinct().collect(Collectors.toList());
    }

    /**
     * creates a list for an attirbute with all the rows of it
     *
     * @param attr the attirbute to make list from
     * @return the list of "rows" with the attribtue value
     */
    public List<String> allValues(String attr){
        List<String> attrVal = new ArrayList<>();
        for (Row r: this.getDataObjects()){
            attrVal.add(r.getAttributeValue(attr));
        }
        return attrVal;
    }

    /**
     * checks if items in the value list equal to the attribute value by comparing to first item
     *
     * @param attr the attirbute to compare based on
     * @return a boolean indicating if the value matches
     */
    public boolean sameValues(String attr){
        List<String> attrVal = this.allValues(attr);
        for (String a : attrVal){
            if (!a.equals(attrVal.get(0))){
                return false;
            }
        }
        return true;
    }

    /**
     * Gets the default value for a node based on the rows contained within it
     *
     * @param rows the rows of a dataset
     * @param targetAttribute the target attribute of the tree
     * @return the default value of the rows contained within the subset of the node based on what appears most frequently
     */
    public String getDefault(List<Row> rows, String targetAttribute) {
        List<String> values = new ArrayList<>();
        if (this.dataObjects.size() == 1) {
            return this.dataObjects.get(0).getAttributeValue(targetAttribute);
        }
        for (Row r : rows) {
            String targetValue = r.getAttributeValue(targetAttribute);
            if (!values.contains(targetValue)) {
                values.add(targetValue);
            }
        }
        int[] counts = new int[values.size()];
        for (Row r : rows) {
            String targetValue = r.getAttributeValue(targetAttribute);
            int index = values.indexOf(targetValue);
            counts[index]++;
        }
        int maxIndex = 0;
        for (int i = 1; i < values.size(); i++) {
            if (counts[i] > counts[maxIndex]) {
                maxIndex = i;
            }
        }
        return values.get(maxIndex);
    }

    /**
     * Partitions the data based on the attribute values
     *
     * @param attribute the attribute to partition off of
     * @return a list of subsetted datasets corresponding to their value of attribute
     */
    public List<Dataset> partition(String attribute) {
        List<String> listVal = this.attValues(attribute).stream().sorted().toList();
        List<Dataset> dataList = new ArrayList<Dataset>();
        for (String val : listVal) {
            dataList.add(new Dataset(this.attributeList, new ArrayList<>(), this.selectionType));
        }
        for (Row r : this.dataObjects) {
            String attributevalue = r.getAttributeValue(attribute);
            int index = listVal.indexOf(attributevalue);
            dataList.get(index).addRow(r);
        }
        for (Dataset d : dataList) {
            d.attributeList.remove(attribute);
        }
        return dataList;
    }

    /**
     * Updates the dataset to have a new row
     *
     * @param r the row to add to dataset
     *
     */
    public void addRow(Row r) {
       this.dataObjects.add(r);
    }

    /**
     * Filters the list of strings to keep only ones containing the relevant data removing the selected attribute - used in partition.
     *
     * @param myList the rows of a dataset
     * @param attribute the target attribute to filter out
     * @return the list of strings not containing rows with the selected attribute
     */
    public List<String> filterList(List<String> myList, String attribute){
        while(myList.contains(attribute)){
            myList.remove(attribute);
        }
        return myList;
    }


}
