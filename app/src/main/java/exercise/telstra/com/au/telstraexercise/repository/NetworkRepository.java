package exercise.telstra.com.au.telstraexercise.repository;


import exercise.telstra.com.au.telstraexercise.model.FactList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * The repository responsible for send Http request and get the response back.
 *
 * For better performance, a local cache version can be developed with the support of Room library.
 */
public class NetworkRepository {

    //and send the request asynchronously. The response will be processed in the callbacks.
    public void loadFactListFromServer(){
        // get the request
        HttpRequestInterface request = createRequest();

        // send the request asynchronously, provide a callback for processing the response.
        request.getFactList().enqueue(new Callback<FactList>() {
            @Override
            public void onResponse(Call<FactList> call, Response<FactList> response) {
                // The Http request is successfully finished.

            }

            @Override
            public void onFailure(Call<FactList> call, Throwable t) {
                // The Http request is failed.

            }
        });
    }

    // create the instance of retrofit request interface.
    private HttpRequestInterface createRequest(){

        // create the request interface instance through retrofit
        return new Retrofit.Builder()
                .baseUrl("https://dl.dropboxusercontent.com") // The base URL
                .addConverterFactory(GsonConverterFactory.create()) // The converter for parsing Json string to Java Object
                .build().create(HttpRequestInterface.class);
    }
}
