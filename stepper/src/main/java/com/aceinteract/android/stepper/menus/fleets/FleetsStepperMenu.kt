/*
 * Copyright 2020 Ayomide Falobi.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.aceinteract.android.stepper.menus.fleets

import android.animation.ValueAnimator
import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import android.widget.ProgressBar
import com.aceinteract.android.stepper.R
import com.aceinteract.android.stepper.menus.base.StepperMenu
import com.aceinteract.android.stepper.menus.base.StepperMenuItem

/**
 * Menu showing steps progress in a fleets.
 *
 * @property fleetDuration how long fleets should stay for.
 */
public class FleetsStepperMenu(
    context: Context,
    override var widgetColor: Int,
    override var iconSizeInPX: Int,
    override var textAppearance: Int,
    override var textColor: Int,
    override var textSizeInPX: Int?,
    public var fleetDuration: Long = 5000
) : StepperMenu(context, widgetColor, iconSizeInPX, textAppearance, textColor, textSizeInPX) {

    override var currentStep: Int = 0

    override val menuItems: List<StepperMenuItem> get() = _menuItems

    private val _menuItems: ArrayList<FleetsStepperMenuItem> = arrayListOf()

    override fun updateUI() {
        _menuItems.forEachIndexed { index, item ->
            val progressBar = item.view.findViewById<ProgressBar>(R.id.progress_stepper).apply {
                progressTintList = ColorStateList.valueOf(widgetColor)
                progressBackgroundTintList = ColorStateList.valueOf(widgetColor)
            }
            item.progressAnimator.cancel()
            when {
                index < currentStep -> {
                    progressBar.progress = 100
                }
                index > currentStep -> {
                    progressBar.progress = 0
                }
                index == currentStep -> {
                    item.progressAnimator =
                        ValueAnimator.ofFloat(0F, 100F).setDuration(fleetDuration).apply {
                            addUpdateListener {
                                val progress = (it.animatedValue as Float).toInt()
                                progressBar.progress = progress
                                if (progress >= 100) {
                                    onStepChangedListener(currentStep + 1)
                                }
                            }
                            start()
                        }
                }
            }
        }
    }

    /**
     * Pause all running fleet animations.
     */
    public fun pause() {
        _menuItems.forEach { it.progressAnimator.pause() }
    }

    /**
     * Resume all running fleet animations.
     */
    public fun resume() {
        _menuItems.forEach { it.progressAnimator.resume() }
    }

    private fun addItemView(
        groupId: Int,
        itemId: Int,
        order: Int
    ): FleetsStepperMenuItem {
        val progressView = LayoutInflater.from(context).inflate(
            R.layout.stepper_progress,
            this,
            false
        ) as FrameLayout

        progressView.id = View.generateViewId()

        addView(progressView)

        (progressView.layoutParams as LayoutParams).apply {
            endToEnd = id
            topToTop = id
        }

        if (_menuItems.isNotEmpty()) {
            val lastView = _menuItems.last().view

            (lastView.layoutParams as LayoutParams).endToStart = progressView.id

            (progressView.layoutParams as LayoutParams).apply {
                startToEnd = lastView.id
                marginStart =
                    context.resources.getDimension(R.dimen.stepper_progress_spacing).toInt()
            }
        } else {
            (progressView.layoutParams as LayoutParams).startToStart = id
        }

        return FleetsStepperMenuItem(
            itemId,
            groupId,
            order,
            progressView,
            ValueAnimator.ofFloat()
        )
    }

    /**
     * Remove all menu items.
     */
    override fun clear() {
        _menuItems.clear()
        updateUI()
    }

    /**
     * Remove menu item with item id [id].
     */
    override fun removeItem(id: Int) {
        _menuItems.removeAll { it.itemId == id }
        updateUI()
    }

    /**
     * Remove menu items associated with [groupId].
     */
    override fun removeGroup(groupId: Int) {
        _menuItems.removeAll { groupId == it.groupId }
        updateUI()
    }

    /**
     * Add a new menu item.
     */
    override fun add(
        groupId: Int,
        itemId: Int,
        order: Int,
        title: CharSequence?
    ): MenuItem = addItemView(groupId, itemId, order).apply {
        _menuItems.add(this)
        _menuItems.sortBy { it.order }
        updateUI()
    }
}
