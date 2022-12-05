package com.example.cookbook.ui.fragments.addrecipe

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.cookbook.databinding.FragmentAddRecipeBinding
import kotlinx.android.synthetic.main.fragment_add_recipe.*


class AddRecipeFragment : Fragment() {
    private val GALLERY_REQUEST = 1
    lateinit var binding: FragmentAddRecipeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddRecipeBinding.inflate(inflater, container, false)
        return inflater.inflate(com.example.cookbook.R.layout.fragment_add_recipe, container, false)
    }
    override fun onViewCreated(
        view: View, savedInstanceState: Bundle?
    ){
        super.onViewCreated(view, savedInstanceState)
        addImageBtn.setOnClickListener {
            val photoPickerIntent = Intent(Intent.ACTION_PICK)
            photoPickerIntent.type = "image/*"
            startActivityForResult(photoPickerIntent, GALLERY_REQUEST)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, imageReturnedIntent: Intent?) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent)
            super.onActivityResult(requestCode, resultCode, imageReturnedIntent)
            when (requestCode) {
                GALLERY_REQUEST -> if (resultCode == RESULT_OK) {
                    val selectedImage = imageReturnedIntent?.data
                    recipeImage.setImageURI(selectedImage);
                }
            }
    }
}