package com.example.calendarapp.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.calendarapp.R
import com.example.calendarapp.model.DateModel
import java.time.LocalDate
import java.util.*

class CalendarAdapter() : RecyclerView.Adapter<CalendarAdapter.ViewHolder>() {
    private var currentDay: Boolean = false
    private var selectedDate: LocalDate = LocalDate.now()
    private var listDayOfMonth: ArrayList<DateModel> = arrayListOf()
    private var clickCount = 0
    private var startTime: Long = 0
    private var duration: Long = 0
    private val MAX_DURATION = 500
    private var dayOfNextMonth: Int = 0
    var rowIndex = -1
    var dayOfWeek: Int = 0

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var dayOfMonth: TextView = itemView.findViewById(R.id.cellDayText)
        var itemBg: ConstraintLayout = itemView.findViewById(R.id.item_bg)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.item_layout, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val date: DateModel = listDayOfMonth[position]
        holder.dayOfMonth.text = date.day.toString()
        currentDay =
            date.day == selectedDate.dayOfMonth && date.month == selectedDate.monthValue && date.year == selectedDate.year
        if (currentDay) {
            holder.dayOfMonth.setTextColor(Color.RED)
        }
        holder.itemBg.setOnClickListener {
            rowIndex = position
            notifyDataSetChanged()
        }
        if (rowIndex == position) {
            holder.dayOfMonth.setTextColor(Color.parseColor("#e600e6"))
            holder.itemBg.setOnTouchListener { v, event ->
                when (event.action and MotionEvent.ACTION_MASK) {
                    MotionEvent.ACTION_DOWN -> {
                        startTime = System.currentTimeMillis()
                        clickCount++
                    }
                    MotionEvent.ACTION_UP -> {
                        val time = System.currentTimeMillis() - startTime
                        duration += time
                        if (clickCount == 2) {
                            if (duration <= MAX_DURATION) {
                                holder.dayOfMonth.setTextColor(genRandomColor())
                            }
                            clickCount = 0
                            duration = 0
                        }
                    }
                }
                true
            }
        } else {
//            if (position < dayOfWeek - 1) {
//                if (listDayOfMonth[position].day > 25 && listDayOfMonth[position].day != 1 ) {
//                    holder.dayOfMonth.setTextColor(Color.parseColor("#FF888888"))
//                }
//
//            }
//            if (position > listDayOfMonth.size - 1 - dayOfNextMonth) {
//                if (listDayOfMonth[position].day < 31) {
//                    holder.dayOfMonth.setTextColor(Color.parseColor("#FF888888"))
//                }
//
//            }
            if (position < 7) {
                if (listDayOfMonth[position].day > 25) {
                    holder.dayOfMonth.setTextColor(Color.parseColor("#FF888888"))
                }
            }
            if (position > 35){
                if (listDayOfMonth[position].day < 28) {
                    holder.dayOfMonth.setTextColor(Color.parseColor("#FF888888"))
                }
            }
            else {
                if (listDayOfMonth[position].day == selectedDate.dayOfMonth && listDayOfMonth[position].month == selectedDate.monthValue){
                    holder.dayOfMonth.setTextColor(Color.RED)
                }
            }
        }


    }

    override fun getItemCount(): Int {
        return listDayOfMonth.size
    }

    fun setData(data: ArrayList<DateModel>) {
        listDayOfMonth = data
        notifyDataSetChanged()
    }

    fun getDayOfWeek(data: Int, abc: Int) {
        dayOfWeek = data
        dayOfNextMonth = abc
        notifyDataSetChanged()
    }

    private fun genRandomColor(): Int {
        val rnd = Random()
        return Color.argb(
            255,
            rnd.nextInt(256),
            rnd.nextInt(256),
            rnd.nextInt(256)
        )
    }

    interface onClickListener {
        fun onClick(data: DateModel)
    }
}