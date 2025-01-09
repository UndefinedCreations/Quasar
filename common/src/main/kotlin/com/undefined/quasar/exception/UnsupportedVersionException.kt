package com.undefined.quasar.exception

class UnsupportedVersionException(version: String) : RuntimeException("This version [$version] of Minecraft is unsupported by Quasar api")