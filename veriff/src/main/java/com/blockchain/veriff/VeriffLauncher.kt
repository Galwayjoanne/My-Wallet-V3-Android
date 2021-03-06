package com.blockchain.veriff

import android.app.Activity
import androidx.core.content.ContextCompat
import mobi.lab.veriff.data.Branding
import mobi.lab.veriff.data.Veriff
import timber.log.Timber

class VeriffLauncher {

    fun launchVeriff(activity: Activity, applicant: VeriffApplicantAndToken, requestCode: Int) {
        val sessionToken = applicant.token
        Timber.d("Veriff session token: $sessionToken")
        val veriffSDK = Veriff.Builder("https://magic.veriff.me/v1/", sessionToken)
        val branding = Branding.Builder()
            .setThemeColor(ContextCompat.getColor(activity, R.color.primary_blue_accent))
            .build()
        veriffSDK.setBranding(branding)
        veriffSDK.launch(activity, requestCode)
    }
}
