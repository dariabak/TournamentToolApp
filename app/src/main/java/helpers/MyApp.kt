package helpers
import android.app.Application


class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
            density = applicationContext.resources.displayMetrics.density
        }

    companion object {
        var density: Float = 0f
            private set
        }
}