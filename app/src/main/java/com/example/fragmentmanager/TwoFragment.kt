package com.example.fragmentmanager

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.result.ActivityResultCallback
import com.example.note.RegisterFragment
import com.model.fragmentmanager.contracts.StartFragmentForResult
import com.model.fragmentmanager.contracts.bean.FragmentResult
import com.model.fragmentmanager.supper.ActivityFragment

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TwoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@RegisterFragment
class TwoFragment : ActivityFragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val inflate = inflater.inflate(R.layout.fragment_two, container, false)
        inflate.findViewById<TextView>(R.id.tv_click).setOnClickListener {
            val intent = Intent(requireContext(), OneFragment::class.java)
            intent.putExtra("asda", "sadasdasdas")
            startFragment(intent)
        }
        return inflate;
    }

    override fun params(params: MutableMap<String, Any>?) {
        super.params(params)
        //TODO 携带返回值跳转方式
//        val registerForFragmentResult = registerForFragmentResult(
//            StartFragmentForResult()
//        ) { it ->
//            //返回值在这
//        }
//        val intent = Intent(requireContext(), OneFragment::class.java)
//        intent.putExtra("asda", "sadasdasdas")
//        registerForFragmentResult.launch(intent)

//        startFragmentForResult(intent, 100)

//        startFragmentForResult(intent, 100, { it ->
//            //返回值
//        })
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TwoFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TwoFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onFragmentResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onFragmentResult(requestCode, resultCode, data)
    }
}