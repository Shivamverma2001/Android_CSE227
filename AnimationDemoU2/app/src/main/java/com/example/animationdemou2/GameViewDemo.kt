package com.example.animationdemou2

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Rect
import android.view.View

class GameViewDemo(c:Context):View(c) {
    var bg : Bitmap = BitmapFactory.decodeResource(resources,R.drawable.marioback)
    var m1 : Bitmap = BitmapFactory.decodeResource(resources,R.drawable.m1)
    var ma : Bitmap = BitmapFactory.decodeResource(resources,R.drawable.maee)

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val r1 = Rect(0,0,bg.width,bg.height)
        val r2 = Rect(0,0,canvas!!.width,canvas.height)

        val mr1 = Rect(0,canvas.height-m1.height-10,canvas!!.width,canvas.height+2000)
        val mae = Rect(0,canvas.height-m1.height+50,canvas!!.width,canvas.height+2000)


        canvas.drawBitmap(bg,r1,r2,null)

        canvas.drawBitmap(m1,r1,mr1,null)

    }

}