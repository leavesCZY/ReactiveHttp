package github.leavesc.reactivehttpsamples.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * @Author: leavesC
 * @Date: 2020/10/26 15:30
 * @Desc:
 * @GitHub：https://github.com/leavesC
 */
open class CommonItemDecoration : RecyclerView.ItemDecoration {

    companion object {
        private fun getDivider(context: Context): Drawable {
            val attrs = intArrayOf(android.R.attr.listDivider)
            val typedArray = context.obtainStyledAttributes(attrs)
            val drawable = typedArray.getDrawable(0)
            typedArray.recycle()
            return drawable!!
        }
    }

    private val orientation: Int

    private val drawable: Drawable?

    constructor(drawable: Drawable?, orientation: Int) {
        this.drawable = drawable
        if (orientation != LinearLayoutManager.HORIZONTAL && orientation != LinearLayoutManager.VERTICAL) {
            throw RuntimeException("CommonItemDecoration RuntimeException")
        }
        this.orientation = orientation
    }

    constructor(context: Context, orientation: Int = LinearLayoutManager.VERTICAL) : this(
            getDivider(context),
            orientation
    )

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view)
        if (drawDecoration(position, parent.childCount)) {
            if (orientation == LinearLayoutManager.HORIZONTAL) {
                outRect.set(0, 0, drawable!!.intrinsicWidth, 0)
            } else if (orientation == LinearLayoutManager.VERTICAL) {
                outRect.set(0, 0, 0, drawable!!.intrinsicHeight)
            }
        } else {
            outRect.set(0, 0, 0, 0)
        }
    }

    override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(canvas, parent, state)
        if (drawable == null || parent.layoutManager !is LinearLayoutManager) {
            return
        }
        if (orientation == LinearLayoutManager.HORIZONTAL) {
            drawVerticalDivider(canvas, parent)
        } else if (orientation == LinearLayoutManager.VERTICAL) {
            drawHorizontalDivider(canvas, parent)
        }
    }

    private fun drawVerticalDivider(canvas: Canvas, parent: RecyclerView) {
        canvas.save()
        for (i in 0 until parent.childCount) {
            if (drawDecoration(i, parent.childCount)) {
                val child = parent.getChildAt(i)

                //受 child layout_marginEnd 属性的影响
                val params = child.layoutParams as RecyclerView.LayoutParams
                val left = child.right + params.rightMargin

                //不受 child layout_marginEnd 属性的影响，会直接绘制在 child 右侧
                //              int left = child.getRight();
                val top = child.top

                val right = left + drawable!!.intrinsicWidth
                val bottom = child.bottom
                drawable.setBounds(left, top, right, bottom)
                drawable.draw(canvas)
            }
        }
        canvas.restore()
    }

    private fun drawHorizontalDivider(canvas: Canvas, parent: RecyclerView) {
        canvas.save()
        for (i in 0 until parent.childCount) {
            if (drawDecoration(i, parent.childCount)) {
                val child = parent.getChildAt(i)
                val left = child.left

                //不受 child layout_marginBottom 属性的影响，会直接绘制在 child 底部
                //            int top = child.getBottom();

                //会受 child layout_marginBottom 属性的影响
                val params = child.layoutParams as RecyclerView.LayoutParams
                val top = child.bottom + params.bottomMargin

                val right = child.right
                val bottom = top + drawable!!.intrinsicHeight
                drawable.setBounds(left, top, right, bottom)
                drawable.draw(canvas)
            }
        }
        canvas.restore()
    }

    protected open fun drawDecoration(index: Int, total: Int): Boolean {
        return true
    }

}