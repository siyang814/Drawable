package com.example.drawable

import android.content.Context
import android.graphics.*
import android.text.TextUtils
import android.util.AttributeSet
import androidx.cardview.widget.CardView

/**
 *   yy
 *   2022/1/4
 */
class RoundCardView:CardView {

    var myRadius:FloatArray?=null

    var paint:Paint?=null
    var colorLine:String?="#000000"
    var lineStrokeWidth = 1f

    constructor(context: Context) : this(context,null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )
    {
        var typedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundCardView)
        var tl = typedArray.getDimension(R.styleable.RoundCardView_tl_radius, 0f)
        var tr = typedArray.getDimension(R.styleable.RoundCardView_tr_radius, 0f)
        var bl = typedArray.getDimension(R.styleable.RoundCardView_bl_radius, 0f)
        var br = typedArray.getDimension(R.styleable.RoundCardView_br_radius, 0f)

        lineStrokeWidth = typedArray.getDimension(R.styleable.RoundCardView_lineWidth, 0f)
        colorLine = typedArray.getString(R.styleable.RoundCardView_lineColor)

        typedArray.recycle()
        myRadius = floatArrayOf(tl, tl, tr, tr, br, br, bl, bl)
    }


    init {
        radius = 0f
        elevation = 0f
        paint = Paint().apply {
            isAntiAlias = true
            style = Paint.Style.STROKE
//            color = Color.parseColor(colorLine)
//            strokeWidth = lineStrokeWidth
        }

    }

    override fun onDrawForeground(canvas: Canvas?) {
        if ( TextUtils.isEmpty(colorLine) || lineStrokeWidth <= 0 ) return super.onDrawForeground(canvas)
        paint?.let {
            it.color = Color.parseColor(colorLine)
            it.strokeWidth = lineStrokeWidth
        }
        myRadius?.let {
            var rectF = getDrawLineRectF()
            var path = Path()
            path.addRoundRect(rectF, it, Path.Direction.CW)
            canvas?.drawPath(path, paint!!)
        }
        super.onDrawForeground(canvas)
    }

    override fun onDraw(canvas: Canvas?) {
        myRadius?.let {
            var rectF = getDrawRectF()
            var path = Path()
            path.addRoundRect(rectF, it, Path.Direction.CW)
            canvas?.clipPath(path)
        }
        super.onDraw(canvas)
    }

    private fun getDrawLineRectF ():RectF
    {
        var rect = Rect();
        getDrawingRect(rect)
        rect.left += 1
        rect.top += 1
        rect.right -= 1
        rect.bottom -= 1
        var rectF = RectF(rect)
        return rectF;
    }

    private fun getDrawRectF ():RectF
    {
        var rect = Rect();
        getDrawingRect(rect)
        var rectF = RectF(rect)
        return rectF;
    }

    fun setRadius ( tl:Float, tr:Float, bl:Float, br:Float)
    {
        myRadius = floatArrayOf(tl, tl, tr, tr, br, br, bl, bl)
        invalidate()
    }

    fun setLine ( lineWidth:Int, color:String )
    {
        lineStrokeWidth = lineWidth.toFloat()
        colorLine = color
        invalidate()
    }


}