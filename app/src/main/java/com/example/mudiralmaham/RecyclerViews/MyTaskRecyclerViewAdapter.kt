package com.example.mudiralmaham.RecyclerViews

import android.graphics.Paint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import com.example.mudiralmaham.R
import com.example.mudiralmaham.dataModels.Task


import com.example.mudiralmaham.pages.TaskFragment.OnListFragmentInteractionListener
import com.example.mudiralmaham.utils.ContextHolder
import com.example.mudiralmaham.utils.Database

import kotlinx.android.synthetic.main.fragment_task.view.*
import java.util.*

/**
 * [RecyclerView.Adapter] that can display DueTimeReceiver [Task] and makes DueTimeReceiver call to the
 * specified [OnListFragmentInteractionListener].
 * TODO: Replace the implementation with code for your data type.
 */
class MyTaskRecyclerViewAdapter(
    private val mValues: List<Task>,
    private val mListener: OnListFragmentInteractionListener?
) : RecyclerView.Adapter<MyTaskRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as Task
            // Notify the active callbacks interface (the activity, if the fragment is attached to
            // one) that an item has been selected.
            mListener?.onListFragmentInteraction(item)
        }
        sortTasks()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_task, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        holder.mTitleView.text = item.name
//        holder.mContentView.text = item.content
        if (item.isOver)
            holder.mTitleView.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG

        holder.mView.findViewById<CheckBox>(R.id.task_list_done_btn).setOnClickListener {
            mValues[position].isDone = true
            ContextHolder.updateTask(item)
            ContextHolder.getCacheData()
        }

        with(holder.mView) {
            tag = item
            this.findViewById<CheckBox>(R.id.task_list_done_btn).isChecked = item.isDone
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = mValues.size

    fun sortTasks() {
        Collections.sort(mValues) { t1: Task, t2: Task ->

            var p1 = 0
            var p2 = 0
            if (t1.isDone)
                p1 += 3
            if (t1.isOver)
                p1 += 1
            if (t2.isDone)
                p2 += 3
            if (t2.isOver)
                p2 += 1
            if (p1 == 4)
                p1 -= 2
            if (p2 == 4)
                p2 -= 2
            p2 - p1

        }
    }


    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mTitleView: TextView = mView.task_list_title

//        val mContentView: TextView = mView.content
/*
        override fun toString(): String {
            return super.toString() + " '" + mContentView.text + "'"
        }*/
    }
}
