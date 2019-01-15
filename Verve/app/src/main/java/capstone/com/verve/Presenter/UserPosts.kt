package capstone.com.verve.Presenter

class UserPosts {
    lateinit var datePost: String
    lateinit var firstname: String
    lateinit var middlename: String
    lateinit var lastname: String
    lateinit var postDescription: String
    lateinit var postTitle: String
    lateinit var postCateg: String
    lateinit var timePost: String
    lateinit var uid: String
//    lateinit var postId: String


    constructor() {

    }

    constructor(
        datePost: String,
        firstname: String,
        middlename: String,
        lastname: String,
        postDescription: String,
        postTitle: String,
        postCateg: String,
        timePost: String,
        uid: String
    ) {
        this.datePost = datePost
        this.firstname = firstname
        this.middlename = middlename
        this.lastname = lastname
        this.postDescription = postDescription
        this.postTitle = postTitle
        this.postCateg = postCateg
        this.timePost = timePost
        this.uid = uid
    }




}
