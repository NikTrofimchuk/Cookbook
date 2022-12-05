package com.example.cookbook.ui.fragments.addingredients

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.cookbook.R
import com.example.cookbook.databinding.FragmentAddIngredientsBinding
import com.example.cookbook.ui.SearchIngredientsActivity
import kotlinx.android.synthetic.main.fragment_add_ingredients.*
import kotlinx.android.synthetic.main.fragment_my_recipes.*


class AddIngredientsFragment : Fragment() {

    private lateinit var binding: FragmentAddIngredientsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddIngredientsBinding.inflate(inflater, container, false)
        return inflater.inflate(R.layout.fragment_add_ingredients, container, false)
    }

    override fun onViewCreated(
        view: View, savedInstanceState: Bundle?
    ){
        super.onViewCreated(view, savedInstanceState)
        add_ingredient_btn.setOnClickListener {
            val intent = Intent(context, SearchIngredientsActivity::class.java)
            startActivity(intent)
        }
    }
}