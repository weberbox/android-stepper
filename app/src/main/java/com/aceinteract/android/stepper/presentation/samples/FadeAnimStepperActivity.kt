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
package com.aceinteract.android.stepper.presentation.samples

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.ui.setupActionBarWithNavController
import com.aceinteract.android.stepper.R
import com.aceinteract.android.stepper.StepperNavListener
import com.aceinteract.android.stepper.StepperNavigationView
import com.aceinteract.android.stepper.databinding.TabStepperActivityBinding
import com.aceinteract.android.stepper.models.StepperSettings
import com.aceinteract.android.stepper.presentation.steps.StepperViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ng.softcom.android.utils.ui.findNavControllerFromFragmentContainer
import ng.softcom.android.utils.ui.showToast

/**
 * Activity showing an sample usage of custom transition animations.
 */
@ExperimentalCoroutinesApi
class FadeAnimStepperActivity : AppCompatActivity(), StepperNavListener {

    private val viewModel: StepperViewModel by lazy { ViewModelProvider(this)[StepperViewModel::class.java] }

    private lateinit var binding: TabStepperActivityBinding

    /**
     * Setup stepper and activity.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = TabStepperActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onBackPressedDispatcher.addCallback(this,onBackPressedCallback)

        binding.stepper.initializeStepper()

        setSupportActionBar(binding.toolbar)

        // Setup Action bar for title and up navigation.
        setupActionBarWithNavController(findNavControllerFromFragmentContainer(binding.frameStepper.id))

        binding.buttonNext.setOnClickListener {
            binding.stepper.goToNextStep()
        }

        collectStateFlow()
    }

    private fun StepperNavigationView.initializeStepper() {
        viewModel.updateStepper(
            StepperSettings(
                widgetColor,
                textColor,
                textSize,
                iconSize
            )
        )

        stepperNavListener = this@FadeAnimStepperActivity
        setupWithNavController(findNavControllerFromFragmentContainer(binding.frameStepper.id)) {
            enter = android.R.anim.fade_in
            exit = android.R.anim.fade_out
            popEnter = android.R.anim.fade_in
            popExit = android.R.anim.fade_out
        }
    }

    private fun collectStateFlow() {
        viewModel.stepperSettings.onEach {
            binding.stepper.widgetColor = it.iconColor
            binding.stepper.textColor = it.textColor
            binding.stepper.textSize = it.textSize
            binding.stepper.iconSize = it.iconSize
        }.launchIn(lifecycleScope)
    }

    override fun onStepChanged(step: Int) {
        showToast("Step changed to: ${step + 1}")
        if (step == 3) {
            binding.buttonNext.setImageResource(R.drawable.ic_check)
        } else {
            binding.buttonNext.setImageResource(R.drawable.ic_right)
        }
    }

    override fun onCompleted() {
        showToast("Stepper completed")
    }

    /**
     * Use navigation controller to navigate up.
     */
    override fun onSupportNavigateUp(): Boolean =
        findNavControllerFromFragmentContainer(binding.frameStepper.id).navigateUp()

    /**
     * Navigate up when the back button is pressed.
     */
    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (binding.stepper.currentStep == 0) {
                finish()
            } else {
                findNavControllerFromFragmentContainer(binding.frameStepper.id).navigateUp()
            }
        }
    }
}
