package ability.co.mz.nahu.exceptions

import java.lang.Exception

/**
 * Created by Paulo Enoque on 3/24/2018.
 */
class ComponentNotSetException(private val componentName: String): Exception() {

    override val message: String?
        get() = "No $componentName has been specified, " +
                "be sure to initialize the $componentName variable in the requestNahuPermissions" +
                " function"
}