package exercise.telstra.com.au.telstraexercise.ui;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import exercise.telstra.com.au.telstraexercise.R;
import exercise.telstra.com.au.telstraexercise.databinding.FactItemBinding;
import exercise.telstra.com.au.telstraexercise.model.Fact;

/**
 * The adapter for the recycler view to display the fact list.
 * With data binding, the adapter focused on preparing the data and feed it to the list.
 * When updating data, the adapter will compare the new data with current one and only notify
 * the updated items to achieve a better performance.
 */
public class FactListAdapter extends RecyclerView.Adapter<FactListAdapter.FactViewHolder> {

    // The data will be displayed on the list view.
    List<Fact> data;


    /**
     *  Set new fact data to the adapter.
     *  By comparing the new data with current data, find the changed items and update accordingly.
     *
     * @param factList
     */
    public void setFactList(final List<Fact> factList){
        if (data == null){
            data = factList;
            notifyItemRangeInserted(0, data.size());
        }else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return data.size();
                }

                @Override
                public int getNewListSize() {
                    return factList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return data.get(oldItemPosition) == factList.get(newItemPosition);
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    Fact old = data.get(oldItemPosition);
                    Fact fact = factList.get(newItemPosition);
                    return old.title.equals(fact.title)
                            && old.description.equals(fact.description)
                            && old.imageHref.equals(fact.imageHref);
                }
            });
            data = factList;
            result.dispatchUpdatesTo(this);
        }
    }

    /**
     * Create a new view holder.
     *
     * @param viewGroup the parent view group.
     * @param i the position of the view
     * @return ViewHolder a viewHolder instance
     */
    @NonNull
    @Override
    public FactViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        // bind the UI
        FactItemBinding factItemBinding = DataBindingUtil
                .inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.fact_item, viewGroup, false);
        // return the view holder with binding object available.
        return new FactViewHolder(factItemBinding);
    }

    /**
     * Bind a data with a viewHolder.
     *
     * @param factViewHolder The viewHolder ready to bind new data to display
     * @param i The index of the data in the list.
     */
    @Override
    public void onBindViewHolder(@NonNull FactViewHolder factViewHolder, int i) {
        // bind a new data to the view
        factViewHolder.binding.setFact(data.get(i));
    }

    /**
     * return the total number of the items in the list.
     *
     * @return total item numbers hold in the list.
     */
    @Override
    public int getItemCount() {
        return data == null? 0 : data.size();
    }

    /**
     * Viewholder holds all the inflated UI component, when an item become invisible, the UI component will
     * be recycled and bind with a new data about to be visible.
     */
    static class FactViewHolder extends RecyclerView.ViewHolder{

        // The databinding object for the item layout.
        final FactItemBinding binding;

        /**
         * Constructor method for FactViewHolder.
         *
         * @param factItemBinding the instance for binding data with the viewHolder
         */
        public FactViewHolder(@NonNull FactItemBinding factItemBinding) {
            super(factItemBinding.getRoot());
            binding = factItemBinding;
        }
    }
}
