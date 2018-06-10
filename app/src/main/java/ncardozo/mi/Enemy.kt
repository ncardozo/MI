package ncardozo.mi

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas

class Enemy(private val img : Bitmap ) {
    var x = 0
    var y = 0
    var w = 0
    var h = 0
    private var xVelocity = 10
    private var screenWidth = Resources.getSystem().displayMetrics.widthPixels
    private var screenHeight = Resources.getSystem().displayMetrics.heightPixels

    init {
        w = img.width
        h = img.height
        x = screenHeight - img.width
        y = screenWidth - (img.height * 2)
    }

    fun draw(canvas : Canvas) {
        canvas.drawBitmap(img, x.toFloat(), y.toFloat(), null)
    }

    fun update() {
        x -= (xVelocity)
        //y = touch_y - h / 2
    }

}