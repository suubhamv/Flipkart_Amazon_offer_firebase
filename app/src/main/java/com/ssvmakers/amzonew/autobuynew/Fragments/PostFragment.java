package com.ssvmakers.amzonew.autobuynew.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.ssvmakers.amzonew.autobuynew.Model.HomeOfferModel;
import com.ssvmakers.amzonew.autobuynew.R;
import com.ssvmakers.amzonew.autobuynew.Utils.Validatation;

import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

public class PostFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private Button uploadButton;
    private StorageReference mStorageRef;
    private OnFragmentInteractionListener mListener;
    private Button postButton;
    private TextInputEditText productUrl, productSource, newPrice, oldProce;
    private TextInputEditText productName;
    private Uri imageUrl;
    private DatabaseReference mDatabase;
    public static final String Firebase_Server_URL = "https://amazonscript-5f7ed.firebaseio.com/";
    private static final int PICK_IMAGE_REQUEST = 21;
    private CircleImageView productImage;
    private Uri filePath;
    private ProgressDialog PDialog;
    private StorageReference storageRef;

    public PostFragment() {

    }

    public static PostFragment newInstance(String param1, String param2) {
        PostFragment fragment = new PostFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_post, container, false);
        PDialog = new ProgressDialog(getContext());
        PDialog.setCancelable(false);
        PDialog.setTitle("AMZO");
        PDialog.setMessage("Please Wait Posting");
        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageRef = storage.getReferenceFromUrl("gs://amazonscript-5f7ed.appspot.com");
        mDatabase = FirebaseDatabase.getInstance().getReference("Amzo_App");
        uploadButton = view.findViewById(R.id.uploadimage);
        postButton = view.findViewById(R.id.postbutton);
        productName = view.findViewById(R.id.productname);
        productSource = view.findViewById(R.id.src);
        productImage = view.findViewById(R.id.productPic);
        newPrice = view.findViewById(R.id.new_price);
        oldProce = view.findViewById(R.id.oldprice);
        productUrl = view.findViewById(R.id.producturl);
        productUrl.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Patterns.WEB_URL.matcher(productUrl.getText()).matches();
                if (!Patterns.WEB_URL.matcher(productUrl.getText()).matches()) {
                    productUrl.setError("invalid Url");
                } else {
                    try {
                        URL address = new URL("http://amzn.to/2jpMdpO");


                        //Connect & check for the location field
                        HttpURLConnection connection = null;
                        try {
                            connection = (HttpURLConnection) address.openConnection(Proxy.NO_PROXY);
                            connection.setInstanceFollowRedirects(false);
                            connection.connect();
                            String expandedURL = connection.getHeaderField("Location");
                            if (expandedURL != null) {
                                URL expanded = new URL(expandedURL);
                                address = expanded;
                            }
                        } catch (Throwable e) {
                            System.out.println("Problem while expanding {}" + address + e);
                        } finally {
                            if (connection != null) {
                                System.out.println(connection.getInputStream());
                            }
                        }
                        Toast.makeText(getContext(), connection.getInputStream().toString(), Toast.LENGTH_SHORT).show();
                        System.out.println("Original URL" + address);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        });
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadFile();
            }
        });
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitPost();
            }
        });
        return view;
    }

    private void submitPost() {
        if (!Validatation.hasText(productName)) {
            productName.setError("Please Enter Valid Product Name");
        } else if (!Validatation.hasText(productSource)) {
            productSource.setError("Please Enter Valid Prodct Site");
        } else if (!Validatation.hasText(productUrl)) {
            productUrl.setError("Please Enter Valid Product URL");
        } else if (!Validatation.hasText(newPrice)) {
            newPrice.setError("Please Enter Valid Product New Price");
        } else if (!Validatation.hasText(oldProce)) {
            oldProce.setError("Please Enter Valid Product Old Price");
        } else if (imageUrl.equals(null)) {
            uploadButton.setError("Please Enter Upload image of Product");
        } else if (!Patterns.WEB_URL.matcher(productUrl.getText().toString()).matches()) {
            productUrl.setError("Please Enter Valid Product URL");
        } else {
            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Amzo_App").child("homeoffers");
            String offerid = mDatabase.push().getKey();
            HomeOfferModel model = new HomeOfferModel();



            model.setProductname(productName.getText().toString());
            //model.setNewprice(Long.parseLong(newPrice.getText().toString()));
            model.setNewprice(Long.parseLong(newPrice.getText().toString()));
            //model.setOldprice(Long.parseLong(oldProce.getText().toString()));
            model.setOldprice(oldProce.getText().toString());
            model.setSrc(productSource.getText().toString());
            model.setimageurl(imageUrl.toString());
            mDatabase.child(offerid).setValue(model);
        }

    }

    private void uploadFile() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();

            try {
                //getting image from gallery
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
                //Setting image to ImageView
                productImage.setImageBitmap(bitmap);
                if (filePath != null) {
                    PDialog.show();

                    StorageReference childRef = storageRef.child("image.jpg");

                    //uploading the image
                    UploadTask uploadTask = childRef.putFile(filePath);

                    StorageTask<UploadTask.TaskSnapshot> taskSnapshotStorageTask = uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            PDialog.dismiss();


                            @SuppressWarnings("VisibleForTests") Uri firebaseImageUrl = taskSnapshot.getDownloadUrl();
                            imageUrl = firebaseImageUrl;
                            Toast.makeText(getContext(), "Upload successful", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            PDialog.dismiss();
                            Toast.makeText(getContext(), "Upload Failed -> " + e, Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(getContext(), "Select an image", Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
