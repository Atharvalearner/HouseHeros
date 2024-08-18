package com.example.constructionmodel;

        import android.content.Context;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.BaseAdapter;
        import android.widget.RatingBar;
        import android.widget.TextView;

        import java.text.SimpleDateFormat;
        import java.util.List;
        import java.util.Locale;

public class ReviewAdapter extends BaseAdapter {

    private Context context;
    private List<Review> reviewList;

    public ReviewAdapter(Context context, List<Review> reviewList) {
        this.context = context;
        this.reviewList = reviewList;
    }

    @Override
    public int getCount() {
        return reviewList.size();
    }

    @Override
    public Object getItem(int position) {
        return reviewList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.review_item, parent, false);
        }

        Review review = (Review) getItem(position);

        TextView tvUserName = convertView.findViewById(R.id.tvUserName);
        TextView tvReviewText = convertView.findViewById(R.id.tvReviewText);
        RatingBar rbReviewRating = convertView.findViewById(R.id.rbReviewRating);
        TextView tvReviewDate = convertView.findViewById(R.id.tvReviewDate);

        tvUserName.setText(review.getUserName());
        tvReviewText.setText(review.getReviewText());
        rbReviewRating.setRating(review.getRating());

        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        tvReviewDate.setText(sdf.format(review.getTimestamp()));

        return convertView;
    }
}
