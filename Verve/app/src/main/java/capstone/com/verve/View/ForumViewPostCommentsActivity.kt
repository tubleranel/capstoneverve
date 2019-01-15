package capstone.com.verve.View

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import capstone.com.verve.API.FirebaseConnection
import capstone.com.verve.Model.ForumData
import capstone.com.verve.Presenter.Posts
import capstone.com.verve.Presenter.UserComments
import capstone.com.verve.Presenter.UserPosts
import capstone.com.verve.R
import capstone.com.verve.R.id.*
import capstone.com.verve.View.Adapters.ForumPostCommentsRecyclerViewAdapter
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.*
import com.scottyab.aescrypt.AESCrypt
import kotlinx.android.synthetic.main.item_posts_comments.view.*
import org.jetbrains.anko.find
import org.w3c.dom.Text
import java.security.GeneralSecurityException
import java.util.ArrayList

class ForumViewPostCommentsActivity : AppCompatActivity() {

    var postId: String? = null
    internal var firebaseConnection = FirebaseConnection()
    internal var posts = Posts()

    var txtName: TextView? = null
    var txtCancerType: TextView? = null
    var txtDate: TextView? = null
    var txtTime: TextView? = null
    var txtPostTitle: TextView? = null
    var txtPostDetails: TextView? = null
    private var recyclerView: RecyclerView? = null

    /*FOR COMMENTS*/
    var commentUsername: TextView? = null
    var commentDate: TextView? = null
    var commentTime: TextView? = null
    var commentComment: TextView? = null
    var sendComment: ImageButton? = null
    var editComment: EditText? = null
    internal var postComment = Posts()
    lateinit var cDatabase: DatabaseReference
    /*FOR COMMENTS*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forum_view_post_comments)

        postId = intent.getStringExtra("postId")

        //for posts
        txtName = findViewById(R.id.txtName)
        txtCancerType = findViewById(R.id.txtCancerType)
        txtDate = findViewById(R.id.txtDate)
        txtTime = findViewById(R.id.txtTime)
        txtPostTitle = findViewById(R.id.txtPostTitle)
        txtPostDetails = findViewById(R.id.txtPostDetails)
        recyclerView = findViewById(R.id.commentRecyclerView)
        setPostDetails()

        /*FOR SENDING COMMENT*/
        sendComment = findViewById(R.id.btn_sendcomment)
        editComment = findViewById(R.id.editComment)

        sendComment?.setOnClickListener {

            postComment.saveComment(firebaseConnection.getProfileReference("Users"),firebaseConnection.firebaseAuth, postId,
                firebaseConnection.firebaseDatabaseReference.child("Posts"), this@ForumViewPostCommentsActivity, editComment)

        }
        /*FOR SENDING COMMENT*/

        /*FOR COMMENTS RECYCLERVIEW*/
        cDatabase = FirebaseDatabase.getInstance().reference
        val commentsQuery = cDatabase
            .child("Posts")
            .child(postId!!)
            .child("Comments")

        val commentsOptions =
            FirebaseRecyclerOptions.Builder<UserComments>()
                .setQuery(commentsQuery, UserComments::class.java)
                .setLifecycleOwner(this)
                .build()

        var layoutManager = LinearLayoutManager(this@ForumViewPostCommentsActivity)
        recyclerView?.layoutManager = layoutManager

        var mCommentsViewHolder = object : FirebaseRecyclerAdapter<UserComments, CommentsViewHolder>(commentsOptions) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentsViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_posts_comments, parent, false)

                commentUsername = view.findViewById(R.id.txt_name)
                commentDate = view.findViewById(R.id.txt_commentdate)
                commentTime = view.findViewById(R.id.txt_commenttime)
                commentComment = view.findViewById(R.id.txt_comment)

                return CommentsViewHolder(view)
            }

            override fun onBindViewHolder(holder: CommentsViewHolder, position: Int, model: UserComments) {
                holder.bind(model)

            }

        }

        recyclerView?.adapter = mCommentsViewHolder
        /*FOR COMMENTS RECYCLERVIEW*/
    }

    private fun logRecylerView() {

    }

    class CommentsViewHolder(val cView: View, var comments: UserComments? = null) : RecyclerView.ViewHolder(cView) {

        fun bind(comments: UserComments) = with(comments) {

            cView.txt_name?.text = comments.firstname
            cView.txt_commentdate.text = comments.date
            cView.txt_commenttime.text = comments.time
            cView.txt_comment.text = comments.comment
        }
    }

    fun setPostDetails(){
        val postQuery = firebaseConnection.firebaseDatabaseReference.child("Posts").child(postId!!)

        postQuery.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    var firstname: String= dataSnapshot.child("firstname").value!!.toString()
                    var middlename:String = dataSnapshot.child("middlename").value!!.toString()
                    var lastname: String = dataSnapshot.child("lastname").value!!.toString()
                    val postCateg = dataSnapshot.child("postCateg").value!!.toString()
                    val postDescription = dataSnapshot.child("postDescription").value!!.toString()
                    val postTitle = dataSnapshot.child("postTitle").value!!.toString()
                    val datepost = dataSnapshot.child("datePost").value!!.toString()
                    val timePost = dataSnapshot.child("timePost").value!!.toString()

                    if (middlename.isEmpty()) {
                        val nameNoMiddle = "$firstname $lastname"
                        txtName!!.setText(nameNoMiddle)
                    } else {
                        val nameWithMiddle = firstname + (" " + middlename[0] + ".") + " $lastname"
                        txtName!!.setText(nameWithMiddle)
                    }

                    txtCancerType!!.setText(postCateg)
                    txtDate!!.setText(datepost)
                    txtPostDetails!!.setText(postDescription)
                    txtPostTitle!!.setText(postTitle)
                    txtTime!!.setText(timePost)


                }
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        });

    }

    }

