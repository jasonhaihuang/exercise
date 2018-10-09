package exercise.telstra.com.au.telstraexercise.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import exercise.telstra.com.au.telstraexercise.R;
import exercise.telstra.com.au.telstraexercise.databinding.MainFragmentBinding;
import exercise.telstra.com.au.telstraexercise.viewmodel.FactListResponse;
import exercise.telstra.com.au.telstraexercise.viewmodel.MainViewModel;

public class MainFragment extends Fragment {

    public static final String TAG = "MainFragment";

    // the view model contains live data
    private MainViewModel mViewModel;

    // view-data binding, which make new data automatically updated on UI.
    private MainFragmentBinding mainFragmentBinding;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mainFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false);
        // init the value as loading will hide the empty list and display a message
        mainFragmentBinding.setIsLoading(true);
        return mainFragmentBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        subscribUi(mViewModel);
    }

    // UI retained the LiveData through the view model and observe on it.
    private void subscribUi(MainViewModel viewModel){
        viewModel.getFactListResponseLiveData().observe(this, new Observer<FactListResponse>() {
            @Override
            public void onChanged(@Nullable FactListResponse factListResponse) {
                if (factListResponse.factList != null){
                    // The reqeust is succeed, data is ready to bind on UI.
                    Log.d(TAG, "title = "+factListResponse.factList.title+", size = "+factListResponse.factList.rows.size());
                    mainFragmentBinding.setIsLoading(false);
                }else if (factListResponse.errorMessage != null){
                    // The request failed, show error message.
                    Log.e(TAG, "error = "+factListResponse.errorMessage);
                    mainFragmentBinding.setIsLoading(false);
                }
            }
        });
    }

}
