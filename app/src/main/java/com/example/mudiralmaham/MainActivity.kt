package com.example.mudiralmaham

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.design.widget.FloatingActionButton
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.MenuItem
import android.support.v4.widget.DrawerLayout
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.SubMenu
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.mudiralmaham.dataModels.Task
import com.example.mudiralmaham.events.CreateProjectEvent
import com.example.mudiralmaham.events.CreateTaskEvent
import com.example.mudiralmaham.pages.ProjectCreationFragment
import com.example.mudiralmaham.pages.TaskCreationFragment
import com.example.mudiralmaham.pages.TaskFragment
import com.example.mudiralmaham.utils.ContextHolder
import com.example.mudiralmaham.utils.OnBackPressed
import com.example.mudiralmaham.webservice.request.AddProjectRequest
import com.example.mudiralmaham.webservice.request.AddTaskRequest
import com.example.mudiralmaham.webservice.response.AddResponse
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
    TaskFragment.OnListFragmentInteractionListener {
    companion object {
        var lastFragment: Fragment? = null
        var currentFragment: Fragment? = null
    }

    private var navSubMenu: SubMenu? = null

    override fun onListFragmentInteraction(item: Task?) {
//        TODO(show task page)
        return
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        EventBus.getDefault().register(this)
        ContextHolder.networkMonitor?.enable(this)

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            lastFragment = currentFragment
            currentFragment = TaskCreationFragment()
            currentFragment?.let {
                showPage(it)
            }
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener(this)

        val user_name: TextView? = findViewById(R.id.user_nav_name)
        val user_email: TextView? = findViewById(R.id.user_nav_email)
        user_name?.text = ContextHolder.user?.name; user_email?.text = ContextHolder.user?.email

//        to init lateinit vars in ContextHolder
        ContextHolder.getCacheData()

        addMenuItemInNavMenuDrawer()
        setNavHeaderInfo()


        if (currentFragment == null) {
            currentFragment = TaskFragment()
            (currentFragment as TaskFragment).projectName = "TODAY"
        }
        currentFragment?.let {
            showPage(it)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        ContextHolder.networkMonitor?.disable()
        EventBus.getDefault().unregister(this)
    }

    override fun onBackPressed() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            val fragments: List<Fragment> = supportFragmentManager.fragments
            for (fragment in fragments) {
                (fragment as OnBackPressed).onBackPressed()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify DueTimeReceiver parent activity in AndroidManifest.mudirwidget.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_add_project -> {
                lastFragment = currentFragment
                currentFragment = ProjectCreationFragment()
                showPage(currentFragment!!)
            }
            else -> {
                if (item.numericShortcut.toString().toInt() > 2) {
                    currentFragment = TaskFragment()
                    (currentFragment as TaskFragment).projectName = item.title.toString()
                    currentFragment?.let {
                        showPage(it)
                    }
                }
            }
        }
        val drawerLayout
                : DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    fun showPage(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
//        fragmentTransaction.replace(R.id.fragment_holder, TaskCreationFragment())
        fragmentTransaction.replace(R.id.main_fragment_holder, fragment).addToBackStack("main_page")
        fragmentTransaction.commit()

    }

    private fun addMenuItemInNavMenuDrawer() {
        val navView: NavigationView? = findViewById<NavigationView>(R.id.nav_view)

        val menu = navView?.menu
        navSubMenu = menu?.addSubMenu("Projects")

//        TODO(to be replaced with real projects)
        for (project in ContextHolder.projects) {
            navSubMenu?.add(project.name)?.setIcon(R.drawable.ic_format_list_bulleted_red_24dp)?.numericShortcut = '3'
        }
        navView?.invalidate()
    }

    private fun setNavHeaderInfo() {
        val navigationBarHeader = findViewById<NavigationView>(R.id.nav_view).getHeaderView(0)
        navigationBarHeader.findViewById<TextView>(R.id.user_nav_name).text = ContextHolder.user?.name
        navigationBarHeader.findViewById<TextView>(R.id.user_nav_email).text = ContextHolder.user?.email

    }

    @Subscribe
    fun onTaskCreated(createTaskEvent: CreateTaskEvent) {

    }

    @Subscribe
    fun onProjectCreated(createProjectEvent: CreateProjectEvent) {
        if (createProjectEvent.result == 0) {
            val navView: NavigationView? = findViewById<NavigationView>(R.id.nav_view)
            navSubMenu?.add(createProjectEvent.project.name)?.setIcon(R.drawable.ic_format_list_bulleted_red_24dp)
                ?.numericShortcut = '3'
            navView?.invalidate()
        }
    }


    /**
     * a monitor class for network connectivity
     */
    class ConnectionMonitor(context: Context) :
        ConnectivityManager.NetworkCallback() {

        val projectSyncQueue: MutableList<AddProjectRequest> = mutableListOf()
        val taskSyncQueue: MutableList<AddTaskRequest> = mutableListOf()
        private val networkRequest: NetworkRequest = NetworkRequest.Builder()
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .build()
        private var context: Context? = null
        internal var connection: Boolean? = false
            private set

        init {
            enable(context)
        }

        internal fun enable(context: Context) {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            cm.registerNetworkCallback(networkRequest, this)
            this.context = context
            val network = cm.activeNetworkInfo
            if (network == null || !network.isConnected) {
            } else {
                ContextHolder.isNetworkConnected = true
            }
        }

        fun disable() {
            val cm = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            cm.unregisterNetworkCallback(this)
            this.context = null
        }

        override fun onAvailable(network: Network) {
            ContextHolder.isNetworkConnected = true

            var idx = 0
            while (idx < projectSyncQueue.size) {
                val request: Call<AddResponse> = ContextHolder.webservice.addProject("Bearer ${ContextHolder.user?.token}", projectSyncQueue[idx])
                request.enqueue(object : Callback<AddResponse> {
                    override fun onFailure(call: Call<AddResponse>, t: Throwable) {
                    }

                    override fun onResponse(call: Call<AddResponse>, response: Response<AddResponse>) {
                        projectSyncQueue.removeAt(idx)
                        idx--
                    }
                })
                idx ++
            }
            idx = 0
            while (idx < taskSyncQueue.size) {
                val request: Call<AddResponse> = ContextHolder.webservice.addTask("Bearer ${ContextHolder.user?.token}", taskSyncQueue[idx])
                request.enqueue(object : Callback<AddResponse> {
                    override fun onFailure(call: Call<AddResponse>, t: Throwable) {
                    }

                    override fun onResponse(call: Call<AddResponse>, response: Response<AddResponse>) {
                        taskSyncQueue.removeAt(idx)
                        idx--
                    }
                })
                idx ++
            }
        }

        override fun onLost(network: Network) {
            ContextHolder.isNetworkConnected = false
        }
    }

}
