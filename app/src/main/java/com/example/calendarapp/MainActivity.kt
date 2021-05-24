package com.example.calendarapp

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.calendarapp.adapter.CalendarAdapter
import com.example.calendarapp.adapter.DayFragmentAdapter
import com.example.calendarapp.model.DateModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var dayFragmentAdapter: DayFragmentAdapter
    private lateinit var viewPager2: ViewPager
    private lateinit var dayTitle : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        dayTitle = tv_day
        dayFragmentAdapter = DayFragmentAdapter(supportFragmentManager)
        viewPager2 = viewPager
        viewPager2.adapter = dayFragmentAdapter
        viewPager2.currentItem = 100
        viewPager2.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {

            }

            override fun onPageScrollStateChanged(state: Int) {
            }

        })

    }

}