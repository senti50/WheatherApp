package com.example.wheatherapp.Fragments

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.wheatherapp.R
import kotlinx.android.synthetic.main.fragment_search.*


class SearchFragment : Fragment() {
    companion object{
        fun newInstance():SearchFragment{
            return SearchFragment()
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_search, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (context!!.isConnectedToNetwork()) {
            // button
            var button = getView()?.findViewById<Button>(R.id.buttonSearch)

            button?.setOnClickListener {
                try {
                    val city: String = editTextCity.text.toString().removeSuffix(" ")
                    val bundle = Bundle()
                    bundle.putString("city", city)
                    val fragment = WeatherFragment.newInsatnce()
                    fragment.arguments = bundle
                    replaceFragment(fragment)

                } catch (e: Exception) {
                    Toast.makeText(activity, "Something went wrong ", Toast.LENGTH_LONG).show()
                }

            }
        }else{
            Toast.makeText(context,"Chceck network connection",Toast.LENGTH_LONG).show()
        }
}
    fun Context.isConnectedToNetwork(): Boolean {
        val connectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        return connectivityManager?.activeNetworkInfo?.isConnectedOrConnecting() ?: false
    }
    private fun replaceFragment(fragment: Fragment){
            val fragmentMenager = activity?.supportFragmentManager
            val fragmentTransaction = fragmentMenager?.beginTransaction()
            fragmentTransaction?.replace(R.id.fragmentsContainer, fragment)
            fragmentTransaction?.addToBackStack(null)
            fragmentTransaction?.commit()

    }
}


