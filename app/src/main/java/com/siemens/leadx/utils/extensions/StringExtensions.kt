package com.siemens.leadx.utils.extensions

import android.content.Context
import android.content.Intent
import android.net.Uri


fun String.makeCall(context: Context?) {
    val callIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$this"))
    context?.startActivity(callIntent)
}

fun String.sendMail(context: Context?) {
    val emailIntent = Intent(
        Intent.ACTION_SENDTO, Uri.parse(
            "mailto:${this}"
        )
    )
    context?.startActivity(Intent.createChooser(emailIntent, ""))
}