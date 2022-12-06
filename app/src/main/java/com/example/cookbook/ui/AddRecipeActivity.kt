package com.example.cookbook.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.cookbook.R
import com.example.cookbook.data.database.MyRecipesEntity
import com.example.cookbook.viewmodels.MyRecipesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_add_recipe.*

@AndroidEntryPoint
class AddRecipeActivity : AppCompatActivity() {

    private val GALLERY_REQUEST = 1
    private lateinit var myRecipesViewModel: MyRecipesViewModel
    private lateinit var myrecipe:MyRecipesEntity
    private lateinit var imageUrl:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_recipe)
        myRecipesViewModel = ViewModelProvider(this).get(MyRecipesViewModel::class.java)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        addImageBtn.setOnClickListener {
            val photoPickerIntent = Intent(Intent.ACTION_PICK)
            photoPickerIntent.type = "image/*"
            startActivityForResult(photoPickerIntent, GALLERY_REQUEST)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.addrecipe_menu, menu)
        return true
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, imageReturnedIntent: Intent?) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent)
        when (requestCode) {
            GALLERY_REQUEST -> if (resultCode == RESULT_OK) {
                val selectedImage = imageReturnedIntent?.data
                recipeImage.setImageURI(selectedImage)
                imageUrl = selectedImage.toString()
                Log.d("AddRecipes", selectedImage.toString())
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
            R.id.menu_add_button -> dataValidation()
        }
        return super.onOptionsItemSelected(item)
    }
    private fun dataValidation(){
        if(title_ed.length() == 0 || time_ed.length() == 0 || description_ed.length() == 0 || instruction_ed.length() == 0)
            Toast.makeText(getApplicationContext(),R.string.error,Toast.LENGTH_SHORT).show()
        else
            myrecipe.title = title_ed.toString()
            myrecipe.time = time_ed.toString()
            myrecipe.description = description_ed.toString()
            myrecipe.instruction = instruction_ed.toString()
            myrecipe.image = imageUrl
            finish()
    }
}