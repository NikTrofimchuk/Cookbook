package com.example.cookbook.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.cookbook.R
import com.example.cookbook.models.ExtendedIngredient
import com.example.cookbook.util.Constants
import com.example.cookbook.util.RecipesDiffUtil
import kotlinx.android.synthetic.main.basket_ingredients_row_layout.view.*
import kotlinx.android.synthetic.main.ingredients_row_layout.view.*
import java.util.*

class BasketIngredientsAdapter : RecyclerView.Adapter<BasketIngredientsAdapter.BasketIngredientViewHolder>() {

    private var ingredientsList = emptyList<ExtendedIngredient>()

    class BasketIngredientViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketIngredientViewHolder {
        return BasketIngredientViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.basket_ingredients_row_layout, parent, false))
    }

    override fun onBindViewHolder(holder: BasketIngredientViewHolder, position: Int) {
        if(ingredientsList[position].image.toString() == "null"){
            holder.itemView.basket_ingredient_imageView.load(R.drawable.ic_error_placeholder)
        }
        else
            holder.itemView.basket_ingredient_imageView.load(Constants.BASE_IMAGE_URL + ingredientsList[position].image) {
                crossfade(600)
                error(R.drawable.ic_error_placeholder)
            }
        holder.itemView.basket_ingredient_name.text = ingredientsList[position].name.capitalize(Locale.ROOT)
        holder.itemView.basket_ingredient_amount.text = ingredientsList[position].amount.toString()
        holder.itemView.basket_ingredient_unit.text = ingredientsList[position].unit
    }

    override fun getItemCount(): Int {
        return ingredientsList.size
    }

    fun setData(newIngredients: List<ExtendedIngredient>) {
        val ingredientsDiffUtil =
            RecipesDiffUtil(ingredientsList, newIngredients)
        val diffUtilResult = DiffUtil.calculateDiff(ingredientsDiffUtil)
        ingredientsList = newIngredients
        diffUtilResult.dispatchUpdatesTo(this)
    }
}