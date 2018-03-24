package ability.co.mz.nahu.exceptions

import java.lang.Exception

/**
 * Created by Paulo Enoque on 3/24/2018.
 */
class ComponentNotSetException(private val component: String): Exception() {

    override val message: String?
        get() = "No $component has been specified, " +
                "make sure to initialize the $component variable in the permissionManager function"
}