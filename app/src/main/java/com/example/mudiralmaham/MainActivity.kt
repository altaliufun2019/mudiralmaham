package com.example.mudiralmaham

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.MenuItem
import android.support.v4.widget.DrawerLayout
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.widget.TextViewCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.widget.TextView
import com.example.mudiralmaham.dataModels.Task
import com.example.mudiralmaham.pages.TaskCreationFragment
import com.example.mudiralmaham.pages.TaskFragment
import com.example.mudiralmaham.utils.ContextHolder
import com.example.mudiralmaham.utils.OnBackPressed

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
    TaskFragment.OnListFragmentInteractionListener {
    companion object{
        var currentFragment: Fragment? = null
    }

    override fun onListFragmentInteraction(item: Task?) {
        return
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            currentFragment = TaskCreationFragment()
            currentFragment?.let{
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


        if (currentFragment == null) {
            currentFragment = TaskFragment()
            (currentFragment as TaskFragment).projectName = "TODAY"
        }
        currentFragment?.let {
            showPage(it)
        }
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
        // as you specify DueTimeReceiver parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
//        if (item.menuInfo)
        currentFragment = TaskFragment()
        (currentFragment as TaskFragment).projectName = item.title.toString()
        currentFragment?.let {
            showPage(it)
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    fun showPage(fragment:Fragment){
        val fragmentTransaction = supportFragmentManager.beginTransaction()
//        fragmentTransaction.replace(R.id.fragment_holder, TaskCreationFragment())
        fragmentTransaction.replace(R.id.main_fragment_holder, fragment).addToBackStack("main_page")
        fragmentTransaction.commit()

    }

    private fun addMenuItemInNavMenuDrawer() {
        val navView: NavigationView? = findViewById<NavigationView>(R.id.nav_view)

        val menu = navView?.menu
        val submenu = menu?.addSubMenu("Projects")

//        TODO(to be replaced with real projects)
        for (project in ContextHolder.projects) {
            submenu?.add(project.name)?.setIcon(R.drawable.ic_format_list_bulleted_red_24dp)
        }
        navView?.invalidate()
    }


}
