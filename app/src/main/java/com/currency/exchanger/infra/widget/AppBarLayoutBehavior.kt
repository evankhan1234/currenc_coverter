package com.currency.exchanger.infra.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.OverScroller
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.appbar.AppBarLayout
import java.lang.reflect.Field

class AppBarLayoutBehavior(context: Context?, attrs: AttributeSet?) :
    AppBarLayout.Behavior(context, attrs) {
    private var isFlinging = false
    private var shouldBlockNestedScroll = false
    override fun onInterceptTouchEvent(
        parent: CoordinatorLayout,
        child: AppBarLayout,
        ev: MotionEvent
    ): Boolean {
        LogUtil.d(TAG, "onInterceptTouchEvent:" + child.totalScrollRange)
        shouldBlockNestedScroll = isFlinging
        when (ev.actionMasked) {
            MotionEvent.ACTION_DOWN ->                 // Stop fling when your finger touches the screen
                stopAppbarLayoutFling(child)
            else -> {}
        }
        return super.onInterceptTouchEvent(parent, child, ev)
    }// Possibly 28 or more versions// Support design 27 and the following version

    /**
     * Reflect to get private flingRunnable attributes, considering the problem of variable name modification after support 28
     * @return Field
     * @throws NoSuchFieldException
     */
    @get:Throws(NoSuchFieldException::class)
    private val flingRunnableField: Field?
        private get() {
            val superclass: Class<*>? = this.javaClass.superclass
            return try {
                // Support design 27 and the following version
                var headerBehaviorType: Class<*>? = null
                if (superclass != null) {
                    headerBehaviorType = superclass.superclass.superclass
                }
                headerBehaviorType?.getDeclaredField("flingRunnable")
            } catch (e: NoSuchFieldException) {
                e.printStackTrace()
                // Possibly 28 or more versions
                val headerBehaviorType = superclass!!.superclass
                headerBehaviorType?.getDeclaredField("mFlingRunnable")
            }
        }// Possibly 28 or more versions// Support design 27 and the following version

    /**
     * Reflect to get private scroller attributes, considering the problem of variable name modification after support 28
     * @return Field
     * @throws NoSuchFieldException
     */
    @get:Throws(NoSuchFieldException::class)
    private val scrollerField: Field?
        private get() {
            val superclass: Class<*>? = this.javaClass.superclass
            return try {
                // Support design 27 and the following version
                var headerBehaviorType: Class<*>? = null
                if (superclass != null) {
                    headerBehaviorType = superclass.superclass.superclass
                }
                headerBehaviorType?.getDeclaredField("scroller")
            } catch (e: NoSuchFieldException) {
                e.printStackTrace()
                // Possibly 28 or more versions
                val headerBehaviorType = superclass!!.superclass
                headerBehaviorType?.getDeclaredField("mScroller")
            }
        }

    /**
     * Stop appbarLayout's fling event
     * @param appBarLayout
     */
    private fun stopAppbarLayoutFling(appBarLayout: AppBarLayout) {
        // Get the flingRunnable variable in HeaderBehavior by reflection
        try {
            val flingRunnableField = flingRunnableField
            val scrollerField = scrollerField
            if (flingRunnableField != null) {
                flingRunnableField.isAccessible = true
            }
            if (scrollerField != null) {
                scrollerField.isAccessible = true
            }
            var flingRunnable: Runnable? = null
            try {
                if (flingRunnableField != null) {
                    flingRunnable = flingRunnableField[this] as Runnable
                }
                val overScroller = scrollerField!![this] as OverScroller
                if (flingRunnable != null) {
                    LogUtil.d(TAG, "Flying Runnable")
                    appBarLayout.removeCallbacks(flingRunnable)
                    flingRunnableField!![this] = null
                }
                if (overScroller != null && !overScroller.isFinished) {
                    overScroller.abortAnimation()
                }
            } catch (e: Exception) {
            }
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }
    }

    override fun onStartNestedScroll(
        parent: CoordinatorLayout, child: AppBarLayout,
        directTargetChild: View, target: View,
        nestedScrollAxes: Int, type: Int
    ): Boolean {
        LogUtil.d(TAG, "onStartNestedScroll")
        stopAppbarLayoutFling(child)
        return super.onStartNestedScroll(
            parent, child, directTargetChild, target,
            nestedScrollAxes, type
        )
    }

    override fun onNestedPreScroll(
        coordinatorLayout: CoordinatorLayout,
        child: AppBarLayout, target: View,
        dx: Int, dy: Int, consumed: IntArray, type: Int
    ) {
        LogUtil.d(
            TAG, "onNestedPreScroll:" + child.totalScrollRange
                    + " ,dx:" + dx + " ,dy:" + dy + " ,type:" + type
        )
        // When type returns to 1, it indicates that the current target is in a non-touch sliding.
        // The bug is caused by the sliding of the NestedScrolling Child2 interface in Coordinator Layout when the AppBar is sliding
        // The subclass has not ended its own fling
        // So here we listen for non-touch sliding of subclasses, and then block the sliding event to AppBarLayout
        if (type == TYPE_FLING) {
            isFlinging = true
        }
        if (!shouldBlockNestedScroll) {
            super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type)
        }
    }

    override fun onNestedScroll(
        coordinatorLayout: CoordinatorLayout, child: AppBarLayout,
        target: View, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int,
        dyUnconsumed: Int, type: Int, consumed: IntArray
    ) {
        if (!shouldBlockNestedScroll) {
            super.onNestedScroll(
                coordinatorLayout,
                child,
                target,
                dxConsumed,
                dyConsumed,
                dxUnconsumed,
                dyUnconsumed,
                type,
                consumed
            )
        }
    }

    //
    //    @Override
    //    public void onNestedScroll(CoordinatorLayout coordinatorLayout, AppBarLayout child,
    //                               View target, int dxConsumed, int dyConsumed, int
    //            dxUnconsumed, int dyUnconsumed, int type) {
    //        LogUtil.d(TAG, "onNestedScroll: target:" + target.getClass() + " ,"
    //                + child.getTotalScrollRange() + " ,dxConsumed:"
    //                + dxConsumed + " ,dyConsumed:" + dyConsumed + " " + ",type:" + type);
    //        if (!shouldBlockNestedScroll) {
    //            super.onNestedScroll(coordinatorLayout, child, target, dxConsumed,
    //                    dyConsumed, dxUnconsumed, dyUnconsumed, type);
    //        }
    //    }
    override fun onStopNestedScroll(
        coordinatorLayout: CoordinatorLayout, abl: AppBarLayout,
        target: View, type: Int
    ) {
        LogUtil.d(TAG, "onStopNestedScroll")
        super.onStopNestedScroll(coordinatorLayout, abl, target, type)
        isFlinging = false
        shouldBlockNestedScroll = false
    }

    private object LogUtil {
        fun d(tag: String?, string: String?) {
//            Log.d(tag,string);
        }
    }

    companion object {
        private const val TAG = "AppbarLayoutBehavior"
        private const val TYPE_FLING = 1
    }
}