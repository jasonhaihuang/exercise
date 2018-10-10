package exercise.telstra.com.au.telstraexercise.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import exercise.telstra.com.au.telstraexercise.MainActivity;
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

    // the adapter for the recycler view.
    private FactListAdapter factListAdapter;

    // the activity hold this fragment.
    private MainActivity mainActivity;

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

        // init the recycler view, setting adapter and layout manager.
        factListAdapter = new FactListAdapter();
        mainFragmentBinding.factList.setAdapter(factListAdapter);
        mainFragmentBinding.factList.setLayoutManager(new LinearLayoutManager(getContext()));

        return mainFragmentBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        subscribUi(mViewModel);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity){
            mainActivity = (MainActivity)context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mainActivity = null;
    }

    // UI retained the LiveData through the view model and observe on it.
    private void subscribUi(MainViewModel viewModel){
        viewModel.getFactListResponseLiveData().observe(this, new Observer<FactListResponse>() {
            @Override
            public void onChanged(@Nullable FactListResponse factListResponse) {
                if (factListResponse.factList != null){
                    // The reqeust is succeed, data is ready to bind on UI.
                    // stop displaying loading message, make recycler view visible.
                    mainFragmentBinding.setIsLoading(false);
                    // set the data to the adapter
                    factListAdapter.setFactList(factListResponse.factList.rows);
                    // update the title
                    if (mainActivity != null) mainActivity.setTitle(factListResponse.factList.title);

                }else if (factListResponse.errorMessage != null){
                    // The request failed, show error message.
                    mainFragmentBinding.loadingTv.setText(factListResponse.errorMessage);
                }
            }
        });
    }

}
