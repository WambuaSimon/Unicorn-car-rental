package com.wizag.unicorn.ui.fragments

import android.app.ActionBar
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.github.zawadz88.materialpopupmenu.popupMenu
import com.wizag.unicorn.R
import kotlinx.android.synthetic.main.activity_cars.*
import kotlinx.android.synthetic.main.docs_layout.*
import kotlinx.android.synthetic.main.docs_layout.view.*
import kotlinx.android.synthetic.main.profile.*
import kotlinx.android.synthetic.main.profile.view.*
import kotlinx.android.synthetic.main.profile_fragment.view.*
import org.jetbrains.anko.toast


class ProfileFragment : Fragment() {
    private lateinit var viewOfLayout: View

    companion object {
        fun newInstance() = ProfileFragment()
    }

    private lateinit var viewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewOfLayout = inflater!!.inflate(R.layout.profile_fragment, container, false)



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            viewOfLayout.photoHeader.setTranslationZ(6.0F)
            viewOfLayout.photoHeader.invalidate()

        }

        if (viewOfLayout.license.drawable == null) {
//            license_card.visibility = View.GONE
            viewOfLayout.license.setImageResource(R.drawable.doc_sample)


        } else if (viewOfLayout.insurance.drawable == null) {
//            insurance_card.visibility = View.GONE
            viewOfLayout.insurance.setImageResource(R.drawable.doc_sample)
        } else if (viewOfLayout.national_id.drawable == null) {
//            national_id_card.visibility = View.GONE
            viewOfLayout.national_id.setImageResource(R.drawable.doc_sample)
        }

        viewOfLayout.upload_docs.setOnClickListener {
            onSingleSectionWithIconsClicked(viewOfLayout)
        }

//        tool bar
//        viewOfLayout.toolbar.setNavigationIcon(R.drawable.back)
//        viewOfLayout.toolbar.setNavigationOnClickListener {
//
//            activity?.onBackPressed()
//
//        }


        return viewOfLayout

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ProfileViewModel::class.java)
        // TODO: Use the ViewModel
    }


    fun onSingleSectionWithIconsClicked(view: View) {
        val popupMenu = popupMenu {
            section {
                item {
                    label = "License"
                    callback = {
                        //optional

                        license_card.visibility = View.VISIBLE
                    }
                }
                item {
                    label = "Insurance"

                    callback = {
                        //optional
                        insurance_card.visibility = View.VISIBLE
                    }
                }
                item {
                    label = "National ID card"
                    callback = {
                        national_id_card.visibility = View.VISIBLE
                    }


                }
            }
        }

        popupMenu.show(view.context, view)
    }
}


