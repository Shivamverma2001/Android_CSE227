package com.example.animationdemou2

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Rect
import android.view.MotionEvent
import android.view.View

class GameViewDemo(c:Context):View(c) {
    var x = 100
    var y=20;
    var bp1: Bitmap =BitmapFactory.decodeResource(resources,R.drawable.m1)
    var bg1: Bitmap =BitmapFactory.decodeResource(resources,R.drawable.marioback)
    var c1: Bitmap =BitmapFactory.decodeResource(resources,R.drawable.ma_e)
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val r1=Rect(0,0,bg1.getWidth(),bg1.height)
        val r2=Rect(0,0, canvas!!.width,canvas.height)
        canvas.drawBitmap(bg1,r1,r2,null)
        canvas.drawBitmap(bp1,0f+y-200f,
            (canvas.height-bp1.height).toFloat()-x,null)
        canvas.drawBitmap(c1,890f-500-y,(canvas.height-c1.height).toFloat()-100f-x,null)
    }
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN ->
                startFan()
            MotionEvent.ACTION_UP ->
                stopFan()
        }
        return true
    }
    fun stopFan() {
        x=x-150;
        invalidate()
    }
    fun startFan() {
        x = x + 150
        y=y+110
        invalidate()
    }

}