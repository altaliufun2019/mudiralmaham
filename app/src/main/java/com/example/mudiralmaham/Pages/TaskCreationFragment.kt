package com.example.mudiralmaham.Pages

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.widget.CardView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.mudiralmaham.R
import com.example.mudiralmaham.Utils.ContextHolder
import com.jaredrummler.materialspinner.MaterialSpinner
import java.time.Year
import java.util.*


class TaskCreationFragment: Fragment(), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private var _task_name_input: EditText? = null
    private var _comment_input: EditText? = null
    private var _due_date: CardView? = null
    private var _date_text: TextView? = null
    private var _due_time: CardView? = null
    private var _time_text: TextView? = null
    private var _parent_spinner: MaterialSpinner? = null
    private var _root_view: View? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _root_view = inflater.inflate(R.layout.task_creation_fragment, container, false)
        _task_name_input = _root_view?.findViewById(R.id.task_name)
        _comment_input = _root_view?.findViewById(R.id.input_comment)
        _due_date = _root_view?.findViewById(R.id.date_picker)
        _date_text = _root_view?.findViewById(R.id.date_text)
        _due_time = _root_view?.findViewById(R.id.time_picker)
        _time_text = _root_view?.findViewById(R.id.time_text)
        _parent_spinner = _root_view?.findViewById(R.id.parent_spinner)
        return _root_view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // initialize spinner for parent
        val projectNames = mutableListOf<String>()
        for (project in ContextHolder.projects){
            projectNames.add(project.name)
        }
        _parent_spinner?.setItems(projectNames)
        _parent_spinner?.setOnItemSelectedListener(object: MaterialSpinner.OnItemSelectedListener<String> {
            override fun onItemSelected(view: MaterialSpinner?, position: Int, id: Long, item: String?) {
                Snackbar.make(_root_view!!, item!!, Snackbar.LENGTH_SHORT).show()
            }
        }
        )
        // initialize pickers
        _due_date?.setOnClickListener{
            val now = Calendar.getInstance()
            val date_picker = DatePickerDialog(
                context!!,
                this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
            )
            date_picker.show()
        }
        _due_time?.setOnClickListener{
            val now = Calendar.getInstance()
            val time_picker = TimePickerDialog(
                context!!,
                this,
                now.get(Calendar.HOUR),
                now.get(Calendar.MINUTE),
                true
            )
            time_picker.show()
        }
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        Snackbar.make(_root_view!!, "time set to $hourOfDay:$minute", Snackbar.LENGTH_SHORT).show()
        _time_text?.text = "$hourOfDay:$minute"
    }

    override fun onDateSet(view: DatePicker?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        Snackbar.make(_root_view!!, "date set to $year/$monthOfYear/$dayOfMonth", Snackbar.LENGTH_SHORT).show()
        _date_text?.text = "$year/$monthOfYear/$dayOfMonth"
    }
}