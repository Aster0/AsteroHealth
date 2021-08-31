package me.astero.asterohealth.adapters;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import me.astero.asterohealth.R;
import me.astero.asterohealth.database.objects.GridData;

public class WaterAdapter extends RecyclerView.Adapter<WaterAdapter.ViewHolder> {

    private List<GridData> localDataSet;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;

        private final TextView descriptionText, metricsText, valueText;


        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            textView = (TextView) view.findViewById(R.id.headerText);
            metricsText = view.findViewById(R.id.metricsText);
            valueText = view.findViewById(R.id.valueText);
            descriptionText =  view.findViewById(R.id.descriptionText);


        }

        public TextView getTextView() {
            return textView;
        }

        public TextView getDescriptionText() {
            return descriptionText;
        }

        public TextView getMetricsText() {
            return metricsText;
        }

        public TextView getValueText() {
            return valueText;
        }
    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used
     * by RecyclerView.
     */
    public WaterAdapter(List<GridData> dataSet) {
        localDataSet = dataSet;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_water_intake, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element

        int glasses = localDataSet.get(position).value;
        double litres = glasses * 0.2;

        String headerText = viewHolder.getTextView().getText().toString();
        viewHolder.getTextView().setText(headerText.replace("%name%", localDataSet.get(position).name)
            .replace("%value%", String.format("%.1f", litres)));

        viewHolder.getValueText().setText(String.valueOf(localDataSet.get(position).value));
        viewHolder.getMetricsText().setText(localDataSet.get(position).metrics);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}
