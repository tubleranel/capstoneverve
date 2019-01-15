package capstone.com.verve.View.Fragments

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import capstone.com.verve.API.FirebaseConnection
import capstone.com.verve.Interface.AcceptListener
import capstone.com.verve.Presenter.Posts
import capstone.com.verve.Presenter.UserDetails
import capstone.com.verve.Presenter.UserPosts
import capstone.com.verve.Presenter.Users
import capstone.com.verve.R
import capstone.com.verve.View.*
import capstone.com.verve.View.Adapters.ForumPagerAdapter
import capstone.com.verve.View.Adapters.ForumRecyclerViewAdapter
import com.firebase.ui.database.FirebaseRecyclerAdapter
import kotlinx.android.synthetic.main.fragment_forum_following.*
import kotlinx.android.synthetic.main.item_post_forum.view.*
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_forum.*

class ForumHomeFragment : Fragment(), AcceptListener {

    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null
    lateinit var mPostsViewHolder: FirebaseRecyclerAdapter<UserPosts, PostsViewHolder>
    lateinit var mRecyclerView: RecyclerView
    lateinit var mDatabase: DatabaseReference
    private var postRef: DatabaseReference? = null
    private var userRef: DatabaseReference? = null
    private var auth: FirebaseAuth? = null
    internal var posts = Posts()
    internal var firebaseConnection = FirebaseConnection()

    private var editComment: EditText? = null

    var imgComment: ImageButton? = null

    var commentImage: ImageButton? = null
    var txtName: TextView? = null

    private var postId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var rootView = inflater.inflate(R.layout.fragment_forum_following, container, false)
        mRecyclerView = rootView.findViewById(R.id.followingRecyclerView)
        txtName = rootView.findViewById(R.id.txtName)



        return rootView
    }

    override fun onStart() {
        super.onStart()
        mPostsViewHolder.startListening()
    }

    override fun onStop() {
        super.onStop()
        mPostsViewHolder.stopListening()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        mRecyclerView.hasFixedSize()
        var layoutManager = LinearLayoutManager(context)
        mRecyclerView.layoutManager = layoutManager

        //firebase recyclerview
        mDatabase = FirebaseDatabase.getInstance().reference
        val postsQuery = mDatabase.child("Posts")
        val postsOptions =
            FirebaseRecyclerOptions.Builder<UserPosts>().setQuery(postsQuery, UserPosts::class.java).build()

        mPostsViewHolder = object : FirebaseRecyclerAdapter<UserPosts, PostsViewHolder>(postsOptions) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_post_forum, parent, false)

                txtName = view.findViewById(R.id.txtName)
                imgComment = view.findViewById(R.id.img_comment)


                userRef = FirebaseDatabase.getInstance().reference.child("Users")
                postRef = FirebaseDatabase.getInstance().reference.child("Posts")
                auth = FirebaseAuth.getInstance()


                return PostsViewHolder(view)
            }

            override fun onBindViewHolder(holder: PostsViewHolder, position: Int, model: UserPosts) {
                holder.bind(getItem(position))

                txtName?.setOnClickListener {
                    postId = getRef(position).key!!
                    postRef!!.child(postId).addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            val userId = dataSnapshot.child("uid").getValue(String::class.java)
                            if(userId.equals(firebaseConnection.currentUser)){
                                val intent = Intent(this@ForumHomeFragment.context, ProfileActivity::class.java)
                                startActivity(intent)
                            }else{
                                Toast.makeText(context, "Another Profile", Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onCancelled(databaseError: DatabaseError) {

                        }
                    })
                    
                }

                imgComment?.setOnClickListener {
                    postId = getRef(position).key!!
                    val intent = Intent(this@ForumHomeFragment.context, ForumViewPostCommentsActivity::class.java)
                    intent.putExtra("postId", postId)
                    startActivity(intent)

                }
            }
        }

        mRecyclerView.adapter = mPostsViewHolder
    }

    class PostsViewHolder(mView: View) : RecyclerView.ViewHolder(mView) {
        fun bind(post: UserPosts) = with(itemView) {
            txtCancerType?.text = post.postCateg
            txtDate?.text = post.datePost


            if (post.middlename.isEmpty()) {
                txtName?.text = post.firstname.plus(" " + post.lastname)
            } else {
                txtName?.text = post.firstname.plus(" " + post.middlename[0] + ".").plus(" " + post.lastname)
            }

            txtPostDetails?.text = post.postDescription
            txtPostTitle?.text = post.postTitle
            txtTime?.text = post.timePost

        }
    }

    private fun sendToOwnProfile(context: Context) {
        val intent = Intent(context, ProfileActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        context.startActivity(intent)

    }

    /*//Search Filter
    fun getFilter() : Filter {
        return object: Filter() {
            override fun performFiltering(charSequence:CharSequence) : FilterResults {
                val charString = charSequence.toString()
                if (charString.isEmpty())
                {
                    itemsList = items
                }
                else
                {
                    val filteredList = ArrayList<UserPosts>()
                    for (row in items)
                    {
                        if (row.uName.toLowerCase().contains(charString.toLowerCase()))
                        {
                            filteredList.add(row)
                        }
                        if (row.uDate.toLowerCase().contains(charString.toLowerCase()))
                        {
                            filteredList.add(row)
                        }
                        if (row.uTime.toLowerCase().contains(charString.toLowerCase()))
                        {
                            filteredList.add(row)
                        }
                        if (row.postTitle.toLowerCase().contains(charString.toLowerCase()))
                        {
                            filteredList.add(row)
                        }
                        if (row.postDetails.toLowerCase().contains(charString.toLowerCase()))
                        {
                            filteredList.add(row)
                        }
                        if (row.lastPerson.toLowerCase().contains(charString.toLowerCase()))
                        {
                            filteredList.add(row)
                        }
                        if (row.lastComment.toLowerCase().contains(charString.toLowerCase()))
                        {
                            filteredList.add(row)
                        }
                        if (row.dateComment.toLowerCase().contains(charString.toLowerCase()))
                        {
                            filteredList.add(row)
                        }
                        if (row.timeComment.toLowerCase().contains(charString.toLowerCase()))
                        {
                            filteredList.add(row)
                        }
                    }
                    itemsList = filteredList
                }

                val filterResults = FilterResults()
                filterResults.values = itemsList
                return filterResults
            }

            override fun publishResults(charSequence:CharSequence, filterResults: FilterResults) {
                itemsList = filterResults.values as ArrayList<UserPosts>
                // refresh the list with filtered data
                notifyDataSetChanged()
            }
        }
    }
*/

    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    override fun onSubmit() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ForumHomeFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
/*
    fun option(v: View) {
        if (v.id == R.id.btnCommentPost) {

        }
    }*/
}

