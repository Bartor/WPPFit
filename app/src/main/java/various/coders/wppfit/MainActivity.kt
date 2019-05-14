package various.coders.wppfit

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.support.v4.app.Fragment
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import various.coders.wppfit.fragments.AddExerciseFragment
import various.coders.wppfit.fragments.AddMealFragment
import various.coders.wppfit.fragments.HomeScreenFragment
import various.coders.wppfit.fragments.UserProfileFragment
import various.coders.wppfit.model.AppViewModel
import java.util.*

const val EDIT_PROFILE = 0

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

        //select the "home" menu item
        nav_view.menu.getItem(0).setChecked(true)
        nav_view.setNavigationItemSelectedListener(this)

        val uid = getPreferences(Context.MODE_PRIVATE).getInt("uid", -1)
        //if there is no set user in prefs, we start an edit profile activity to create one
        if (uid == -1) {
            val intent = Intent(this, EditProfileActivity::class.java)
            startActivityForResult(intent, EDIT_PROFILE)
        } else { //otherwise we set current user to the uid
            viewModel.getMeals(uid, Date(System.currentTimeMillis() - 86400000))
            viewModel.getExercises(uid, Date(System.currentTimeMillis() - 86400000))
            viewModel.setCurrentUser(uid)
            viewModel.currentUser.observe(this, Observer {
                currentFragment = HomeScreenFragment()
                supportFragmentManager.beginTransaction().add(R.id.fragmentContainer, currentFragment).commit()
            })
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            EDIT_PROFILE -> {
                when (resultCode) {
                    Activity.RESULT_OK -> {
                        //info about updating the user
                        Toast.makeText(this, "User updated", Toast.LENGTH_LONG).show()
                    }
                    Activity.RESULT_FIRST_USER -> {
                        //we try to get the newest id (newest user - just created)
                        viewModel.getNewestUser().observe(this, Observer {
                            if (it != null) {
                                //we set the current user to it
                                viewModel.setCurrentUser(it.uid)
                                //and we updated prefs
                                with(getPreferences(Context.MODE_PRIVATE).edit()) {
                                    putInt("uid", it.uid)
                                    apply()
                                }
                            }
                        })
                    }
                }
            }
        }
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> {
                println(viewModel.currentUser.value)
                println(viewModel.meals.value)
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
