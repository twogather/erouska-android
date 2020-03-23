package cz.covid19cz.app.ui.main

import android.os.Bundle
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import cz.covid19cz.app.R
import cz.covid19cz.app.databinding.ActivityMainBinding
import cz.covid19cz.app.ui.base.BaseActivity
import cz.covid19cz.app.ui.help.HelpFragment
import cz.covid19cz.app.utils.L
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.RuntimeException

class MainActivity :
    BaseActivity<ActivityMainBinding, MainVM>(R.layout.activity_main, MainVM::class) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)
        findNavController(R.id.nav_host_fragment).let {
            NavigationUI.setupWithNavController(bottom_navigation, it);
            it.addOnDestinationChangedListener { controller, destination, arguments ->
                if (destination.label != null) {
                    title = destination.label
                } else {
                    setTitle(R.string.app_name)
                }
                bottom_navigation.visibility =
                    if (destination.arguments["fullscreen"]?.defaultValue == true
                        || arguments?.getBoolean("fullscreen") == true
                    ) {
                        GONE
                    } else {
                        VISIBLE
                    }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.nav_help -> {
                navigate(R.id.nav_help, Bundle().apply { putBoolean("fullscreen", true) })
                true
            }
            else -> {
                NavigationUI.onNavDestinationSelected(
                    item,
                    findNavController(R.id.nav_host_fragment)
                ) || super.onOptionsItemSelected(item)
            }
        }
    }
}
