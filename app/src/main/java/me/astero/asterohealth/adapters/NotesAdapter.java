package me.astero.asterohealth.adapters;

import android.text.Html;
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
import me.astero.asterohealth.database.objects.NotesData;
import me.astero.asterohealth.util.Util;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {

    private List<NotesData> localDataSet;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;

        private final TextView descriptionText, timeText;

        private View view;


        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            textView = (TextView) view.findViewById(R.id.headerText);

            descriptionText =  view.findViewById(R.id.descriptionText);

            timeText = view.findViewById(R.id.timeText);


            this.view = view;


        }

        public View getView() { return view; }

        public TextView getTextView() {
            return textView;
        }

        public TextView getDescriptionText() {
            return descriptionText;
        }


        public TextView getTimeText() {
            return timeText;
        }


    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used
     * by RecyclerView.
     */
    public NotesAdapter(List<NotesData> dataSet) {
        localDataSet = dataSet;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_note, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element

        viewHolder.getTextView().setText(localDataSet.get(position).title);
        viewHolder.getDescriptionText().setText(localDataSet.get(position).description);

        System.out.println(localDataSet.get(position).title);
        viewHolder.timeText.setText(localDataSet.get(position).date);


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}
