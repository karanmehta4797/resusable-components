package com.example.resusablecomponents

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.helpers.TestActivity
import com.example.helpers.utilities.ConnectivityReceiver
import com.example.resusablecomponents.utilities.RateAppHelper
import com.example.resusablecomponents.utilities.ShareAppHelper
import com.google.android.material.snackbar.Snackbar

class FeedbackActivity : AppCompatActivity(), View.OnClickListener,
    ConnectivityReceiver.ConnectivityReceiverListener {
    override fun onNetworkChangedListener(isConnected: Boolean) {
        if (isConnected)
            Snackbar.make(relativeLayout, "Connected", Snackbar.LENGTH_LONG).show()
        else
            Snackbar.make(relativeLayout, "Disconnected", Snackbar.LENGTH_LONG).show()
    }

    private lateinit var spinner: Spinner
    private lateinit var txtComment: EditText
    private lateinit var btnSubmit: Button
    private lateinit var btnRate: Button
    private lateinit var ratingBar: RatingBar
    private lateinit var linearLayout: LinearLayout
    private lateinit var relativeLayout: RelativeLayout

    private var spinnerItems: Array<String> = Array(3) { "" }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback)
        init()
        loadSpinner()
    }

    private fun loadSpinner() {
        spinnerItems[0] = getString(R.string.SPINNER_ITEM1)
        spinnerItems[1] = getString(R.string.SPINNER_ITEM2)
        spinnerItems[2] = getString(R.string.SPINNER_ITEM3)

        var arrayAdapter =
            ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, spinnerItems)
        spinner.adapter = arrayAdapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (spinnerItems.get(position).equals(getString(R.string.SPINNER_ITEM1))) {
                    linearLayout.visibility = View.VISIBLE
                } else {
                    linearLayout.visibility = View.GONE
                }
            }
        }
    }

    private fun init() {
        spinner = findViewById(R.id.spinner)
        txtComment = findViewById(R.id.txtComment)
        btnSubmit = findViewById(R.id.btnSubmit)
        btnRate = findViewById(R.id.btnRateUs)
        ratingBar = findViewById(R.id.ratingBar)
        linearLayout = findViewById(R.id.linearRating)
        relativeLayout = findViewById(R.id.relativeLayoutFeedback)

        /*registerReceiver(
            ConnectivityReceiver(),
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )
        ConnectivityReceiver.connectivityReceiverListener = this*/
        Log.e("RECEIVER", "Registered")
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btnSubmit -> {
                var intent = Intent(this, TestActivity::class.java)
                startActivity(intent)
            }
            R.id.btnRateUs -> {
                RateAppHelper.showRateAppDialog(
                    this,
                    Constants.PLAYSTORE_APP_URL,
                    getString(R.string.RATE_DIALOG_TITLE),
                    getString(R.string.RATE_DIALOG_MESSAGE)
                )
            }
            R.id.btnShareThisApp -> {
                ShareAppHelper.showShareAppChooser(
                    this,
                    getString(R.string.SHARE_CHOOSER_TITLE),
                    Constants.SHARE_CHOOSER_URL_TEXT
                )
            }
        }
    }

    override fun onStop() {
        super.onStop()
        //unregisterReceiver(ConnectivityReceiver())
        Log.e("RECEIVER", "Unregistered")
    }

    private fun restartActivity() {
        finish()
        startActivity(getIntent())
    }
}
