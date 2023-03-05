package com.example.cookbook.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import coil.load
import com.example.cookbook.R
import com.example.cookbook.models.Result
import com.example.cookbook.util.Constants.Companion.RECIPE_RESULT_KEY
import kotlinx.android.synthetic.main.fragment_overview.view.*
import org.jsoup.Jsoup


class OverviewFragment : Fragment() {
    private val TAG = "MyApp"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_overview, container, false)

        val args = arguments
        val myBundle: Result? = args?.getParcelable(RECIPE_RESULT_KEY)

        view.main_imageView.load(myBundle?.image)
        view.title_textView.text = myBundle?.title
        view.likes_textView.text = myBundle?.aggregateLikes.toString()
        view.time_textView.text = myBundle?.readyInMinutes.toString()
        myBundle?.summary.let {
            val summary = Jsoup.parse(it).text()
            view.summary_textView.text = summary
            val calSummary = summary.substringBefore("calories").substringBeforeLast(" ")
            val calories: String = calSummary.substring(calSummary.lastIndexOf(" ") + 1)

            view.calories_textview.text = calories
        }


        if(myBundle?.vegetarian == true){
            view.vegetarian_imageView.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            view.vegetarian_textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }

        if(myBundle?.vegan == true){
            view.vegan_imageView.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            view.vegan_textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }

        if(myBundle?.glutenFree == true){
            view.gluten_free_imageView.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            view.gluten_free_textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }

        if(myBundle?.dairyFree == true){
            view.dairy_free_imageView.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            view.dairy_free_textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }

        if(myBundle?.veryHealthy == true){
            view.healthy_imageView.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            view.healthy_textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }

        if(myBundle?.cheap == true){
            view.cheap_imageView.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            view.cheap_textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }


        return view
    }
}