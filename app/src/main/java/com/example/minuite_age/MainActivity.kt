package com.example.minuite_age

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private var tvSelectedDate : TextView ?= null
    private var dates : TextView ?= null
    private var hours : TextView ?= null
    private var minutes : TextView ?= null
    private var seconds : TextView ?= null
    private var notion : TextView ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnDatePicker : Button = findViewById(R.id.btnDatePicker)
        tvSelectedDate = findViewById(R.id.tvSelectedDate)
        dates = findViewById(R.id.dates)
        hours = findViewById(R.id.hours)
        minutes = findViewById(R.id.minutes)
        seconds = findViewById(R.id.seconds)
        notion = findViewById(R.id.notionfuture)
        btnDatePicker.setOnClickListener{
            clickDatePicker()

        }
    }

    private fun clickDatePicker() {
        val myCalendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)
        val dpd =   DatePickerDialog(this,{
                _,selectedYear,selectedMonth,selectedDayOfMonth ->

            //Toast.makeText(this, "Year was $selectedyear, month is ${selectedmonth+1} day of month was $selecteddayOfMonth", Toast.LENGTH_LONG).show()

            val selectedDate = "${selectedYear}년 ${selectedMonth+1}월 ${selectedDayOfMonth}일"

            tvSelectedDate?.text = (selectedDate)

            //데이터 포멧(형식 잡아줌)
            val sdf = SimpleDateFormat("yyyy년 MM월 dd일", Locale.KOREA)
            val theDate = sdf.parse(selectedDate)

            theDate?.let{

                val selectedDateInDay = theDate.time/ 86400000
                val selectedDateInHour = theDate.time/ 3600000
                //사용자 나이를 분으로 나눔
                val selectedDateInMinutes = theDate.time /60000
                val selectedDateInSecond = theDate.time/ 1000

                //시스템 현재 시간을 밀리초 단위로 불러와서 분으로 나눔
                val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))
                currentDate?.let{

                    val currentDateInDay = currentDate.time/ 86400000
                    val currentDateInHour = currentDate.time/ 3600000
                    val currentDateInMinutes = currentDate.time/60000
                    val currentDateInSecond = currentDate.time/ 1000
                    //시스템 시간에서 나이를 빼면 살아온 날이 분단위로 나옴.
                    val differenceInDay = currentDateInDay - selectedDateInDay
                    val differenceInHour = currentDateInHour - selectedDateInHour
                    val differenceInMinutes = currentDateInMinutes - selectedDateInMinutes
                    val differenceInSecond = currentDateInSecond - selectedDateInSecond

                    if(currentDateInMinutes >= selectedDateInMinutes) {
                        dates?.text = differenceInDay.toString()
                        hours?.text = differenceInHour.toString()
                        minutes?.text = differenceInMinutes.toString()
                        seconds?.text = differenceInSecond.toString()
                        notion?.visibility = View.GONE
                    }else{
                        dates?.text = "계산할 수 없어요"
                        hours?.text = "????"
                        minutes?.text = "????"
                        seconds?.text = "????"
                        notion?.visibility = View.VISIBLE
                    }
                }
            }


        }, year,month, day)

        //달력에 미래 날짜 아예 입력 못하게 하려면...이렇게 하자
        //dpd.datePicker.maxDate = System.currentTimeMillis() - 86400000
        dpd.show()
    }
}