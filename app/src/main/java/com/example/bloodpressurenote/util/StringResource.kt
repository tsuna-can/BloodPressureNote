package com.example.bloodpressurenote.util

import android.content.Context
import androidx.annotation.StringRes

interface StringResource {
    fun getString(context: Context): String
}

data class ResStringResource(
    @StringRes private val resId: Int,
    private val params: List<Any> = emptyList()
) : StringResource {
    companion object {
        fun create(
            @StringRes resId: Int,
            vararg params: Any
        ): ResStringResource {
            return ResStringResource(resId, listOf(*params))
        }
    }

    override fun getString(context: Context): String {
        if (params.isEmpty()) {
            return context.getString(resId)
        }

        val params = params.map {
            if (it is StringResource) {
                it.getString(context)
            } else {
                it
            }
        }
        return context.getString(resId, *params.toTypedArray())
    }
}
