package com.example.ultimatecm;

import static com.example.ultimatecm.R.drawable.*;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

public class SecurityFragment extends Fragment {

    Button btnPasswordReset;
    FirebaseUser currentUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_security, container, false);

        btnPasswordReset = view.findViewById(R.id.btnPasswordReset);
        btnPasswordReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentUser != null && currentUser.getEmail() != null) {
                    String email = currentUser.getEmail();
                    FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    showDialog("Password reset email sent.", "Password reset email sent successfully", "Okay");
                                } else {
                                    if (task.getException() instanceof FirebaseAuthInvalidUserException) {
                                        showDialog("No user found.", "No user found with this email.", "Okay");
                                    } else {
                                        showDialog("Failed to send password reset email.", "Error: " + task.getException().getMessage(), "Okay");
                                    }
                                }
                            });
                }
            }
        });
        return view;
    }


    private void showDialog(String title, String msg, String positiveButton) {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getContext());
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setPositiveButton(positiveButton, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

}
