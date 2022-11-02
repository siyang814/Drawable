package com.example.drawable

import android.graphics.drawable.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.drawable.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding

    private var clipDrawable: ClipDrawable?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
//        var b = false
//        if ( b )
//        Glide.with(this).load("").into(binding.imageView)

        binding.button.setOnClickListener {
            var transition = binding.imageView.background as TransitionDrawable?
            transition?.startTransition(1000)
        }

        clipDrawable = binding.textView.background as ClipDrawable?

        clipDrawable?.level = 9000

        var scaleDrawable = binding.textView1.background as ScaleDrawable?
        scaleDrawable?.level = 9000

        binding.seekBar.progress = 9000

        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                clipDrawable?.level = p1
                scaleDrawable?.level = p1
                var scale = p1 / 10000f
                binding.button.scaleX = scale
            }
            override fun onStartTrackingTouch(p0: SeekBar?) {}
            override fun onStopTrackingTouch(p0: SeekBar?) {}

        })

        var gradient = GradientDrawable()
        gradient?.let {
            it.setColor(ContextCompat.getColor(this, R.color.design_default_color_secondary))
            it.cornerRadii = floatArrayOf(30f, 30f, 30f, 30f, 60f, 60f, 60f, 60f)
            it.setStroke(3, ContextCompat.getColor(this, R.color.black))
        }

        binding.textView2.background = gradient

        gradient = GradientDrawable()
        gradient?.let {
            it.setColor(ContextCompat.getColor(this, R.color.black))
            it.cornerRadii = floatArrayOf(30f, 30f, 30f, 30f, 60f, 60f, 60f, 60f)
        }
        var gradient1 = GradientDrawable()
        gradient1?.let {
            it.setColor(ContextCompat.getColor(this, R.color.design_default_color_secondary))
            it.cornerRadii = floatArrayOf(30f, 30f, 30f, 30f, 60f, 60f, 60f, 60f)

        }

        var drawables = arrayOf(gradient, gradient1)

        var layer = LayerDrawable(drawables)
        layer?.let {
            it.setLayerInset(1, 0, 2, 0, 3)
        }

        binding.textView3.background = layer

        binding.button2.setOnClickListener {
            binding.clipLayout.setRadius(50f, 20f, 30f, 10f)
            binding.clipLayout.setLine(2, "#ff0000")
        }

    }
}