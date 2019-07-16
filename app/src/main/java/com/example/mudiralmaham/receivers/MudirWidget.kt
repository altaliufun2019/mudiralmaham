package com.example.mudiralmaham.receivers

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.RemoteViews
import com.example.mudiralmaham.AuthActivity
import com.example.mudiralmaham.R
import com.example.mudiralmaham.dataModels.DaoMaster
import com.example.mudiralmaham.dataModels.DaoSession
import com.example.mudiralmaham.dataModels.Task
import com.example.mudiralmaham.pages.TaskFragment
import com.example.mudiralmaham.utils.Database

class MudirWidget: AppWidgetProvider(), TaskFragment.OnListFragmentInteractionListener{
    override fun onListFragmentInteraction(item: Task?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    var daoSession: DaoSession? = null
    var tasks: MutableList<Task> = mutableListOf()
    val root_view = this

    override fun onUpdate(context: Context?, appWidgetManager: AppWidgetManager?, appWidgetIds: IntArray?) {


        appWidgetIds?.forEach { appWidgetId ->
            // Create an Intent to launch ExampleActivity
            val pendingIntent: PendingIntent = Intent(context, AuthActivity::class.java)
                .let { intent ->
                    PendingIntent.getActivity(context, 0, intent, 0)
                }

            val listIntent = Intent(context, ListWidgetService::class.java).apply {
                // Add the app widget ID to the intent extras.
                putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
                data = Uri.parse(toUri(Intent.URI_INTENT_SCHEME))
            }


            // Get the layout for the App Widget and attach an on-click listener
            // to the button
            val views: RemoteViews = RemoteViews(
                context?.packageName,
                R.layout.widget
            ).apply {
                setOnClickPendingIntent(R.id.widget_holder, pendingIntent)
                setRemoteAdapter(R.id.widget_list, listIntent)
            }
            // Tell the AppWidgetManager to perform an update on the current app widget
            appWidgetManager?.updateAppWidget(appWidgetId, views)
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds)
    }


    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
    }

    override fun onEnabled(context: Context?) {
        super.onEnabled(context)
    }

    override fun onAppWidgetOptionsChanged(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetId: Int,
        newOptions: Bundle?
    ) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions)
    }


}