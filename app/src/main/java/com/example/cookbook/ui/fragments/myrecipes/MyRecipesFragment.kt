package com.example.cookbook.ui.fragments.myrecipes

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.cookbook.R
import com.example.cookbook.databinding.FragmentMyRecipesBinding
import kotlinx.android.synthetic.main.fragment_my_recipes.*

class MyRecipesFragment : Fragment() {

    private lateinit var binding: FragmentMyRecipesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyRecipesBinding.inflate(inflater, container, false)
        return inflater.inflate(R.layout.fragment_my_recipes, container, false)
    }

    override fun onViewCreated(
        view: View, savedInstanceState: Bundle?
    ){
        super.onViewCreated(view, savedInstanceState)
        add_recipes_btn.setOnClickListener {
            Log.v("myrecipe","click")
            findNavController().navigate(R.id.action_myRecipesFragment_to_addRecipeActivity)
        }
    }
}