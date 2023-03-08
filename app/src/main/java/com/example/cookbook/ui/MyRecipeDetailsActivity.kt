package com.example.cookbook.ui

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.core.net.toUri
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.navArgs
import com.example.cookbook.R
import com.example.cookbook.data.database.MyRecipesEntity
import com.example.cookbook.viewmodels.MyRecipesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_add_recipe.*
import kotlinx.android.synthetic.main.activity_my_recipe_details.*
import kotlinx.android.synthetic.main.activity_my_recipe_details.view.*
import kotlinx.android.synthetic.main.placeholder_row_layout.*

@AndroidEntryPoint
class MyRecipeDetailsActivity : AppCompatActivity() {

    private val args by navArgs<MyRecipeDetailsActivityArgs>()

    private lateinit var myRecipe:MyRecipesEntity
    private lateinit var myRecipesViewModel: MyRecipesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_recipe_details)
        myRecipesViewModel = ViewModelProvider(this).get(MyRecipesViewModel::class.java)
        myRecipe = args.recipe

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val imageUri: Uri
        imageUri = myRecipe.image.toUri()
        main_imageView.setImageURI(imageUri)
        time_textView.text = myRecipe.time
        description_text.text = myRecipe.description
        instruction_text.text = myRecipe.instruction
        title_textView.text = myRecipe.title
        calories_textview.text = myRecipe.calories
        if(myRecipe.calories.isEmpty())
        calories_details.visibility = View.GONE
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.myrecipe_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
            R.id.menu_delete -> {
                Log.d("myImage", myRecipe.title)
                myRecipesViewModel.deleteInMyRecipes(myRecipe.title)
                Toast.makeText(applicationContext,R.string.delete, Toast.LENGTH_SHORT).show()
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

}