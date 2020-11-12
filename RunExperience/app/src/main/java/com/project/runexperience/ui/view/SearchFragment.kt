package com.project.runexperience.ui.view

import android.graphics.drawable.Animatable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.project.runexperience.R


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SearchFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var root = inflater.inflate(R.layout.fragment_search, container, false)
        var rv = root.findViewById(R.id.recyclerView) as LinearLayout
        var example = inflater.inflate(R.layout.layout_example, container, false) as View
        rv.addView(example)

        var container2 = example.findViewById(R.id.container) as ViewGroup
        container2.setOnClickListener(View.OnClickListener {
            for (i in 0 until container2.childCount) {
                animateDrawables(container2.getChildAt(i))
            }
        })
        for (i in 0 until container2.childCount) {
            val child = container2.getChildAt(i)
            (child as? TextView)?.setOnClickListener {
                for (i in 0 until container2.childCount) {
                    animateDrawables(container2.getChildAt(i))
                }
            }
        }

        // Inflate the layout for this fragment
        return root
    }
    private fun animateDrawables(view: View) {
        if (view !is TextView) {
            return
        }
        for (drawable in view.compoundDrawables) {
            if (drawable is Animatable) {
                (drawable as Animatable).start()
            }
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SearchFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String = null.toString(), param2: String = null.toString()) =
            SearchFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

}