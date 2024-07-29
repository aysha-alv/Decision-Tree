package sol;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import static org.junit.Assert.assertFalse;

import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import src.AttributeSelection;
import src.DecisionTreeCSVParser;
import src.Row;

import java.util.ArrayList;
import java.util.List;

/**
 * A class containing the tests for methods in the TreeGenerator and Dataset classes
 */
public class DecisionTreeTest {
        String trainingPath = "data/fruits-and-vegetables.csv"; // TODO: replace with your own input file
        String targetAttribute = "foodType"; // TODO: replace with your own target attribute
        TreeGenerator testGenerator;
        Dataset fruits;

        /**
         * Constructs the decision tree for testing based on the input file and the target attribute.
         */
        @Before
        public void buildTreeForTest() {
            List<Row> dataObjects = DecisionTreeCSVParser.parse(this.trainingPath);
            List<String> attributeList = new ArrayList<>(dataObjects.get(0).getAttributes());
            Dataset fruits = new Dataset(attributeList, dataObjects, AttributeSelection.ASCENDING_ALPHABETICAL);
            this.fruits = fruits;
            // builds a TreeGenerator object and generates a tree for "foodType"
            this.testGenerator = new TreeGenerator();
            this.testGenerator.generateTree(fruits, this.targetAttribute);
        }
    /**
     * Tests size of a dataset
     */

    @Test
    public void testSize() {
        Assert.assertEquals(7, this.fruits.size());
    }

    /**
     * Tests if attvalues produces list of unique attribute values
     */
    @Test
    public void testattValues() {
        ArrayList attVals = new ArrayList<>();
        attVals.add("green");
        attVals.add("orange");
        attVals.add("yellow");
        Assert.assertEquals(attVals, this.fruits.attValues("color"));
    }


    /**
     * Tests if getDefault returns appropriate default value
     */

    @Test
    public void testgetDefault() {
        Assert.assertEquals("vegetable", this.fruits.getDefault(this.fruits.getDataObjects(), "foodType"));
        Dataset newData = this.fruits.partition("color").get(0);
        Assert.assertEquals("vegetable",  this.fruits.getDefault(newData.getDataObjects(), "foodType"));
    }

    /**
     * Tests if partition creates list of partitioned datasets
     */

    @Test
    public void testpartition() {
        this.fruits.getAttributeList().remove("color");
        Assert.assertEquals(this.fruits.getAttributeList(), this.fruits.partition("color").get(0).getAttributeList());
    }

    /**
     * Tests if filterlist removes attribute from a list
     */

    @Test
    public void testfilterList() {
        this.fruits.getAttributeList().remove("color");
        Assert.assertEquals(this.fruits.getAttributeList(), this.fruits.filterList(this.fruits.getAttributeList(), "color")); }




}
