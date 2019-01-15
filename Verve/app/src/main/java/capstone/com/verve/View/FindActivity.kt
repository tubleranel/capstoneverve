package capstone.com.verve.View

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.ImageView
import capstone.com.verve.R

class FindActivity : AppCompatActivity() {

    var imgForum: ImageView? = null
    var imgProfile: ImageView? = null
    var imgReminders: ImageView? = null
    var imgChat: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_find)

        imgReminders = findViewById(R.id.img_reminders)
        imgForum = findViewById(R.id.img_home)
        imgProfile = findViewById(R.id.img_profile)
        imgChat = findViewById(R.id.img_messages)

        imgForum?.setOnClickListener {
            showForum()
        }

        imgProfile?.setOnClickListener {
            showProfile()
        }
    }

    private fun showForum() {
        val intent = Intent(this@FindActivity, ForumActivity::class.java)
        startActivity(intent)
    }

    private fun showProfile() {
        val intent = Intent(this@FindActivity, ProfileActivity::class.java)
        startActivity(intent)
    }
}
