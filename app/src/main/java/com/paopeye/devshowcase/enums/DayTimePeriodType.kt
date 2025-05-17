package com.paopeye.devshowcase.enums

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import com.paopeye.kit.extension.emptyLong
import java.util.Calendar

enum class DayTimePeriodType {
    UNSPECIFIED {
        override fun getBackgroundColor(): GradientDrawable {
            val colors = intArrayOf(
                Color.parseColor("#FFFFFF")
            )

            val gradient = GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM,
                colors
            )
            gradient.gradientType = GradientDrawable.LINEAR_GRADIENT
            return gradient
        }
    },
    MORNING {
        override fun getBackgroundColor(): GradientDrawable {
            val colors = intArrayOf(
                Color.parseColor("#FFD3B6"),
                Color.parseColor("#FFAA33"),
                Color.parseColor("#D4E4F7")
            )

            val gradient = GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM,
                colors
            )
            gradient.gradientType = GradientDrawable.LINEAR_GRADIENT
            return gradient
        }
    },
    NOON {
        override fun getBackgroundColor(): GradientDrawable {
            val colors = intArrayOf(
                Color.parseColor("#87CEEB"),
                Color.parseColor("#FFD700")
            )

            val gradient = GradientDrawable(
                GradientDrawable.Orientation.LEFT_RIGHT,
                colors
            )
            return gradient
        }
    },
    AFTERNOON {
        override fun getBackgroundColor(): GradientDrawable {
            val colors = intArrayOf(
                Color.parseColor("#FF8C61"),
                Color.parseColor("#6699CC")
            )

            val gradient = GradientDrawable(
                GradientDrawable.Orientation.TL_BR,
                colors
            )
            return gradient
        }
    },
    EVENING {
        override fun getBackgroundColor(): GradientDrawable {
            val colors = intArrayOf(
                Color.parseColor("#FF6B35"),
                Color.parseColor("#8A2BE2"),
                Color.parseColor("#191970")
            )

            val gradient = GradientDrawable(
                GradientDrawable.Orientation.BOTTOM_TOP,
                colors
            )
            return gradient
        }
    },
    NIGHT {
        override fun getBackgroundColor(): GradientDrawable {
            val colors = intArrayOf(
                Color.parseColor("#4B0082"),
                Color.parseColor("#000000")
            )

            val gradient = GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM,
                colors
            )
            gradient.gradientType = GradientDrawable.RADIAL_GRADIENT
            gradient.gradientRadius = 1000F
            return gradient
        }
    };

    abstract fun getBackgroundColor(): GradientDrawable

    companion object {
        fun getType(timestamp: Long = emptyLong()): DayTimePeriodType {
            val timeInMillis = timestamp * 1000L
            val calendar = Calendar.getInstance()
            if (timestamp != emptyLong()) calendar.timeInMillis = timeInMillis
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            if (hour in 5..11) return MORNING
            if (hour == 12) return NOON
            if (hour in 13..16) return AFTERNOON
            if (hour in 17..20) return EVENING
            if (hour in 21..23 || hour in 1..4) return NIGHT
            return UNSPECIFIED
        }
    }
}
