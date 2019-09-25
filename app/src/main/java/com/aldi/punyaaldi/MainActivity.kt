package com.aldi.punyaaldi

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AlphaAnimation
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.Toolbar
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
import com.aldi.punyaaldi.`interface`.FragmentInteraction
import com.aldi.punyaaldi.`interface`.ToolbarTitleListener
import com.aldi.punyaaldi.menu.FirstMenuFragment
import com.aldi.punyaaldi.menu.SecondMenuFragment
import com.aldi.punyaaldi.menu.ThirdMenuFragment
import com.aldi.punyaaldi.slidepage.PrefManager
import com.aldi.punyaaldi.slidepage.SlideScreen
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_first_menu.*

class MainActivity : AppCompatActivity(), ToolbarTitleListener {
    private lateinit var mNavController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var mBottomNav: BottomNavigationView
    private lateinit var host: NavHostFragment
    private lateinit var mToolbar: Toolbar
    private lateinit var mToolbarTitle: TextView
    private var mBackPressCounter = 0
    private var isOnPersonalThreadFragment: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
      //  showSlider()
     //   setupMenu()
        setupNavController()
        setupDrawer()
        setupActionBar(mNavController, appBarConfiguration)
        setupNavigationMenu(mNavController)
        setupBottomNavMenu(mNavController)
        mBackPressCounter = 0
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
        mToolbarTitle.text = title
    }

    override fun toolbarAction(onClickListener: View.OnClickListener) {
        mToolbar.setOnClickListener(onClickListener)
    }

    override fun updateNavIcon(drawable: Drawable) {
        mToolbar.navigationIcon = drawable
    }

    override fun showToolbar(show: Boolean) {
        mToolbar.visibility = if (show) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    override fun onBackPressed() {
        if (isOnPersonalThreadFragment) {
            mBackPressCounter++
            if (mBackPressCounter == 2) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    finishAndRemoveTask()
                } else {
                    finish()
                }
            } else {
                Toast.makeText(this, R.string.confirm_exit, Toast.LENGTH_SHORT).show()
            }
        } else {
            val consumed = primary_navigation_fragment.childFragmentManager.fragments.let {
                if ( it.size > 0 && it[0] is FragmentInteraction){
                    (it[0] as FragmentInteraction).onBackPressed()
                } else {
                    false
                }
            }

            val fragmentsSize = host.childFragmentManager.fragments.size

            mBackPressCounter = 0

            if (!consumed && fragmentsSize > 1) {
                super.onBackPressed()
            } else {
                findNavController(R.id.primary_navigation_fragment).navigateUp(appBarConfiguration)
            }
        }
    }

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
    }

    private fun setupNavController() {
        host = supportFragmentManager
            .findFragmentById(R.id.primary_navigation_fragment) as NavHostFragment? ?: return
        mNavController = host.navController
    }

    private fun setupDrawer() {
        val drawerLayout: DrawerLayout? = findViewById(R.id.drawer_layout)
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.thirdMenuFragment, R.id.secondMenuFragment, R.id.firstMenuFragment),
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

    private fun setupBottomNavMenu(navController: NavController) {
        mBottomNav = findViewById(R.id.menu_bottom)
        mBottomNav.setupWithNavController(navController)

        mNavController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.thirdMenuFragment ||
                destination.id == R.id.secondMenuFragment ||
                destination.id == R.id.firstMenuFragment
            ) {
                mToolbarTitle.text=destination.label
                drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
                showBottomNavigation()
            } else {
                drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                hideBottomNavigation()
            }
        }
    }

    private fun setupNavigationMenu(navController: NavController) {
        val sideNavView = findViewById<NavigationView>(R.id.menu_navigation)
        sideNavView?.setupWithNavController(navController)
        sideNavView.itemIconTintList = null
        setupDrawerHeader(sideNavView)
    }

    private fun setupDrawerHeader(navView: NavigationView) {

    }

    private fun loadAvatar(){

    }

    private fun setupActionBar(
        navController: NavController,
        appBarConfig: AppBarConfiguration
    ) {
        mToolbar = findViewById(R.id.toolbar)
        setSupportActionBar(mToolbar)
        mToolbarTitle = mToolbar.findViewById(R.id.toolbarTitle)
        setupActionBarWithNavController(navController, appBarConfig)
    }

    private fun hideBottomNavigation() {
        // bottom_navigation is BottomNavigationView
        with(mBottomNav) {
            if (visibility == View.VISIBLE && alpha == 1f) {
                animate()
                    .alpha(0f)
                    .withEndAction { visibility = View.GONE }
                    .duration = 500
            }
        }
    }

    private fun showBottomNavigation() {
        // bottom_navigation is BottomNavigationView
        with(mBottomNav) {
            visibility = View.VISIBLE
            animate()
                .alpha(1f)
                .duration = 500
        }
    }
}


