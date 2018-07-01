package ability.co.mz.nahu

import ability.co.mz.nahu.exceptions.ComponentNotSetException
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.widget.Toast

/**
 * Created by GHOST on 3/3/2018.
 */
class PermissionHandler {
    var permissions: Array<String>? = null
    var activity: Activity? = null
    private var context: Context? = null
    var requestCode: Int? = null
    var permissionExplanation: String? = null

    fun then(block: () -> Unit) {

        if (noNullsFound()) {
            context = activity?.baseContext

            // Here, thisActivity is the current activity
            if (!hasPermissions()) {

                // Should we show an explanation?
                if (shouldShowExplanation()) {

                    // Show an explanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.
                    permissionExplanation?.let {
                        Toast.makeText(context, permissionExplanation, Toast.LENGTH_LONG).show()
                    }

//                  Request the permission again
                    ActivityCompat.requestPermissions(activity!!,
                            permissions!!,
                            requestCode!!)

                } else {

                    // No explanation needed, we can request the permission.
                    ActivityCompat.requestPermissions(activity!!,
                            permissions!!,
                            requestCode!!)


                }
            } else {
                // Permission has already been granted
                block()

            }
        }


    }

    private fun noNullsFound(): Boolean {
        if (activity == null)    throw ComponentNotSetException(component = "Activity")
        if (permissions == null || permissions?.size == 0) {
            throw ComponentNotSetException(component = "Permissions")
        }
        if (requestCode == null) throw ComponentNotSetException(component = "Request Code")

        return true
    }

    private fun hasPermissions(): Boolean {
        for (permission in permissions!!) {
            if (ContextCompat.checkSelfPermission(context!!, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }

    private fun shouldShowExplanation(): Boolean {
        for (permission in permissions!!) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity!!, permission)) {
                return true
            }
        }
        return false
    }

}