package com.example.cookbook.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.navArgs
import com.example.cookbook.R
import com.example.cookbook.adapters.PagerAdapter
import com.example.cookbook.data.database.BookmarkEntity
import com.example.cookbook.ui.fragments.IngredientsFragment
import com.example.cookbook.ui.fragments.InstructionsFragment
import com.example.cookbook.ui.fragments.OverviewFragment
import com.example.cookbook.util.Constants.Companion.RECIPE_RESULT_KEY
import com.example.cookbook.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {

    private val args by navArgs<DetailsActivityArgs>()

    private var bookmarkID = 0
    private lateinit var mainViewModel: MainViewModel

    lateinit var item : MenuItem

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.addinbookmarks_menu, menu)
        if (menu != null) {
            item = menu.findItem(R.id.menu_addinbookmarks)
        }
        if(args.result.inBookmark){
            setMenuCheckedButton(item)
        }
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        setSupportActionBar(toolbar)
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
//
        val fragments = ArrayList<Fragment>()
        fragments.add(OverviewFragment())
        fragments.add(IngredientsFragment())
        fragments.add(InstructionsFragment())

        val titles = ArrayList<String>()
        titles.add(getString(R.string.title_overview))
        titles.add(getString(R.string.title_ingredients))
        titles.add(getString(R.string.title_instruction))

        val resultBundle = Bundle()
        resultBundle.putParcelable(RECIPE_RESULT_KEY, args.result)

        val adapter = PagerAdapter(
            resultBundle,
            fragments,
            titles,
            supportFragmentManager
        )
        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)
        Log.d("RecipesFragment", args.result.toString())
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        lifecycleScope.launch{
            when (item.itemId) {
                android.R.id.home -> finish()
                R.id.menu_addinbookmarks -> {
                    if (args.result.inBookmark)
                    {
                        setMenuButton(item)

                        Toast.makeText(applicationContext,R.string.bookmarks_deleted, Toast.LENGTH_SHORT).show()

                        if(bookmarkID == 0)mainViewModel.deleteBookmark(args.result.IdBookmark)
                        else mainViewModel.deleteBookmark(bookmarkID)

                        args.result.inBookmark = false
                    }
                    else
                    {
                        args.result.inBookmark = true

                        Toast.makeText(applicationContext,R.string.bookmarks_added, Toast.LENGTH_SHORT).show()

                        val bookmarkEntity = BookmarkEntity(args.result)
                        bookmarkID = mainViewModel.writeInBookmarks(bookmarkEntity).toInt()
                        setMenuCheckedButton(item)
                    }
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setMenuCheckedButton(item: MenuItem){
        item.setIcon(R.drawable.ic_baseline_bookmark_24)
    }

    private fun setMenuButton(item: MenuItem){
        item.setIcon(R.drawable.ic_baseline_bookmark_border_24)
    }
}