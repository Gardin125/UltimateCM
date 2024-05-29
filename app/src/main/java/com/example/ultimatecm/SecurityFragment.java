package com.example.ultimatecm;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

public class SecurityFragment extends Fragment {

    Button btnPasswordReset, btnChangeEmail;
    FirebaseUser currentUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_security, container, false);

        btnPasswordReset = view.findViewById(R.id.btnPasswordReset);
        btnChangeEmail = view.findViewById(R.id.btnChangeEmail);

        currentUser = DBManager.getAuth().getCurrentUser();

        btnPasswordReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentUser != null && currentUser.getEmail() != null) {
                    String email = currentUser.getEmail();
                    FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getContext(), "Password reset email sent.", Toast.LENGTH_SHORT).show();
                                } else {
                                    if (task.getException() instanceof FirebaseAuthInvalidUserException) {
                                        showDialog("No user found.", "No user found with this email.", "okay");
                                    } else {
                                        showDialog("Failed to send password reset email. ", "This error occur because of " +  task.getException().getMessage(), "Okay");
                                    }
                                }
                            });
                } else {

                }
            }
        });


        btnChangeEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChangeEmailDialog();
            }
        });

        return view;
    }

    private void showChangeEmailDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.change_email_dialog, null);
        builder.setView(dialogView);

        EditText editTextNewEmail = dialogView.findViewById(R.id.editTextNewEmail);
        Button btnSubmitEmailChange = dialogView.findViewById(R.id.btnSubmitEmailChange);

        AlertDialog dialog = builder.create();

        btnSubmitEmailChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newEmail = editTextNewEmail.getText().toString().trim();
                if (newEmail.isEmpty()) {
                    showDialog("Field is empty. ", "Make sure your wrote new email. ", "Okay");
                } else if (!emailIsAlreadyExists(newEmail)) {
                    showDialog("Email is already exists", "Please use other mail.", "Okay");
                } else {
                    currentUser.updateEmail(newEmail)
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    showDialog("Updated Successfully! ", "Email update completed. ", "Done");
                                    dialog.dismiss();
                                }
                            });
                }
            }
        });

        dialog.show();
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

    private boolean emailIsAlreadyExists(String email) {
        for (Person p: DataManager.getPeople()) {
            if (p.getEmail().equalsIgnoreCase(email)) {
                return true;
            }
        }
        return false;
    }
}
