package com.example.citra_moblie.dao;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.citra_moblie.EditUserActivity;
import com.example.citra_moblie.HomeActivity;
import com.example.citra_moblie.LoginActivity;
import com.example.citra_moblie.RegisterUserActivity;
import com.example.citra_moblie.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.concurrent.atomic.AtomicBoolean;

public class UserDAO implements IUserDAO{
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private static Context context;
    private static UserDAO userDAO = null;
    private User user;

    private UserDAO(Context context) {
        UserDAO.context = context;
    }

    public static IUserDAO getInstance(Context context) {
        if (userDAO == null) {
            userDAO = new UserDAO(context);
        }
        return userDAO;
    }

    public void authUser(String email, String password, OnCompleteListener completeListener, ValueEventListener eventListener, boolean isLogged) {
        if (!isLogged) {
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(completeListener);
        }else{
            DatabaseReference userReference = FirebaseDatabase.getInstance().getReference();
            userReference.child("usuarios").child(auth.getCurrentUser().getUid()).addValueEventListener(eventListener);
        }
    }

    public void saveUser(OnCompleteListener completeListener, User newUser) {
        auth.createUserWithEmailAndPassword(newUser.getEmail(),
                newUser.getPassword()).addOnCompleteListener(completeListener);
    }

    public void saveImageUser(Activity activity, ImageView imageView, ValueEventListener eventListener, User editedUser) {
        // salvando imagem
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Bitmap userImage = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        userImage.compress(Bitmap.CompressFormat.JPEG, 100, baos);

        // converte pixels em uma matriz de bytes
        byte[] imageData = baos.toByteArray();

        // definindo nó
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference imageRef = storageReference.child("usersImages").child(user.getId());

        // objeto que controla upload
        UploadTask uploadTask = imageRef.putBytes(imageData);

        // tratando respostas
        uploadTask.addOnSuccessListener(activity, taskSnapshot -> {
            imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                editedUser.setImage(uri.toString());
                editUser(eventListener, editedUser);
            });
        });

        uploadTask.addOnFailureListener(activity, e -> {
            Toast.makeText(activity, "Não foi possível salvar imagem!", Toast.LENGTH_SHORT).show();
        });
    }

    public void editUser(ValueEventListener eventListener, User editedUser) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("usuarios").child(auth.getCurrentUser().getUid()).setValue(editedUser);
        reference.child("usuarios").child(auth.getCurrentUser().getUid()).addValueEventListener(eventListener);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
