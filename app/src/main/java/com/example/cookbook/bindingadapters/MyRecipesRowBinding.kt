package com.example.cookbook.bindingadapters

import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import com.example.cookbook.data.database.MyRecipesEntity
import com.example.cookbook.ui.fragments.MyRecipesFragmentDirections
import kotlinx.android.synthetic.main.activity_add_recipe.*
import org.jsoup.Jsoup
import java.lang.Exception

class MyRecipesRowBinding {
    companion object {

        @BindingAdapter("onMyRecipeClickListener")
        @JvmStatic
        fun onMyRecipeClickListener(myRecipeRowLayout: ConstraintLayout, recipe: MyRecipesEntity) {
            Log.d("onRecipeClickListener", "CALLED")
            myRecipeRowLayout.setOnClickListener {
                try {
                    val action =
                        MyRecipesFragmentDirections.actionMyRecipesFragmentToMyRecipesDetailsActivity(recipe)
                    Log.d("onRecipeClickListener", recipe.toString())
                    myRecipeRowLayout.findNavController().navigate(action)
                } catch (e: Exception) {
                    Log.d("onRecipeClickListener", e.toString())
                }
            }
        }

        @BindingAdapter("loadImage")
        @JvmStatic
        fun loadImage(imageView: ImageView, image: String) {
            var imageUri: Uri
            imageUri = image.toUri()
            imageView.setImageURI(imageUri)
        }

        @BindingAdapter("setTime")
        @JvmStatic
        fun setTime(textView: TextView, time: String) {
            textView.text = time
        }

        @BindingAdapter("checkCalories")
        @JvmStatic
        fun checkCalories(imageView: ImageView, calories: String) {
            if (calories.isEmpty())
                imageView.visibility = View.GONE
        }
        @BindingAdapter("setCalories")
        @JvmStatic
        fun setCalories(textView: TextView, calories: String) {
            textView.text = calories
        }

        @BindingAdapter("parse")
        @JvmStatic
        fun parse(textView: TextView, description: String?){
            if(description != null) {
                val desc = Jsoup.parse(description).text()
                textView.text = desc
            }
        }

    }
}