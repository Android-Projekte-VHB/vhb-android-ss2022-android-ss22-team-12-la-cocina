package com.example.lacocina.recipe;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lacocina.R;
import com.example.lacocina.grocerylist.GroceryList;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("deprecation")
public class GroceryListAdapter extends RecyclerView.Adapter<GroceryListAdapter.GroceryListHolder> {

    private List<GroceryList> groceryLists = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public GroceryListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.grocery_list_item, parent, false);

        return new GroceryListHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull GroceryListHolder holder, int position) {
        GroceryList currentGroceryList = groceryLists.get(position);
        holder.groceryListName.setText(currentGroceryList.getListName());

        String isFavourite = currentGroceryList.getIsFavourite();
        if(isFavourite != null) {
            if (isFavourite.equals("1")) {
                holder.favouriteList.setVisibility(View.VISIBLE);
            } else if (isFavourite.equals("0")) {
                holder.favouriteList.setVisibility(View.INVISIBLE);
            }
        } else {
            holder.favouriteList.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return groceryLists.size();
    }

    public void setLists(List<GroceryList> groceryLists) {
        this.groceryLists = groceryLists;
        notifyDataSetChanged();
    }

    public GroceryList getListAt(int position) {
        return groceryLists.get(position);
    }


    class GroceryListHolder extends RecyclerView.ViewHolder {
        private TextView groceryListName;
        private ImageView favouriteList;

        public GroceryListHolder(View itemView) {
            super(itemView);
            groceryListName = itemView.findViewById(R.id.grocery_list_name_id);
            favouriteList = itemView.findViewById(R.id.favourite_grocery_list);

            // Catches click on card view item on its position, thus getting the position in the list object
            itemView.setOnClickListener(view -> {
                int position = getAdapterPosition();
                // Checks for invalid recyclerview position and null pointer listener object
                if(listener != null && position != RecyclerView.NO_POSITION)
                listener.onItemClick(groceryLists.get(position));
            });

        }

    }

    // Handling click on grocery list item in recyclerview to actual object
    public interface OnItemClickListener {
        void onItemClick(GroceryList groceryList);
    }
    // Calls OnItemClickListener which calls onItemClick to get to the grocery list item
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }
}
