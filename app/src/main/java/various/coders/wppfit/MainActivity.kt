package various.coders.wppfit

import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.Fragment
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import various.coders.wppfit.fragments.AddExerciseFragment
import various.coders.wppfit.fragments.AddMealFragment
import various.coders.wppfit.fragments.HomeScreenFragment
import various.coders.wppfit.fragments.UserProfileFragment
import various.coders.wppfit.model.AppViewModel

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var currentFragment: Fragment
    private lateinit var viewModel: AppViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        viewModel = ViewModelProviders.of(this).get(AppViewModel::class.java)
        viewModel.getDb(this)

        currentFragment = HomeScreenFragment()
        supportFragmentManager.beginTransaction().add(R.id.fragmentContainer, currentFragment).commit()

        //select the "home" menu item
        nav_view.menu.getItem(0).setChecked(true)

        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
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
        when (item.itemId) {
            R.id.nav_home -> {
                if (currentFragment !is HomeScreenFragment) {
                    currentFragment = HomeScreenFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, currentFragment).commit()
                }
            }
            R.id.nav_exercise -> {
                if (currentFragment !is AddExerciseFragment) {
                    currentFragment = AddExerciseFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, currentFragment).commit()
                }
            }
            R.id.nav_meal -> {
                if (currentFragment !is AddMealFragment) {
                    currentFragment = AddMealFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, currentFragment).commit()
                }
            }
            R.id.nav_profile -> {
                if (currentFragment !is UserProfileFragment) {
                    currentFragment = UserProfileFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, currentFragment).commit()
                }
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
