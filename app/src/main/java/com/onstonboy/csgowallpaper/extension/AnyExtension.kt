package com.onstonboy.csgowallpaper.extension

/**
 * Created by daolq on 5/15/18.
 * MedHome_Android
 */

inline fun <T : Any> T?.notNull(f: (it: T) -> Unit) {
  if (this != null) f(this)
}

inline fun <T : Any> T?.isNull(f: () -> Unit) {
  if (this == null) f()
}
