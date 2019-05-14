package various.coders.wppfit

import android.app.Activity
import android.app.DatePickerDialog
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_edit_profile.*
import various.coders.wppfit.model.AppViewModel
import various.coders.wppfit.model.database.entities.User
import various.coders.wppfit.model.database.types.ActivityLevel
import java.lang.NumberFormatException
import java.util.*

class EditProfileActivity : AppCompatActivity() {
    //view model is initiated by and for this activity
    //as the view model communicates with the database
    private lateinit var model: AppViewModel
    //date selected in dateText
    private lateinit var date: Date
    private lateinit var spinner: Spinner
    //to check if there's a user
    private var user = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        //init model
        model = ViewModelProviders.of(this).get(AppViewModel::class.java)
        model.getDb(this)

        //set something to do with the date
        dateText.setOnClickListener {
            val picker = DatePickerDialog(this).apply {
                setOnDateSetListener { _, year, month, dayOfMonth ->
                    this@EditProfileActivity.updateDate(Date().also {
                        it.year = year
                        it.month = month
                        it.date = dayOfMonth
                    })
                }
            }
            picker.show()
        }

        acceptButton.setOnClickListener { apply() }

        //user is read from shared prefs
        val prefs = getPreferences(Context.MODE_PRIVATE)
        user = prefs.getInt("uid", -1)


        // Setting up ActivityLevel spinner
        activitySpinner.adapter =
            ArrayAdapter<ActivityLevel>(this, android.R.layout.simple_spinner_item, ActivityLevel.values())

        //if there is set user in prefs
        if (user != -1) {
            //get it from the database
            model.currentUser.observe(this, android.arch.lifecycle.Observer {
                if (it != null) {
                    nameText.setText(it.firstName)
                    surnameText.setText(it.lastName)
                    dateText.setText(it.age.toString())
                    date = it.age
                    weightText.setText(it.weight)
                    heightText.setText(it.height)
                    gender.check(if (it.gender) R.id.maleButton else R.id.femaleButton)
                } else {
                    Toast.makeText(this, "Currently set user was wrong", Toast.LENGTH_LONG).show()
                }
            })
            model.setCurrentUser(user)
        }
    }

    private fun updateDate(date: Date) {
        this.date = date
        dateText.setText("${date.date}/${date.month}/${date.year}")
    }

    private fun verify(): Boolean {
        if (nameText.text.isBlank() || surnameText.text.isBlank() || !::date.isInitialized || weightText.text.isBlank() || heightText.text.isBlank()) return false
        val w = try {
            weightText.text.toString().toInt()
        } catch (e: NumberFormatException) {
            return false
        }
        val h = try {
            heightText.text.toString().toInt()
        } catch (e: NumberFormatException) {
            return false
        }
        //age > 13, 30 < weight < 300 (kg), 50 < height < 250 (cm)
        return date.time - Date().time > 409968000000L && w > 30 && w < 300 && h > 50 && h < 250
    }

    private fun apply() {
        if (!verify()) {
            Toast.makeText(this, "Fill up every box", Toast.LENGTH_LONG).show()
        } else {
            val u = User(
                uid = if (user != -1) model.currentUser.value!!.uid else 0,
                firstName = nameText.text.toString(),
                lastName = surnameText.text.toString(),
                gender = gender.checkedRadioButtonId == R.id.maleButton,
                age = date,
                activity = activitySpinner.selectedItem as ActivityLevel,
                weight = heightText.text.toString().toInt(),
                height = heightText.text.toString().toInt()
            )
            if (user != -1) {
                model.updateUser(u)
                //ok is user was edited
                setResult(Activity.RESULT_OK)
            } else {
                model.insertUser(u)
                //first user+0 if it was inserted
                setResult(Activity.RESULT_FIRST_USER)
            }
            finish()
        }
    }
}
