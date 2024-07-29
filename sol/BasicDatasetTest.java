package sol;

import org.junit.Assert;
import org.junit.Test;
import src.AttributeSelection;
import src.DecisionTreeCSVParser;
import src.Row;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;

import javax.xml.crypto.Data;

/**
 * A class to test basic decision tree functionality on a basic training dataset
 */
public class BasicDatasetTest {
    // IMPORTANT: for this filepath to work, make sure the project is open as the top-level directory in IntelliJ
    // (See the first yellow information box in the handout testing section for details)
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

//    @Test
//    public void testingstuff(){
//        this.
//    }


    /**
     * Tests the expected classification of the "tangerine" row is a fruit
     */
    @Test
    public void testClassification() {
        // makes a new (partial) Row representing the tangerine from the example
        // TODO: make your own rows based on your dataset
        Row mars = new Row("test row (Mars)");
        mars.setAttributeValue("Cost", "Medium");
        mars.setAttributeValue("Filling", "Yes");
        mars.setAttributeValue("Size", "Large");
        // TODO: make your own assertions based on the expected classifications
        // TODO: Uncomment this once you've implemented getDecision

        Row tangerine = new Row("test row (tangerine)");
        tangerine.setAttributeValue("color", "orange");
        tangerine.setAttributeValue("highProtein", "false");
        tangerine.setAttributeValue("calories", "high");
        // TODO: make your own assertions based on the expected classifications
        // TODO: Uncomment this once you've implemented getDecision
        Assert.assertEquals("fruit", this.testGenerator.getDecision(tangerine));
//        System.out.println(this.fruits.partition("color").get(0).getDataObjects().get(0).getAttributeValue("color"));
//        System.out.println(this.fruits.partition("color").get(0).getDataObjects().size());
//        System.out.println(this.fruits.partition("color").get(1).getDataObjects().get(0).getAttributeValue("color"));
//        System.out.println(this.fruits.partition("color").get(1).getDataObjects().size());
//        System.out.println(this.fruits.partition("color").get(2).getDataObjects().get(0).getAttributeValue("color"));
//        System.out.println(this.fruits.partition("color").get(2).getDataObjects().size());


        //this.fruits.getDefault()
//

    }

//    @Test
//    public void testSize() {
//        Assert.assertEquals(169, this.mushrooms.size());
//    }
//
//    @Test
//    public void testvals() {
//        System.out.println(this.mushrooms.partition("capColor").get(0).getDataObjects().get(0).getAttributeValue("capColor"));
//    }
//
//    @Test
//    public void testpartition() {
//        List<Dataset> shrooms1 = this.mushrooms.partition("stalkShape");
//        System.out.println(shrooms1.get(0).partition("capSurface").get(0).getAttributeList());
//
//    }

}
