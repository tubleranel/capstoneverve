package capstone.com.verve.View.Fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import capstone.com.verve.Interface.AcceptListener
import capstone.com.verve.Interface.ShowPostListener

import capstone.com.verve.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ForumViewPostFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ForumViewPostFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class ForumViewPostFragment : DialogFragment(), AcceptListener {


    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

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
        return inflater.inflate(R.layout.fragment_forum_view_post, container, false)
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    override fun onSubmit() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    lateinit var acceptListen: AcceptListener
    fun setListener(accept: AcceptListener) {
        acceptListen = accept
    }

    companion object {
        fun newInstance(title: String): ForumViewPostFragment {
            val frag = ForumViewPostFragment()
            val args = Bundle()
            args.putString("title", title)
            frag.arguments = args
            return frag
        }
    }

}
