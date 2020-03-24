package com.example.wheatherapp.Fragments

import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.util.rangeTo
import com.example.wheatherapp.Forecast

import com.example.wheatherapp.R
import com.google.gson.Gson
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt


class WeatherFragment : Fragment() {
companion object{
    fun newInsatnce():WeatherFragment{
        return WeatherFragment()
    }
}

    override fun onCreate(savedInstanceState: Bundle?) {
        if (arguments?.getString("city")!=null) {
            val city = arguments?.getString("city")
            WeatherTask(city.toString()).execute()
            //Thread.sleep(2000)
            //Log.d("TAG",city)
        }
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_weather, container, false)

        return view
    }

   inner class WeatherTask (val city:String): AsyncTask<String, Void, String>() {
        val API_KEY:String="413c12c4375379410d50896552c776de"
        override fun onPreExecute() {
            super.onPreExecute()

        }

        override fun doInBackground(vararg params: String?): String? {
            var response: String?
            try {
                response =
                    URL("https://api.openweathermap.org/data/2.5/weather?q=$city&units=metric&appid=$API_KEY").readText(
                        Charsets.UTF_8
                    )
            } catch (e: java.lang.Exception) {
                response = null
            }
            return response
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            try {
                view?.findViewById<RelativeLayout>(R.id.mainContainer)?.visibility = View.GONE
                val forecast = Gson().fromJson<Forecast>(result, Forecast::class.java)
                Log.d("tag",forecast.toString())
                // city,country
                val country=forecast.sys?.country
                val city=forecast.name
                val adress=city+", "+country
                view?.findViewById<TextView>(R.id.address)?.text=adress
                //time
                val time="Updated at: "+ SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(Date(forecast.dt.times(1000)))
                view?.findViewById<TextView>(R.id.time)?.text=time
                //temp
                val temp=forecast.main?.temp?.roundToInt().toString()+"°C"
                view?.findViewById<TextView>(R.id.temp)?.text=temp
                //short description wheather
                val description=forecast.weather[0].description
                view?.findViewById<TextView>(R.id.status)?.text=description
                //sunrise
                val sunrise=forecast.sys?.sunrise
                view?.findViewById<TextView>(R.id.sunrise)?.text= SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(sunrise!!.times(1000)))
                // sunset
                val sunset=forecast.sys?.sunset
                view?.findViewById<TextView>(R.id.sunset)?.text= SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(sunset!!.times(1000)))
                // pressure
                val pressure=forecast.main?.pressure.toString()
                view?.findViewById<TextView>(R.id.pressure)?.text=pressure

                //picture: clouds,clear sky,rain
                val descriptionWeather=forecast.weather[0].icon

                when(descriptionWeather){
                    //day icon
                    "01d"->view?.findViewById<ImageView>(R.id.weather_icon)?.setImageResource(R.drawable.ww01d)
                    "02d"->view?.findViewById<ImageView>(R.id.weather_icon)?.setImageResource(R.drawable.w02d)
                    "03d"->view?.findViewById<ImageView>(R.id.weather_icon)?.setImageResource(R.drawable.w03d)
                    "04d"->view?.findViewById<ImageView>(R.id.weather_icon)?.setImageResource(R.drawable.w04d)
                    "09d"->view?.findViewById<ImageView>(R.id.weather_icon)?.setImageResource(R.drawable.w09d)
                    "10d"->view?.findViewById<ImageView>(R.id.weather_icon)?.setImageResource(R.drawable.w10d)
                    "11d"->view?.findViewById<ImageView>(R.id.weather_icon)?.setImageResource(R.drawable.w11d)
                    "13d"->view?.findViewById<ImageView>(R.id.weather_icon)?.setImageResource(R.drawable.w13d)
                    "50d"->view?.findViewById<ImageView>(R.id.weather_icon)?.setImageResource(R.drawable.w50d)
                    //night icon
                    "01n"->view?.findViewById<ImageView>(R.id.weather_icon)?.setImageResource(R.drawable.w01n)
                    "02n"->view?.findViewById<ImageView>(R.id.weather_icon)?.setImageResource(R.drawable.w02n)
                    "03n"->view?.findViewById<ImageView>(R.id.weather_icon)?.setImageResource(R.drawable.w03n)
                    "04n"->view?.findViewById<ImageView>(R.id.weather_icon)?.setImageResource(R.drawable.w04n)
                    "09n"->view?.findViewById<ImageView>(R.id.weather_icon)?.setImageResource(R.drawable.w09n)
                    "10n"->view?.findViewById<ImageView>(R.id.weather_icon)?.setImageResource(R.drawable.w10n)
                    "11n"->view?.findViewById<ImageView>(R.id.weather_icon)?.setImageResource(R.drawable.w11n)
                    "13n"->view?.findViewById<ImageView>(R.id.weather_icon)?.setImageResource(R.drawable.w13n)
                    "50n"->view?.findViewById<ImageView>(R.id.weather_icon)?.setImageResource(R.drawable.w50n)
                    else->view?.findViewById<LinearLayout>(R.id.imageContainer)?.visibility =View.GONE
                }

                view?.findViewById<RelativeLayout>(R.id.mainContainer)?.visibility = View.VISIBLE


            } catch (e: java.lang.Exception) {

                view?.findViewById<TextView>(R.id.errorText)?.visibility = View.VISIBLE
            }
        }
    }

}
