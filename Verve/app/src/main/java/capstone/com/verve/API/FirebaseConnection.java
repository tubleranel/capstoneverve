package capstone.com.verve.API;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseConnection {
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;
    DatabaseReference profileReference;
    String currentUserId;

    public FirebaseAuth getFirebaseAuth() {
        return firebaseAuth = FirebaseAuth.getInstance();
    }

    public FirebaseUser getFirebaseUser() {
        return firebaseUser = getFirebaseAuth().getCurrentUser();
    }

    public String getCurrentUser(){
        return currentUserId = getFirebaseUser().getUid();
    }

    public FirebaseDatabase getFirebaseDatabase() {
        return firebaseDatabase = FirebaseDatabase.getInstance();
    }

    public DatabaseReference getFirebaseDatabaseReference(){
        return databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    public DatabaseReference getProfileReference(String node) {
        return profileReference =getFirebaseDatabaseReference().child(node).child(getCurrentUser());
    }


}
