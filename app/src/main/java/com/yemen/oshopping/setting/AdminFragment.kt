package com.yemen.oshopping.setting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.Navigation
import com.yemen.oshopping.R

class AdminFragment : Fragment() {
    lateinit var showCategoryTV: TextView
    lateinit var showReportTV: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_admin, container, false)
        showCategoryTV=view.findViewById(R.id.show_category_tv)
        showReportTV=view.findViewById(R.id.show_report_tv)

        showCategoryTV.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_adminFragment_to_showCategoryFragment)
        }
        showReportTV.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_adminFragment_to_showReportFragment)
        }

        return view
    }


}