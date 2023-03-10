package com.example.cookbook.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cookbook.R
import com.example.cookbook.models.BasketRecipe
import kotlinx.android.synthetic.main.basket_recipes_row_layout.view.*
import kotlin.collections.ArrayList

class BasketRecipesAdapter : RecyclerView.Adapter<BasketRecipesAdapter.BasketRecipesHolder>() {

    private var recipesList = ArrayList<BasketRecipe>()

    class BasketRecipesHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketRecipesHolder {
        return BasketRecipesHolder(LayoutInflater.from(parent.context).inflate(R.layout.basket_recipes_row_layout, parent, false))
    }

    override fun onBindViewHolder(holder: BasketRecipesHolder, position: Int) {
        holder.itemView.basket_recipe_name.text = recipesList[position].recipe_name
        holder.itemView.basket_recipe_multiplier.text = recipesList[position].multiplier.toString()
    }

    override fun getItemCount(): Int {
        return recipesList.size
    }

    fun setData(newData: List<BasketRecipe>) {
        recipesList.addAll(newData)
        notifyDataSetChanged()
    }
}