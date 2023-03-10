package com.example.cookbook.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cookbook.R
import com.example.cookbook.models.BasketRecipe
import kotlinx.android.synthetic.main.basket_recipes_row_layout.view.*
import kotlin.collections.ArrayList

class BasketRecipesAdapter(private val listener: OnItemClickListener) : RecyclerView.Adapter<BasketRecipesAdapter.BasketRecipesHolder>() {

    private var recipesList = ArrayList<BasketRecipe>()

    class BasketRecipesHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketRecipesHolder {
        return BasketRecipesHolder(LayoutInflater.from(parent.context).inflate(R.layout.basket_recipes_row_layout, parent, false))
    }

    override fun onBindViewHolder(holder: BasketRecipesHolder, position: Int) {
        holder.itemView.basket_recipe_name.text = recipesList[position].recipe_name
        holder.itemView.basket_recipe_multiplier.text = recipesList[position].multiplier.toString()

        holder.itemView.minus_multiplier.isEnabled = recipesList[position].multiplier != 1

        holder.itemView.minus_multiplier.setOnClickListener {
            listener.onMinusClick(position, recipesList[position].recipe_name)
        }

        holder.itemView.plus_multiplier.setOnClickListener {
            listener.onPlusClick(position, recipesList[position].recipe_name)
        }
    }

    override fun getItemCount(): Int {
        return recipesList.size
    }

    interface OnItemClickListener {
        fun onMinusClick(position: Int, name : String)
        fun onPlusClick(position: Int, name : String)
    }

    fun setData(newData: List<BasketRecipe>) {
        recipesList.clear()
        recipesList.addAll(newData)
        notifyDataSetChanged()
    }
}