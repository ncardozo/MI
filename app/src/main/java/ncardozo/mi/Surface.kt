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
        when(touched) {
            true -> {
                intruder!!.update(-10)
                touched = false
            }
            false -> {
                intruder!!.update(10)
                touched = true
            }
        }

    }

    override fun draw(canvas : Canvas) {
        super.draw(canvas)

        intruder!!.draw(canvas)
        enemy!!.draw(canvas)
    }

    /*
    override fun onTouchEvent(event : MotionEvent) : Boolean {

        val action = event.action
        when(action) {
            MotionEvent.ACTION_DOWN -> {
                touched = true
                touched_y = 10
            }
            MotionEvent.ACTION_MOVE -> touched = false
            MotionEvent.ACTION_UP -> {
                touched = true
                touched_y = -10
            }
            MotionEvent.ACTION_CANCEL ->  touched = false
            MotionEvent.ACTION_OUTSIDE -> touched = false
        }
        return true
    }
    */
}