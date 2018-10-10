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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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

    /**
     * Get the instance of the MainFragment.
     * @return a instance of MainFragment
     */
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
        mainFragmentBinding.setIsEmptyList(true);

        // init the recycler view, setting adapter and layout manager.
        factListAdapter = new FactListAdapter();
        mainFragmentBinding.factList.setAdapter(factListAdapter);
        mainFragmentBinding.factList.setLayoutManager(new LinearLayoutManager(getContext()));

        // handle the button's click event
        mainFragmentBinding.btnLoadData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // load new fact list from server through the view model.
                mViewModel.loadFactList();
                // set the UI in loading mode
                mainFragmentBinding.setIsLoading(true);
            }
        });

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

    /**
     * UI retained the LiveData through the view model and observe on it.
     * @param viewModel the ViewModel on which the LiveData is maintained.
     */
    private void subscribUi(MainViewModel viewModel){
        viewModel.getFactListResponseLiveData().observe(this, new Observer<FactListResponse>() {
            @Override
            public void onChanged(@Nullable FactListResponse factListResponse) {
                if (factListResponse.factList != null){
                    // The reqeust is succeed, data is ready to bind on UI.
                    // stop displaying loading message, make recycler view visible.
                    mainFragmentBinding.setIsLoading(false);
                    mainFragmentBinding.setIsEmptyList(false);
                    // set the data to the adapter
                    factListAdapter.setFactList(factListResponse.factList.rows);
                    // update the title
                    if (mainActivity != null) mainActivity.setTitle(factListResponse.factList.title);

                    // show a toast to user
                    Toast.makeText(getContext(), R.string.data_refreshed, Toast.LENGTH_SHORT).show();
                }else if (factListResponse.errorMessage != null){
                    // stop displaying loading message, make recycler view visible.
                    mainFragmentBinding.setIsLoading(false);
                    mainFragmentBinding.setIsEmptyList(false);
                    mainFragmentBinding.setHasError(true);
                    // The request failed, show error message.
                    mainFragmentBinding.loadingTv.setText(factListResponse.errorMessage);
                    // show a toast to user
                    Toast.makeText(getContext(), R.string.data_load_fail, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
