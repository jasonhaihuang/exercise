package exercise.telstra.com.au.telstraexercise.repository;

import exercise.telstra.com.au.telstraexercise.model.FactList;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * The interface is the place defines all the Http Service APIs.
 *
 * In each method, Http request method and relatvie URL are defined in the annotation.
 * The function can accept parameters (if there is any) and return the response.
 * The response will be convert into Java object through retrofit's convert factory.
 */

public interface HttpRequestInterface {


    /**
     *  Get the fact list from the server.
     *  The HTTP METHOD is GET, the relative URL is 's/2iodh4vg0eortkl/facts.json'
     *  The return value is an instance of FactList.
     *
     * @return Call<FactList> the HTTP call response
     */
    @GET("s/2iodh4vg0eortkl/facts.json")
    Call<FactList> getFactList();


    // Get ... (for other API calls)
}
