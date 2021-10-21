package com.siemens.leadx.utils.locale

import java.util.*

sealed class LocaleLanguage {
    object Arabic : LocaleLanguage() {
        fun getId(): String = "ar"
    }

    object English : LocaleLanguage() {
        fun getId(): String = "en"
    }

    companion object {
        fun getDefaultLanguage() =
            //Arabic.getId()
            if (Locale.getDefault().language == Locale(Arabic.getId()).language)
                Arabic.getId()
            else
                English.getId()
    }
}
