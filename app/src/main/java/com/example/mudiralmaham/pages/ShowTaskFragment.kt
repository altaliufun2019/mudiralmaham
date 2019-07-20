package com.example.mudiralmaham.pages

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.mudiralmaham.R
import com.example.mudiralmaham.dataModels.Task
import java.text.SimpleDateFormat

class ShowTaskFragment : DialogFragment() {
    var task: Task? = null
    private var taskName: TextView? = null
    private var taskComment: TextView? = null
    private var taskDueDate: TextView? = null
    private var taskDueTime: TextView? = null
    private var taskProject: TextView? = null
    private var _rootView: View? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _rootView = inflater.inflate(R.layout.task_show_layout, container, false)
        taskName = _rootView?.findViewById(R.id.task_name)
        taskComment = _rootView?.findViewById(R.id.task_comment)
        taskDueDate = _rootView?.findViewById(R.id.task_due_date)
        taskDueTime = _rootView?.findViewById(R.id.task_due_time)
        taskProject = _rootView?.findViewById(R.id.task_project)
        return _rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dateFormatter = SimpleDateFormat("yyyy/MM/dd")
        val timeFormatter = SimpleDateFormat("HH:mm")
        taskName?.text = task?.name; taskComment?.text = task?.comment; taskDueDate?.text =
            dateFormatter.format(task?.due_date); taskDueTime?.text =
            timeFormatter.format(task?.due_date); taskProject?.text = task?.project
    }


}