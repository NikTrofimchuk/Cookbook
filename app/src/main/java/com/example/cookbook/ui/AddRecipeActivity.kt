package com.example.cookbook.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.navArgs
import com.example.cookbook.R
import com.example.cookbook.adapters.PagerAdapter
import com.example.cookbook.ui.fragments.addingredients.AddIngredientsFragment
import com.example.cookbook.ui.fragments.addrecipe.AddRecipeFragment
import com.example.cookbook.ui.fragments.ingredients.IngredientsFragment
import com.example.cookbook.ui.fragments.instructions.InstructionsFragment
import com.example.cookbook.ui.fragments.overview.OverviewFragment
import com.example.cookbook.util.Constants
import kotlinx.android.synthetic.main.activity_add_recipe.*
import kotlinx.android.synthetic.main.activity_details.*

class AddRecipeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_recipe)

        setSupportActionBar(toolbarAddRecipe)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val fragments = ArrayList<Fragment>()
        fragments.add(AddRecipeFragment())
        fragments.add(AddIngredientsFragment())

        val titles = ArrayList<String>()
        titles.add("Рецепт")
        titles.add("Ингредиенты")

        val resultBundle = Bundle()

        val adapter = PagerAdapter(
            resultBundle,
            fragments,
            titles,
            supportFragmentManager
        )

        viewPagerAddRecipe.adapter = adapter
        tabLayoutAddRecipe.setupWithViewPager(viewPagerAddRecipe)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}