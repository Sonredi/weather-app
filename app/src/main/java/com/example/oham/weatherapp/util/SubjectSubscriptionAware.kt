package com.example.oham.weatherapp.util

import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger

class SubjectSubscriptionAware<T>(val source: Observable<T>) {

    companion object {
        private val ADD_SUBSCRIBER = 1
        private val REMOVE_SUBSCRIBER = -1
    }

    private val sourceEmitter = BehaviorSubject.create<T>()

    private var sourceDisposable: Disposable? = null

    private val compositeDisposable = CompositeDisposable()

    private val subscriptionManager = BehaviorSubject.create<Int>()

    private val subscriptionCounter = AtomicInteger()

    private val isEmitting = AtomicBoolean(false)

    val publisher = sourceEmitter
            .doOnSubscribe { subscriptionManager.onNext(ADD_SUBSCRIBER) }
            .doOnDispose { subscriptionManager.onNext(REMOVE_SUBSCRIBER) }

    init {
        compositeDisposable.add(subscriptionManager.subscribe { event -> onSubscriptionEvent(event) })
    }

    private fun onSubscriptionEvent(event: Int) {
        val numSubscribers = subscriptionCounter.addAndGet(event)
        if (numSubscribers > 0 && isEmitting.compareAndSet(false, true)) {
            sourceDisposable = source.subscribe { value -> sourceEmitter.onNext(value) }
        } else if (numSubscribers == 0 && isEmitting.compareAndSet(true, false)) {
            sourceDisposable?.dispose()
        }
    }

}