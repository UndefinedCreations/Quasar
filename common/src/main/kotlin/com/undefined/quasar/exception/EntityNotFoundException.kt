package com.undefined.quasar.exception


class EntityNotFoundException(entityName: String?) : Exception("[${entityName}] was not found.")