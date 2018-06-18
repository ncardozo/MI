package ncardozo.mi

import android.content.*
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.MotionEvent

class Surface(context: Context, attributes:AttributeSet) : SurfaceView(context, attributes), SurfaceHolder.Callback {

    private val thread : GameThread
    private var intruder : Intruder? = null
    private var enemy : Enemy? = null

    private var touched = false
    private var touched_x = 0
    private var touched_y = 0

    var score = 0

    private var intruderColor = 1

    init {
        holder.addCallback(this)
        thread = GameThread(holder, this)
    }

    override fun surfaceCreated(p0: SurfaceHolder?) {
        intruder = Intruder(BitmapFactory.decodeResource(resources, R.drawable.green))
        enemy = Enemy(BitmapFactory.decodeResource(resources, R.drawable.copcar))

        thread.setRunning(true)
        thread.start()
    }

    override fun surfaceDestroyed(p0 : SurfaceHolder?) {
        var retry = true
        while(retry) {
            try {
                thread.setRunning(false)
                thread.join()
            } catch(e : Exception) {
                e.printStackTrace()
            }
            retry = false
        }
    }

    override fun surfaceChanged(p0: SurfaceHolder?, p1: Int, p2: Int, p3: Int) {

    }

    fun update() {
        enemy!!.update()
        intruder!!.update(touched_y)
        if (intruder!!.lifePower <= 0) {
            when(intruderColor ) {
                1 -> intruder = Intruder(BitmapFactory.decodeResource(resources, R.drawable.red))
                2 -> intruder = Intruder(BitmapFactory.decodeResource(resources, R.drawable.yellow))
                3 -> intruder = Intruder(BitmapFactory.decodeResource(resources, R.drawable.blue))
            }
            intruderColor.inc()
        }

    }

    override fun draw(canvas : Canvas) {
        super.draw(canvas)
        var typeWritter = Paint()
        typeWritter.setARGB(255, 255, 255 , 255)
        typeWritter.textSize  = 30f
        typeWritter.setTypeface(Typeface.SERIF)
        canvas.drawText("points:" +score.toString(), 60.toFloat(), 60.toFloat(), typeWritter)

        intruder!!.draw(canvas)
        enemy!!.draw(canvas)
    }


    override fun onTouchEvent(event : MotionEvent) : Boolean {

        val action = event.action
        when(action) {
            MotionEvent.ACTION_DOWN -> {
                touched_y = -40
            }
            else -> {
                touched_y = 0
            }
        }
        return true
    }

}