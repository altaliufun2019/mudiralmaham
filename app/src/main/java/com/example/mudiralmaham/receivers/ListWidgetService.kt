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

class ListWidgetService: RemoteViewsService() {
    override fun onGetViewFactory(intent: Intent?): RemoteViewsFactory {
        val master: DaoMaster.DevOpenHelper = DaoMaster.DevOpenHelper(applicationContext, "makhzan")
        val daoSession: DaoSession = DaoMaster(master.writableDb).newSession()
        val tasks = Database.getTasks(daoSession)
        val listWidgetFactory = ListWidgetFactory(this.applicationContext, intent!!, tasks)
        return listWidgetFactory
    }
}

class ListWidgetFactory(private val context: Context, intent: Intent, val tasks: List<Task>): RemoteViewsService.RemoteViewsFactory {

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun onDataSetChanged() {

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
        }
    }

}