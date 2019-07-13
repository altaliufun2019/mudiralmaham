package com.example.mudiralmaham.pages

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.CardView
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.annotation.RequiresApi
import com.example.mudiralmaham.events.CreateTaskEvent
import com.example.mudiralmaham.R
import com.example.mudiralmaham.utils.ContextHolder
import com.example.mudiralmaham.utils.OnBackPressed
import com.jaredrummler.materialspinner.MaterialSpinner
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.*


class TaskCreationFragment: Fragment(), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener, OnBackPressed {

    private var _task_name_input: EditText? = null
    private var _comment_input: EditText? = null
    private var _due_date: CardView? = null
    private var _date_text: TextView? = null
    private var _due_time: CardView? = null
    private var _time_text: TextView? = null
    private var _parent_spinner: MaterialSpinner? = null
    private var _root_view: View? = null
    private var _back_btn: ImageView? = null
    private var _save_btn: ImageView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _root_view = inflater.inflate(R.layout.task_creation_fragment, container, false)

        _back_btn = _root_view?.findViewById(R.id.back_btn)
        _save_btn = _root_view?.findViewById(R.id.save_btn)

        _task_name_input = _root_view?.findViewById(R.id.task_name)
        _comment_input = _root_view?.findViewById(R.id.input_comment)
        _parent_spinner = _root_view?.findViewById(R.id.parent_spinner)
        _due_date = _root_view?.findViewById(R.id.date_picker)
        _due_time = _root_view?.findViewById(R.id.time_picker)

        _date_text = _root_view?.findViewById(R.id.date_text)
        _time_text = _root_view?.findViewById(R.id.time_text)

        return _root_view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSpinner()
        initPickers()
        initButtons()

    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        (activity as AppCompatActivity).supportActionBar?.hide()
        val fab: FloatingActionButton? = activity?.findViewById(R.id.fab)
        fab?.animation = AnimationUtils.loadAnimation(activity?.applicationContext, R.anim.fab_hide)
        fab?.hide()
    }

    override fun onDetach() {
        super.onDetach()
        (activity as AppCompatActivity).supportActionBar?.show()
        val fab: FloatingActionButton? = activity?.findViewById(R.id.fab)
        fab?.animation = AnimationUtils.loadAnimation(activity?.applicationContext, R.anim.fab_hide)
        fab?.show()
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onPause() {
        super.onPause()
        EventBus.getDefault().unregister(this)
    }

//    override fun action() {
//        onBackPressed()
//    }

    private fun initSpinner() {
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
        })
    }

    private fun initPickers() {
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

    private fun initButtons() {
        _back_btn?.setOnClickListener {
            onBackPressed()
        }

        _save_btn?.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                createNewTask()
            }
        }
    }

    override fun onBackPressed() {
        fragmentManager?.popBackStack()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNewTask() {
        if ( !(_date_text?.text?.contains('/')!!) || !(_time_text?.text?.contains(':')!!)){
            Snackbar.make(_root_view!!, "must choose a date and time", Snackbar.LENGTH_SHORT).show()
                return
        }
        EventBus.getDefault().post(
            CreateTaskEvent(
                _task_name_input?.text.toString(),
                _comment_input?.text.toString(),
                _date_text?.text?.split('/')?.get(0)?.toInt()!!,
                _date_text?.text?.split('/')?.get(1)?.toInt()!!,
                _date_text?.text?.split('/')?.get(2)?.toInt()!!,
                _time_text?.text?.split(':')?.get(0)?.toInt()!!,
                _time_text?.text?.split(':')?.get(1)?.toInt()!!
            ))
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun taskCreated(createTaskEvent: CreateTaskEvent) {
        if(createTaskEvent.result)
            Snackbar.make(_root_view!!, "task added successfully", Snackbar.LENGTH_SHORT).show()
        else {
            when(createTaskEvent.reason) {
                0 -> Snackbar.make(_root_view!!, "there is already a task with this name", Snackbar.LENGTH_SHORT).show()
                1 -> Snackbar.make(_root_view!!, "due date is already over", Snackbar.LENGTH_SHORT).show()
            }
        }
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