package ability.co.mz.nahu

import ability.co.mz.nahu.exceptions.ComponentNotSetException
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.widget.Toast

/**
 * Created by GHOST on 3/3/2018.
 */
class PermissionHandler {
    private var activity: Activity? = null
    private var fragment: Fragment? = null
    var permissions: Array<String>? = null
    var requestCode: Int? = null
    var permissionExplanation: String? = null

    constructor()

    constructor(activity: Activity): this() {
        this.activity = activity
    }

    constructor(fragment: Fragment): this() {
        this.fragment = fragment
    }

    fun then(block: () -> Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!hasNullProperties()) {
                val context = if(activity == null) {
                    fragment!!.context
                } else {
                    activity!!.baseContext
                }

                // Here, thisActivity is the current activity
                if (!hasPermissions(context!!)) {

                    // Should we show an explanation?
                    val shouldShowExplanation = if (isRequestingFromFragment()) {
                        shouldShowExplanation(fragment!!.activity as Activity)
                    } else {
                        shouldShowExplanation(activity!!)
                    }
                    if (shouldShowExplanation) {

                        // Show an explanation to the user *asynchronously* -- don't block
                        // this thread waiting for the user's response! After the user
                        // sees the explanation, try again to request the permission.

                        Toast.makeText(context,
                                permissionExplanation ?: context
                                        .getString(R.string.default_permission_explanation),
                                Toast.LENGTH_LONG).show()
                    }
                    // Request the permission
                    requestPermissions()
                } else {
                    // Permission has already been granted
                    block()

                }
            }
        }


    }

    private fun hasNullProperties(): Boolean {
        if (permissions == null || permissions?.size == 0) {
            throw ComponentNotSetException(componentName = "Permissions")
        }
        return false
    }

    private fun hasPermissions(context: Context): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true
        }
        for (permission in permissions!!) {
            if (ContextCompat.checkSelfPermission(context, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }

    private fun shouldShowExplanation(activity: Activity): Boolean {
        for (permission in permissions!!) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                return true
            }
        }
        return false
    }

    private fun isRequestingFromFragment() = fragment != null

    private fun requestPermissions() {
        if (isRequestingFromFragment()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                fragment!!.requestPermissions(permissions!!,
                        requestCode ?: DEFAULT_NAHU_REQUEST_CODE)
            }
        } else {
            ActivityCompat.requestPermissions(activity!!,
                    permissions!!,
                    requestCode ?: DEFAULT_NAHU_REQUEST_CODE)
        }
    }

}