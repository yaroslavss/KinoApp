package com.yara.kinoapp.utils

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

class AutoDisposable : DefaultLifecycleObserver {
    // use CompositeDisposable to cancel Observable
    lateinit var compositeDisposable: CompositeDisposable

    // lifecycle to observe
    fun bindTo(lifecycle: Lifecycle) {
        lifecycle.addObserver(this)
        compositeDisposable = CompositeDisposable()
    }

    // add Observable into CompositeDisposable
    fun add(disposable: Disposable) {
        if (::compositeDisposable.isInitialized) {
            compositeDisposable.add(disposable)
        } else {
            throw NotImplementedError("must bind AutoDisposable to a Lifecycle first")
        }
    }

    // This method will be called before the LifecycleOwner's onDestroy method is called.
    override fun onDestroy(owner: LifecycleOwner) {
        compositeDisposable.dispose()
    }
}

// extension function
fun Disposable.addTo(autoDisposable: AutoDisposable) {
    autoDisposable.add(this)
}