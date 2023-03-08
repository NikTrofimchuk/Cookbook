package com.example.cookbook.ui

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.text.format.Time
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.ViewModelProvider
import com.example.cookbook.R
import com.example.cookbook.data.database.MyRecipesEntity
import com.example.cookbook.viewmodels.MyRecipesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_add_recipe.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream

@AndroidEntryPoint
class AddRecipeActivity : AppCompatActivity() {

    private val GALLERY_REQUEST = 1
    private lateinit var myRecipesViewModel: MyRecipesViewModel
    private lateinit var myrecipe:MyRecipesEntity
    private lateinit var imageUrl:String
    private lateinit var myImageUrl:String
    private var photoPicker: Boolean = false

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
                photoPicker = true
                val selectedImage = imageReturnedIntent?.data
                recipeImage.setImageURI(selectedImage)
                val image: BitmapDrawable = recipeImage.drawable as BitmapDrawable
                imageUrl = selectedImage.toString()
                getTempFile(this, imageUrl, image)
                Log.d("AddRecipes", selectedImage.toString())
            }
        }
    }

    private fun getTempFile(context: Context, url: String, image: BitmapDrawable) {
        val fOut: OutputStream
        val file: File
        val time = Time()
        time.setToNow()
        try {
            val filename : String = time.year.toString() + time.month.toString() + time.monthDay.toString() + time.hour.toString() + time.minute.toString() + time.second.toString()
            Log.d("myImage", filename)
            file = File.createTempFile(filename, ".jpg", context.cacheDir)
            fOut = FileOutputStream(file)

            image.toBitmap().compress(Bitmap.CompressFormat.JPEG, 90, fOut)
            myImageUrl = file.absolutePath.toString()
            Log.d("myImage", myImageUrl)
            fOut.flush();
            fOut.close();
        }
        catch (e:IOException) {

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
        else if(photoPicker){
            myrecipe = MyRecipesEntity(
                title = title_ed.text.toString(),
                time = time_ed.text.toString(),
                description = description_ed.text.toString(),
                instruction = instruction_ed.text.toString(),
                image = myImageUrl,
                calories = calories_ed.text.toString())
            myRecipesViewModel.writeInMyRecipes(myrecipe)
            finish()
        }
        else Toast.makeText(getApplicationContext(),R.string.photo,Toast.LENGTH_SHORT).show()
    }
}