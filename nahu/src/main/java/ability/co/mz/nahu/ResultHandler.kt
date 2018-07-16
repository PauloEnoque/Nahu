package ability.co.mz.nahu

import android.content.pm.PackageManager

class ResultHandler {
    var requestCode: Int? = null
    var permissions: Array<out String>? = null
    var grantResults: IntArray? = null

    fun onPermissionDenied(block: ResultHandler.(deniedPermission: String) -> Unit) {
        for (i in 0 until grantResults!!.size) {
            if (!isPermissionGranted(grantResults!![i])) {
                block(permissions!![i])
            }
        }
    }

    fun onPermissionGranted(block: ResultHandler.(grantedPermission: String) -> Unit) {
        for (i in 0 until grantResults!!.size) {
            if (isPermissionGranted(grantResults!![i])) {
                block(permissions!![i])
            }
        }
    }

    private fun isPermissionGranted(result: Int): Boolean = result == PackageManager.PERMISSION_GRANTED
}