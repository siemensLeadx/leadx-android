package com.siemens.leadx.utils.extensions

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.content.pm.PackageInfoCompat
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.siemens.leadx.R
import com.siemens.leadx.databinding.DialogChangeLanguageBinding
import com.siemens.leadx.databinding.DialogLogoutBinding
import com.siemens.leadx.databinding.DialogRootedBinding
import com.siemens.leadx.utils.Constants
import com.siemens.leadx.utils.locale.LocaleLanguage

/**
 * Created by Norhan Elsawi on 4/10/2021.
 */
fun Context.getColorCompat(@ColorRes color: Int): Int {
    return ContextCompat.getColor(this, color)
}

fun Context.getDrawableCompat(@DrawableRes drawable: Int): Drawable? {
    return ContextCompat.getDrawable(this, drawable)
}

fun Context.getSharedPref(): SharedPreferences {
    return EncryptedSharedPreferences.create(
        this,
        Constants.SHARED_PREF_NAME,
        getMasterKey(this),
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )
}

private fun getMasterKey(context: Context): MasterKey {
    return MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()
}

fun Context.getVersionCode(): String {
    try {
        val pInfo = packageManager.getPackageInfo(packageName, 0)
        val longVersionCode = PackageInfoCompat.getLongVersionCode(pInfo)
        return "$longVersionCode.0"
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return ""
}

fun Context.openGooglePlayStoreForUpdate() {
    val appPackageName = packageName
    try {
        val intent = Intent(
            Intent.ACTION_VIEW, Uri.parse("market://details?id=$appPackageName")
        )
        intent.setPackage("com.android.vending")
        startActivity(intent)
    } catch (e: Exception) {
        startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")
            )
        )
    }
}

fun Context.isRtl(): Boolean {
    val config = this.resources.configuration
    return config.layoutDirection == View.LAYOUT_DIRECTION_RTL
}

fun Context.getDimension(dim: Int): Int {
    return this.resources.getDimension(dim).toInt()
}

fun Context.showLanguageDialog(
    currentLanguage: String,
    onLanguageSelected: (language: String) -> Unit,
) {
    val binding = DialogChangeLanguageBinding.inflate(LayoutInflater.from(this))
    val dialog = Dialog(this, R.style.DialogCustomTheme)
    dialog.setContentView(binding.root)
    dialog.setCancelable(false)
    dialog.window?.setLayout(
        FrameLayout.LayoutParams.MATCH_PARENT,
        FrameLayout.LayoutParams.WRAP_CONTENT
    )
    var selectedLanguage = currentLanguage
    with(binding) {
        if (currentLanguage == LocaleLanguage.Arabic.getId())
            ivCheckArabic.setImageResource(R.drawable.ic_checked)
        else
            ivCheckEnglish.setImageResource(R.drawable.ic_checked)

        tvArabic.setOnClickListener {
            ivCheckArabic.setImageResource(R.drawable.ic_checked)
            ivCheckEnglish.setImageResource(R.drawable.ic_unchecked)
            selectedLanguage = LocaleLanguage.Arabic.getId()
        }
        tvEnglish.setOnClickListener {
            ivCheckArabic.setImageResource(R.drawable.ic_unchecked)
            ivCheckEnglish.setImageResource(R.drawable.ic_checked)
            selectedLanguage = LocaleLanguage.English.getId()
        }
        btnChange.setOnClickListener {
            if (currentLanguage != selectedLanguage)
                onLanguageSelected.invoke(selectedLanguage)
            dismissDialog(dialog)
        }
        btnCancel.setOnClickListener {
            dismissDialog(dialog)
        }
    }
    if (!dialog.isShowing)
        dialog.show()
}

fun Context.showLogOutDialog(onLogOut: () -> Unit) {
    val binding = DialogLogoutBinding.inflate(LayoutInflater.from(this))
    val dialog = Dialog(this, R.style.DialogCustomTheme)
    dialog.setContentView(binding.root)
    dialog.setCancelable(false)
    dialog.window?.setLayout(
        FrameLayout.LayoutParams.MATCH_PARENT,
        FrameLayout.LayoutParams.WRAP_CONTENT
    )
    with(binding) {
        btnLogout.setOnClickListener {
            dismissDialog(dialog)
            onLogOut.invoke()
        }
        btnCancel.setOnClickListener {
            dismissDialog(dialog)
        }
    }
    if (!dialog.isShowing)
        dialog.show()
}

fun Activity.showRootedDialog() {
    val binding =
        DialogRootedBinding.inflate(LayoutInflater.from(this))
    val dialog = Dialog(this, R.style.DialogCustomTheme)
    dialog.setContentView(binding.root)
    dialog.window?.setLayout(
        FrameLayout.LayoutParams.MATCH_PARENT,
        FrameLayout.LayoutParams.MATCH_PARENT
    )
    dialog.setOnDismissListener {
        finish()
    }

    if (!dialog.isShowing)
        dialog.show()
}

fun dismissDialog(dialog: Dialog) {
    if (dialog.isShowing)
        dialog.dismiss()
}
