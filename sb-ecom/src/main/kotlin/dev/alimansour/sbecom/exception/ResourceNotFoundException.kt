package dev.alimansour.sbecom.exception

class ResourceNotFoundException : RuntimeException {
    var resourceName: String? = null
    var field: String? = null
    var fieldName: String? = null
    var fieldId: Long? = null

    constructor()

    constructor(
        resourceName: String?,
        field: String?,
        fieldName: String?
    ) : super("$resourceName not found with $field: $fieldName") {
        this.resourceName = resourceName
        this.field = field
        this.fieldName = fieldName
    }

    constructor(
        resourceName: String?,
        field: String?,
        fieldId: Long?
    ) : super("$resourceName not found with $field: $fieldId") {
        this.resourceName = resourceName
        this.field = field
        this.fieldId = fieldId
    }

}
