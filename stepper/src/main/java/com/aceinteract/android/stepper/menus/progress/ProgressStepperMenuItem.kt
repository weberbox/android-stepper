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
package com.aceinteract.android.stepper.menus.progress

import android.view.MenuItem
import com.aceinteract.android.stepper.menus.base.StepperMenuItem

/**
 * Menu item for [ProgressStepperMenu].
 */
public class ProgressStepperMenuItem(
    id: Int,
    groupId: Int = 0,
    order: Int = 0
) : StepperMenuItem(id, groupId, order) {

    /**
     * Do nothing since items don't have views.
     */
    override fun setTitle(title: CharSequence?): MenuItem = this

    /**
     * Do nothing since items don't have views.
     */
    override fun setTitle(title: Int): MenuItem = this

    /**
     * Returns an empty string since items don't have views.
     */
    override fun getTitle(): CharSequence = ""

    /**
     * Do nothing since items don't have views.
     */
    override fun getTitleCondensed(): CharSequence = ""
}
