package com.example.safetynet.contactview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.safetynet.R;

import java.util.ArrayList;
import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {

    private List<Contact> contactSet;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView contactTextView;
        private CheckBox actionButton;

        public ViewHolder(View view, boolean check) {
            super(view);
            contactTextView = (TextView) itemView.findViewById(R.id.contact_name);
            actionButton = (CheckBox) itemView.findViewById(R.id.action_button);
            if(!check) actionButton.setButtonDrawable(R.drawable.x_checkbox_selector);

        }

        public TextView getTextView() {
            return contactTextView;
        }
        public CheckBox getActionButton() {
            return actionButton;
        }
    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used
     * by RecyclerView.
     */
    private boolean check = false;
    private List<Integer> indices = new ArrayList<>();
    public ContactAdapter(List<Contact> dataSet) {
        contactSet = dataSet;
    }
    public ContactAdapter(List<Contact> dataSet, boolean check) {
        contactSet = dataSet;
        this.check = check;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.contact_row_item, viewGroup, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView, check);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.getTextView().setText(contactSet.get(position).toString());
        viewHolder.getActionButton().setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(!check) {
                    int pos = viewHolder.getAdapterPosition();
                    contactSet.remove(pos);
                    notifyItemChanged(pos);
                    notifyItemRangeChanged(pos,getItemCount());
                }
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return contactSet.size();
    }
}
