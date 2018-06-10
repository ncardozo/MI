package ncardozo.mi

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas

class Intruder(var img : Bitmap) {
    var x = 0
    var y = 0
    var w = 0
    var h = 0
    private var xVelocity = 20
    private var yVelocity = 20
    private var screenWidth = Resources.getSystem().displayMetrics.widthPixels
    private var screenHeight = Resources.getSystem().displayMetrics.heightPixels

    init {
        w = img.width
        h = img.height
        x = screenWidth/2
        y = screenHeight/2
    }

    fun draw(canvas : Canvas) {
        canvas.drawBitmap(img, x.toFloat(), y.toFloat(), null)
    }

    fun update() {
        if(x > screenWidth - img.width || x < img.width) {
            xVelocity = xVelocity * -1
        }
        if(y > screenHeight - img.height || y < img.height) {
            yVelocity = yVelocity * -1
        }

        x += xVelocity
        y += yVelocity
    }

}