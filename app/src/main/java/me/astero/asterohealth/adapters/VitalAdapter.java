package me.astero.asterohealth.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import me.astero.asterohealth.R;
import me.astero.asterohealth.database.objects.GridData;
import me.astero.asterohealth.util.Util;

public class VitalAdapter extends RecyclerView.Adapter<VitalAdapter.ViewHolder> {

    private List<GridData> localDataSet;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;

        private final TextView descriptionText, metricsText, valueText;
        private final ImageView icon;

        private View view;


        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            textView = (TextView) view.findViewById(R.id.headerText);
            metricsText = view.findViewById(R.id.metricsText);
            valueText = view.findViewById(R.id.valueText);
            descriptionText =  view.findViewById(R.id.descriptionText);


            icon = view.findViewById(R.id.imageIcon);

            this.view = view;


        }

        public View getView() { return view; }

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



        public ImageView getIcon() {
            return icon;
        }
    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used
     * by RecyclerView.
     */
    public VitalAdapter(List<GridData> dataSet) {
        localDataSet = dataSet;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_vital_stats, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element

        viewHolder.getTextView().setText(localDataSet.get(position).name);

        viewHolder.getMetricsText().setText(localDataSet.get(position).metrics);
        viewHolder.getValueText().setText(String.valueOf(localDataSet.get(position).value));

        viewHolder.getIcon().setImageResource(Util.getId(viewHolder.view.getContext(),
                localDataSet.get(position).icon, "drawable"));



    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}
