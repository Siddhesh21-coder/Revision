package com.codersarena.revision.repository;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.codersarena.revision.model.Image;
import com.codersarena.revision.model.SubTopicM;
import com.codersarena.revision.model.SubjectGroup;
import com.codersarena.revision.model.Topic;
import com.codersarena.revision.model.User;
import com.codersarena.revision.views.DisplayActivity;
import com.codersarena.revision.views.GroupActivity;
import com.codersarena.revision.views.SubTopic;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Repository {
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestore;
    private FirebaseUser user;
    String userName1="";

    private FirebaseDatabase database;
    private DatabaseReference reference;
    private MutableLiveData<List<SubjectGroup>> mutableSubject;

    private MutableLiveData<List<Topic>> topicList;
    private MutableLiveData<List<SubTopicM>> subTopicList;
    public Repository() {
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Users");
        firebaseAuth = FirebaseAuth.getInstance();
        this.mutableSubject = new MutableLiveData<>();
        this.topicList = new MutableLiveData<>();
        this.subTopicList = new MutableLiveData<>();
        firestore = FirebaseFirestore.getInstance();
    }

    public void logEmailPassword(Context context, String email, String password) {
        if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password))
        {
            Log.v("TAGI","Called");
            firebaseAuth.signInWithEmailAndPassword(email,password).addOnSuccessListener(
                    new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Toast.makeText(context,"Hello",Toast.LENGTH_SHORT).show();
                            user = firebaseAuth.getCurrentUser();
                            Intent i = new Intent(context, GroupActivity.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(i);


                        }
                    }
            );

        }


    }

    public void createUser(Context context,String email,String password, String name){
        if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(name))
        {
            firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        createUserDb(email,name);
                        Toast.makeText(context,"Happy Birthday",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

    private void createUserDb(String email, String name) {
        User user1 = new User(email,name);
//        String randomKey = reference.push().getKey();
        reference.child(firebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user1);

    }

    public void saveToDb(Context context, String groupName, String name, String link, ArrayList<Image> stringUrl)
    {
        Map<String,Object> data = new HashMap<>();
        data.put("Name",name);
        data.put("Link",link);
        data.put("Images",stringUrl);
        String email = firebaseAuth.getCurrentUser().getEmail();
        firestore.collection(groupName+" "+email).add(data).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Intent i = new Intent(context, DisplayActivity.class);
                context.startActivity(i);
            }
        });
    }
    public MutableLiveData<List<SubTopicM>> getSubTopicMutableList(String groupName)
    {
        List<SubTopicM> subTopicMList = new ArrayList<>();

        DatabaseReference referenceS = database.getReference().child("SubTopic/"+firebaseAuth.getCurrentUser().getUid()+"/"+groupName);
        referenceS.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                subTopicMList.clear();
                for(DataSnapshot snapshot1: snapshot.getChildren())
                {
                    SubTopicM subtop = snapshot1.getValue(SubTopicM.class);
                    subTopicMList.add(subtop);

                }
                subTopicList.postValue(subTopicMList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return subTopicList;
    }
    public MutableLiveData<List<Topic>> getTopicList(String subjectName) {
        MutableLiveData<List<Topic>> topic1 = new MutableLiveData<>();
        List<Topic> topic = new ArrayList<>();
        Log.v("CollectionName",subjectName);

        String email = firebaseAuth.getCurrentUser().getEmail();
        firestore.collection(subjectName+" "+email).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful())
                {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Topic topic2 = document.toObject(Topic.class);
                        Log.d(TAG, document.getId() + " => " + document.getData());
                        topic.add(topic2);

                    }
                    topic1.postValue(topic);
                }

            }
        });
        return topic1;

    }

    public MutableLiveData<List<SubjectGroup>> getGroupMutableList(){
        List<SubjectGroup> group = new ArrayList<>();

        DatabaseReference referenceGroup = database.getReference().child("Groups/"+firebaseAuth.getCurrentUser().getUid());
        referenceGroup.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                group.clear();
                for(DataSnapshot dataSnapshot: snapshot.getChildren())
                {
                    SubjectGroup subjectGroup = dataSnapshot.getValue(SubjectGroup.class);
                    group.add(subjectGroup);
                }
                mutableSubject.postValue(group);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }

        });
        return mutableSubject;
    }




}
