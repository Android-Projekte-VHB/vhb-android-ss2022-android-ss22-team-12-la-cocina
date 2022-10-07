package com.example.lacocina.recipe;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lacocina.R;
import com.example.lacocina.link.RecipeLink;

import java.util.ArrayList;
import java.util.List;

public class RecipeLinkAdapter extends RecyclerView.Adapter<RecipeLinkAdapter.RecipeLinkHolder> {
    private List<RecipeLink> recipeLinks= new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public RecipeLinkHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_link_item, parent, false);
        return new RecipeLinkHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeLinkHolder holder, int position) {
        RecipeLink currentRecipeLink = recipeLinks.get(position);
        holder.tvRecipeTitle.setText(currentRecipeLink.getTitle());
        holder.tvRecipeDescription.setText(currentRecipeLink.getShortDescription());

        // Show star when recipe is favourite
        String isFavourite = currentRecipeLink.getIsFavourite();
        if(isFavourite != null) {
            if (isFavourite.equals("1") && isFavourite != null) {
                holder.favStar.setVisibility(View.VISIBLE);
            } else if (isFavourite == null || isFavourite.equals("0")) {
                holder.favStar.setVisibility(View.INVISIBLE);
            }
        } else {
            holder.favStar.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return recipeLinks.size();
    }

    public void setRecipeLinks(List<RecipeLink> recipeLinks) {
        this.recipeLinks = recipeLinks;
        notifyDataSetChanged();
    }

    public RecipeLink getRecipeLinkAt(int position) {
        return recipeLinks.get(position);
    }

    class RecipeLinkHolder extends RecyclerView.ViewHolder {
        private TextView tvRecipeTitle;
        private TextView tvRecipeDescription;
        private ImageView favStar;

        public RecipeLinkHolder(View itemView) {
            super(itemView);
            tvRecipeTitle = itemView.findViewById(R.id.recipe_link_name);
            tvRecipeDescription = itemView.findViewById(R.id.recipe_link_short_description);
            favStar = itemView.findViewById(R.id.favourite_recipe_link);



            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(recipeLinks.get(position));
                    }
                }
            });


        }
    }

    public interface OnItemClickListener {
        void onItemClick(RecipeLink recipeLink);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}