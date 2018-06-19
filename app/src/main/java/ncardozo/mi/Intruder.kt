package ncardozo.mi

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Typeface

class Intruder(var img : Bitmap) {
    var x = 0
    var y = 0
    var w = 0
    var h = 0
    var lifePower = 100

    private var screenWidth = Resources.getSystem().displayMetrics.widthPixels
    private var screenHeight = Resources.getSystem().displayMetrics.heightPixels
    private val gravity = 5

    init {
        w = img.width
        h = img.height
        x = screenWidth/2
        y = screenHeight -  2*img.height
        lifePower = 100
    }

    fun draw(canvas : Canvas) {
        var typeWriter = Paint()
        typeWriter.setARGB(255, 255, 255 , 255)
        typeWriter.textSize  = 30f
        typeWriter.typeface = Typeface.SERIF
        canvas.drawText("life:" + lifePower.toString(), (screenWidth - 140).toFloat(), 60.toFloat(), typeWriter)
        canvas.drawBitmap(img, x.toFloat(), y.toFloat(), null)
    }

    fun update(touched_y : Int, lifeLost : Int = 0) {
        y += (touched_y)
        if(y <= screenHeight -  2*img.height)
            y += gravity

        lifePower -= lifeLost
    }

}