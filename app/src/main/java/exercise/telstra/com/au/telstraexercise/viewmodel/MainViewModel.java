package exercise.telstra.com.au.telstraexercise.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import exercise.telstra.com.au.telstraexercise.model.Fact;
import exercise.telstra.com.au.telstraexercise.model.FactList;
import exercise.telstra.com.au.telstraexercise.repository.NetworkRepository;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * The MainViewModel keeps and manages a mutable live data, factListResponseLiveData, which represents a list of facts.
 *
 * When the activity/fragment start observe the live data, the factListResponseLiveData will be created by the view model.
 * Then the view model will invoke the NetworkRepository to send a request to server for retrieving the fact list.
 *
 * When the http request come back with either a successful or fail message, the factListResponseLiveData will be updated,
 * and the observer will received the updates through callback.
 */

public class MainViewModel extends ViewModel {

    // The Live Data contains the fact list or error message.
    private MutableLiveData<FactListResponse> factListResponseLiveData;

    // The repository for network interactions.
    private NetworkRepository networkRepository;

    /**
     * This method will return invoker a LiveData, with the LiveData, all the data and following update
     * can be notified to the observers.
     *
     * @return a observable LiveData
     */
    public LiveData<FactListResponse> getFactListResponseLiveData(){
        // If the live data is null, create a new instance.
        if (factListResponseLiveData == null){
            factListResponseLiveData = new MutableLiveData<>();
            // send request to load fact list from server for the first time.
            loadFactList();
        }
        return factListResponseLiveData;
    }

    /**
     * Load the fact list from the server.
     */
    public void loadFactList(){
        if (networkRepository == null){
            // create network repository if it hasn't be initialized.
            networkRepository = new NetworkRepository();
        }

        // start Http request to load fact list, response will be processed by the callback.
        networkRepository.loadFactListFromServer(new Callback<FactList>() {
            @Override
            public void onResponse(Call<FactList> call, Response<FactList> response) {
                // check the received data, filter out empty one.
                List<Fact> filtered = filterEmptyData(response.body().rows);
                response.body().rows = filtered;
                // The Http request is successfully finished. Set the Fact List to Live Data, this will trigger a invoke of observer.
                factListResponseLiveData.postValue(new FactListResponse(response.body(), null));
            }

            @Override
            public void onFailure(Call<FactList> call, Throwable t) {
                // The Http request is failed. Post the live data to notify observer.
                factListResponseLiveData.postValue(new FactListResponse(null, t.getMessage()));
            }
        });
    }

    /**
     * remove items with all it's fields null or empty.
     * @param list the data received from the HTTP response.
     * @return a filtered list without empty data.
     */
    private List<Fact> filterEmptyData(List<Fact> list){
        List<Fact> newList = new ArrayList<>();
        for (Fact fact : list){
            if ((fact.title == null || fact.title.isEmpty())
                    && (fact.description == null || fact.description.isEmpty())
                    && (fact.imageHref == null || fact.imageHref.isEmpty())){
                // this is an empty object, don't need anymore.
            }else{
                newList.add(fact);
            }
        }
        return newList;
    }
}
