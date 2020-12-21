package com.example.linefromupbarview

import android.view.View
import android.view.MotionEvent
import android.graphics.Paint
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.Canvas
import android.graphics.RectF

val parts : Int = 3
val scGap : Float = 0.02f / parts
val strokeFactor : Float = 90f
val sizeFactor : Float = 4.9f
val delay : Long = 20
val colors : Array<Int> = arrayOf(
    "#F44336",
    "#3F51B5",
    "#FF5722",
    "#4CAF50",
    "#795548"
).map {
    Color.parseColor(it)
}.toTypedArray()
val backColor : Int = Color.parseColor("#BDBDBD")

fun Int.inverse() : Float = 1f / this
fun Float.maxScale(i : Int, n : Int) : Float = Math.max(0f, this - i * n.inverse())
fun Float.divideScale(i : Int, n : Int) : Float = Math.min(n.inverse(), maxScale(i, n)) * n
fun Float.sinify() : Float = Math.sin(this * Math.PI).toFloat()

fun Canvas.drawLineFromUp(scale : Float, w : Float, h : Float, paint : Paint) {
    val sf : Float = scale.sinify()
    val sf1 : Float = sf.divideScale(0, parts)
    val sf2 : Float = sf.divideScale(1, parts)
    val size : Float = Math.min(w, h) / sizeFactor
    save()
    translate(w / 2, h)
    for (j in 0..1) {
        save()
        drawLine(-size, -h * (1 - sf1) - size / 2, -size, 0f, paint)
        drawRect(RectF(-size, -size / 2, -size + size * sf2, 0f), paint)
        restore()
    }
    restore()
}

fun Canvas.drawLFUNode(i : Int, scale : Float, paint : Paint) {
    val w : Float = width.toFloat()
    val h : Float = height.toFloat()
    paint.color = colors[i]
    paint.strokeCap = Paint.Cap.ROUND
    paint.strokeWidth = Math.min(w, h) / strokeFactor
    drawLineFromUp(scale, w, h, paint)
}
