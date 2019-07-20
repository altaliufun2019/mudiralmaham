package com.example.mudiralmaham.pages

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.annotation.RequiresApi
import com.example.mudiralmaham.events.CreateTaskEvent
import com.example.mudiralmaham.R
import com.example.mudiralmaham.events.CreateProjectEvent
import com.example.mudiralmaham.utils.ContextHolder
import com.example.mudiralmaham.utils.OnBackPressed
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class ProjectCreationFragment : Fragment(), OnBackPressed{
    private var _project_name_input: EditText? = null
    private var _description_input: EditText? = null
    private var _collaborators_holder: TextView? = null
    private var _root_view: View? = null
    private var _back_btn: ImageView? = null
    private var _save_btn: ImageView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _root_view = inflater.inflate(R.layout.project_creation_fragment, container, false)

        _back_btn = _root_view?.findViewById(R.id.project_creation_back_btn)
        _save_btn = _root_view?.findViewById(R.id.project_creation_save_btn)

        _project_name_input = _root_view?.findViewById(R.id.project_name)
        _description_input = _root_view?.findViewById(R.id.input_description)
        _collaborators_holder = _root_view?.findViewById(R.id.collaborators_holder)

        return _root_view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
        fab?.animation = AnimationUtils.loadAnimation(activity?.applicationContext, R.anim.fab_show)
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

    override fun onDestroy() {
        super.onDestroy()
    }

//    override fun action() {
//        onBackPressed()
//    }

    private fun initButtons() {
        _back_btn?.setOnClickListener {
            onBackPressed()
        }

        _save_btn?.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                createNewProject()
            }
        }
    }

    override fun onBackPressed() {
        fragmentManager?.popBackStack()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNewProject() {
        if (_project_name_input?.text.toString() == "") {
            Snackbar.make(_root_view!!, "Enter name to continue", Snackbar.LENGTH_SHORT).show()
            return
        }
        EventBus.getDefault().post(
             CreateProjectEvent(_project_name_input?.text.toString(), _description_input?.text.toString(), "", activity?.applicationContext!!)
        )
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun taskCreated(createTaskEvent: CreateTaskEvent) {
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun projectCreated(createProjectEvent: CreateProjectEvent) {
        ContextHolder.getCacheData()
        if (createProjectEvent.result == 0)
            Snackbar.make(_root_view!!, "project added successfully", Snackbar.LENGTH_SHORT).show()
        else {
            when (createProjectEvent.result) {
                1 -> Snackbar.make(_root_view!!, "There is already project with this name", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

}