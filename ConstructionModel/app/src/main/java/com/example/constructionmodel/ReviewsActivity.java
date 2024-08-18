package com.example.constructionmodel;

        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.ListView;
        import android.widget.RatingBar;
        import android.widget.Toast;

        import androidx.annotation.NonNull;
        import androidx.appcompat.app.AppCompatActivity;

        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.ValueEventListener;

        import java.util.ArrayList;
        import java.util.List;

public class ReviewsActivity extends AppCompatActivity {

    private ListView lvReviews;
    private EditText etReviewText;
    private RatingBar rbReviewRating;
    private Button btnSubmitReview;
    private List<Review> reviewList;
    private ReviewAdapter reviewAdapter;
    private DatabaseReference reviewsRef;
    private String workerId = "workerIdExample";  // Replace with the actual worker ID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);

        lvReviews = findViewById(R.id.lvReviews);
        etReviewText = findViewById(R.id.etReviewText);
        rbReviewRating = findViewById(R.id.rbReviewRating);
        btnSubmitReview = findViewById(R.id.btnSubmitReview);

        reviewList = new ArrayList<>();
        reviewAdapter = new ReviewAdapter(this, reviewList);
        lvReviews.setAdapter(reviewAdapter);

        reviewsRef = FirebaseDatabase.getInstance().getReference("workers").child(workerId).child("reviews");

        // Fetch reviews
        fetchReviews();

        btnSubmitReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitReview();
            }
        });
    }

    private void fetchReviews() {
        reviewsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                reviewList.clear();
                for (DataSnapshot reviewSnapshot : dataSnapshot.getChildren()) {
                    Review review = reviewSnapshot.getValue(Review.class);
                    reviewList.add(review);
                }
                reviewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ReviewsActivity.this, "Failed to load reviews.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void submitReview() {
        String reviewText = etReviewText.getText().toString().trim();
        float rating = rbReviewRating.getRating();
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String userName = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();

        if (!reviewText.isEmpty() && rating > 0) {
            String reviewId = reviewsRef.push().getKey();
            Review review = new Review(userId, userName, workerId, reviewText, rating, System.currentTimeMillis());

            if (reviewId != null) {
                reviewsRef.child(reviewId).setValue(review);
                Toast.makeText(this, "Review submitted!", Toast.LENGTH_SHORT).show();
                etReviewText.setText("");
                rbReviewRating.setRating(0);
            }
        } else {
            Toast.makeText(this, "Please enter a review and rating.", Toast.LENGTH_SHORT).show();
        }
    }
}
