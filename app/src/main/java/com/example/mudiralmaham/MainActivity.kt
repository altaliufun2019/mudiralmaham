package com.example.mudiralmaham

import android.os.Bundle
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

//        to init lateinit vars in ContextHolder
        ContextHolder.getCacheData()

        addMenuItemInNavMenuDrawer()
        val taskFragment = TaskFragment()
        taskFragment.projectName = ""//TODO(proper value)
        showPage(taskFragment)

        if (currentFragment == null)
            currentFragment = TaskFragment()
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
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_home -> {
                // Handle the camera action
            }
            R.id.nav_gallery -> {

            }
            R.id.nav_add_project -> {

            }
            R.id.nav_tools -> {

            }
           /* R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }*/
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
        submenu?.add("Super Item1")?.setIcon(R.drawable.ic_format_list_bulleted_red_24dp)
        submenu?.add("Super Item2")?.setIcon(R.drawable.ic_format_list_bulleted_red_24dp)
        submenu?.add("Super Item3")?.setIcon(R.drawable.ic_format_list_bulleted_red_24dp)
        submenu?.add("Super Item3")?.setIcon(R.drawable.ic_format_list_bulleted_red_24dp)
        submenu?.add("Super Item3")?.setIcon(R.drawable.ic_format_list_bulleted_red_24dp)
        submenu?.add("Super Item3")?.setIcon(R.drawable.ic_format_list_bulleted_red_24dp)
        submenu?.add("Super Item3")?.setIcon(R.drawable.ic_format_list_bulleted_red_24dp)
        submenu?.add("Super Item1")?.setIcon(R.drawable.ic_format_list_bulleted_red_24dp)
        submenu?.add("Super Item2")?.setIcon(R.drawable.ic_format_list_bulleted_red_24dp)
        submenu?.add("Super Item3")?.setIcon(R.drawable.ic_format_list_bulleted_red_24dp)
        submenu?.add("Super Item3")?.setIcon(R.drawable.ic_format_list_bulleted_red_24dp)
        submenu?.add("Super Item3")?.setIcon(R.drawable.ic_format_list_bulleted_red_24dp)
        submenu?.add("Super Item3")?.setIcon(R.drawable.ic_format_list_bulleted_red_24dp)
        submenu?.add("Super Item3")?.setIcon(R.drawable.ic_format_list_bulleted_red_24dp)

        navView?.invalidate()
    }


}
