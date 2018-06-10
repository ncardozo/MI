package ncardozo.mi

import android.content.*
import android.graphics.BitmapFactory
import android.graphics.Canvas
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

    init {
        holder.addCallback(this)
        thread = GameThread(holder, this)
    }

    override fun surfaceCreated(p0: SurfaceHolder?) {
        intruder = Intruder(BitmapFactory.decodeResource(resources, R.drawable.green))
        enemy = Enemy(BitmapFactory.decodeResource(resources, R.drawable.cop_car))

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
        intruder!!.update()
        //if(touched)
            enemy!!.update()
    }

    override fun draw(canvas : Canvas) {
        super.draw(canvas)

        intruder!!.draw(canvas)
        enemy!!.draw(canvas)
    }

    override fun onTouchEvent(event : MotionEvent) : Boolean {
        touched_x = event.x.toInt()
        touched_y = event.y.toInt()

        val action = event.action
        when(action) {
            MotionEvent.ACTION_DOWN -> touched = true
            MotionEvent.ACTION_MOVE -> touched = true
            MotionEvent.ACTION_UP -> touched = false
            MotionEvent.ACTION_CANCEL ->  touched = false
            MotionEvent.ACTION_OUTSIDE -> touched = false
        }
        return true
    }
}