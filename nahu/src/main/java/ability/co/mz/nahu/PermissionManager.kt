package ability.co.mz.nahu

/**
 * Created by GHOST on 3/3/2018.
 */
fun permissionManager(block: PermissionHandler.() -> Unit){
    val handler = PermissionHandler()
    handler.block()
}




