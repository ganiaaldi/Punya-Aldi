package com.aldi.punyaaldi

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.animation.AlphaAnimation
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.aldi.punyaaldi.`interface`.ChangeToolbarTitle
import com.aldi.punyaaldi.menu.FirstMenuFragment
import com.aldi.punyaaldi.menu.SecondMenuFragment
import com.aldi.punyaaldi.menu.ThirdMenuFragment
import com.aldi.punyaaldi.slidepage.PrefManager
import com.aldi.punyaaldi.slidepage.SlideScreen
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_first_menu.*

class MainActivity : AppCompatActivity(), ChangeToolbarTitle {
    private lateinit var mainNavControl: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var mainBottomNav: BottomNavigationView
    private lateinit var host: NavHostFragment
    private lateinit var mainToolbar: Toolbar
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var mainToolbarTitle: TextView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
      //  showSlider()
     //   setupMenu()
        setupNavController()
        setupDrawer()
        setupActionBar(mainNavControl, appBarConfiguration)
        showNavigationMenu(mainNavControl)
        showBottomMenu(mainNavControl)

    }

   //  fun showSlider() {
      //  btn_ShowSlider.setOnClickListener {
     //       PrefManager(applicationContext).setLaunched(true)
      //      startActivity(Intent(this, SlideScreen::class.java))
      //      finish()
      //  }
    // }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.primary_navigation_fragment).navigateUp(appBarConfiguration)
    }

    override fun updateTitle(title: String) {
        mainToolbarTitle.text = title
    }

    override fun toolbarAction(onClickListener: View.OnClickListener) {
        mainToolbar.setOnClickListener(onClickListener)
    }

    override fun updateNavIcon(drawable: Drawable) {
        mainToolbar.navigationIcon = drawable
    }

    override fun showToolbar(show: Boolean) {
        mainToolbar.visibility = if (show) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    override fun onBackPressed() {
                finish()
            }


    /**
    private fun setupMenu(){
        val  mNavSelec = BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when(item.itemId){
              0 -> {
                    val fragment = FirstMenuFragment()
                   addFragment(fragment)
                   return@OnNavigationItemSelectedListener true
                }
                1 -> {
                    val fragment = SecondMenuFragment()
                    addFragment(fragment)
                    return@OnNavigationItemSelectedListener true
                }
               2 -> {
                    val fragment = ThirdMenuFragment()
                    addFragment(fragment)
                    return@OnNavigationItemSelectedListener true
                }
                else -> false
            }
        }
    }

    private fun addFragment(fragment: Fragment){
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(R.anim.design_bottom_sheet_slide_in,R.anim.design_bottom_sheet_slide_out)
            .replace(R.id.content,fragment, fragment.javaClass.getSimpleName())
            .commit()
    }  end here option2 **/

    private fun setupNavController() {
        host = supportFragmentManager
            .findFragmentById(R.id.primary_navigation_fragment) as NavHostFragment? ?: return
        mainNavControl = host.navController
    }

    private fun setupDrawer() {
        drawerLayout = findViewById(R.id.drawer_layout)
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.thirdMenuFragment, R.id.secondMenuFragment, R.id.firstMenuFragment, R.id.loginFragment, R.id.registerFragment),
            drawerLayout
       )

        drawerLayout!!.addDrawerListener(object: DrawerLayout.DrawerListener{
            override fun onDrawerStateChanged(newState: Int) {
                // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDrawerClosed(drawerView: View) {
                // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDrawerOpened(drawerView: View) {
                loadAvatar()
            }

        })
    }

    private fun showBottomMenu(navController: NavController) {
        mainBottomNav = findViewById(R.id.menu_bottom)
        mainBottomNav.setupWithNavController(navController)

        mainNavControl.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.thirdMenuFragment ||
                destination.id == R.id.secondMenuFragment ||
                destination.id == R.id.firstMenuFragment
            ) {
                mainToolbarTitle.text=destination.label
                drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
                showBottomMenu()
            } else {
                drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                hideBottomMenu()
            }
        }
    }

    private fun showNavigationMenu(navController: NavController) {
        val sideNavView : NavigationView = findViewById(R.id.menu_navigation)
        sideNavView.setupWithNavController(navController)
         sideNavView.setNavigationItemSelectedListener { menuItem ->
             // set item as selected to persist highlight
             menuItem.isChecked = true
             // close drawer when item is tapped
             drawerLayout.closeDrawers()

             // Handle navigation view item clicks here.
             when (menuItem.itemId) {

                 R.id.drawerMenu1 -> {
                     navController.navigate(R.id.firstMenuFragment)
                 }
                 R.id.drawerMenu2 -> {
                     navController.navigate(R.id.secondMenuFragment)
                 }
                 R.id.drawerMenu3 -> {
                     navController.navigate(R.id.thirdMenuFragment)
                 }
                 R.id.drawerMenu4 -> {
                 navController.navigate(R.id.loginFragment)
                 }
                 R.id.drawerMenu5 -> {
                     navController.navigate(R.id.registerFragment)
                 }
             }
             // Add code here to update the UI based on the item selected
             // For example, swap UI fragments here
             true
         }
         }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                drawerLayout.openDrawer(GravityCompat.START)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
        /** sideNavView.setupWithNavController(navController)
        setupDrawerHeader(sideNavView)
        mainNavControl.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.loginFragment ||
                destination.id == R.id.registerFragment ||
                destination.id == R.id.loginFragment ||
                destination.id == R.id.registerFragment ||
                destination.id == R.id.loginFragment
            ) {
                mainToolbarTitle.text=destination.label
                drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            } else {
                drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            }
        }
    } **/

    private fun setupDrawerHeader(navView: NavigationView) {
    }

    private fun loadAvatar(){
    }

    private fun setupActionBar(
        navController: NavController,
        appBarConfig: AppBarConfiguration
    ) {
        mainToolbar = findViewById(R.id.toolbar)
        setSupportActionBar(mainToolbar)
        mainToolbarTitle = mainToolbar.findViewById(R.id.toolbarTitle)
        setupActionBarWithNavController(navController, appBarConfig)
    }

    private fun hideBottomMenu() {
        // bottom_navigation is BottomNavigationView
        with(mainBottomNav) {
            if (visibility == View.VISIBLE && alpha == 1f) {
                animate()
                    .alpha(0f)
                    .withEndAction { visibility = View.GONE }
                    .duration = 500
            }
        }
    }

    private fun showBottomMenu() {
        // bottom_navigation is BottomNavigationView
        with(mainBottomNav) {
            visibility = View.VISIBLE
            animate()
                .alpha(1f)
                .duration = 500
        }
    }
}


