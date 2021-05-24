package com.example.calendarapp.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.calendarapp.DayFragment

class DayFragmentAdapter(fm: FragmentManager) :
    FragmentPagerAdapter(fm) {

    override fun getCount(): Int {
        return 200
    }

    override fun getItem(position: Int): Fragment {
        val dayFragment = DayFragment()
        val bundle = Bundle()
        bundle.putString("message", "$position")
        dayFragment.arguments = bundle
        return dayFragment
    }

}