package com.example.cookbook.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.cookbook.data.database.MyRecipesEntity
import com.example.cookbook.databinding.MyrecipesRowLayoutBinding
import com.example.cookbook.util.RecipesDiffUtil

class MyRecipesAdapter: RecyclerView.Adapter<MyRecipesAdapter.MyRecipesHolder>() {

    private var recipesList = ArrayList<MyRecipesEntity>()

    class MyRecipesHolder(private val binding: MyrecipesRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(myRecipe: MyRecipesEntity){
            binding.recipe = myRecipe
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): MyRecipesHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = MyrecipesRowLayoutBinding.inflate(layoutInflater, parent, false)
                return MyRecipesHolder(binding)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyRecipesHolder {
        return MyRecipesHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyRecipesHolder, position: Int) {
        val currentRecipe = recipesList[position]
        holder.bind(currentRecipe)
    }

    override fun getItemCount(): Int {
        return recipesList.size
    }

    fun setData(newData: List<MyRecipesEntity>){
        val recipesDiffUtil = RecipesDiffUtil(recipesList, newData)
        val diffUtilResult = DiffUtil.calculateDiff(recipesDiffUtil)
        recipesList = newData as ArrayList<MyRecipesEntity>
        Log.v("recipeListSize:",recipesList.size.toString())
        diffUtilResult.dispatchUpdatesTo(this)
    }
}