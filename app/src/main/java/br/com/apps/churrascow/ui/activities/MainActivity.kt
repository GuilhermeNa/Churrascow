package br.com.apps.churrascow.ui.activities

import android.content.res.Configuration
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import br.com.apps.churrascow.R
import br.com.apps.churrascow.databinding.ActivityMainBinding
import br.com.apps.churrascow.ui.fragments.home.HomeFragmentDirections
import br.com.apps.churrascow.util.toolbarLightAndDarkColors

private const val TOOLBAR_TITLE_NEW_EVENT = "Novo evento"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var navItemPlaceHolder: MenuItem? = null
    private var menu: Menu? = null

    private val navController by lazy { findNavController(R.id.nav_host_fragment_container_main) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initNavigationView()
        initToolbar()
        initFloatingActionBtn()
        initViewController()

    }

    /**
     * Navigation controller configurations.
     */
    private fun initNavigationView() {
        val navigationView = binding.mainActivityNavigation
        navigationView.setupWithNavController(navController)
        navItemPlaceHolder = navigationView.menu.findItem(R.id.placeholder)
    }

    /**
     * Action bar configurations.
     */
    private fun initToolbar() {
        setSupportActionBar(binding.myToolbar)
    }

    /**
     * Responsible for Floating action Button.
     */
    private fun initFloatingActionBtn() {
        binding.mainActivityFab.setOnClickListener {
            val direction = HomeFragmentDirections.actionHomeFragmentToFormEventFragment()
            navController.navigate(direction)
        }
    }

    /**
     * This method is responsible for manipulate the activity's view.
     *
     * Show and hide views, like fab, navigation view and toolbar.
     */
    private fun initViewController() {
        val fab = binding.mainActivityFab
        val navBar = binding.mainActivityNavigation
        val coordinator = binding.mainActivityCoordinator

        navController.addOnDestinationChangedListener { _, navDestination, _ ->
            when (navDestination.id) {
                R.id.homeFragment -> {
                    coordinator.visibility = VISIBLE
                    fab.visibility = VISIBLE
                    supportActionBar?.show()
                    supportActionBar?.title = null
                    toolbarLightAndDarkColors(
                        supportActionBar,
                        R.color.off_white,
                        R.color.light_grey
                    )
                    menu?.let {
                        it.findItem(R.id.menu_home_profile).isVisible = true
                        it.findItem(R.id.menu_home_save).isVisible = false
                    }
                    navItemPlaceHolder?.let {
                        it.isVisible = true
                    }
                }

                R.id.formEventFragment -> {
                    coordinator.visibility = GONE
                    supportActionBar?.setDisplayHomeAsUpEnabled(true)
                    supportActionBar?.setHomeAsUpIndicator(R.drawable.icon_left_arrow)
                    supportActionBar?.title = TOOLBAR_TITLE_NEW_EVENT
                    supportActionBar?.title
                    binding.myToolbar.setTitleTextColor(resources.getColor(R.color.white))
                    menu?.let {
                        it.findItem(R.id.menu_home_profile).isVisible = false
                        it.findItem(R.id.menu_home_save).isVisible = true
                    }
                    toolbarLightAndDarkColors(
                        supportActionBar,
                        R.color.midnightblue,
                        R.color.black
                    )
                }

                R.id.paymentsFragment -> {
                    fab.visibility = GONE
                    supportActionBar?.show()
                    navItemPlaceHolder?.let {
                        it.isVisible = false
                    }
                }

                R.id.profileFragment -> {
                    coordinator.visibility = GONE
                    supportActionBar?.show()
                    navItemPlaceHolder?.let {
                        it.isVisible = false
                    }
                }

            }
        }
    }

    //--------------------------------------------------------------------------------------------//
    // OPTIONS MENU
    //--------------------------------------------------------------------------------------------//

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        this.menu = menu
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_home_profile ->
                navController.navigate(R.id.action_global_profileFragment)


        }

        return super.onOptionsItemSelected(item)
    }

}
