package capstone.com.verve.View.Adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import capstone.com.verve.Model.ForumData
import capstone.com.verve.R
import kotlinx.android.synthetic.main.item_posts_comments.view.*


class ForumPostCommentsRecyclerViewAdapter( private var commentsList: MutableList<ForumData>) : RecyclerView.Adapter<ForumPostCommentsRecyclerViewAdapter.ViewHolder>(){

    var comments: MutableList<ForumData>? = commentsList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_posts_comments,parent, false)

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        /* holder.bind(transactionsList[position])*/
        holder.bind(comments?.get(position) ?: ForumData())
    }

    override fun getItemCount() = comments?.size ?: 0

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(comment: ForumData) = with(itemView) {
            txt_name.text = comment.uName //change to firstname,middlename,lastname
            txt_commentdate.text = comment.dateComment
            txt_commenttime.text = comment.timeComment
            txt_comment.text = comment.comments
        }
    }
}