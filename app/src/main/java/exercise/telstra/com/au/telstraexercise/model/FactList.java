package exercise.telstra.com.au.telstraexercise.model;

import java.util.List;

/**
 * This is a POJO class for parsing the JSON string.
 *
 * The example of the JSON is as follow:
 *
 * {
 *  "title":"About Canada",
 *  "rows":[...]
 * }
 */

public class FactList {
    public String title;
    public List<Fact> rows;
}
