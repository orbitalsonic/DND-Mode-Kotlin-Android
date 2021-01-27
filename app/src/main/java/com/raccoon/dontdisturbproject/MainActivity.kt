package com.raccoon.dontdisturbproject


import android.app.NotificationManager
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomsheet.BottomSheetDialog


class MainActivity : AppCompatActivity() {

    var checkOnOff=true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mNotificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        /**
        Ringtone for calls, notifications, alarms and vibrations are muted
        ***/
        val btnFullDND: Button = findViewById(R.id.fullDND)
        btnFullDND.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!mNotificationManager.isNotificationPolicyAccessGranted) {
                    showPermissionDialog()
                } else {
                    if (checkOnOff) {
                        checkOnOff = false
                        // DND off
                        mNotificationManager.setInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_ALL)
                    } else {
                        checkOnOff = true
                        //DND on
                        mNotificationManager.setInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_NONE)
                    }

                }

            }

            /**
            Ringtone for calls ,notifications and vibrations are muted but alarm is allowed
             ***/
            val btnFullDNDAlarm: Button = findViewById(R.id.alarmDND)
            btnFullDNDAlarm.setOnClickListener {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (!mNotificationManager.isNotificationPolicyAccessGranted) {
                        showPermissionDialog()
                    } else {
                        if (checkOnOff) {
                            checkOnOff = false
                            // DND off
                            mNotificationManager.setInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_ALL)
                        } else {
                            checkOnOff = true
                            // DND on
                            mNotificationManager.setInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_ALARMS)
                        }

                    }

                }
            }


        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun showPermissionDialog() {
        val mDialog = BottomSheetDialog(this)
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
         mDialog.setCanceledOnTouchOutside(false)
        mDialog.setContentView(R.layout.dialog_permission)

        val mCancel=mDialog.findViewById<TextView>(R.id._cancel)
        mCancel?.setOnClickListener{
            mDialog.dismiss()
        }

        val mDone=mDialog.findViewById<TextView>(R.id._done)
        mDone?.setOnClickListener{
            val intent = Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS)
            startActivity(intent)
            mDialog.dismiss()
        }

        mDialog.show()
    }

}