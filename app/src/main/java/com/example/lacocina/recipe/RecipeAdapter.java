package com.example.lacocina.recipe;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lacocina.R;

import java.util.ArrayList;
import java.util.List;
@SuppressWarnings("deprecation")
public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeHolder> {
    private List<Recipe> recipes = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public RecipeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_item, parent, false);
        return new RecipeHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeHolder holder, int position) {
        Recipe currentRecipe = recipes.get(position);
        holder.tvRecipeTitle.setText(currentRecipe.getRecipeTitle());
        holder.tvRecipeDescription.setText(currentRecipe.getDescription());

        // Show star when recipe is favourite
        String isFavourite = currentRecipe.getIsFavourite();
        if(isFavourite != null) {
            if (isFavourite.equals("1")) {
                holder.favStar.setVisibility(View.VISIBLE);
            } else if (isFavourite.equals("0")) {
                holder.favStar.setVisibility(View.INVISIBLE);
            }
        } else {
            holder.favStar.setVisibility(View.INVISIBLE);
        }

        // Showing diet icon
        String diet = currentRecipe.getDiet();
        if(diet != null) {
            if(diet.equals("VEGAN")) {
                holder.dietIcon.setImageResource(R.drawable.ic_vegan_viewholder);
            } else if(diet.equals("VEGGIE")) {
                holder.dietIcon.setImageResource(R.drawable.ic_veggie_viewholder);
            } else if(diet.equals("MEAT")) {
                holder.dietIcon.setImageResource(R.drawable.ic_meat_viewholder);
            } else if(diet.equals("PESCE")) {
                holder.dietIcon.setImageResource(R.drawable.ic_fish_viewholder);
            }
        } else {
            holder.dietIcon.setVisibility(View.INVISIBLE);
        }



    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
        notifyDataSetChanged();
    }

    public Recipe getRecipeAt(int position) {
        return recipes.get(position);
    }

    class RecipeHolder extends RecyclerView.ViewHolder {
        private TextView tvRecipeTitle;
        private TextView tvRecipeDescription;
        private ImageView favStar;
        private ImageView dietIcon;

        public RecipeHolder(View itemView) {
            super(itemView);
            tvRecipeTitle = itemView.findViewById(R.id.text_view_recipe_title);
            tvRecipeDescription = itemView.findViewById(R.id.text_view_description);
            favStar = itemView.findViewById(R.id.favourite_recipe);
            dietIcon = itemView.findViewById(R.id.diet_icon);


            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(recipes.get(position));
                }
            });


        }
    }

    public interface OnItemClickListener {
        void onItemClick(Recipe recipe);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
