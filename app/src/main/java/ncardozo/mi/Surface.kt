package ncardozo.mi

import android.content.*
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView

class Surface(context: Context, attributes:AttributeSet) : SurfaceView(context, attributes), SurfaceHolder.Callback {

    private val thread : GameThread
    private var intruder : Intruder? = null

    init {
        holder.addCallback(this)
        thread = GameThread(holder, this)
    }

    override fun surfaceCreated(p0: SurfaceHolder?) {
        intruder = Intruder(BitmapFactory.decodeResource(resources, R.drawable.green))

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
    }

    override fun draw(canvas : Canvas) {
        super.draw(canvas)

        intruder!!.draw(canvas)
    }
}