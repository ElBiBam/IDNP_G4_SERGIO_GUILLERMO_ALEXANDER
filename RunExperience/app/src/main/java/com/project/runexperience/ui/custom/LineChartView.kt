package com.project.runexperience.ui.custom


import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.os.Build
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.RelativeLayout.ALIGN_LEFT
import androidx.annotation.RequiresApi


class LineChartViewView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : View(context, attrs, defStyleAttr) {

    // Paint object for coloring and styling
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    // Some colors for the face background, eyes and mouth.
    private var faceColor = Color.GRAY
    private var borderColor = Color.BLACK
    // Face border width in pixels
    private var borderWidth = 1.0f
    // View size in pixels
    private var size = 320

    val chartLinesBackground = mutableListOf<Float>()
    var padTopChart = 50F
    var padBottomChart = 40F
    var padLeftChart = 50F
    var padRightChart = 50F

    var separatorLine = 50F

    var padTopChartTitle = 50F
    var padBottomChartTitle = 50F
    var chartTitle = "My Chart"
    var textPaint = TextPaint()

    var widthCharTitle = 200F
    var heightCharTitle = 40F
    var textHeight = 40

    var padTopHTitle = 40F
    var hTitle = "X Title"
    var hTitleHeight = 0

    var padTopHLabels = 40F
    var heightHLabels = 50F
    var textHLabel = "Dia"
    var pointsHLabels = FloatArray(0)

    var vTitle = "Y Title"
    var vTitleHeight = 0

    public fun setNPointDatas(points: FloatArray ){
        pointsHLabels = points
    }
    public fun setTitle(title: String){
        chartTitle = title
    }
    @JvmName("setHTitle1")
    public fun setHTitle(hTitle: String){
        this.hTitle = hTitle
    }
    @JvmName("setVTitle1")
    public fun setVTitle(vTitle: String){
        this.vTitle = vTitle
    }
    private fun init() {
        pointsHLabels = FloatArray(4)
        pointsHLabels.set(0, 2*100F)
        pointsHLabels.set(1, 1*100F)
        pointsHLabels.set(2, 3*100F)
        pointsHLabels.set(3, 0*100F)
    }

    private fun measureDimension(desiredSize: Int, measureSpec: Int): Int {
        var result: Int
        val specMode = MeasureSpec.getMode(measureSpec)
        val specSize = MeasureSpec.getSize(measureSpec)
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize
        } else {
            result = desiredSize
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize)
            }
        }
        if (result < desiredSize) {
            Log.e("ChartView", "The view is too small, the content might get cut")
        }
        return result
    }
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        Log.v("Chart onMeasure w", MeasureSpec.toString(widthMeasureSpec))
        Log.v("Chart onMeasure h", MeasureSpec.toString(heightMeasureSpec))
        val desiredWidth = suggestedMinimumWidth + paddingLeft + paddingRight
        val desiredHeight = suggestedMinimumHeight + paddingTop + paddingBottom
        setMeasuredDimension(measureDimension(desiredWidth, widthMeasureSpec),
                measureDimension(desiredHeight, heightMeasureSpec))
    }

    /**
     * @return text height
     */
    private fun getTextHeight(text: String, paint: Paint): Int {
        val rect = Rect()
        paint.getTextBounds(text, 0, text.length, rect)
        return rect.height()
    }
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onDraw(canvas: Canvas) {
        // call the super method to keep any drawing from the parent side.
        super.onDraw(canvas)

        init();
        canvas.drawColor(Color.WHITE)

        drawHTitle(canvas)
        drawTitle(canvas)
        drawVTitle(canvas)

        drawHLabels(canvas)

        drawChartBackground(canvas)
        drawHPoints(canvas)
    }
    @RequiresApi(Build.VERSION_CODES.M)
    private fun drawTitle(canvas: Canvas) {
        //var centerX = width/2F
        val myTextPaint = TextPaint()
        myTextPaint.isAntiAlias = true
        myTextPaint.textSize = 16 * resources.displayMetrics.density
        myTextPaint.color = Color.BLACK
        myTextPaint.style = Paint.Style.FILL;

        val width = width - hTitleHeight.toInt() - padLeftChart.toInt() - padRightChart.toInt()
        val alignment = Layout.Alignment.ALIGN_CENTER
        val spacingMultiplier = 1f
        val spacingAddition = 0f
        val includePadding = false

        val builder = StaticLayout.Builder.obtain(chartTitle
                , 0, chartTitle.length
                , myTextPaint, width)
                .setAlignment(Layout.Alignment.ALIGN_CENTER)
                .setLineSpacing(spacingAddition, spacingMultiplier)
                .setIncludePad(includePadding)
                .setMaxLines(3)

        val myStaticLayout = builder.build()
        textHeight = myStaticLayout.height
        canvas.save();
        canvas.translate(padLeftChart.toInt()+hTitleHeight.toFloat(), padRightChart);
        myStaticLayout.draw(canvas);
        canvas.restore();
    }
    private fun drawChartBackground(canvas: Canvas) {
        var countSeparatorLine = padTopChart+heightCharTitle+padBottomChartTitle
        for(i in 1..10) {
            // line starting x y
            chartLinesBackground.add(hTitleHeight + padLeftChart) // x
            chartLinesBackground.add(countSeparatorLine) // y
            // line ending point x y
            chartLinesBackground.add(width - padLeftChart)
            chartLinesBackground.add(countSeparatorLine)
            countSeparatorLine += separatorLine
        }

        paint.color = borderColor
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = borderWidth

        canvas.drawLines(chartLinesBackground.toFloatArray(), paint)

    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun drawHTitle(canvas: Canvas) {
        //var centerX = width/2F
        val myTextPaint = TextPaint()
        myTextPaint.isAntiAlias = true
        myTextPaint.textSize = 16 * resources.displayMetrics.density
        myTextPaint.color = Color.BLACK
        myTextPaint.style = Paint.Style.FILL;

        val width = width - hTitleHeight - padLeftChart.toInt() - padRightChart.toInt()
        val alignment = Layout.Alignment.ALIGN_CENTER
        val spacingMultiplier = 1f
        val spacingAddition = 0f
        val includePadding = false

        val builder = StaticLayout.Builder.obtain(hTitle, 0
                , hTitle.length, myTextPaint, width)
                .setAlignment(Layout.Alignment.ALIGN_CENTER)
                .setLineSpacing(spacingAddition, spacingMultiplier)
                .setIncludePad(includePadding)
                .setMaxLines(3)

        val myStaticLayout = builder.build()
        hTitleHeight = myStaticLayout.height
        canvas.save();
        canvas.translate(padLeftChart+vTitleHeight
                , padTopChart
                +hTitleHeight
                +padBottomChart
                +padTopHLabels
                +heightHLabels
                +padTopHTitle
                +separatorLine*9);
        myStaticLayout.draw(canvas);
        canvas.restore();
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun drawVTitle(canvas: Canvas) {
        //var centerX = width/2F
        val myTextPaint = TextPaint()
        myTextPaint.isAntiAlias = true
        myTextPaint.textSize = 16 * resources.displayMetrics.density
        myTextPaint.color = Color.BLACK
        myTextPaint.style = Paint.Style.FILL;

        val width = separatorLine.toInt()*9
        val alignment = Layout.Alignment.ALIGN_CENTER
        val spacingMultiplier = 1f
        val spacingAddition = 0f
        val includePadding = false

        val builder = StaticLayout.Builder.obtain(vTitle
                , 0, vTitle.length
                , myTextPaint, width)
                .setAlignment(Layout.Alignment.ALIGN_CENTER)
                .setLineSpacing(spacingAddition, spacingMultiplier)
                .setIncludePad(includePadding)
                .setMaxLines(3)

        val myStaticLayout = builder.build()
        vTitleHeight = myStaticLayout.height
        canvas.save();
        var countSeparatorLine = padTopChart+padTopChartTitle+padBottomChartTitle

        val rect = Rect()
        //myTextPaint.getTextBounds(xLabel, 0, xLabel.length, rect)

        canvas.rotate(-90f);

        canvas.translate(-width.toFloat()-countSeparatorLine, padLeftChart);
        myStaticLayout.draw(canvas);

        canvas.restore();
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun drawHLabels(canvas: Canvas) {
        var n = pointsHLabels.size
        var widthChartLine = (width - hTitleHeight
                - padLeftChart.toInt() - padRightChart.toInt())/n
        for(i in 1..n) {
            var textHLabel = "$textHLabel $i"
            val myTextPaint = TextPaint()
            myTextPaint.isAntiAlias = true
            myTextPaint.textSize = 16 * resources.displayMetrics.density
            myTextPaint.color = Color.BLACK
            myTextPaint.style = Paint.Style.FILL;

            val width = (width - hTitleHeight
            - padLeftChart.toInt() - padRightChart.toInt())/n
            val alignment = Layout.Alignment.ALIGN_CENTER
            val spacingMultiplier = 1f
            val spacingAddition = 0f
            val includePadding = false

            val builder = StaticLayout.Builder.obtain(textHLabel, 0, textHLabel.length, myTextPaint, width)
                    .setAlignment(Layout.Alignment.ALIGN_CENTER)
                    .setLineSpacing(spacingAddition, spacingMultiplier)
                    .setIncludePad(includePadding)
                    .setMaxLines(3)

            val myStaticLayout = builder.build()
            hTitleHeight = myStaticLayout.height
            canvas.save();
            canvas.translate(padLeftChart + hTitleHeight
                    + widthChartLine*(i-1)
                    , padTopChart
                    + hTitleHeight
                    + padBottomChart
                    + padTopHLabels
                    + separatorLine * 9)
            myStaticLayout.draw(canvas)
            canvas.restore()

        }
    }
    private fun drawHPoints(canvas: Canvas) {

        var countSeparatorLine = padTopChart+heightCharTitle+padBottomChartTitle
        var heightChartBackground = separatorLine.toInt()*9
        var n = pointsHLabels.size
        var widthChartLine = (width - hTitleHeight
                - padLeftChart.toInt() - padRightChart.toInt())/n

        var startXPoint = padLeftChart+vTitleHeight+widthChartLine/2
        var startYPoint = countSeparatorLine + heightChartBackground

        var chartLines = FloatArray(4*n)
        var startXPoint_temp = startXPoint
        var startYPoint_temp = startYPoint-pointsHLabels[0]
        var count = 0
        for(i in 1..n-1) {
            // line starting x y
            chartLines.set(count++, startXPoint_temp) // x
            chartLines.set(count++, startYPoint_temp) // y
            // line ending point x y
            chartLines.set(count++, startXPoint_temp+widthChartLine)
            chartLines.set(count++, startYPoint-pointsHLabels[i])

            startXPoint_temp += widthChartLine
            startYPoint_temp = startYPoint-pointsHLabels[i]
            //countSeparatorLine += separatorLine
        }

        paint.color = borderColor
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = borderWidth

        canvas.drawLines(chartLines, paint)

    }
}
