package ability.co.mz.nahu

/**
 * Created by GHOST on 3/3/2018.
 */
private var handlerRequestCode: Int? = null

fun permissionManager(block: PermissionHandler.() -> Unit) {
    val handler = PermissionHandler()
    handlerRequestCode = handler.requestCode
    handler.block()
}

fun handlePermissionsResult(block: ResultHandler.() -> Unit) {
    val resultHandler = ResultHandler()
    if (handlerRequestCode == resultHandler.requestCode) {
        resultHandler.block()
    }
}