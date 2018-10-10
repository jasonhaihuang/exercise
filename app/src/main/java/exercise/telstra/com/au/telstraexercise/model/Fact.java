package exercise.telstra.com.au.telstraexercise.model;

/**
 * This is a POJO class for parsing the JSON string.
 *
 * The example of the JSON is as follow:
 *
 *  {
 * 	    "title":"Beavers",
 * 	    "description":"Beavers are second only to humans in their ability to manipulate and change their environment. They can measure up to 1.3 metres long. A group of beavers is called a colony",
 * 	    "imageHref":"http://upload.wikimedia.org/wikipedia/commons/thumb/6/6b/American_Beaver.jpg/220px-American_Beaver.jpg"
 *  }
 */

public class Fact {
    public Fact(String t, String d, String img){
        title = t;
        description = d;
        imageHref = img;
    }
    public String title;
    public String description;
    public String imageHref;
}
