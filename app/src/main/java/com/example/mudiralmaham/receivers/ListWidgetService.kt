package com.example.mudiralmaham.receivers

import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.example.mudiralmaham.R
import com.example.mudiralmaham.dataModels.DaoMaster
import com.example.mudiralmaham.dataModels.DaoSession
import com.example.mudiralmaham.dataModels.Task
import com.example.mudiralmaham.dataModels.WidgetItem
import com.example.mudiralmaham.utils.Database
import java.text.SimpleDateFormat

class ListWidgetService: RemoteViewsService() {
    override fun onGetViewFactory(intent: Intent?): RemoteViewsFactory {
        val master: DaoMaster.DevOpenHelper = DaoMaster.DevOpenHelper(applicationContext, "makhzan")
        ListWidgetFactory.daoSession = DaoMaster(master.writableDb).newSession()
        val tasks = Database.getTasks(ListWidgetFactory.daoSession!!)
        val listWidgetFactory = ListWidgetFactory(this.applicationContext, intent!!, tasks)
        return listWidgetFactory
    }
}

class ListWidgetFactory(private val context: Context, intent: Intent, var tasks: List<Task>): RemoteViewsService.RemoteViewsFactory {
    companion object{
        var daoSession: DaoSession? = null
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun onDataSetChanged() {
        tasks = daoSession?.let { Database.getTasks(it) } as List<Task>
    }

    override fun hasStableIds(): Boolean {
        return true
    }

    override fun getCount(): Int {
        return tasks.size
    }

    override fun getViewTypeCount(): Int {
        return 1
    }

    override fun onDestroy() {
    }

    override fun getLoadingView(): RemoteViews? {
        return null
    }

    override fun onCreate() {
    }

    override fun getViewAt(position: Int): RemoteViews {
        return RemoteViews(context.packageName, R.layout.widget_item).apply {
            setTextViewText(R.id.widget_task_name, tasks[position].name)
            setTextViewText(R.id.widget_task_comment, tasks[position].comment)
            setTextViewText(R.id.widget_due_date, SimpleDateFormat("yyyy/MM/dd").format(tasks[position].due_date))
        }
    }

}