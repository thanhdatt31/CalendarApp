package com.example.calendarapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.calendarapp.adapter.CalendarAdapter
import com.example.calendarapp.adapter.DayTitleAdapter
import com.example.calendarapp.model.DateModel
import kotlinx.android.synthetic.main.fragment_day.view.*
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter


class DayFragment : Fragment() {
    private var selectedDate: LocalDate = LocalDate.now()
    private var position: Long = 0L
    private var dayOfWeek: Int = 0
    private var dayOfNextMonth = 0
    private var listDayOfMonth: ArrayList<DateModel> = arrayListOf()
    private var listTitle: ArrayList<Int> = arrayListOf()
    private val calendarAdapter: CalendarAdapter = CalendarAdapter()
    private val dayTitleAdapter: DayTitleAdapter = DayTitleAdapter()

    private var monthOfYear: String = ""
    private var dayTitle: String = ""
    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewTitle: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_day, container, false)
        position = arguments!!.getString("message")!!.toLong()
        if (listDayOfMonth.size == 0) {
            listDayOfMonth = genListDayOfMonth(position)
            view.tv_monthYear.text = monthOfYear
        } else {
            view.tv_monthYear.text = monthOfYear
        }
        if (listTitle.size == 0) {
            listTitle = arrayListOf(0, 1, 2, 3, 4, 5, 6)
        }
        recyclerViewTitle = view.tv_day_title
        recyclerViewTitle.apply {
            layoutManager = GridLayoutManager(context, 7)
            dayTitleAdapter.setData(listTitle)
            adapter = dayTitleAdapter
        }
        recyclerView = view.recyclerView
        recyclerView.apply {
            layoutManager = GridLayoutManager(context, 7)
            calendarAdapter.setData(listDayOfMonth)
            calendarAdapter.getDayOfWeek(dayOfWeek, dayOfNextMonth)
            adapter = calendarAdapter
        }
        val days = resources.getStringArray(R.array.Days)
        val spinner: Spinner = view.findViewById(R.id.spinner)
        val adapter = context?.let {
            ArrayAdapter(
                it,
                android.R.layout.simple_spinner_item, days
            )
        }
        spinner.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (days[position]) {
//                    "MON" -> {
//                        switchTitle(0)
//                        calendarAdapter.setData(genListDay(0))
//                        calendarAdapter.getDayOfWeek(dayOfWeek, dayOfNextMonth)
//                    }
                    "TUE" -> {
                        switchTitle(1)
                        calendarAdapter.setData(genListDay(1))
                        calendarAdapter.getDayOfWeek(dayOfWeek - 1, dayOfNextMonth)
                    }
                    "WED" -> {
                        switchTitle(2)
                        calendarAdapter.setData(genListDay(2))
                        calendarAdapter.getDayOfWeek(dayOfWeek - 2, dayOfNextMonth)
                    }
                    "THU" -> {
                        switchTitle(3)
                        calendarAdapter.setData(genListDay(3))
                        calendarAdapter.getDayOfWeek(dayOfWeek - 2, dayOfNextMonth)
                    }

                    "FRI" -> {
                        switchTitle(4)
                        calendarAdapter.setData(genListDay(4))
                        calendarAdapter.getDayOfWeek(dayOfWeek - 2, dayOfNextMonth)
                    }
                    "SAT" -> {
                        switchTitle(5)
                        calendarAdapter.setData(genListDay(5))
                        calendarAdapter.getDayOfWeek(dayOfWeek - 2, dayOfNextMonth)
                    }
                    "SUN" -> {
                        switchTitle(6)
                        calendarAdapter.setData(genListDay(6))
                        calendarAdapter.getDayOfWeek(dayOfWeek - 2, dayOfNextMonth)
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }
        return view
    }

    private fun switchTitle(i: Int) {
        when (i) {
            0 -> listTitle = arrayListOf(0, 1, 2, 3, 4, 5, 6)
            1 -> listTitle = arrayListOf(1, 2, 3, 4, 5, 6, 0)
            2 -> listTitle = arrayListOf(2, 3, 4, 5, 6, 0, 1)
            3 -> listTitle = arrayListOf(3, 4, 5, 6, 0, 1, 2)
            4 -> listTitle = arrayListOf(4, 5, 6, 0, 1, 2, 3)
            5 -> listTitle = arrayListOf(5, 6, 0, 1, 2, 3, 4)
            6 -> listTitle = arrayListOf(6, 0, 1, 2, 3, 4, 5)
        }
        dayTitleAdapter.setData(listTitle)

    }

    private fun genListDayOfMonth(position: Long): ArrayList<DateModel> {
        val listDayOfMonth: ArrayList<DateModel> = arrayListOf()
        selectedDate = selectedDate.minusMonths(100)
        selectedDate = selectedDate.plusMonths(position)
        //get month
        monthOfYear = monthYearFromDate(selectedDate)
        val firstOfMonth = selectedDate.withDayOfMonth(1)
        dayOfWeek = firstOfMonth.dayOfWeek.value
        val currentMonth = daysInMonthArray(selectedDate)
        val previousMonth = daysInMonthArray(selectedDate.minusMonths(1))
        for (i in 1 until dayOfWeek) {
            listDayOfMonth.add(
                0,
                DateModel(
                    previousMonth[previousMonth.size - i].toInt(),
                    selectedDate.minusMonths(1).monthValue,
                    selectedDate.minusMonths(1).year
                )
            )
        }
        for (i in currentMonth) {
            listDayOfMonth.add(DateModel(i.toInt(), selectedDate.monthValue, selectedDate.year))
        }
        dayOfNextMonth = 42 - listDayOfMonth.size
        for (i in 1..42 - listDayOfMonth.size) {
            listDayOfMonth.add(
                DateModel(
                    i,
                    selectedDate.plusMonths(1).monthValue,
                    selectedDate.plusMonths(1).year
                )
            )
        }

        return listDayOfMonth
    }

    private fun monthYearFromDate(date: LocalDate): String {
        val formatter = DateTimeFormatter.ofPattern("MMMM yyyy")
        return date.format(formatter)
    }

    private fun daysInMonthArray(date: LocalDate): ArrayList<String> {
        val daysInMonthArray = ArrayList<String>()
        val yearMonth = YearMonth.from(date)
        val daysInMonth = yearMonth.lengthOfMonth()
        val firstOfMonth: LocalDate = selectedDate.withDayOfMonth(1)
        val dayOfWeek = firstOfMonth.dayOfWeek.value
        for (i in 1..42) {
            if (i <= dayOfWeek || i > daysInMonth + dayOfWeek) {
                //
            } else {
                daysInMonthArray.add((i - dayOfWeek).toString())
            }
        }
        return daysInMonthArray
    }

    private fun genListDay(data: Int): ArrayList<DateModel> {
        selectedDate = selectedDate.minusMonths(100)
        val listDayNew: ArrayList<DateModel> = arrayListOf()
        val currentMonth = daysInMonthArray(selectedDate)
        val previousMonth = daysInMonthArray(selectedDate.minusMonths(1))
        for (i in 1 until dayOfWeek - data) {
            listDayNew.add(
                0,
                DateModel(
                    previousMonth[previousMonth.size - i].toInt(),
                    selectedDate.minusMonths(1).monthValue,
                    selectedDate.minusMonths(1).year
                )
            )
        }
        for (i in currentMonth) {
            listDayNew.add(DateModel(i.toInt(), selectedDate.monthValue, selectedDate.year))
        }
        return listDayNew
    }


}