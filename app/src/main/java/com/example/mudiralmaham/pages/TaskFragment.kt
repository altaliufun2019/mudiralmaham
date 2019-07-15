package com.example.mudiralmaham.pages

import android.content.Context
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import com.example.mudiralmaham.R
import com.example.mudiralmaham.pages.dummy.DummyContent
import com.example.mudiralmaham.pages.dummy.DummyContent.DummyItem
import com.example.mudiralmaham.utils.MyBounceInterpolator
import com.example.mudiralmaham.utils.OnBackPressed


/**
 * A fragment representing a list of Items.
 * Activities containing this fragment MUST implement the
 * [TaskFragment.OnListFragmentInteractionListener] interface.
 */
class TaskFragment : Fragment(), OnBackPressed {

    override fun onBackPressed() {
    }

    // TODO: Customize parameters
    private var columnCount = 1

    private var listener: OnListFragmentInteractionListener? = null

    private var doneButton: CheckBox? = null
    private var title: TextView? = null
    private var importantButton: CheckBox? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        doneButton = view?.findViewById(R.id.task_list_done_btn)
        importantButton = view?.findViewById(R.id.task_list_important_btn)
        title = view?.findViewById(R.id.task_list_title)

        /*doneButton?.setOnClickListener {
            doneButton?.startAnimation(AnimationUtils.loadAnimation(context, R.anim.scale_up))
        }
        */arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_task_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = MyTaskRecyclerViewAdapter(DummyContent.ITEMS, listener)
            }
        }
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnListFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnListFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson
     * [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onListFragmentInteraction(item: DummyItem?)
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            TaskFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
/*
    fun didTapButton(view: View) {
        val button = getView()?.findViewById(R.id.task_list_done_btn) as ImageButton
        val myAnim = AnimationUtils.loadAnimation(context, R.anim.scale_up)

        // Use bounce interpolator with amplitude 0.2 and frequency 20
        val interpolator = MyBounceInterpolator(0.2, 20.0)
        myAnim.interpolator = interpolator

        button.startAnimation(myAnim)
    }*/

}
