package com.diegomalone.githubviewer.util.extension

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

fun Disposable.addToCompositeDisposable(composite: CompositeDisposable) {
    composite.add(this)
}
