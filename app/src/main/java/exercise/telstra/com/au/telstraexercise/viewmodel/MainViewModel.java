package exercise.telstra.com.au.telstraexercise.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

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

    // Return the Live Data, create new instance if it is null, and load the fact list from server.
    public LiveData<FactListResponse> getFactListResponseLiveData(){
        // If the live data is null, create a new instance.
        if (factListResponseLiveData == null){
            factListResponseLiveData = new MutableLiveData<>();
            // send request to load fact list from server for the first time.
            loadFactList();
        }
        return factListResponseLiveData;
    }

    // Load the fact list from the server.
    public void loadFactList(){
        if (networkRepository == null){
            // create network repository if it hasn't be initialized.
            networkRepository = new NetworkRepository();
        }

        // start Http request to load fact list, response will be processed by the callback.
        networkRepository.loadFactListFromServer(new Callback<FactList>() {
            @Override
            public void onResponse(Call<FactList> call, Response<FactList> response) {
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
}
