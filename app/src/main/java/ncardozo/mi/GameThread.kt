package ncardozo.mi

import android.graphics.Canvas
import android.view.SurfaceHolder

class GameThread(private val surfaceHolder : SurfaceHolder, private val gameView : Surface) :Thread() {

    private var running = false
    private val targetFPS = 50

    fun setRunning(isRunning : Boolean) {
        this.running = isRunning
    }

    override fun run() {
        var startTime : Long
        var waitTime : Long
        var timeMillis : Long
        val targetTime = (1000/targetFPS).toLong()

        while (running) {
            startTime = System.nanoTime()
            canvas = null
            try {
                canvas = this.surfaceHolder.lockCanvas()
                synchronized(surfaceHolder) {
                    this.gameView.update()
                    this.gameView.draw(canvas!!)
                }
            } catch (e : Exception) {
                e.printStackTrace()
            } finally {
                if(canvas != null) {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas)
                    } catch (e : Exception) {
                        e.printStackTrace()
                    }
                }
            }

            timeMillis = (System.nanoTime() - startTime) / 1000
            waitTime = targetTime - timeMillis

            try {
                sleep(waitTime)
            } catch (e : Exception) {
                e.printStackTrace()
            }
        }
    }

    companion object {
        private var canvas : Canvas? = null
    }
}