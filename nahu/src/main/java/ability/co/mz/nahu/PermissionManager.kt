package ability.co.mz.nahu

import android.app.Activity
import android.support.v4.app.Fragment

private var handlerRequestCode: Int? = null
const val DEFAULT_NAHU_REQUEST_CODE = 123

fun Activity.requestNahuPermissions(block: PermissionHandler.() -> Unit) {
    val handler = PermissionHandler(this)
    handlerRequestCode = handler.requestCode
    handler.block()
}

fun Fragment.requestNahuPermissions(block: PermissionHandler.() -> Unit) {
    val handler = PermissionHandler(this)
    handlerRequestCode = handler.requestCode
    handler.block()
}

fun handlePermissionsResult(block: ResultHandler.() -> Unit) {
    val resultHandler = ResultHandler()
    if (handlerRequestCode == resultHandler.requestCode) {
        resultHandler.block()
    }
}