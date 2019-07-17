package com.example.mudiralmaham.pages

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import com.example.mudiralmaham.R
import com.example.mudiralmaham.RecyclerViews.MyTaskRecyclerViewAdapter
import com.example.mudiralmaham.dataModels.Task
import com.example.mudiralmaham.events.TaskChange
import com.example.mudiralmaham.utils.ContextHolder
import com.example.mudiralmaham.utils.OnBackPressed
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.util.*


/**
 * A fragment representing DueTimeReceiver list of Items.
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
    internal var projectName: String = "TODAY"
        set
        get

    private var tasks: MutableList<Task>? = null
    private var root_view: View? = null


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
        root_view = inflater.inflate(R.layout.fragment_task_list, container, false)
        tasks = ContextHolder.getProjectTasks(projectName)
        var tempList: LinkedList<Task> = LinkedList(listOf())
        for (task in tasks!!){
            if (task.isOver)
                tempList.push(task)
            else
                tempList.add(task)
        }
        tasks = tempList
        tasks?.reverse()
        // Set the adapter
        with(root_view as RecyclerView) {
            layoutManager = when {
                columnCount <= 1 -> LinearLayoutManager(context)
                else -> GridLayoutManager(context, columnCount)
            }
            adapter = MyTaskRecyclerViewAdapter(tasks!!, listener)
        }
        return root_view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        EventBus.getDefault().register(this)
        if (context is OnListFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnListFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        EventBus.getDefault().unregister(this)
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
        fun onListFragmentInteraction(item: Task?)
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

    @Subscribe
    fun taskListChange(taskChange: TaskChange) {
        tasks = ContextHolder.getProjectTasks(projectName)
        val tempList: LinkedList<Task> = LinkedList(listOf())
        for (task in tasks!!){
            if (task.isOver)
                tempList.push(task)
            else
                tempList.add(task)
        }
        tasks = tempList
        tasks?.reverse()
        with(root_view as RecyclerView) {
            layoutManager = when {
                columnCount <= 1 -> LinearLayoutManager(context)
                else -> GridLayoutManager(context, columnCount)
            }
            adapter = MyTaskRecyclerViewAdapter(tasks!!, listener)
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
