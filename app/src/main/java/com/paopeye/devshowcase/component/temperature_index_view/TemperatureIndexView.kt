package com.paopeye.devshowcase.component.temperature_index_view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Shader
import android.util.AttributeSet
import android.view.View
import com.paopeye.devshowcase.R
import com.paopeye.kit.extension.emptyFloat

class TemperatureIndexView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var minTemp: Float = 0f
    private var maxTemp: Float = 100f
    private var currentTemp: Float = 50f
    private val trackHeight = 12.dpToPx()
    private val thumbRadius = 10.dpToPx()
    private val cornerRadius = 4.dpToPx()
    private val padding = 8.dpToPx()
    private val gradientColors = intArrayOf(
        Color.BLUE,
        Color.CYAN,
        Color.GREEN,
        Color.YELLOW,
        Color.RED
    )

    private val trackPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val thumbPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.WHITE
        style = Paint.Style.FILL
    }

    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.WHITE
        textSize = 14.spToPx()
    }

    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.TemperatureGradientView,
            0,
            0
        ).apply {
            try {
                minTemp = getFloat(R.styleable.TemperatureGradientView_minTemp, 0f)
                maxTemp = getFloat(R.styleable.TemperatureGradientView_maxTemp, 100f)
                currentTemp = getFloat(R.styleable.TemperatureGradientView_currentTemp, 50f)
            } finally {
                recycle()
            }
        }
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val width = width - 2 * padding
        val startX = padding
        val centerY = height / 2f

        val gradient = LinearGradient(
            startX, centerY, startX + width, centerY,
            gradientColors, null, Shader.TileMode.CLAMP
        )
        trackPaint.shader = gradient
        canvas.drawRoundRect(
            startX, centerY - trackHeight / 2,
            startX + width, centerY + trackHeight / 2,
            cornerRadius, cornerRadius,
            trackPaint
        )

        val progress = (currentTemp - minTemp) / (maxTemp - minTemp)
        val thumbX = startX + (width * progress)
        canvas.drawCircle(thumbX, centerY, thumbRadius, thumbPaint)

        canvas.drawText(
            "${minTemp.toInt()}",
            startX,
            centerY - trackHeight - 12.dpToPx(),
            textPaint
        )

        canvas.drawText(
            "${maxTemp.toInt()}",
            startX + width,
            centerY - trackHeight - 12.dpToPx(),
            textPaint.apply { textAlign = Paint.Align.RIGHT }
        )

        canvas.drawText(
            "${currentTemp.toInt()}",
            thumbX,
            centerY - trackHeight - 12.dpToPx(),
            textPaint.apply { textAlign = Paint.Align.CENTER }
        )
    }

    fun setTemperatures(min: Int, max: Int, current: Int) {
        val minFloat = min.toFloat()
        val maxFloat = max.toFloat()
        val currentFloat = current.toFloat()
        minTemp = minFloat
        maxTemp = maxFloat
        currentTemp = currentFloat.coerceIn(minFloat, maxFloat)
        invalidate()
    }

    private fun Int.dpToPx(): Float = this * resources.displayMetrics.density
    private fun Int.spToPx(): Float = this * resources.displayMetrics.scaledDensity
}
