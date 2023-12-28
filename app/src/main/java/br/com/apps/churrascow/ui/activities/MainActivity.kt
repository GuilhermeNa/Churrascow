package br.com.apps.churrascow.ui.activities

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import br.com.apps.churrascow.R
import br.com.apps.churrascow.databinding.ActivityMainBinding
import java.security.AccessController.getContext


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initSupportActionBar()

    }

    /**
     * Action bar configurations.
     */
    private fun initSupportActionBar() {
        setSupportActionBar(binding.myToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = null

        val nightModeFlags: Int = resources.configuration.uiMode and
                Configuration.UI_MODE_NIGHT_MASK

        when (nightModeFlags) {
            Configuration.UI_MODE_NIGHT_YES -> {
                supportActionBar?.setHomeAsUpIndicator(R.drawable.icon_menu_lines_dark)

            }
            Configuration.UI_MODE_NIGHT_NO -> {
                supportActionBar?.setHomeAsUpIndicator(R.drawable.icon_menu_lines)

            }
        }
    }

    //--------------------------------------------------------------------------------------------//
    // OPTIONS MENU
    //--------------------------------------------------------------------------------------------//

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {

        return super.onPrepareOptionsMenu(menu)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_home_fragment, menu)
        return super.onCreateOptionsMenu(menu)
    }

}
