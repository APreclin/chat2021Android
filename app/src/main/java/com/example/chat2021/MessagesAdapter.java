package com.example.chat2021;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.ViewHolder> {

    private ListMessage listMessage;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewMessage;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            textViewMessage = (TextView) view.findViewById(R.id.textViewMessage);
        }

        public TextView getTextView() {
            return textViewMessage;
        }
    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param lm ListMessage containing the data to populate views to be used
     * by RecyclerView.
     */
    public MessagesAdapter(ListMessage lm) {
        listMessage = lm;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.message_layout, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.getTextView().setText(listMessage.get(position).contenu);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return listMessage.messages.size();
    }
}
